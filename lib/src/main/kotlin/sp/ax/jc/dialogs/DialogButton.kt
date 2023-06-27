package sp.ax.jc.dialogs

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.Dp

/**
 * Stores values to describe how to draw the dialog button.
 * @property padding Padding around button text.
 * @property shape Button background shape.
 * @property height Button height.
 * @property textStyle Button text style.
 * @author [Stanley Wintergreen](https://github.com/kepocnhh)
 * @since 0.1.0
 */
data class DialogButton(
    val padding: PaddingValues,
    val shape: Shape,
    val height: Dp,
    val textStyle: TextStyle,
)
