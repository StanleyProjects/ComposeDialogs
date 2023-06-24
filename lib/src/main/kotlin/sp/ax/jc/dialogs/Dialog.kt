package sp.ax.jc.dialogs

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicText
import androidx.compose.runtime.Composable
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties

object Dialog {
    data class Text(
        val modifier: Modifier,
        val value: String,
        val style: TextStyle,
    )

    data class Buttons(
        val alignment: Alignment.Horizontal,
        val space: Dp,
        val padding: PaddingValues,
        val shape: Shape,
        val height: Dp,
        val textStyle: TextStyle,
    )
}

data class DialogStyle(
    val message: Dialog.Text,
    val buttons: Dialog.Buttons,
)

//val LocalDialogStyle = staticCompositionLocalOf {
//    DialogStyle(
//        message = TextStyle(fontSize = 14.sp, color = Color.Black),
//        buttons = Dialog.Buttons(
//            alignment = Alignment.End,
//            space = 8.dp,
//            padding = PaddingValues(start = 12.dp, end = 12.dp),
//            shape = RoundedCornerShape(20.dp),
//            height = 40.dp,
//            textStyle = TextStyle(fontSize = 14.sp, color = Color.Black)
//        ),
//    )
//}

@Composable
fun Dialog(
    modifier: Modifier,
    onDismissRequest: () -> Unit,
    properties: DialogProperties = DialogProperties(),
    title: Dialog.Text,
    message: Dialog.Text,
    alignment: Alignment.Horizontal,
    button: Dialog.Text,
    vararg buttons: Dialog.Text,
) {
    Dialog(
        onDismissRequest = onDismissRequest,
        properties = properties,
    ) {
        Column(modifier = modifier) {
            check(title.value.isNotEmpty())
            BasicText(
                modifier = title.modifier,
                text = title.value,
                style = title.style,
            )
            check(message.value.isNotEmpty())
            BasicText(
                modifier = message.modifier,
                text = message.value,
                style = message.style,
            )
            Row(modifier = Modifier.align(alignment)) {
                check(button.value.isNotEmpty())
                BasicText(
                    modifier = button.modifier,
                    text = button.value,
                    style = button.style,
                )
                buttons.forEach {
                    check(it.value.isNotEmpty())
                    BasicText(
                        modifier = it.modifier,
                        text = it.value,
                        style = it.style,
                    )
                }
            }
        }
    }
}


@Composable
fun Dialog(
    button: Pair<String, () -> Unit>,
    vararg buttons: Pair<String, () -> Unit>,
    minWidth: Dp = 280.dp,
    minHeight: Dp = Dp.Unspecified,
    color: Color = Color.White,
    shape: Shape = RoundedCornerShape(28.dp),
    padding: PaddingValues = PaddingValues(24.dp),
    onDismissRequest: () -> Unit,
    properties: DialogProperties = DialogProperties(),
    title: String,
    titleTextStyle: TextStyle = TextStyle(fontSize = 24.sp, color = Color.Black),
    message: String,
    messageTextStyle: TextStyle = TextStyle(fontSize = 14.sp, color = Color.Black),
    space: Dp = 24.dp,
    style: Dialog.Buttons = Dialog.Buttons(
        alignment = Alignment.End,
        space = 8.dp,
        padding = PaddingValues(start = 12.dp, end = 12.dp),
        shape = RoundedCornerShape(20.dp),
        height = 40.dp,
        textStyle = TextStyle(fontSize = 14.sp, color = Color.Black)
    ),
) {
    Dialog(
        modifier = Modifier
            .defaultMinSize(minWidth = minWidth, minHeight = minHeight)
            .background(
                color = color,
                shape = shape,
            )
            .padding(padding),
        onDismissRequest = onDismissRequest,
        properties = properties,
        title = Dialog.Text(
            modifier = Modifier
                .padding(bottom = 16.dp),
            value = title,
            style = titleTextStyle,
        ),
        message = Dialog.Text(
            modifier = Modifier.padding(bottom = space),
            value = message,
            style = messageTextStyle,
        ),
        alignment = style.alignment,
        button = Dialog.Text(
            modifier = Modifier
                .clip(style.shape)
                .height(style.height)
                .clickable(onClick = button.second)
                .padding(style.padding)
                .wrapContentHeight(),
            value = button.first,
            style = style.textStyle,
        ),
        buttons = buttons.map {
            Dialog.Text(
                modifier = Modifier
                    .padding(start = style.space)
                    .clip(style.shape)
                    .height(style.height)
                    .clickable(onClick = it.second)
                    .padding(style.padding)
                    .wrapContentHeight(),
                value = it.first,
                style = style.textStyle,
            )
        }.toTypedArray()
    )
}
