package com.example.examtrackerandmore

import android.os.Build
import android.text.Editable
import androidx.annotation.RequiresApi
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter


@RequiresApi(Build.VERSION_CODES.O)

// Function to get the current date
fun currentDate(): String? {
    // Current Date
    val current = LocalDateTime.now()

    // Format
    val formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy")

    return current.format(formatter)
}
