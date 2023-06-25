package sp.ax.jc.dialogs.util

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.semantics.SemanticsActions
import androidx.compose.ui.semantics.getOrNull
import androidx.compose.ui.test.SemanticsMatcher
import androidx.compose.ui.text.TextLayoutInput
import androidx.compose.ui.text.TextLayoutResult
import androidx.compose.ui.text.TextStyle

internal fun hasTextLayoutResult(
    description: String = "TextLayoutResult",
    block: (TextLayoutResult) -> Boolean,
): SemanticsMatcher {
    return SemanticsMatcher(description) { node ->
        val key = node.config.getOrNull(SemanticsActions.GetTextLayoutResult) ?: error("key is null!")
        val action = key.action ?: error("action is null!")
        val list: List<TextLayoutResult> = mutableListOf<TextLayoutResult>().also(action::invoke)
        if (list.size != 1) error("List size is ${list.size}!")
        block(list.single())
    }
}

internal fun hasTextLayoutInput(
    description: String = "TextLayoutInput",
    block: (TextLayoutInput) -> Boolean,
): SemanticsMatcher {
    return hasTextLayoutResult(description = description) {
        block(it.layoutInput)
    }
}

internal fun hasTextStyle(
    description: String = "TextStyle",
    block: (TextStyle) -> Boolean,
): SemanticsMatcher {
    return hasTextLayoutInput(description = description) {
        block(it.style)
    }
}

internal fun hasTextColor(
    color: Color,
    description: String = "TextStyle:Color",
): SemanticsMatcher {
    return hasTextStyle(description = description) {
        it.color == color
    }
}
