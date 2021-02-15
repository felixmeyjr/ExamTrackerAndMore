package com.example.examtrackerandmore.data

import android.os.Build
import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.android.parcel.Parcelize
import java.text.DateFormat
import java.time.format.DateTimeFormatter
import java.util.*

@Entity(tableName = "exam_table") // necessary for the sqlite room package
@Parcelize // parcelize is necessary to send data between fragments
data class Exam(
    val name: String,
    val date: String,
    val no_lecs: Int,
    val no_tuts: Int,
    // todo relative values etc
    // todo add visibility bool
    // todo add daysLeft

    @PrimaryKey(autoGenerate = true) val id: Int = 0 // necessary for the sqlite room package
) : Parcelable {
    val createDateFormatted: String
        get() = DateFormat.getDateInstance().format(date)
}