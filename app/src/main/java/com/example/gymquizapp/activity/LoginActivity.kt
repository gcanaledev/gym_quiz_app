package com.example.gymquizapp.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ArrayAdapter
import com.example.gymquizapp.enumerator.GymExperienceLevel
import com.example.gymquizapp.R
import com.example.gymquizapp.databinding.LoginActivityBinding

class LoginActivity : AppCompatActivity() {

    private lateinit var viewBinding: LoginActivityBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        viewBinding = LoginActivityBinding.inflate(layoutInflater)
        setContentView(viewBinding.root)

        populateExperienceSpinner()

        viewBinding.playButton.setOnClickListener {  }
    }

    private fun populateExperienceSpinner(){

        ArrayAdapter.createFromResource(
            this,
            R.array.user_experience_levels,
            android.R.layout.simple_spinner_item
        ).also { adapter ->

            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            viewBinding.experienceSpinner.adapter = adapter
        }
    }

    private fun playButtonOnClicked(){

        val userChosenGymExperience = enumValueOf<GymExperienceLevel>(
            viewBinding.experienceSpinner.selectedItem.toString())

        when (userChosenGymExperience){
            GymExperienceLevel.Starter ->
                GymExperienceLevel.Intermediary

            ->
                GymExperienceLevel.Bodybuilder

            ->
        }

    }
}