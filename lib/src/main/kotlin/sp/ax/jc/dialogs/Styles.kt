package sp.ax.jc.dialogs

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.Dp

data class DialogTextStyle(
    val modifier: Modifier,
    val textStyle: TextStyle,
) {
    constructor(
        shape: Shape,
        height: Dp,
        padding: PaddingValues,
        textStyle: TextStyle,
    ) : this(
        modifier = Modifier
            .clip(shape)
            .height(height)
            .padding(padding)
            .wrapContentHeight(),
        textStyle = textStyle,
    )
}
