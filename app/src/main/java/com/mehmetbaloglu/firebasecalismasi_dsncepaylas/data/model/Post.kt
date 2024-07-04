package com.mehmetbaloglu.firebasecalismasi_dsncepaylas.data.model

import java.io.Serializable

data class Post(var userMail: String?,
           var postMessage: String?,
           var PostUrl: String?,
           var postDate: Any?) : Serializable

