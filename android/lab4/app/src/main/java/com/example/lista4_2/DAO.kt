package com.example.lista4_2

import androidx.room.Insert
import androidx.room.Update
import androidx.room.Dao as Dao1
import androidx.room.Query as Query1

@Dao1
interface ItemDao {
    @Query1("SELECT * FROM 'items-database'")
    fun getAllItems(): List<ListItem>

    @Insert
    fun insertItem(item: ListItem)

    @Query1("DELETE FROM 'items-database' WHERE id = :id")
    fun deleteItemById(id: Int)

    @Update
    suspend fun updateItem(itemEntity: ListItem)
}
