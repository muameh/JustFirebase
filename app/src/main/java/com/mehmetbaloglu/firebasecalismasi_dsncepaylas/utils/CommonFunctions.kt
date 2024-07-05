package com.mehmetbaloglu.firebasecalismasi_dsncepaylas.utils

import com.google.firebase.Timestamp
import java.text.SimpleDateFormat
import java.util.Locale

object CommonFunctions {

    fun formatTimestampToString(timestamp: Timestamp): String {
        val sdf = SimpleDateFormat("yyyy/MM/dd HH:mm", Locale.getDefault())
        val date = timestamp.toDate()
        return sdf.format(date)
    }
}