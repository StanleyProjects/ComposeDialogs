package sp.sample.dialogs

internal enum class ColorsType {
    DARK,
    LIGHT,
}

internal data class ThemeState(
    val colorsType: ColorsType,
)
