package com.example.lista4_2

import android.Manifest
import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import com.google.android.material.snackbar.Snackbar
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import java.io.File
import java.io.FileOutputStream
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale
import java.util.UUID

class FragmentPageTwo : Fragment() {

    private val REQUEST_CAMERA_PERMISSION = 1
    private val REQUEST_GALLERY_PERMISSION = 2
    private val REQUEST_IMAGE_CAPTURE = 3
    private val REQUEST_IMAGE_PICK = 4

    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: ImageGridAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_page_two, container, false)

        recyclerView = view.findViewById(R.id.recycler_view_strings)
        recyclerView.layoutManager = GridLayoutManager(context, 2)
        adapter = ImageGridAdapter(getFileUris())
        recyclerView.adapter = adapter

        recyclerView.addOnItemTouchListener(object : RecyclerView.SimpleOnItemTouchListener() {
            override fun onInterceptTouchEvent(rv: RecyclerView, e: MotionEvent): Boolean {
                if (e.action == MotionEvent.ACTION_UP) {
                    val view = rv.findChildViewUnder(e.x, e.y)
                    val position = rv.getChildAdapterPosition(view!!)
                    val imagePopupFragment = ImagePopupFragment(getFileUris())
                    imagePopupFragment.show(requireActivity().supportFragmentManager, "imagePopup")
                }
                return super.onInterceptTouchEvent(rv, e)
            }
        })


        view.findViewById<Button>(R.id.button_camera).setOnClickListener {
            if (hasPermission(Manifest.permission.CAMERA)) {
                launchCamera()
            } else {
                requestPermissions(arrayOf(Manifest.permission.CAMERA), REQUEST_CAMERA_PERMISSION)
            }
        }

        view.findViewById<Button>(R.id.button_image).setOnClickListener {
            if (hasPermission(Manifest.permission.READ_EXTERNAL_STORAGE)) {
                openGallery()
            } else {
                requestPermissions(
                    arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE),
                    REQUEST_GALLERY_PERMISSION
                )
            }
        }

        return view
    }

    private fun getFileUris(): List<Uri> {
        val fileUris = mutableListOf<Uri>()
        val files = context?.filesDir?.listFiles()
        files?.forEach { file ->
            if (file.name.endsWith(".jpg")) {
                fileUris.add(Uri.fromFile(file))
            }
        }
        return fileUris
    }

    private fun hasPermission(permission: String): Boolean {
        return ContextCompat.checkSelfPermission(
            requireContext(),
            permission
        ) == PackageManager.PERMISSION_GRANTED
    }

    private fun launchCamera() {
        Intent(MediaStore.ACTION_IMAGE_CAPTURE).also { takePictureIntent ->
            takePictureIntent.resolveActivity(requireContext().packageManager)?.also {
                startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE)
            }
        }
    }

    private fun openGallery() {
        Intent(
            Intent.ACTION_PICK,
            MediaStore.Images.Media.EXTERNAL_CONTENT_URI
        ).also { pickPhotoIntent ->
            startActivityForResult(pickPhotoIntent, REQUEST_IMAGE_PICK)
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        when (requestCode) {
            REQUEST_CAMERA_PERMISSION -> {
                if ((grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED)) {
                    launchCamera()
                } else {
                    Snackbar.make(
                        requireView(),
                        "Camera permission is required to use the camera",
                        Snackbar.LENGTH_SHORT
                    ).show()
                }
                return
            }

            REQUEST_GALLERY_PERMISSION -> {
                if ((grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED)) {
                    openGallery()
                } else {
                    Snackbar.make(
                        requireView(),
                        "Storage permission is required to access the gallery",
                        Snackbar.LENGTH_SHORT
                    ).show()
                }
                return
            }
        }
    }

    private fun generateRandomPhotoName(): String {
        val timeStamp = SimpleDateFormat("yyyyMMdd_HHmmss", Locale.getDefault()).format(Date())
        val randomUUID = UUID.randomUUID().toString()
        return "IMG_${timeStamp}_$randomUUID.jpg"
    }

    private fun storeImageInPrivateDirectory(imageUri: Uri) {
        val context = requireContext()
        val inputStream = context.contentResolver.openInputStream(imageUri)
        inputStream?.let { stream ->
            val fileName = generateRandomPhotoName()
            val file = File(context.filesDir, fileName)
            FileOutputStream(file).use { output ->
                stream.copyTo(output)
            }
            adapter = ImageGridAdapter(getFileUris())
            recyclerView.adapter = adapter
        }
    }

    private fun storeImageInSharedDirectory(imageUri: Uri) {
        val fileName = generateRandomPhotoName()
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        when (requestCode) {
            REQUEST_IMAGE_CAPTURE -> {
                if (resultCode == Activity.RESULT_OK && data != null) {
                    val imageUri = data.extras?.get("data") as? Bitmap
                    imageUri?.let {
                        // Process and store the captured image
                        processCapturedImage(it)
                    }
                    // Update the RecyclerView with the new list of images
                    adapter = ImageGridAdapter(getFileUris())
                    recyclerView.adapter = adapter
                }
            }

            REQUEST_IMAGE_PICK -> {
                if (resultCode == Activity.RESULT_OK && data != null) {
                    val selectedImageUri = data.data
                    // Handle the selected image URI
                    processSelectedImage(false, selectedImageUri)
                    // Update the RecyclerView with the new list of images
                    adapter = ImageGridAdapter(getFileUris())
                    recyclerView.adapter = adapter
                }
            }
        }
    }

    private fun processCapturedImage(bitmap: Bitmap) {
        val fileName = generateRandomPhotoName()
        val file = File(requireContext().filesDir, fileName)
        FileOutputStream(file).use { fos ->
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, fos)
        }
    }


    private fun processSelectedImage(storeInSharedDirectory: Boolean, imageUri: Uri?) {
        imageUri?.let { uri ->
            if (storeInSharedDirectory) {
                storeImageInSharedDirectory(uri)
            } else {
                storeImageInPrivateDirectory(uri)
            }
        }
    }
}
