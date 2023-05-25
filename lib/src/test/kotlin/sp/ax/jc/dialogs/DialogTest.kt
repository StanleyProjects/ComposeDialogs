package sp.ax.jc.dialogs

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
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
    @get:Rule
    val rule = createComposeRule()

    @Test
    fun fooTest() {
        val dialogTag = "foo"
        val message = "bar"
        val buttonTag = "baz"
        rule.setContent {
            var value by remember { mutableStateOf(false) }
            if (value) {
                Dialog(
                    modifier = Modifier.testTag(dialogTag),
                    onDismissRequest = {
                        value = false
                    },
                    message = message,
                )
            }
            Box(
                modifier = Modifier.fillMaxSize()
                    .testTag(buttonTag)
                    .clickable {
                        value = true
                    },
            )
        }
        rule.onNodeWithTag(dialogTag).assertDoesNotExist()
        rule.onNodeWithTag(buttonTag).performClick()
        rule.onNodeWithTag(dialogTag).assertIsDisplayed()
        Espresso.pressBack()
        rule.onNodeWithTag(dialogTag).assertDoesNotExist()
    }
}
