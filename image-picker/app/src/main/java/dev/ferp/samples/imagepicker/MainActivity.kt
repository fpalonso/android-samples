package dev.ferp.samples.imagepicker

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.ui.Modifier
import dagger.hilt.android.AndroidEntryPoint
import dev.ferp.samples.imagepicker.addprofilepic.AddProfilePicScreen
import dev.ferp.samples.imagepicker.ui.theme.ImagePickerTheme

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            ImagePickerTheme {
                AddProfilePicScreen(
                    modifier = Modifier,
                    onSave = ::finish,
                    onDismiss = ::finish
                )
            }
        }
    }
}
