package sp.ax.jc.dialogs

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.text.BasicText
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties

object Dialog {
    data class Text(
        val modifier: Modifier,
        val value: String,
        val style: TextStyle,
    )
}

@Composable
fun Dialog(
    modifier: Modifier,
    alignment: Alignment.Horizontal,
    onDismissRequest: () -> Unit,
    properties: DialogProperties = DialogProperties(),
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
    modifier: Modifier,
    onDismissRequest: () -> Unit,
    properties: DialogProperties = DialogProperties(),
    title: Dialog.Text,
    message: Dialog.Text,
    buttons: List<Dialog.Text>,
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
            Row(modifier = Modifier.align(Alignment.End)) {
                checkNotNull(buttons.firstOrNull()).also { text ->
                    BasicText(
                        modifier = text.modifier,
                        text = text.value,
                        style = text.style,
                    )
                }
                for (i in 1 until buttons.size) {
                    Spacer(modifier = Modifier.width(8.dp))
                    val text = buttons[i]
                    BasicText(
                        modifier = text.modifier,
                        text = text.value,
                        style = text.style,
                    )
                }
            }
        }
    }
}
