package sp.ax.jc.dialogs

import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle

/**
 * Stores values to describe how to draw text inside the dialog.
 * For example, the text of a message or the text on a button.
 *
 * ```
 * +-------------------+
 * | Lorem ipsum...    |
 * |                   |
 * |     [cancel] [ok] |
 * +-------------------+
 * ```
 * @property modifier [Modifier] to apply to text inside the dialog.
 * @property textStyle Style configuration for the text inside the dialog such as color, font, line height etc.
 * @author [Stanley Wintergreen](https://github.com/kepocnhh)
 * @since 0.1.0
 */
data class DialogText(
    val modifier: Modifier,
    val textStyle: TextStyle,
)
