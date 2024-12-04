package com.example.wishlistapp.data

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [Wish::class], version = 1, exportSchema = false) //entities=array of wish

abstract class WishDatabase: RoomDatabase() {
    abstract fun wishDao(): WishDao
}