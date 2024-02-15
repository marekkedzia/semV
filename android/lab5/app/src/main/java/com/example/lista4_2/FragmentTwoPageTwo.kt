package com.example.lista4_2

import DatabaseHandler
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.Toast


class FragmentTwoPageTwo : Fragment() {
    private lateinit var databaseHandler: DatabaseHandler

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        databaseHandler = DatabaseHandler(requireContext())
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_two_page_two, container, false)

        val saveButton = view.findViewById<Button>(R.id.saveButton)
        val quoteEditText = view.findViewById<EditText>(R.id.helloText)
        val authorEditText = view.findViewById<EditText>(R.id.author)
        val descriptionEditText = view.findViewById<EditText>(R.id.description)
        val imageEditText = view.findViewById<EditText>(R.id.image)

        saveButton.setOnClickListener {
            val id = (1..10000).random()
            val quote = quoteEditText.text.toString()
            val author = authorEditText.text.toString()
            val description = descriptionEditText.text.toString()
            val imageUrl = imageEditText.text.toString()


            val listItem = ListItem(
                id,
                quote,
                author,
                description,
                imageUrl,
            )
            databaseHandler.addItem(listItem)

            Toast.makeText(context, "Saved", Toast.LENGTH_SHORT).show()
        }

        return view
    }

}