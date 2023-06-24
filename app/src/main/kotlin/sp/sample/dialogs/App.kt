package sp.sample.dialogs

import android.app.Application
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.ReadOnlyComposable
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.compose.viewModel

internal class App : Application() {
    object Theme {
        private val LocalColors = staticCompositionLocalOf<Colors> { error("no colors") }

        val colors: Colors
            @Composable
            @ReadOnlyComposable
            get() = LocalColors.current

        @Composable
        fun Composition(
            themeState: ThemeState,
            content: @Composable () -> Unit,
        ) {
            CompositionLocalProvider(
                LocalColors provides when (themeState.colorsType) {
                    ColorsType.DARK -> Colors.Dark
                    ColorsType.LIGHT -> Colors.Light
                },
                content = content,
            )
        }
    }

    override fun onCreate() {
        super.onCreate()
        _viewModelFactory = object : ViewModelProvider.Factory {
            override fun <U : ViewModel> create(modelClass: Class<U>): U {
                return modelClass.getConstructor().newInstance()
            }
        }
    }

    companion object {
        private var _viewModelFactory: ViewModelProvider.Factory? = null

        @Composable
        inline fun <reified T : ViewModel> viewModel(): T {
            return viewModel(factory = checkNotNull(_viewModelFactory))
        }
    }
}
