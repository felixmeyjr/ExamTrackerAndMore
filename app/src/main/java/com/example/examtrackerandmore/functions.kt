package com.example.examtrackerandmore

import android.os.Build
import androidx.annotation.RequiresApi
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter


@RequiresApi(Build.VERSION_CODES.O)

// Function to get the current date
fun currentDate(): String? {
    // Current Date
    val current = LocalDateTime.now()

    // Format
    val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.SSS")
    val formatted = current.format(formatter)

    return formatted
}

fun main() {

}

