package com.example.gymquizapp.activity

import android.content.Intent
import android.graphics.drawable.Drawable
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.core.content.ContextCompat
import com.example.gymquizapp.R
import com.example.gymquizapp.Tools
import com.example.gymquizapp.databinding.ResultActivityBinding
import com.example.gymquizapp.dialog.FeedbackMessageDialog
import com.example.gymquizapp.enumerator.MessageType

class ResultActivity : AppCompatActivity() {

    private lateinit var viewBinding: ResultActivityBinding
    private var userHits: Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.result_activity)

        viewBinding = ResultActivityBinding.inflate(layoutInflater)
        setContentView(viewBinding.root)

        userHits = intent.getIntExtra(EXTRA_HITS, -1)
        verifyReceivedValue(userHits)

        viewBinding.resultShower.text = resources.getString(R.string.resultShower, userHits)
        viewBinding.userDescription.text = getUserCorrespondingRepresentationDescription()
        viewBinding.userDescriptionImage.setImageDrawable(getUserCorrespondingRepresentationImage())

        viewBinding.finishButton.setOnClickListener { finish() }
        viewBinding.restartButton.setOnClickListener { Tools.restartApplication(applicationContext) }
    }

    private fun verifyReceivedValue(extra: Int){
        if (extra == -1){

            FeedbackMessageDialog(MessageType.Error, this)
                .showFeedbackMessageDialog(
                    getString(R.string.error),
                    getString(R.string.not_possible_to_load_result),
                    confirmationButtonAction = { finish() })

            return
        }
    }

    private fun getUserCorrespondingRepresentationDescription(): String{
        if ((0..4).contains(userHits))
            return "Iniciante"

        if ((5..9).contains(userHits))
            return "Intermediário"

        return "Bodybuilder"
    }

    private fun getUserCorrespondingRepresentationImage(): Drawable?{
        if ((0..4).contains(userHits))
            return ContextCompat.getDrawable(applicationContext, R.drawable.ic_skinny)

        if ((5..9).contains(userHits))
            return ContextCompat.getDrawable(applicationContext, R.drawable.ic_medium_strong)

        return ContextCompat.getDrawable(applicationContext, R.drawable.ic_bodybuilder)
    }

    companion object{
        const val EXTRA_HITS = "hits"
    }
}