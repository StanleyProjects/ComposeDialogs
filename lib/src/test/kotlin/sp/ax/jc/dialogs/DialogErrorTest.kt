package sp.ax.jc.dialogs

import androidx.compose.ui.test.junit4.createComposeRule
import org.junit.Assert.assertThrows
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner

@RunWith(RobolectricTestRunner::class)
internal class DialogErrorTest {
    @get:Rule
    val rule = createComposeRule()

    @Test
    fun errorEmptyMessageTest() {
        assertThrows(IllegalArgumentException::class.java) {
            rule.setContent {
                Dialog(
                    button = "" to {},
                    onDismissRequest = {},
                    message = "",
                )
            }
        }
    }

    @Test
    fun errorEmptyMessageButtonsTest() {
        assertThrows(IllegalArgumentException::class.java) {
            rule.setContent {
                Dialog(
                    button = "" to {},
                    "" to {},
                    onDismissRequest = {},
                    message = "",
                )
            }
        }
    }

    @Test
    fun errorEmptyButtonTest() {
        assertThrows(IllegalArgumentException::class.java) {
            rule.setContent {
                Dialog(
                    button = "" to {},
                    onDismissRequest = {},
                    message = "foo",
                )
            }
        }
    }

    @Test
    fun errorEmptyButtonButtonsTest() {
        assertThrows(IllegalArgumentException::class.java) {
            rule.setContent {
                Dialog(
                    button = "" to {},
                    "" to {},
                    onDismissRequest = {},
                    message = "foo",
                )
            }
        }
    }

    @Test
    fun errorEmptyButtonsTest() {
        assertThrows(IllegalArgumentException::class.java) {
            rule.setContent {
                Dialog(
                    button = "bar" to {},
                    "" to {},
                    onDismissRequest = {},
                    message = "foo",
                )
            }
        }
    }

    @Test
    fun errorEmptyButtonsMoreTest() {
        assertThrows(IllegalArgumentException::class.java) {
            rule.setContent {
                Dialog(
                    button = "bar" to {},
                    "baz" to {},
                    "" to {},
                    onDismissRequest = {},
                    message = "foo",
                )
            }
        }
    }
}
