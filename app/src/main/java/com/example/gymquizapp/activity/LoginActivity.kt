package com.example.gymquizapp.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ArrayAdapter
import com.example.gymquizapp.enumerator.GymExperienceLevel
import com.example.gymquizapp.R
import com.example.gymquizapp.databinding.LoginActivityBinding
import com.example.gymquizapp.dialog.FeedbackMessageDialog
import com.example.gymquizapp.enumerator.MessageType

class LoginActivity : AppCompatActivity() {

    private lateinit var viewBinding: LoginActivityBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        viewBinding = LoginActivityBinding.inflate(layoutInflater)
        setContentView(viewBinding.root)

        populateExperienceSpinner()

        viewBinding.playButton.setOnClickListener { playButtonOnClicked() }
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

            GymExperienceLevel.Starter,
            GymExperienceLevel.Intermediary -> {

                FeedbackMessageDialog(MessageType.Warning, this)
                    .showFeedbackMessageDialog(
                        getString(R.string.warning_message_body),
                        { navigateToQuestionActivity() },
                        getString(R.string.warning_message_title))
            }

            GymExperienceLevel.Bodybuilder -> {

                FeedbackMessageDialog(MessageType.Success, this)
                    .showFeedbackMessageDialog(
                        getString(R.string.success_message_body),
                        { navigateToQuestionActivity() },
                        getString(R.string.success_message_title))
            }
        }
    }

    private fun navigateToQuestionActivity() {
        startActivity(Intent(this, QuestionActivity::class.java))
    }
}