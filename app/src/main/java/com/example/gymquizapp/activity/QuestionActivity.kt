package com.example.gymquizapp.activity

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.gymquizapp.FunctionResponse
import com.example.gymquizapp.R
import com.example.gymquizapp.databinding.QuestionActivityBinding
import com.example.gymquizapp.dialog.FeedbackMessageDialog
import com.example.gymquizapp.entity.Question
import com.example.gymquizapp.enumerator.MessageType
import com.example.gymquizapp.provider.QuestionProvider


class QuestionActivity : AppCompatActivity() {

    private lateinit var viewBinding: QuestionActivityBinding
    private lateinit var questions: MutableList<Question>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        viewBinding = QuestionActivityBinding.inflate(layoutInflater)
        setContentView(viewBinding.root)

        val getQuestionsResponse = QuestionProvider(this).getQuestions()
        if (getQuestionsResponse is FunctionResponse.Error){

            val restart = {

                val packageManager = applicationContext.packageManager
                val intent = packageManager.getLaunchIntentForPackage(applicationContext.packageName)
                val mainIntent = intent?.let { Intent.makeRestartActivityTask(it.component)}
                applicationContext.startActivity(mainIntent)
                Runtime.getRuntime().exit(0)
            }

            FeedbackMessageDialog(MessageType.Warning, this)
                .showFeedbackMessageDialog(
                    getString(R.string.warning),
                    "${getQuestionsResponse.error} Deseja reiniciar a aplicação?",
                    denialButtonVisible = true,
                    { restart() },
                    { finishAffinity() })

            return
        }
    }
}