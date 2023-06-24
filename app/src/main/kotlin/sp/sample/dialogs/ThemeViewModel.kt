package sp.sample.dialogs

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

internal class ThemeViewModel : ViewModel() {
    private val _state = MutableStateFlow(ThemeState(ColorsType.LIGHT))
    val state = _state.asStateFlow()

    fun setColorsType(type: ColorsType) {
        viewModelScope.launch {
            _state.value = state.value.copy(colorsType = type)
        }
    }
}
