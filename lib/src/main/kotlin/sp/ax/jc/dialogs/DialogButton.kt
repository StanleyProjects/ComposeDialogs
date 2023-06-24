package sp.ax.jc.dialogs

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.Dp

data class DialogButton(
    val padding: PaddingValues,
    val shape: Shape,
    val height: Dp,
    val textStyle: TextStyle,
)
