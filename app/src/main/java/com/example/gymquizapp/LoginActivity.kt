package com.example.gymquizapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.gymquizapp.databinding.LoginActivityBinding

class LoginActivity : AppCompatActivity() {

    private lateinit var viewBinding: LoginActivityBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        viewBinding = LoginActivityBinding.inflate(layoutInflater)
        setContentView(viewBinding.root)
    }
}