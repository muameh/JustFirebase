package com.mehmetbaloglu.firebasecalismasi_dsncepaylas.data.model

import com.google.firebase.Timestamp
import java.io.Serializable

data class Post(
    var postId: String? = null,
    var userMail: String? = null,
    var userMessage: String? = null,
    var postUrl: String? = null,
    var postDate: Timestamp? = null
) : Serializable {
    constructor() : this(null, null, null, null, null)
}

