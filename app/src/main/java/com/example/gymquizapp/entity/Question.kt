package com.example.gymquizapp.entity

import android.graphics.drawable.Drawable
import java.security.InvalidParameterException

data class Question(
    val statement: String,
    val options: List<String>,
    val image: Drawable,
    val answer: Int){

    init {
        if (options.size != 4)
            throw InvalidParameterException("$options size must be 4.")

        if (answer > 4)
            throw InvalidParameterException("Right answer be between 0 and 4.")
    }
}
