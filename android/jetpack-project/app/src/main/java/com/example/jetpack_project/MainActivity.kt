import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.material.Tab
import androidx.compose.material.TabRow
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.tooling.preview.Preview
import com.example.jetpack_project.R
import kotlinx.coroutines.launch

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MyApp()
        }
    }
}

@Composable
fun MyApp() {
    var selectedImagePath by remember { mutableStateOf("") }
    val tabs = listOf("Landing Page", "Zdjęcia", "Lokalizacje")
    val coroutineScope = rememberCoroutineScope()
    var tabIndex by remember { mutableStateOf(0) }

    Column {
        TabRow(selectedTabIndex = tabIndex) {
            tabs.forEachIndexed { index, text ->
                Tab(
                    selected = tabIndex == index,
                    onClick = {
                        coroutineScope.launch {
                            tabIndex = index
                        }
                    }
                ) {
                    Text(text)
                }
            }
        }
        when (tabIndex) {
            0 -> LandingPage(imageId = R.drawable.jetpack_default_image)
            1 -> PhotosTab { selectedImagePath = it }
            2 -> LocationsTab()
        }
    }
}


@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    MyApp()
}
