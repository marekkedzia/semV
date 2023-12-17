package com.example.lista3

import android.os.Bundle
import android.view.View
import android.widget.ListView
import android.widget.ArrayAdapter

val catColors = listOf("Szary", "Biały", "Czarny", "Pomarańczowy")

class CatFragment : AnimalFragment() {

    override fun getLayoutResId(): Int {
        return R.layout.fragment_cat
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val listView: ListView = view.findViewById(R.id.colorListView)
        val adapter = ArrayAdapter(requireContext(), android.R.layout.simple_list_item_1, catColors)
        listView.adapter = adapter
    }
}
