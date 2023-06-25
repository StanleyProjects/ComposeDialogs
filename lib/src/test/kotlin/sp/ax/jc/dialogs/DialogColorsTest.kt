package sp.ax.jc.dialogs

import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.test.assert
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.filterToOne
import androidx.compose.ui.test.hasText
import androidx.compose.ui.test.isDialog
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onChildren
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import sp.ax.jc.dialogs.util.hasTextColor

@RunWith(RobolectricTestRunner::class)
internal class DialogColorsTest {
    @get:Rule
    val rule = createComposeRule()

    @Test
    fun colorsTest() {
        val message = "message"
        val button = "button"
        val buttons = setOf("foo", "bar", "baz")
        check(buttons.size > 1)
        check(!buttons.contains(button))
        var dialogStyle: DialogStyle? = null
        rule.setContent {
            dialogStyle = LocalDialogStyle.current
            Dialog(
                button = button to {},
                buttons = buttons.map { it to {} }.toTypedArray(),
                onDismissRequest = {},
                message = message,
            )
        }
        // todo background
        rule.onNode(isDialog())
            .assertExists()
            .assertIsDisplayed()
            .onChildren().also { children ->
                children.filterToOne(hasText(message))
                    .assertExists()
                    .assertIsDisplayed()
                    .assert(hasTextColor(checkNotNull(dialogStyle).foreground))
                children.filterToOne(hasText(button))
                    .assertExists()
                    .assertIsDisplayed()
                    .assert(hasTextColor(checkNotNull(dialogStyle).foreground))
                buttons.forEach {
                    children.filterToOne(hasText(it))
                        .assertExists()
                        .assertIsDisplayed()
                        .assert(hasTextColor(checkNotNull(dialogStyle).foreground))
                }
            }
    }

    @Test
    fun colorsCustomTest() {
        val message = "message"
        val button = "button"
        val buttons = setOf("foo", "bar", "baz")
        check(buttons.size > 1)
        check(!buttons.contains(button))
        var dialogStyle: DialogStyle? = null
        val foregroundColor: Color = Color.Green
        val backgroundColor: Color = Color.Red
        rule.setContent {
            dialogStyle = LocalDialogStyle.current
            Dialog(
                button = button to {},
                buttons = buttons.map { it to {} }.toTypedArray(),
                onDismissRequest = {},
                message = message,
                foregroundColor = foregroundColor,
                backgroundColor = backgroundColor,
            )
        }
        check(checkNotNull(dialogStyle).foreground != foregroundColor)
        check(checkNotNull(dialogStyle).background != backgroundColor)
        // todo background
        rule.onNode(isDialog())
            .assertExists()
            .assertIsDisplayed()
            .onChildren().also { children ->
                children.filterToOne(hasText(message))
                    .assertExists()
                    .assertIsDisplayed()
                    .assert(hasTextColor(foregroundColor))
                children.filterToOne(hasText(button))
                    .assertExists()
                    .assertIsDisplayed()
                    .assert(hasTextColor(foregroundColor))
                buttons.forEach {
                    children.filterToOne(hasText(it))
                        .assertExists()
                        .assertIsDisplayed()
                        .assert(hasTextColor(foregroundColor))
                }
            }
    }

    @Test
    fun colorsLocalTest() {
        val message = "message"
        val button = "button"
        val buttons = setOf("foo", "bar", "baz")
        check(buttons.size > 1)
        check(!buttons.contains(button))
        var dialogStyle: DialogStyle? = null
        val foregroundColor: Color = Color.Green
        val backgroundColor: Color = Color.Red
        rule.setContent {
            dialogStyle = LocalDialogStyle.current
            check(checkNotNull(dialogStyle).foreground != foregroundColor)
            check(checkNotNull(dialogStyle).background != backgroundColor)
            CompositionLocalProvider(
                LocalDialogStyle provides DialogStyle(
                    background = backgroundColor,
                    foreground = foregroundColor,
                )
            ) {
                dialogStyle = LocalDialogStyle.current
                Dialog(
                    button = button to {},
                    buttons = buttons.map { it to {} }.toTypedArray(),
                    onDismissRequest = {},
                    message = message,
                )
            }
        }
        check(checkNotNull(dialogStyle).foreground == foregroundColor)
        check(checkNotNull(dialogStyle).background == backgroundColor)
        // todo background
        rule.onNode(isDialog())
            .assertExists()
            .assertIsDisplayed()
            .onChildren().also { children ->
                children.filterToOne(hasText(message))
                    .assertExists()
                    .assertIsDisplayed()
                    .assert(hasTextColor(foregroundColor))
                children.filterToOne(hasText(button))
                    .assertExists()
                    .assertIsDisplayed()
                    .assert(hasTextColor(foregroundColor))
                buttons.forEach {
                    children.filterToOne(hasText(it))
                        .assertExists()
                        .assertIsDisplayed()
                        .assert(hasTextColor(foregroundColor))
                }
            }
    }
}
