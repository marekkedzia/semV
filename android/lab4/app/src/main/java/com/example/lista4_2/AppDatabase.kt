package com.example.lista4_2

import androidx.room.Database as Database1
import androidx.room.RoomDatabase as RoomDatabase1

@Database1(entities = [ListItem::class], version = 2)
abstract class AppDatabase : RoomDatabase1() {
    abstract fun itemDao(): ItemDao
}
