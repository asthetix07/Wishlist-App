package com.example.wishlistapp

import android.app.Application


//To initialize database from global context
class WishListApp: Application() {
    override fun onCreate() {
        super.onCreate()
        Graph.provide(this)
    }
}