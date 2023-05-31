package sp.ax.jc.dialogs

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicText
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
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
    alignment: Alignment.Horizontal,
    onDismissRequest: () -> Unit,
    properties: DialogProperties,
    message: Dialog.Text,
) {
    Dialog(
        onDismissRequest = onDismissRequest,
        properties = properties,
    ) {
        Column(modifier = modifier, horizontalAlignment = alignment) {
            check(message.value.isNotEmpty())
            BasicText(
                modifier = message.modifier,
                text = message.value,
                style = message.style,
            )
        }
    }
}

@Composable
fun Dialog(
    minWidth: Dp = 280.dp,
    minHeight: Dp = Dp.Unspecified,
    color: Color = Color.White,
    shape: Shape = RoundedCornerShape(28.dp),
    padding: PaddingValues = PaddingValues(24.dp),
    alignment: Alignment.Horizontal = Alignment.CenterHorizontally,
    onDismissRequest: () -> Unit,
    properties: DialogProperties = DialogProperties(),
    message: String,
    style: TextStyle = TextStyle(fontSize = 14.sp, color = Color.Black),
) {
    Dialog(
        modifier = Modifier
            .defaultMinSize(minWidth = minWidth, minHeight = minHeight)
            .background(
                color = color,
                shape = shape,
            )
            .padding(padding),
        alignment = alignment,
        onDismissRequest = onDismissRequest,
        properties = properties,
        message = Dialog.Text(
            modifier = Modifier,
            value = message,
            style = style,
        ),
    )
}
