package sp.sample.dialogs

import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicText
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.DialogProperties
import sp.ax.jc.dialogs.Dialog

internal class MainActivity : AppCompatActivity() {
    @Composable
    private fun OnDialog(onDismissRequest: () -> Unit) {
        val dialog = "dialog"
        val title = "title"
        val message = "message"
        val button = "button"
        Dialog(
            modifier = Modifier
                .testTag(dialog)
                .background(Color.Red),
            onDismissRequest = onDismissRequest,
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

    @Composable
    private fun OnDialogOld(onDismissRequest: () -> Unit) {
        Dialog(
            modifier = Modifier
                .defaultMinSize(280.dp)
                .background(
                    color = Color.White,
                    shape = RoundedCornerShape(28.dp),
                )
                .padding(24.dp),
            onDismissRequest = onDismissRequest,
            properties = DialogProperties(
                dismissOnBackPress = true,
                dismissOnClickOutside = true,
            ),
            title = Dialog.Text(
                modifier = Modifier
                    .padding(bottom = 16.dp),
                value = "title",
                style = TextStyle(fontSize = 24.sp),
            ),
            message = Dialog.Text(
                modifier = Modifier
                    .padding(bottom = 24.dp),
                value = "message",
                style = TextStyle(fontSize = 14.sp),
            ),
            buttons = listOf(
                Dialog.Text(
                    modifier = Modifier
                        .clip(RoundedCornerShape(20.dp))
                        .height(40.dp)
                        .clickable { onDismissRequest() }
                        .padding(start = 12.dp, end = 12.dp)
                        .wrapContentHeight(),
                    value = "foo",
                    style = TextStyle(fontSize = 14.sp),
                ),
                Dialog.Text(
                    modifier = Modifier
                        .clip(RoundedCornerShape(20.dp))
                        .height(40.dp)
                        .clickable { onDismissRequest() }
                        .padding(start = 12.dp, end = 12.dp)
                        .wrapContentHeight(),
                    value = "cancel",
                    style = TextStyle(fontSize = 14.sp),
                ),
                Dialog.Text(
                    modifier = Modifier
                        .clip(RoundedCornerShape(20.dp))
                        .height(40.dp)
                        .clickable { onDismissRequest() }
                        .padding(start = 12.dp, end = 12.dp)
                        .wrapContentHeight(),
                    value = "ok",
                    style = TextStyle(fontSize = 14.sp),
                ),
            ),
        )
    }

    @Composable
    private fun Button(text: String, onClick: () -> Unit) {
        BasicText(
            modifier = Modifier
                .fillMaxWidth()
                .height(56.dp)
                .clickable(onClick = onClick)
                .wrapContentHeight(),
            text = text,
            style = TextStyle(
                textAlign = TextAlign.Center,
                color = Color.White,
            ),
        )
    }

    override fun onCreate(inState: Bundle?) {
        super.onCreate(inState)
        setContent {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(Color.Black),
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .align(Alignment.Center),
                ) {
                    "dialog message".also { text ->
                        var dialog by remember { mutableStateOf(false) }
                        Button(
                            text = text,
                            onClick = {
                                dialog = true
                            },
                        )
                        if (dialog) {
                            Dialog(
                                modifier = Modifier
                                    .defaultMinSize(280.dp)
                                    .background(
                                        color = Color.White,
                                        shape = RoundedCornerShape(28.dp),
                                    )
                                    .padding(24.dp),
                                alignment = Alignment.CenterHorizontally,
                                onDismissRequest = { dialog = false },
                                message = Dialog.Text(
                                    modifier = Modifier,
                                    value = "${System.currentTimeMillis()}ms",
                                    style = TextStyle(
                                        fontSize = 14.sp,
                                    ),
                                ),
                            )
                        }
                    }
                }
            }
        }
    }
}
