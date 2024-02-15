import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.FloatingActionButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.core.content.ContextCompat
import com.example.jetpack_project.R
import java.io.FileOutputStream
import java.io.IOException
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

fun saveImageToInternalStorage(bitmap: Bitmap, context: Context) {
    val filename =
        "JPEG_" + SimpleDateFormat("yyyyMMdd_HHmmss", Locale.getDefault()).format(Date()) + ".jpg"
    val fos: FileOutputStream
    try {
        fos = context.openFileOutput(filename, Context.MODE_PRIVATE)
        bitmap.compress(Bitmap.CompressFormat.JPEG, 85, fos)
        fos.close()
    } catch (e: IOException) {
        e.printStackTrace()
    }
}

@Composable
fun PhotosTab(onImageSelected: (String)->Unit) {
    val context = LocalContext.current
    var hasCameraPermission by remember {
        mutableStateOf(
            ContextCompat.checkSelfPermission(
                context,
                android.Manifest.permission.CAMERA
            ) == android.content.pm.PackageManager.PERMISSION_GRANTED
        )
    }
    var imageFiles by remember { mutableStateOf(listOf<String>()) }

    val requestPermissionLauncher = rememberLauncherForActivityResult(
        ActivityResultContracts.RequestPermission()
    ) { isGranted: Boolean ->
        hasCameraPermission = isGranted
    }

    fun updateImageList() {
        imageFiles = getFileList(context)
    }

    LaunchedEffect(Unit) {
        updateImageList()
    }

    val takePictureLauncher =
        rememberLauncherForActivityResult(ActivityResultContracts.TakePicturePreview()) { bitmap ->
            bitmap?.let {
                saveImageToInternalStorage(it, context)
                updateImageList()
            }

            if (!hasCameraPermission)
                requestPermissionLauncher.launch(android.Manifest.permission.CAMERA)
        }


    LazyVerticalGrid(
        columns = GridCells.Fixed(3), // Adjust number of columns as needed
        contentPadding = PaddingValues(8.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp),
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        items(imageFiles) { filePath ->
            val imageBitmap =
                BitmapFactory.decodeFile(context.getFileStreamPath(filePath).absolutePath)
            imageBitmap?.let { bitmap ->
                SelectableImage(
                    image = bitmap.asImageBitmap(),
                    selectIcon = R.drawable.accept,
                    path = filePath,
                    onImageSelected = { selectedFilePath ->
                            onImageSelected(selectedFilePath)
                    }
                )
            }
        }
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        contentAlignment = Alignment.BottomCenter
    ) {
        FloatingActionButton(
            onClick = {
                takePictureLauncher.launch(null)
            },

            backgroundColor = Color.White,
            modifier = Modifier
                .shadow(4.dp, shape = CircleShape)
                .border(2.dp, Color.Black, shape = CircleShape)
        ) {
            Image(
                painter = painterResource(id = R.drawable.camera),
                contentDescription = "Camera Icon",
                modifier = Modifier
                    .size(40.dp)
            )
        }
    }
}
