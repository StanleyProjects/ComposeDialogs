import com.android.build.gradle.api.BaseVariant
import sp.gx.core.GitHub
import sp.gx.core.Maven
import sp.gx.core.kebabCase

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

android {
    namespace = "sp.ax.jc.dialogs"
    compileSdk = Version.Android.compileSdk

    defaultConfig {
        minSdk = Version.Android.minSdk
        manifestPlaceholders["appName"] = "@string/app_name"
    }

    productFlavors {
        mapOf("version" to setOf("snapshot")).forEach { (dimension, flavors) ->
            flavorDimensions += dimension
            flavors.forEach { flavor ->
                create(flavor) {
                    this.dimension = dimension
                }
            }
        }
    }

    buildFeatures.compose = true

    composeOptions.kotlinCompilerExtensionVersion = Version.Android.compose

    libraryVariants.all {
        val variant = this
        val output = variant.outputs.single()
        check(output is com.android.build.gradle.internal.api.LibraryVariantOutputImpl)
        val versionName = when (variant.buildType.name) {
            "release" -> "${Version.Application.name}-${variant.flavorName}"
            else -> "${Version.Application.name}-${variant.name}"
        }
        output.outputFileName = "${rootProject.name}-${versionName}-${Version.Application.code}.aar"
        afterEvaluate {
            tasks.getByName<JavaCompile>("compile${variant.name.capitalize()}JavaWithJavac") {
                targetCompatibility = Version.jvmTarget
            }
            tasks.getByName<org.jetbrains.kotlin.gradle.tasks.KotlinCompile>("compile${variant.name.capitalize()}Kotlin") {
                kotlinOptions.jvmTarget = Version.jvmTarget
            }
        }
    }
}

dependencies {
    implementation("androidx.compose.foundation:foundation:${Version.Android.compose}")
}
