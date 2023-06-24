package sp.ax.jc.dialogs

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import androidx.test.espresso.Espresso
import androidx.test.espresso.action.ViewActions
import androidx.test.espresso.matcher.ViewMatchers
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner

@RunWith(RobolectricTestRunner::class)
internal class DialogCancelableShortTest {
    @get:Rule
    val rule = createComposeRule()

    @Test
    fun cancelableTest() {
        val show = "show"
        val hide = "hide"
        val message = "message"
        val button = "button"
        val buttons = setOf("foo", "bar", "baz")
        check(buttons.size > 1)
        check(!buttons.contains(button))
        rule.setContent {
            var value by remember { mutableStateOf(false) }
            if (value) {
                Dialog(
                    button = button to { value = false },
                    buttons = buttons.map {
                        it to { value = false }
                    }.toTypedArray(),
                    onDismissRequest = { value = false },
                    message = message,
                )
            }
            Column(Modifier.fillMaxSize()) {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .weight(1f)
                        .testTag(show)
                        .clickable {
                            value = true
                        },
                )
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .weight(1f)
                        .testTag(hide)
                        .clickable {
                            value = false
                        },
                )
            }
        }
        rule.onNodeWithText(message).assertDoesNotExist()
        rule.onNodeWithTag(show).performClick()
        rule.onNodeWithText(message).assertIsDisplayed()
        Espresso.pressBack()
        rule.onNodeWithText(message).assertDoesNotExist()
        rule.onNodeWithTag(show).performClick()
        rule.onNodeWithText(message).assertIsDisplayed()
        Espresso
            .onView(ViewMatchers.isRoot())
            .perform(ViewActions.click())
        rule.onNodeWithText(message).assertDoesNotExist()
        rule.onNodeWithTag(show).performClick()
        rule.onNodeWithText(message).assertIsDisplayed()
        rule.onNodeWithTag(hide).performClick()
        rule.onNodeWithText(message).assertDoesNotExist()
    }
}
