package com.example.gymquizapp.activity

import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.example.gymquizapp.FunctionResponse
import com.example.gymquizapp.R
import com.example.gymquizapp.Tools
import com.example.gymquizapp.databinding.QuestionActivityBinding
import com.example.gymquizapp.dialog.FeedbackMessageDialog
import com.example.gymquizapp.entity.Question
import com.example.gymquizapp.enumerator.MessageType
import com.example.gymquizapp.provider.QuestionProvider


class QuestionActivity : AppCompatActivity() {

    private lateinit var viewBinding: QuestionActivityBinding
    private lateinit var questions: MutableList<Question>

    private var questionNumberCounter: Int = 1

    private lateinit var textViewOptions: List<TextView>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        viewBinding = QuestionActivityBinding.inflate(layoutInflater)
        setContentView(viewBinding.root)

        val getQuestionsResponse = QuestionProvider(this).getQuestions()
        if (getQuestionsResponse is FunctionResponse.Error){

            FeedbackMessageDialog(MessageType.Warning, this)
                .showFeedbackMessageDialog(
                    getString(R.string.warning),
                    "${getQuestionsResponse.error} Deseja reiniciar a aplicação?",
                    denialButtonVisible = true,
                    { Tools.restartApplication(this) },
                    { finishAffinity() })

            return
        }

        questions = getQuestionsResponse.responseData!!

        viewBinding.resetButton.setOnClickListener { resetQuestions() }
        viewBinding.returnButton.setOnClickListener { Tools.restartApplication(this) }

        fillQuestionFields()
    }

    private fun fillQuestionFields(){

        with (getRandomQuestion()){

            viewBinding.questionTitle.text = String.format("Pergunta número %d", questionNumberCounter)
            viewBinding.questionText.text = this.statement
            viewBinding.questionImage.setImageDrawable(this.image)
            viewBinding.confirmationButton.text = if (questions.size != 1) "Próxima questão" else "Finalizar questionário"

            textViewOptions = listOf(viewBinding.optionOne, viewBinding.optionTwo,
                viewBinding.optionThree, viewBinding.optionFour)

            textViewOptions.forEachIndexed { index, it -> it.text = this.options[index] }

            textViewOptions.forEach { textView -> textView.setOnClickListener { answer(it as TextView, this) } }
        }
    }

    private fun turnTextViewToGreen(textView: TextView){
        textView.background = ContextCompat.getDrawable(this, R.drawable.bg_correct_answer)
    }

    private fun turnTextViewToRed(textView: TextView){
        textView.background = ContextCompat.getDrawable(this, R.drawable.bg_wrong_answer)
    }

    private fun disableOptions(){
        textViewOptions.forEach { it.isEnabled = false; it.isClickable = false }
    }

    private fun answer(userChosenTextView: TextView, question: Question){

        val correctOption = question.options[question.answer - 1]

        if (userChosenTextView.text == correctOption){
            turnTextViewToGreen(userChosenTextView)
            disableOptions()
            return
        }

        val correctQuestionTextView = getCorrectQuestionTextView(correctOption)
        if (correctQuestionTextView == null){

            FeedbackMessageDialog(MessageType.Error, this)
                .showFeedbackMessageDialog(
                    getString(R.string.error),
                    "Não foi possível obter a resposta correta :( \nDeseja reiniciar a aplicação?",
                    denialButtonVisible = true,
                    { Tools.restartApplication(this) },
                    { finishAffinity() })

            return
        }

        turnTextViewToRed(userChosenTextView)
        turnTextViewToGreen(correctQuestionTextView)
    }


    private fun getCorrectQuestionTextView(correctOption: String): TextView? {
       return textViewOptions.find { it.text == correctOption }
    }

    private fun confirmQuestion(){

        questionNumberCounter++

        fillQuestionFields()
    }

    private fun getRandomQuestion(): Question{

        val randomQuestionNumber = (0..questions.size).random()

        val randomQuestion = questions[randomQuestionNumber]

        questions.removeAt(randomQuestionNumber)

        return randomQuestion
    }

    private fun resetQuestions(){

        questionNumberCounter = 1

        recreate()
    }
}