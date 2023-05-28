package sp.ax.jc.dialogs

import android.os.SystemClock
import android.view.InputDevice
import android.view.MotionEvent
import android.view.View
import androidx.activity.ComponentActivity
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
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.test.assertHeightIsEqualTo
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.assertTextEquals
import androidx.compose.ui.test.assertWidthIsEqualTo
import androidx.compose.ui.test.click
import androidx.compose.ui.test.filter
import androidx.compose.ui.test.filterToOne
import androidx.compose.ui.test.hasTextExactly
import androidx.compose.ui.test.isRoot
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onChild
import androidx.compose.ui.test.onChildren
import androidx.compose.ui.test.onFirst
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.onRoot
import androidx.compose.ui.test.performClick
import androidx.compose.ui.test.performTouchInput
import androidx.compose.ui.test.printToLog
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.DialogProperties
import androidx.test.espresso.Espresso
import androidx.test.espresso.action.CoordinatesProvider
import androidx.test.espresso.action.GeneralClickAction
import androidx.test.espresso.action.GeneralLocation
import androidx.test.espresso.action.Press
import androidx.test.espresso.action.Tap
import androidx.test.espresso.action.ViewActions
import androidx.test.espresso.assertion.ViewAssertions
import androidx.test.espresso.matcher.ViewMatchers
import org.hamcrest.Description
import org.hamcrest.TypeSafeMatcher
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner

@RunWith(RobolectricTestRunner::class)
internal class DialogTest {
    @get:Rule
//    val rule = createAndroidComposeRule<ComponentActivity>()
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
//        rule.onNodeWithTag(dialog).assertHeightIsEqualTo(1.dp)
//        rule.onNodeWithTag(dialog).assertWidthIsEqualTo(1.dp)
//        rule.activityRule.scenario.onActivity {
//            val display = it.window.decorView.display
//            error("display: ${display.width}/${display.height}")
//        }
//        val display = rule.activity.window.decorView.display
//        error("display: ${display.width}/${display.height}")
//        val event = MotionEvent.obtain(
//            SystemClock.uptimeMillis(),
//            SystemClock.uptimeMillis() + 100,
//            MotionEvent.ACTION_UP,
//            5f,
//            display.height / 2f,
//            0,
//        )
//        rule.activity.window.decorView.dispatchTouchEvent(event)
        Espresso
            .onView(ViewMatchers.isRoot())
            .perform(ViewActions.click())
//        Espresso
//            .onView(ViewMatchers.isRoot())
//            .perform(
//                ViewActions.actionWithAssertions(
//                    GeneralClickAction(
//                        Tap.SINGLE,
////                        object : CoordinatesProvider {
////                            override fun calculateCoordinates(view: View?): FloatArray {
////                                checkNotNull(view)
//////                                val result = floatArrayOf(0f, view.display.height / 2f)
//////                                val result = floatArrayOf(0f, view.display.height / 2f)
////                                val result = floatArrayOf(0f, view.height / 2f)
//////                                val result = floatArrayOf(0f, 0f)
//////                                error("display: ${view.display.width}/${view.display.height}")
////                                return result
////                            }
////                        },
//                        GeneralLocation.CENTER,
//                        Press.FINGER,
//                        InputDevice.SOURCE_UNKNOWN,
//                        MotionEvent.BUTTON_PRIMARY
//                    )
//                )
//            )
//            .check(
//                ViewAssertions.matches(
//                    object : TypeSafeMatcher<View>() {
//                        override fun describeTo(description: Description?) {
//                            checkNotNull(description).appendText("match size...")
//                        }
//
//                        override fun matchesSafely(item: View?): Boolean {
//                            checkNotNull(item)
//                            check(item.width == 200 && item.height == 200) {
//                                "View: ${item.width}/${item.height}"
//                            }
//                            return true
//                        }
//                    }
//                )
//            )
//        rule.onAllNodes(isRoot()).onFirst().performTouchInput {
//            click(position = center)
//        }
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
//        rule.onAllNodes(isRoot()).onFirst().performTouchInput {
//            click(position = bottomCenter)
//        }
        Espresso
            .onView(ViewMatchers.isRoot())
            .perform(ViewActions.click())
        rule.onNodeWithTag(dialog).assertIsDisplayed()
        rule.onNodeWithTag(hide).performClick()
        rule.onNodeWithTag(dialog).assertDoesNotExist()
    }
}
