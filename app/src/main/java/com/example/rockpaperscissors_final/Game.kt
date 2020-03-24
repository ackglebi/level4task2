package com.example.rockpaperscissors_final

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.android.parcel.Parcelize
import java.util.*

@Parcelize
@Entity(tableName = "gameHistory")
data class Game (

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    var id: Long? = null,

    @ColumnInfo(name = "winner")
    var winner: String,

    @ColumnInfo(name = "date")
    var date: Date?,

    @ColumnInfo(name = "computerPick")
    var computerPick: Int,

    @ColumnInfo(name = "userPick")
    var userPick: Int

) : Parcelable