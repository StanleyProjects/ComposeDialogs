package sp.ax.jc.dialogs

import androidx.compose.runtime.Immutable
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.graphics.Color

/**
 * Stores values to describe how to draw the dialog.
 * @property background Color to fill the background.
 * @property foreground Color to fill the foreground.
 * @author [Stanley Wintergreen](https://github.com/kepocnhh)
 * @since 0.1.0
 */
@Immutable
class DialogStyle(
    val background: Color,
    val foreground: Color,
)

/**
 * Provides [DialogStyle] to describe how to draw the dialog.
 *
 * Usage:
 * ```
 * CompositionLocalProvider(
 *     LocalDialogStyle provides DialogStyle(
 *         background = Color.Green,
 *         foreground = Color.Red,
 *     ),
 * ) {
 *     Dialog(
 *         "foo" to { foo() },
 *         onDismissRequest = {},
 *         message = "Lorem ipsum...",
 *     )
 * }
 * ```
 * @author [Stanley Wintergreen](https://github.com/kepocnhh)
 * @since 0.1.0
 */
val LocalDialogStyle = staticCompositionLocalOf {
    DialogStyle(
        background = Color.White,
        foreground = Color.Black,
    )
}
