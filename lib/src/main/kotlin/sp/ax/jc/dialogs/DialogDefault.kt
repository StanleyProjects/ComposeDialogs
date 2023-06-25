package sp.ax.jc.dialogs

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.ui.Alignment
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Suppress("MagicNumber")
internal object DialogDefault {
    val minWidth: Dp = 280.dp
    val minHeight: Dp = Dp.Unspecified
    val shape: Shape = RoundedCornerShape(28.dp)
    val padding: PaddingValues = PaddingValues(24.dp)
    val messageFontSize = 14.sp
    val space: Dp = 24.dp
    val buttonsAlignment: Alignment.Horizontal = Alignment.End
    val buttonsSpace: Dp = 8.dp
    val buttonPadding = PaddingValues(start = 12.dp, end = 12.dp)
    val buttonShape = RoundedCornerShape(20.dp)
    val buttonHeight = 40.dp
    val buttonFontSize = 14.sp
}
