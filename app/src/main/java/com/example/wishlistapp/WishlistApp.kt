package com.example.wishlistapp

import android.app.Application
import com.example.wishlistapp.Graph

class WishlistApp: Application() {
    override fun onCreate() {
        super.onCreate()
        Graph.provide(this)
    }
}