package com.imran.noteme.db.model

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize
import java.util.*

@Parcelize
@Entity(tableName = "note")
data class Note(
    @PrimaryKey(autoGenerate = true)
    var id: Int = 0,
    var title: String = "",
    var description: String = "",
    var createdAt: Long?,
    var deadLine: String = "",
    var status: String = "",

    var email: String?,
    var phone: String?,
    var url: String?
) : Parcelable
