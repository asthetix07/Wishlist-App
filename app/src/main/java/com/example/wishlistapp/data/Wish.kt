package com.example.wishlistapp.data

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

//Define the table(there can be multiple table)
@Entity(tableName = "wish-table")

data class Wish(

    //define primary key
    @PrimaryKey(autoGenerate = true)
    val id: Long= 0L, //0L means null

    //define the column
    @ColumnInfo(name= "wish-title")
    val title: String= "",

    //define the another column
    @ColumnInfo(name = "wish-desc")
    val description: String= ""

    //all the make a row (id, string, string)
)