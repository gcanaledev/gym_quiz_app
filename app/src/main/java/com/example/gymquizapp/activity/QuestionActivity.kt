package com.example.gymquizapp.activity

import android.content.Intent
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

    private var questionNumberHolder: Int = 0
    private var questionsHitsHolder: Int = 0

    private var remainingQuestions: Int = 10
    private var userHasAnswered: Boolean = false
    private val userHasProgress: Boolean = remainingQuestions != 9 || userHasAnswered

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
                    { finish() })

            return
        }

        questions = getQuestionsResponse.responseData!!

        viewBinding.resetButton.setOnClickListener { resetQuestions() }
        viewBinding.returnButton.setOnClickListener { returnToLogin() }
        viewBinding.confirmationButton.setOnClickListener { confirmQuestion() }

        fillQuestionFields()
    }

    private fun returnToLogin(){

        if (userHasProgress){

            FeedbackMessageDialog(MessageType.Warning, this)
                .showFeedbackMessageDialog(
                    getString(R.string.warning),
                    "Caso queira prosseguir com a ação todo progresso será perdido.",
                    denialButtonVisible = true,
                    { Tools.restartApplication(this) })

            return
        }

        Tools.restartApplication(this)
    }

    private fun fillQuestionFields(){

        updateQuestionsHolders()

        val totalQuestions = 10

        with (getRandomQuestion()){

            viewBinding.questionTitle.text = String.format("Questão %d  ---  %d/%d", questionNumberHolder, questionsHitsHolder, totalQuestions)
            viewBinding.questionText.text = this.statement
            viewBinding.questionImage.setImageDrawable(this.image)
            viewBinding.confirmationButton.text = if (remainingQuestions == 0) "Finalizar questionário" else "Próxima questão"

            textViewOptions = listOf(viewBinding.optionOne, viewBinding.optionTwo,
                viewBinding.optionThree, viewBinding.optionFour)

            textViewOptions.forEachIndexed { index, it -> it.text = this.options[index] }

            textViewOptions.forEach { textView -> textView.setOnClickListener { answer(it as TextView, this) } }

            if (remainingQuestions == 10)
                return

            resetOptions()
        }
    }

    private fun showCorrectOption(textView: TextView){
        textView.background = ContextCompat.getDrawable(this, R.drawable.bg_correct_answer)
    }

    private fun showWrongOption(textView: TextView){
        textView.background = ContextCompat.getDrawable(this, R.drawable.bg_wrong_answer)
    }

    private fun disableOptions(){ textViewOptions.forEach { it.isEnabled = false; it.isClickable = false } }

    private fun resetOptions(){

        userHasAnswered = false

        textViewOptions.forEach {
            it.background = ContextCompat.getDrawable(applicationContext, R.drawable.bg_rounded_quaternary)
            it.isEnabled = true
            it.isClickable = true
        }
    }

    private fun updateQuestionsHolders(){

        questionNumberHolder++
        remainingQuestions--
    }

    private fun answer(userChosenTextView: TextView, question: Question){

        userHasAnswered = true

        val correctOption = question.options[question.answer - 1]

        if (userChosenTextView.text == correctOption){

            showCorrectOption(userChosenTextView)
            disableOptions()

            questionsHitsHolder++
            return
        }

        val correctQuestionTextView = getCorrectQuestionTextView(correctOption)
        if (correctQuestionTextView == null){

            FeedbackMessageDialog(MessageType.Error, this)
                .showFeedbackMessageDialog(
                    getString(R.string.error),
                    "Não foi possível localizar a opção correta :( \nDeseja reiniciar a aplicação?",
                    denialButtonVisible = true,
                    { Tools.restartApplication(this) },
                    { finishAffinity() })

            return
        }

        showWrongOption(userChosenTextView)
        showCorrectOption(correctQuestionTextView)
        disableOptions()
    }


    private fun getCorrectQuestionTextView(correctOption: String): TextView? {
       return textViewOptions.find { it.text == correctOption }
    }

    private fun confirmQuestion(){

         val isLastQuestion = remainingQuestions == 0

        val navigateToResultActivity = {

            Intent(this, ResultActivity::class.java)
                .also { it.putExtra(ResultActivity.EXTRA_HITS, questionsHitsHolder)  }
                .also { startActivity(it) }

            finish()
        }

        if (!userHasAnswered){

            FeedbackMessageDialog(MessageType.Warning, this)
                .showFeedbackMessageDialog(
                    getString(R.string.not_answered_question),
                    "Caso queira prosseguir com a ação a questão será contabilizada como errada.",
                    denialButtonVisible = true,
                    { if (isLastQuestion) navigateToResultActivity() else fillQuestionFields() })

            return
        }

        if (isLastQuestion){ navigateToResultActivity(); return}

        fillQuestionFields()
    }

    private fun getRandomQuestion(): Question{

        val randomQuestion = questions.random()
        questions.removeAt(questions.indexOf(randomQuestion))

        return randomQuestion
    }

    private fun resetQuestions(){

        if (userHasProgress){

            FeedbackMessageDialog(MessageType.Warning, this)
                .showFeedbackMessageDialog(
                    getString(R.string.warning),
                    "Caso queira prosseguir com a ação todo progresso será perdido.",
                    denialButtonVisible = true,
                    { recreate() })

            return
        }

        recreate()
    }
}