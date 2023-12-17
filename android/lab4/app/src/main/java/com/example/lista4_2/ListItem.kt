package com.example.lista4_2

import androidx.room.Entity


@Entity(tableName = "items-database")
data class ListItem(
    val id: Int,
    val quote: String,
    val author: String,
    val description: String,
    val imageUrl: String,
)

