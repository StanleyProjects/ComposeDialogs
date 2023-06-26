package sp.ax.jc.dialogs

import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.filterToOne
import androidx.compose.ui.test.hasTextExactly
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onChildren
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.performClick
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.window.DialogProperties
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import sp.ax.jc.dialogs.util.TestLayout

@RunWith(RobolectricTestRunner::class)
internal class DialogTextsTest {
    @get:Rule
    val rule = createComposeRule()

    @Test
    fun textsTest() {
        val dialog = "dialog"
        val show = "show"
        val hide = "hide"
        val message = "message"
        val button = "button"
        val buttons = setOf("foo", "bar", "baz")
        rule.setContent {
            val state = remember { mutableStateOf(false) }
            if (state.value) {
                Dialog(
                    modifier = Modifier.testTag(dialog),
                    onDismissRequest = {
                        state.value = false
                    },
                    properties = DialogProperties(
                        dismissOnBackPress = true,
                        dismissOnClickOutside = true,
                    ),
                    message = message,
                    messageStyle = DialogText(
                        modifier = Modifier,
                        textStyle = TextStyle(),
                    ),
                    buttonsAlignment = Alignment.End,
                    button = button to DialogText(
                        modifier = Modifier,
                        textStyle = TextStyle(),
                    ),
                    buttons = buttons.map {
                        it to DialogText(
                            modifier = Modifier,
                            textStyle = TextStyle(),
                        )
                    }.toTypedArray(),
                )
            }
            TestLayout(show, hide, state)
        }
        rule.onNodeWithTag(dialog).assertDoesNotExist()
        rule.onNodeWithTag(show).performClick()
        rule.onNodeWithTag(dialog).assertIsDisplayed()
        rule.onNodeWithTag(dialog).onChildren().filterToOne(hasTextExactly(message)).assertIsDisplayed()
        rule.onNodeWithTag(dialog).onChildren().filterToOne(hasTextExactly(button)).assertIsDisplayed()
        check(buttons.size > 1)
        check(!buttons.contains(button))
        buttons.forEach {
            rule.onNodeWithTag(dialog).onChildren().filterToOne(hasTextExactly(it)).assertIsDisplayed()
        }
        rule.onNodeWithTag(dialog).assertIsDisplayed()
        rule.onNodeWithTag(hide).performClick()
        rule.onNodeWithTag(dialog).assertDoesNotExist()
    }
}
