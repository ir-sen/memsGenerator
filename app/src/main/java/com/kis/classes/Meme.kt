package com.kis.classes

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "mem_playersDb")
data class Meme(
    @ColumnInfo(name = "box_count")
    var box_count: Int = 0,
    @ColumnInfo(name = "height")
    var height: Int = 0,
    @PrimaryKey(autoGenerate = false)
    var id: String = "",
    @ColumnInfo(name = "name")
    var name: String = "",
    @ColumnInfo(name = "url")
    var url: String = "",
    @ColumnInfo(name = "width")
    var width: Int = 0,
)