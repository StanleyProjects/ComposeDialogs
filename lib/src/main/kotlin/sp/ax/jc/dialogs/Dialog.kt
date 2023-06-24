package sp.ax.jc.dialogs

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicText
import androidx.compose.runtime.Composable
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

@Composable
fun Dialog(
    modifier: Modifier,
    onDismissRequest: () -> Unit,
    properties: DialogProperties,
    message: String,
    messageStyle: DialogText,
    buttonsAlignment: Alignment.Horizontal,
    button: Pair<String, DialogText>,
    vararg buttons: Pair<String, DialogText>,
) {
    Dialog(
        onDismissRequest = onDismissRequest,
        properties = properties,
    ) {
        Column(modifier = modifier) {
            require(message.isNotEmpty())
            BasicText(
                modifier = messageStyle.modifier,
                text = message,
                style = messageStyle.textStyle,
            )
            Row(modifier = Modifier.align(buttonsAlignment)) {
                require(button.first.isNotEmpty())
                BasicText(
                    modifier = button.second.modifier,
                    text = button.first,
                    style = button.second.textStyle,
                )
                buttons.forEach { (text, style) ->
                    require(text.isNotEmpty())
                    BasicText(
                        modifier = style.modifier,
                        text = text,
                        style = style.textStyle,
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
    foregroundColor: Color = LocalDialogStyle.current.foreground,
    backgroundColor: Color = LocalDialogStyle.current.background,
    shape: Shape = RoundedCornerShape(28.dp),
    padding: PaddingValues = PaddingValues(24.dp),
    onDismissRequest: () -> Unit,
    properties: DialogProperties = DialogProperties(),
    //
    message: String,
    textStyle: TextStyle = TextStyle(fontSize = 14.sp, color = foregroundColor),
    space: Dp = 24.dp,
    //
    buttonsAlignment: Alignment.Horizontal = Alignment.End,
    buttonsSpace: Dp = 8.dp,
    buttonStyle: DialogButton = DialogButton(
        padding = PaddingValues(start = 12.dp, end = 12.dp),
        shape = RoundedCornerShape(20.dp),
        height = 40.dp,
        textStyle = TextStyle(fontSize = 14.sp, color = foregroundColor)
    ),
) {
    Dialog(
        modifier = Modifier
            .defaultMinSize(minWidth = minWidth, minHeight = minHeight)
            .background(color = backgroundColor, shape = shape)
            .padding(padding),
        onDismissRequest = onDismissRequest,
        properties = properties,
        message = message,
        messageStyle = DialogText(
            modifier = Modifier.padding(bottom = space),
            textStyle = textStyle,
        ),
        buttonsAlignment = buttonsAlignment,
        button = button.first to DialogText(
            modifier = Modifier
                .clip(buttonStyle.shape)
                .height(buttonStyle.height)
                .clickable(onClick = button.second)
                .padding(buttonStyle.padding)
                .wrapContentHeight(),
            textStyle = buttonStyle.textStyle,
        ),
        buttons = buttons.map { (text, onClick) ->
            text to DialogText(
                modifier = Modifier
                    .padding(start = buttonsSpace)
                    .clip(buttonStyle.shape)
                    .height(buttonStyle.height)
                    .clickable(onClick = onClick)
                    .padding(buttonStyle.padding)
                    .wrapContentHeight(),
                textStyle = buttonStyle.textStyle,
            )
        }.toTypedArray()
    )
}
