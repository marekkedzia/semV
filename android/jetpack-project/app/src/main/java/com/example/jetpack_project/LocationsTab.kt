import android.content.Context
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.clickable
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp

@Composable
fun LocationsTab() {
    val context = LocalContext.current
    val fileList = getFileList(context)
    var selectedItem by remember { mutableStateOf<String?>(null) }

    LazyColumn(contentPadding = PaddingValues(horizontal = 16.dp, vertical = 8.dp)) {
        items(fileList) { file ->
            LocationItem(file, onLongPress = { selectedItem = file })
        }
    }

    selectedItem?.let { fileName ->
        FileOptionsDialog(
            fileName = fileName,
            onDismiss = { selectedItem = null },
            onDelete = {
                deleteFile(context, fileName)
                selectedItem = null
            },
            onRename = { newName ->
                renameFile(context, fileName, newName)
                selectedItem = null
            }
        )
    }
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun LocationItem(fileName: String, onLongPress: () -> Unit) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp)
            .combinedClickable {
                onLongPress()
            },
        elevation = 4.dp
    ) {
        Text(text = fileName, modifier = Modifier.padding(16.dp))
    }
}

@Composable
fun FileOptionsDialog(fileName: String, onDismiss: () -> Unit, onDelete: () -> Unit, onRename: (String) -> Unit) {
    var newName by remember { mutableStateOf(fileName) }
    AlertDialog(
        onDismissRequest = onDismiss,
        title = { Text("File Options") },
        text = { Column {
            Text("File: $fileName")
            OutlinedTextField(
                value = newName,
                onValueChange = { newName = it },
                label = { Text("New Name") }
            )
        }},
        confirmButton = {
            Button(onClick = { onRename(newName) }) { Text("Rename") }
        },
        dismissButton = {
            Button(onClick = onDelete) { Text("Delete") }
        }
    )
}


fun getFileList(context: Context): List<String> {
    val files = context.filesDir.listFiles()
    return files?.filter { it.isFile && it.name.endsWith(".jpg") }?.map { it.name } ?: emptyList()
}

fun renameFile(context: Context, oldFileName: String, newFileName: String): Boolean {
    return try {
        val oldFile = context.getFileStreamPath(oldFileName)
        val newFile = context.getFileStreamPath(newFileName)
        oldFile.renameTo(newFile)
    } catch (e: Exception) {
        e.printStackTrace()
        false
    }
}

fun deleteFile(context: Context, fileName: String): Boolean {
    return try {
        val file = context.getFileStreamPath(fileName)
        file.delete()
    } catch (e: Exception) {
        e.printStackTrace()
        false
    }
}


