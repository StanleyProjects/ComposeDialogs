package sp.ax.jc.dialogs.util

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.semantics.SemanticsActions
import androidx.compose.ui.semantics.getOrNull
import androidx.compose.ui.test.SemanticsMatcher
import androidx.compose.ui.text.TextLayoutInput
import androidx.compose.ui.text.TextLayoutResult
import androidx.compose.ui.text.TextStyle

@Composable
internal fun TestLayout(
    first: String,
    second: String,
    state: MutableState<Boolean>,
) {
    Column(Modifier.fillMaxSize()) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f)
                .testTag(first)
                .clickable {
                    state.value = true
                },
        )
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f)
                .testTag(second)
                .clickable {
                    state.value = false
                },
        )
    }
}

internal fun hasTextLayoutResult(
    description: String = "TextLayoutResult",
    block: (TextLayoutResult) -> Boolean,
): SemanticsMatcher {
    return SemanticsMatcher(description) { node ->
        val key = node.config.getOrNull(SemanticsActions.GetTextLayoutResult) ?: error("key is null!")
        val action = key.action ?: error("action is null!")
        val list: List<TextLayoutResult> = mutableListOf<TextLayoutResult>().also(action::invoke)
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
