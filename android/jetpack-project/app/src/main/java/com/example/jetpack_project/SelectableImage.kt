import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.delay

@Composable
fun SelectableImage(image: ImageBitmap, path:String, onImageSelected: (String) -> Unit, selectIcon: Int) {
    var showSelectIcon by remember { mutableStateOf(false) }

    Box(
        modifier = Modifier
            .pointerInput(Unit) {
                detectTapGestures(
                    onLongPress = {
                        showSelectIcon = true
                    }
                )
            }
    ) {
        Image(bitmap = image, contentDescription = "Selectable Image")

        if (showSelectIcon) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(16.dp),
                contentAlignment = Alignment.BottomEnd
            ) {
                Image(
                    painter = painterResource(id = selectIcon),
                    contentDescription = "Select",
                    modifier = Modifier
                        .size(48.dp)
                        .clickable {
                            onImageSelected(path)
                            showSelectIcon = false
                        }
                )
            }

            // Hide the select icon after 3 seconds
            LaunchedEffect(Unit) {
                delay(3000)
                showSelectIcon = false
            }
        }
    }
}
