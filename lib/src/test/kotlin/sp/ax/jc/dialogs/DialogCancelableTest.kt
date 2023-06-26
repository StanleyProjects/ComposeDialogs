package sp.ax.jc.dialogs

import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.performClick
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.window.DialogProperties
import androidx.test.espresso.Espresso
import androidx.test.espresso.action.ViewActions
import androidx.test.espresso.matcher.ViewMatchers
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import sp.ax.jc.dialogs.util.TestLayout

@Suppress("StringLiteralDuplication")
@RunWith(RobolectricTestRunner::class)
internal class DialogCancelableTest {
    @get:Rule
    val rule = createComposeRule()

    @Test
    fun cancelableOnClickOutsideTest() {
        val dialog = "dialog"
        val show = "show"
        val hide = "hide"
        val message = "message"
        val button = "button"
        rule.setContent {
            val state = remember { mutableStateOf(false) }
            if (state.value) {
                Dialog(
                    modifier = Modifier.testTag(dialog),
                    onDismissRequest = {
                        state.value = false
                    },
                    properties = DialogProperties(
                        dismissOnBackPress = false,
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
                )
            }
            TestLayout(show, hide, state)
        }
        rule.onNodeWithTag(dialog).assertDoesNotExist()
        rule.onNodeWithTag(show).performClick()
        rule.onNodeWithTag(dialog).assertIsDisplayed()
        Espresso.pressBack()
        rule.onNodeWithTag(dialog).assertIsDisplayed()
        Espresso
            .onView(ViewMatchers.isRoot())
            .perform(ViewActions.click())
        rule.onNodeWithTag(dialog).assertDoesNotExist()
        rule.onNodeWithTag(show).performClick()
        rule.onNodeWithTag(dialog).assertIsDisplayed()
        rule.onNodeWithTag(hide).performClick()
        rule.onNodeWithTag(dialog).assertDoesNotExist()
    }

    @Test
    fun cancelableOnBackPressTest() {
        val dialog = "dialog"
        val show = "show"
        val hide = "hide"
        val message = "message"
        val button = "button"
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
                        dismissOnClickOutside = false,
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
                )
            }
            TestLayout(show, hide, state)
        }
        rule.onNodeWithTag(dialog).assertDoesNotExist()
        rule.onNodeWithTag(show).performClick()
        rule.onNodeWithTag(dialog).assertIsDisplayed()
        Espresso.pressBack()
        rule.onNodeWithTag(dialog).assertDoesNotExist()
        rule.onNodeWithTag(show).performClick()
        rule.onNodeWithTag(dialog).assertIsDisplayed()
        Espresso
            .onView(ViewMatchers.isRoot())
            .perform(ViewActions.click())
        rule.onNodeWithTag(dialog).assertIsDisplayed()
        rule.onNodeWithTag(hide).performClick()
        rule.onNodeWithTag(dialog).assertDoesNotExist()
    }

    @Test
    fun cancelableHideOnlyTest() {
        val dialog = "dialog"
        val show = "show"
        val hide = "hide"
        val message = "message"
        val button = "button"
        rule.setContent {
            val state = remember { mutableStateOf(false) }
            if (state.value) {
                Dialog(
                    modifier = Modifier.testTag(dialog),
                    onDismissRequest = {
                        state.value = false
                    },
                    properties = DialogProperties(
                        dismissOnBackPress = false,
                        dismissOnClickOutside = false,
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
                )
            }
            TestLayout(show, hide, state)
        }
        rule.onNodeWithTag(dialog).assertDoesNotExist()
        rule.onNodeWithTag(show).performClick()
        rule.onNodeWithTag(dialog).assertIsDisplayed()
        Espresso.pressBack()
        rule.onNodeWithTag(dialog).assertIsDisplayed()
        Espresso
            .onView(ViewMatchers.isRoot())
            .perform(ViewActions.click())
        rule.onNodeWithTag(dialog).assertIsDisplayed()
        rule.onNodeWithTag(hide).performClick()
        rule.onNodeWithTag(dialog).assertDoesNotExist()
    }

    @Test
    fun cancelableTest() {
        val dialog = "dialog"
        val show = "show"
        val hide = "hide"
        val message = "message"
        val button = "button"
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
                )
            }
            TestLayout(show, hide, state)
        }
        rule.onNodeWithTag(dialog).assertDoesNotExist()
        rule.onNodeWithTag(show).performClick()
        rule.onNodeWithTag(dialog).assertIsDisplayed()
        Espresso.pressBack()
        rule.onNodeWithTag(dialog).assertDoesNotExist()
        rule.onNodeWithTag(show).performClick()
        rule.onNodeWithTag(dialog).assertIsDisplayed()
        Espresso
            .onView(ViewMatchers.isRoot())
            .perform(ViewActions.click())
        rule.onNodeWithTag(dialog).assertDoesNotExist()
        rule.onNodeWithTag(show).performClick()
        rule.onNodeWithTag(dialog).assertIsDisplayed()
        rule.onNodeWithTag(hide).performClick()
        rule.onNodeWithTag(dialog).assertDoesNotExist()
    }
}
