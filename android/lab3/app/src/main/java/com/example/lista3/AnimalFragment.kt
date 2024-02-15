package com.example.lista3

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast

abstract class AnimalFragment : Fragment() {
    protected abstract fun getLayoutResId(): Int
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val text = "Zwierzę zmienione!"
        val duration = Toast.LENGTH_SHORT

        val toast = Toast.makeText(requireContext(), text, duration)
        toast.show()
        return inflater.inflate(getLayoutResId(), container, false)
    }
}
