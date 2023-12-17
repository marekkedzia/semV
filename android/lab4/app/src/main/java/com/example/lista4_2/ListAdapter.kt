package com.example.lista4_2

import DatabaseHandler
import android.app.AlertDialog
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class ListAdapter(
    private val listItems: MutableList<ListItem>,
    private val databaseHandler: DatabaseHandler
) : RecyclerView.Adapter
   <ListAdapter.ListViewHolder>() {

    class ListViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val textViewTitle: TextView = itemView.findViewById(R.id.textViewTitle)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListViewHolder {
        val itemView =
            LayoutInflater.from(parent.context).inflate(R.layout.list_element, parent, false)
        return ListViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: ListViewHolder, position: Int) {
        val currentItem = listItems[position]
        holder.textViewTitle.text = "${currentItem.quote}/${currentItem.author}"

        holder.itemView.setOnClickListener {
            val popupView = LayoutInflater.from(holder.itemView.context).inflate(R.layout.popup_dialog, null)
            val popupBuilder = AlertDialog.Builder(holder.itemView.context)
            popupBuilder.setView(popupView)

            val buttonEdit = popupView.findViewById<Button>(R.id.buttonEdit)
            val description = popupView.findViewById<TextView>(R.id.textViewDescription)
            val author = popupView.findViewById<TextView>(R.id.textViewAuthor)
            val quote = popupView.findViewById<TextView>(R.id.textViewQuote)

            description.text = currentItem.description
            author.text = currentItem.author
            quote.text = currentItem.quote

            val popupDialog = popupBuilder.create()

            buttonEdit.setOnClickListener {
                popupDialog.dismiss()

                val editDialogView =
                    LayoutInflater.from(holder.itemView.context).inflate(R.layout.dialog_add_item, null)
                val editDialogBuilder = AlertDialog.Builder(holder.itemView.context)
                editDialogBuilder.setView(editDialogView)

                val editQuote = editDialogView.findViewById<EditText>(R.id.editQuote)
                val editAuthor = editDialogView.findViewById<EditText>(R.id.editAuthor)
                val editDescription = editDialogView.findViewById<EditText>(R.id.editDescription)
                val editImageUrl = editDialogView.findViewById<EditText>(R.id.editImageUrl)
                val buttonSave = editDialogView.findViewById<Button>(R.id.buttonSave)

                editQuote.setText(currentItem.quote)
                editAuthor.setText(currentItem.author)
                editDescription.setText(currentItem.description)
                editImageUrl.setText(currentItem.imageUrl)

                val editDialog = editDialogBuilder.create()

                buttonSave.setOnClickListener {
                    val updatedListItem = ListItem(
                        currentItem.id,
                        editQuote.text.toString(),
                        editAuthor.text.toString(),
                        editDescription.text.toString(),
                        editImageUrl.text.toString(),
                    )

                    databaseHandler.editItem(updatedListItem)
                    listItems[position] = updatedListItem

                    notifyItemChanged(position)
                    editDialog.dismiss()
                }

                editDialog.show()
            }

            popupDialog.show()
        }

        holder.itemView.setOnLongClickListener {
            showDeleteConfirmation(position, holder)
            true
        }
    }


    override fun getItemCount() = listItems.size

    private fun showDeleteConfirmation(position: Int, holder: ListViewHolder) {
        val currentItem = listItems[position]

        val builder = AlertDialog.Builder(holder.itemView.context)
        builder.setTitle("Delete Item")
        builder.setMessage("Are you sure you want to delete this item?")

        builder.setPositiveButton("Yes") { dialog, which ->
            databaseHandler.deleteItem(currentItem.id)
            listItems.removeAt(position)
            notifyItemRemoved(position)

            dialog.dismiss()
        }
        builder.setNegativeButton("No") { dialog, which ->
            dialog.dismiss()
        }

        val dialog = builder.create()
        dialog.show()
    }
}
