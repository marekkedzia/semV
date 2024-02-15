package com.example.lista4_2

import DatabaseHandler
import android.app.AlertDialog
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class ListFragment : Fragment() {
    private lateinit var databaseHandler: DatabaseHandler

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_list, container, false)

        val recyclerView = view.findViewById<RecyclerView>(R.id.recyclerView)
        databaseHandler = DatabaseHandler(requireContext())
        val items = databaseHandler.readItems()
        val adapter = ListAdapter(items, databaseHandler)
        recyclerView.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager(context)

        val addButton = view.findViewById<Button>(R.id.addButton)
        addButton.setOnClickListener {
            showAddItemPopup()
        }

        return view
    }

    private fun showAddItemPopup() {
        val dialogView = layoutInflater.inflate(R.layout.dialog_add_item, null)
        val editQuote = dialogView.findViewById<EditText>(R.id.editQuote)
        val editAuthor = dialogView.findViewById<EditText>(R.id.editAuthor)
        val editDescription = dialogView.findViewById<EditText>(R.id.editDescription)
        val editImageUrl = dialogView.findViewById<EditText>(R.id.editImageUrl)
        val dialog = AlertDialog.Builder(requireContext())
            .setView(dialogView)
            .create()
        dialogView.findViewById<Button>(R.id.buttonSave).setOnClickListener {
            val quote = editQuote.text.toString()
            val author = editAuthor.text.toString()
            val description = editDescription.text.toString()
            val imageUrl = editImageUrl.text.toString()
            val id = (1..10000).random()

            val listItem = ListItem(id, quote, author, description, imageUrl)
            databaseHandler.addItem(listItem)

            updateRecyclerView()
            dialog.dismiss()
        }

        dialog.show()
    }

    private fun updateRecyclerView() {
        val items = databaseHandler.readItems()
        val recyclerView = view?.findViewById<RecyclerView>(R.id.recyclerView)
        recyclerView?.adapter = ListAdapter(items, databaseHandler)
    }
}
