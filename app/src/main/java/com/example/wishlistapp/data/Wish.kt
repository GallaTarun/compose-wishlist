package com.example.wishlistapp.data

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "wish-table")
data class Wish(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0L,

    @ColumnInfo(name = "wish-title")
    val title: String = "",

    @ColumnInfo(name = "wish-description")
    val description: String = ""
)

object DummyWish {
    val wishList = listOf(
        Wish(title = "Iphone 15", description = "Latest Iphone"),
        Wish(title = "Macbook pro", description = "This is the latest macbook that Apple has launched in the last five years"),
        Wish(title = "Vision pro", description = "This is the latest AR headset apple has launched recently"),
    )
}