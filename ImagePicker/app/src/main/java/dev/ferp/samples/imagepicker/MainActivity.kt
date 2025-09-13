package dev.ferp.samples.imagepicker

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.ui.Modifier
import dev.ferp.samples.imagepicker.addprofilepic.AddProfilePicScreen
import dev.ferp.samples.imagepicker.addprofilepic.AddProfilePicViewModel
import dev.ferp.samples.imagepicker.ui.theme.ImagePickerTheme

class MainActivity : ComponentActivity() {

    private val viewModel: AddProfilePicViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            ImagePickerTheme {
                AddProfilePicScreen(
                    viewModel = viewModel,
                    modifier = Modifier,
                    onSave = ::finish,
                    onDismiss = ::finish
                )
            }
        }
    }
}
