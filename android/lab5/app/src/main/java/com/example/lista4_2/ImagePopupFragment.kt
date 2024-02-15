package com.example.lista4_2

import android.app.Dialog
import android.net.Uri
import android.os.Bundle
import androidx.fragment.app.DialogFragment
import androidx.viewpager2.widget.ViewPager2
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

class ImagePopupFragment(private val imageUris: List<Uri>) : DialogFragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_image_popup, container, false)
        val viewPager: ViewPager2 = view.findViewById(R.id.viewPager)
        viewPager.adapter = ImagePagerAdapter(requireContext(), imageUris)
        return view
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val dialog = super.onCreateDialog(savedInstanceState)
        // Optional: Add more dialog properties here
        return dialog
    }

    // Optional: Add more methods if needed
}
