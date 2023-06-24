package sp.ax.jc.dialogs

import androidx.compose.runtime.Immutable
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.graphics.Color

@Immutable
class DialogStyle(
    val background: Color,
    val foreground: Color,
)

val LocalDialogStyle = staticCompositionLocalOf {
    DialogStyle(
        background = Color.White,
        foreground = Color.Black,
    )
}
