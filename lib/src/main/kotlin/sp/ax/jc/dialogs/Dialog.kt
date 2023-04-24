package sp.ax.jc.dialogs

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.text.BasicText
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.window.Dialog

@Composable
fun Dialog(
    modifier: Modifier,
    onDismissRequest: () -> Unit,
    message: String,
) {
    Dialog(onDismissRequest = onDismissRequest) {
        Box(modifier) {
            BasicText(text = message)
        }
    }
}
