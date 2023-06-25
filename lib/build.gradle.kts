import com.android.build.gradle.api.BaseVariant
import sp.gx.core.Badge
import sp.gx.core.GitHub
import sp.gx.core.Markdown
import sp.gx.core.Maven
import sp.gx.core.camelCase
import sp.gx.core.check
import sp.gx.core.colonCase
import sp.gx.core.existing
import sp.gx.core.file
import sp.gx.core.filled
import sp.gx.core.kebabCase
import sp.gx.core.resolve

version = "0.0.1"

val maven = Maven.Artifact(
    group = "com.github.kepocnhh",
    id = rootProject.name,
)

val gh = GitHub.Repository(
    owner = "StanleyProjects",
    name = rootProject.name,
)

repositories {
    google()
    mavenCentral()
}

plugins {
    id("com.android.library")
    id("kotlin-android")
    id("org.gradle.jacoco")
    id("io.gitlab.arturbosch.detekt") version Version.detekt
}

fun BaseVariant.getVersion(): String {
    check(flavorName.isEmpty())
    return when (buildType.name) {
        "debug" -> kebabCase(version.toString(), "SNAPSHOT")
        "release" -> version.toString()
        else -> error("Build type \"${buildType.name}\" is not supported!")
    }
}

fun BaseVariant.getOutputFileName(extension: String): String {
    require(extension.isNotEmpty())
    return "${kebabCase(rootProject.name, getVersion())}.$extension"
}

fun checkReadme(variant: BaseVariant) {
    task(camelCase("check", variant.name, "Readme")) {
        doLast {
            val badge = Markdown.image(
                text = "version",
                url = Badge.url(
                    label = "version",
                    message = variant.getVersion(),
                    color = "2962ff",
                ),
            )
            val expected = setOf(
                badge,
                Markdown.link("Maven", Maven.Snapshot.url(maven.group, maven.id, variant.getVersion())),
                Markdown.link("Documentation", GitHub.pages(gh.owner, gh.name).resolve("doc").resolve(variant.getVersion())),
                "implementation(\"${colonCase(maven.group, maven.id, variant.getVersion())}\")",
            )
            rootDir.resolve("README.md").check(
                expected = expected,
                report = buildDir.resolve("reports/analysis/readme/${variant.name}/index.html"),
            )
        }
    }
}

jacoco.toolVersion = Version.jacoco

fun checkCoverage(variant: BaseVariant) {
    val taskUnitTest = tasks.getByName<Test>(camelCase("test", variant.name, "UnitTest"))
    val taskCoverageReport = task<JacocoReport>(camelCase("assemble", variant.name, "CoverageReport")) {
        dependsOn(taskUnitTest)
        reports {
            csv.required.set(false)
            html.required.set(true)
            xml.required.set(false)
        }
        sourceDirectories.setFrom(file("src/main/kotlin"))
        val dirs = fileTree(buildDir.resolve("tmp/kotlin-classes").resolve(variant.name))
        classDirectories.setFrom(dirs)
        executionData(buildDir.resolve("outputs/unit_test_code_coverage/${variant.name}UnitTest/${taskUnitTest.name}.exec"))
        doLast {
            val report = buildDir.resolve("reports/jacoco/$name/html/index.html")
            if (report.exists()) {
                println("Coverage report: ${report.absolutePath}")
            }
        }
    }
    task<JacocoCoverageVerification>(camelCase("check", variant.name, "Coverage")) {
        dependsOn(taskCoverageReport)
        violationRules {
            rule {
                limit {
                    minimum = BigDecimal(0.77)
                }
            }
        }
        classDirectories.setFrom(taskCoverageReport.classDirectories)
        executionData(taskCoverageReport.executionData)
    }
}

fun checkCodeQuality(variant: BaseVariant) {
    val configs = setOf(
        "comments",
        "common",
        "complexity",
        "coroutines",
        "empty-blocks",
        "exceptions",
        "naming",
        "performance",
        "potential-bugs",
        "style",
    ).map { config ->
        rootDir.resolve("buildSrc/src/main/resources/detekt/config/$config.yml")
            .existing()
            .file()
            .filled()
    }
    setOf(
        Triple("main", variant.sourceSets.flatMap { it.kotlinDirectories }.distinctBy { it.absolutePath }, ""),
        Triple("test", files("src/test/kotlin"), "UnitTest"),
    ).forEach { (type, sources, postfix) ->
        task<io.gitlab.arturbosch.detekt.Detekt>(camelCase("check", variant.name, "CodeQuality", postfix)) {
            jvmTarget = Version.jvmTarget
            setSource(sources)
            when (type) {
                "main" -> config.setFrom(configs)
                "test" -> {
                    val test = rootDir.resolve("buildSrc/src/main/resources/detekt/config/android/test.yml")
                        .existing()
                        .file()
                        .filled()
                    config.setFrom(configs + test)
                }
                else -> error("Type \"$type\" is not supported!")
            }
            reports {
                html {
                    required.set(true)
                    outputLocation.set(buildDir.resolve("reports/analysis/code/quality/${variant.name}/$type/html/index.html"))
                }
                md.required.set(false)
                sarif.required.set(false)
                txt.required.set(false)
                xml.required.set(false)
            }
            val detektTask = tasks.getByName<io.gitlab.arturbosch.detekt.Detekt>(camelCase("detekt", variant.name, postfix))
            classpath.setFrom(detektTask.classpath)
        }
    }
}

android {
    namespace = "sp.ax.jc.dialogs"
    compileSdk = Version.Android.compileSdk

    testOptions {
        unitTests {
            isIncludeAndroidResources = true
            all {
                // https://stackoverflow.com/a/71834475/4398606
                it.configure<JacocoTaskExtension> {
                    isIncludeNoLocationClasses = true
                    excludes = listOf("jdk.internal.*")
                }
            }
        }
    }

    defaultConfig {
        minSdk = Version.Android.minSdk
        manifestPlaceholders["appName"] = "@string/app_name"
    }

    buildTypes.getByName(testBuildType).isTestCoverageEnabled = true

    buildFeatures.compose = true

    composeOptions.kotlinCompilerExtensionVersion = Version.Android.compose

    libraryVariants.all {
        val variant = this
        val output = variant.outputs.single()
        check(output is com.android.build.gradle.internal.api.LibraryVariantOutputImpl)
        output.outputFileName = getOutputFileName("aar")
        checkReadme(variant)
        if (buildType.name == testBuildType) {
            checkCoverage(variant)
        }
        checkCodeQuality(variant)
        afterEvaluate {
            tasks.getByName<JavaCompile>(camelCase("compile", variant.name, "JavaWithJavac")) {
                targetCompatibility = Version.jvmTarget
            }
            tasks.getByName<org.jetbrains.kotlin.gradle.tasks.KotlinCompile>(camelCase("compile", variant.name, "Kotlin")) {
                kotlinOptions {
                    jvmTarget = Version.jvmTarget
                    freeCompilerArgs = freeCompilerArgs + setOf("-module-name", colonCase(maven.group, maven.id))
                }
            }
        }
    }
}

dependencies {
    implementation("androidx.compose.foundation:foundation:${Version.Android.compose}")
    camelCase("test", android.testBuildType, "Implementation")("org.robolectric:robolectric:4.10")
    camelCase("test", android.testBuildType, "Implementation")("androidx.compose.ui:ui-test-junit4:${Version.Android.compose}")
    camelCase("test", android.testBuildType, "Implementation")("androidx.compose.ui:ui-test-manifest:${Version.Android.compose}")
    camelCase("test", android.testBuildType, "Implementation")("androidx.test.espresso:espresso-core:3.5.1")
}
