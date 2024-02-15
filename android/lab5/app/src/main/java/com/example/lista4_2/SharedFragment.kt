package com.example.lista4_2

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels

class SharedFragment : Fragment() {
    private val sharedViewModel: SharedViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.shared_fragment, container, false)
        val imageView = view.findViewById<ImageView>(R.id.shared_image_view)

        sharedViewModel.imageResId.observe(viewLifecycleOwner) { resId ->
            imageView.setImageResource(resId)
        }

        return view
    }
}