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
import androidx.compose.ui.test.performClick
import androidx.test.espresso.Espresso
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner

@RunWith(RobolectricTestRunner::class)
internal class DialogTest {
    object Tag {
        const val dialog = "dialog"
        const val show = "show"
        const val hide = "hide"
    }

    @get:Rule
    val rule = createComposeRule()

    @Test
    fun showHideTest() {
        val message = "bar"
        rule.setContent {
            var value by remember { mutableStateOf(false) }
            if (value) {
                Dialog(
                    modifier = Modifier.testTag(Tag.dialog),
                    onDismissRequest = {
                        value = false
                    },
                    message = message,
                )
            }
            Column(Modifier.fillMaxSize()) {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .weight(1f)
                        .testTag(Tag.show)
                        .clickable {
                            value = true
                        },
                )
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .weight(1f)
                        .testTag(Tag.hide)
                        .clickable {
                            value = false
                        },
                )
            }
        }
        rule.onNodeWithTag(Tag.dialog).assertDoesNotExist()
        rule.onNodeWithTag(Tag.show).performClick()
        rule.onNodeWithTag(Tag.dialog).assertIsDisplayed()
        rule.onNodeWithTag(Tag.hide).performClick()
        rule.onNodeWithTag(Tag.dialog).assertDoesNotExist()
        rule.onNodeWithTag(Tag.show).performClick()
        rule.onNodeWithTag(Tag.dialog).assertIsDisplayed()
        Espresso.pressBack()
        rule.onNodeWithTag(Tag.dialog).assertDoesNotExist()
    }
}
