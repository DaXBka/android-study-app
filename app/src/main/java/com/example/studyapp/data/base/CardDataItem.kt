package com.example.studyapp.data.base

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "items")
class CardDataItem(
    @ColumnInfo(name = "front") var front: String,
    @ColumnInfo(name = "back") var back: String
) {
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    var id: Int = 0

    @ColumnInfo(name = "is_image")
    var isImage: Boolean = false

}