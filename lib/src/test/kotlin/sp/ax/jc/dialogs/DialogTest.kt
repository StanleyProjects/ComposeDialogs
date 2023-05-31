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
import androidx.compose.ui.test.filterToOne
import androidx.compose.ui.test.hasTextExactly
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onChildren
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

@RunWith(RobolectricTestRunner::class)
internal class DialogTest {
    @get:Rule
    val rule = createComposeRule()

    @Test
    fun showHideTest() {
        val dialog = "dialog"
        val show = "show"
        val hide = "hide"
        val title = "title"
        val message = "message"
        val button = "button"
        rule.setContent {
            var value by remember { mutableStateOf(false) }
            if (value) {
                Dialog(
                    modifier = Modifier.testTag(dialog),
                    onDismissRequest = {
                        value = false
                    },
                    properties = DialogProperties(
                        dismissOnBackPress = true,
                        dismissOnClickOutside = true,
                    ),
                    title = Dialog.Text(
                        modifier = Modifier,
                        value = title,
                        style = TextStyle(),
                    ),
                    message = Dialog.Text(
                        modifier = Modifier,
                        value = message,
                        style = TextStyle(),
                    ),
                    buttons = listOf(
                        Dialog.Text(
                            modifier = Modifier,
                            value = button,
                            style = TextStyle(),
                        ),
                    )
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
        rule.onNodeWithTag(dialog).assertDoesNotExist()
        rule.onNodeWithTag(show).performClick()
        rule.onNodeWithTag(dialog).assertIsDisplayed()
        rule.onNodeWithTag(hide).performClick()
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
    }

    @Test
    fun textsTest() {
        val dialog = "dialog"
        val show = "show"
        val hide = "hide"
        val title = "title"
        val message = "message"
        val button = "button"
        rule.setContent {
            var value by remember { mutableStateOf(false) }
            if (value) {
                Dialog(
                    modifier = Modifier.testTag(dialog),
                    onDismissRequest = {
                        value = false
                    },
                    title = Dialog.Text(
                        modifier = Modifier.testTag(title),
                        value = title,
                        style = TextStyle(),
                    ),
                    message = Dialog.Text(
                        modifier = Modifier.testTag(message),
                        value = message,
                        style = TextStyle(),
                    ),
                    buttons = listOf(
                        Dialog.Text(
                            modifier = Modifier
                                .testTag(button)
                                .clickable {
                                    value = false
                                },
                            value = button,
                            style = TextStyle(),
                        ),
                    )
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
        rule.onNodeWithTag(dialog).assertDoesNotExist()
        rule.onNodeWithTag(show).performClick()
        rule.onNodeWithTag(dialog).assertIsDisplayed()
        rule.onNodeWithTag(dialog).onChildren().filterToOne(hasTextExactly(title)).assertIsDisplayed()
        rule.onNodeWithTag(dialog).onChildren().filterToOne(hasTextExactly(message)).assertIsDisplayed()
        rule.onNodeWithTag(dialog).onChildren().filterToOne(hasTextExactly(button)).assertIsDisplayed()
        rule.onNodeWithTag(button).performClick()
        rule.onNodeWithTag(dialog).assertDoesNotExist()
    }

    @Test
    fun cancelableTest() {
        val dialog = "dialog"
        val show = "show"
        val hide = "hide"
        val title = "title"
        val message = "message"
        val button = "button"
        rule.setContent {
            var value by remember { mutableStateOf(false) }
            if (value) {
                Dialog(
                    modifier = Modifier.testTag(dialog),
                    onDismissRequest = {
                        value = false
                    },
                    properties = DialogProperties(
                        dismissOnBackPress = false,
                        dismissOnClickOutside = false,
                    ),
                    title = Dialog.Text(
                        modifier = Modifier,
                        value = title,
                        style = TextStyle(),
                    ),
                    message = Dialog.Text(
                        modifier = Modifier,
                        value = message,
                        style = TextStyle(),
                    ),
                    buttons = listOf(
                        Dialog.Text(
                            modifier = Modifier,
                            value = button,
                            style = TextStyle(),
                        ),
                    )
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
}
