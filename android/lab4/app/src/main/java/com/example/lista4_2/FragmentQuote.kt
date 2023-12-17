package com.example.lista4_2

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import androidx.lifecycle.ViewModelProvider

abstract class BaseQuoteFragment : Fragment() {
    protected abstract val imageResId: Int

    private lateinit var viewModel: SharedViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel = ViewModelProvider(requireActivity()).get(SharedViewModel::class.java)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_page_one, container, false)
        val imageView = view.findViewById<ImageView>(R.id.image_view)
        val saveButton = view.findViewById<Button>(R.id.save_button)

        imageView.setImageResource(imageResId)

        saveButton.setOnClickListener {
            onSaveClicked()
        }

        return view
    }

    private fun onSaveClicked() {
      viewModel.imageResId.value = imageResId
    }
}
