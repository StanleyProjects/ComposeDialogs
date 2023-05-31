package sp.ax.jc.dialogs

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.text.BasicText
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties

@Composable
fun Dialog(
    modifier: Modifier,
    alignment: Alignment.Horizontal,
    onDismissRequest: () -> Unit,
    properties: DialogProperties,
    message: Dialog.Text,
    button: Dialog.Text,
    vararg buttons: Dialog.Text,
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
            Row(modifier = Modifier.align(Alignment.End)) {
                BasicText(
                    modifier = button.modifier,
                    text = button.value,
                    style = button.style,
                )
                buttons.forEach {
                    Spacer(modifier = Modifier.width(8.dp))
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
