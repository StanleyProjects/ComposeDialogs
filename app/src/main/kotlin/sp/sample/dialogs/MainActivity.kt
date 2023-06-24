package sp.sample.dialogs

import android.os.Bundle
import androidx.activity.compose.BackHandler
import androidx.activity.compose.setContent
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.text.BasicText
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import sp.ax.jc.dialogs.Dialog

internal class MainActivity : AppCompatActivity() {
    @Composable
    private fun Button(
        text: String,
        textColor: Color = App.Theme.colors.foreground,
        background: Color = App.Theme.colors.background,
        onClick: () -> Unit,
    ) {
        BasicText(
            modifier = Modifier
                .fillMaxWidth()
                .height(56.dp)
                .background(background)
                .clickable(onClick = onClick)
                .wrapContentHeight(),
            text = text,
            style = TextStyle(
                textAlign = TextAlign.Center,
                color = textColor,
            ),
        )
    }

    @Composable
    private fun Composition(themeState: ThemeState) {
        App.Theme.Composition(
            themeState = themeState,
        ) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(App.Theme.colors.background)
            ) {
                BackHandler {
                    finish()
                }
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .align(Alignment.Center),
                ) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(56.dp),
                    ) {
                        val themeViewModel = App.viewModel<ThemeViewModel>()
                        BasicText(
                            modifier = Modifier
                                .fillMaxHeight()
                                .weight(1f)
                                .background(Colors.white)
                                .clickable {
                                    themeViewModel.setColorsType(ColorsType.LIGHT)
                                }
                                .wrapContentHeight(),
                            text = "light",
                            style = TextStyle(
                                textAlign = TextAlign.Center,
                                color = Colors.black,
                            ),
                        )
                        BasicText(
                            modifier = Modifier
                                .fillMaxHeight()
                                .weight(1f)
                                .background(Colors.black)
                                .clickable {
                                    themeViewModel.setColorsType(ColorsType.DARK)
                                }
                                .wrapContentHeight(),
                            text = "dark",
                            style = TextStyle(
                                textAlign = TextAlign.Center,
                                color = Colors.white,
                            ),
                        )
                    }
                    "dialog".also { text ->
                        var dialog by remember { mutableStateOf(false) }
                        Button(
                            text = text,
                            onClick = {
                                dialog = true
                            },
                        )
                        if (dialog) {
                            Dialog(
                                "foo" to { dialog = false },
                                "bar" to { dialog = false },
                                onDismissRequest = { dialog = false },
                                message = "${System.currentTimeMillis()}ms",
                            )
                        }
                    }
                }
            }
        }
    }

    override fun onCreate(inState: Bundle?) {
        super.onCreate(inState)
        setContent {
            LocalContext
            val themeState = App.viewModel<ThemeViewModel>().state.collectAsState()
            Composition(themeState = themeState.value)
        }
    }
}
