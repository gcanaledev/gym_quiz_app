package com.example.gymquizapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.gymquizapp.databinding.QuestionActivityBinding

class QuestionActivity : AppCompatActivity() {

    private lateinit var viewBinding: QuestionActivityBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        viewBinding = QuestionActivityBinding.inflate(layoutInflater)
        setContentView(viewBinding.root)
    }
}