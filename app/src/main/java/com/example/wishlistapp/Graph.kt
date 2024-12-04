package com.example.wishlistapp

import android.content.Context
import androidx.room.Room
import com.example.wishlistapp.data.WishDatabase
import com.example.wishlistapp.data.WishRepository


//dependency injection
object Graph {

    lateinit var database: WishDatabase

    val wishRepository by lazy {  //load a thing when it is needed , lazy means when needed
        WishRepository(
            wishDao = database.wishDao()
        )
    }

    fun provide(context: Context){
        database= Room.databaseBuilder(context, WishDatabase::class.java, "wishlist.db").build()
    }

}