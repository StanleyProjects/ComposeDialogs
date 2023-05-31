package sp.ax.jc.dialogs

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.text.BasicText
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties


@Composable
fun Dialog(
    modifier: Modifier,
    onDismissRequest: () -> Unit,
    properties: DialogProperties,
    title: Dialog.Text,
    message: Dialog.Text,
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
        }
    }
}
