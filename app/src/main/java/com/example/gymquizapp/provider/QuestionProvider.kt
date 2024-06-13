package com.example.gymquizapp.provider

import android.content.Context
import androidx.core.content.ContextCompat
import com.example.gymquizapp.FunctionResponse
import com.example.gymquizapp.R
import com.example.gymquizapp.entity.Question

class QuestionProvider(private val context: Context) {

    fun getQuestions(): FunctionResponse<MutableList<Question>>{

        val questionOneImage = ContextCompat.getDrawable(context, R.drawable.img_triceps_workout)
        val questionTwoImage = ContextCompat.getDrawable(context, R.drawable.img_abs_workout)
        val questionThreeImage = ContextCompat.getDrawable(context, R.drawable.img_calf_workout)
        val questionFourImage = ContextCompat.getDrawable(context, R.drawable.img_chest_workout)
        val questionFiveImage = ContextCompat.getDrawable(context, R.drawable.img_concentrated_workout)
        val questionSixImage = ContextCompat.getDrawable(context, R.drawable.img_deadlift_workout)
        val questionSevenImage = ContextCompat.getDrawable(context, R.drawable.img_glute_workout)
        val questionEightImage = ContextCompat.getDrawable(context, R.drawable.img_shoulder_workout)
        val questionNineImage = ContextCompat.getDrawable(context, R.drawable.img_squat_workout)
        val questionTenImage = ContextCompat.getDrawable(context, R.drawable.img_back_workout)

        val errorLoadingImages = listOf(questionOneImage, questionTwoImage, questionThreeImage,
               questionFourImage, questionFiveImage, questionSixImage,
               questionSevenImage, questionEightImage, questionNineImage,
               questionTenImage).any { it == null }

        if (errorLoadingImages)
            return FunctionResponse.Error("Não foi possível carregar todas as imagens.")

        val questionOne = Question(
            "O exercício apresentado abaixo trabalha qual grupo muscular?",
            listOf("Ombro", "Glúteos", "Triceps", "Biceps"),
            questionOneImage!!,
            3
        )

        val questionTwo = Question(
            "O exercício apresentado abaixo tem como objetivo...",
            listOf("Trabalhar os braços", "Trabalhar o peitoral", "Trabalhar o ombro", "Nenhuma das alternativas"),
            questionTwoImage!!,
            4
        )

        val questionThree = Question(
            "O exercício conhecido como \"Pular corda\", tem foco em qual grupo muscular?",
            listOf("Panturrilha", "Quadriceps", "Biceps", "Ombro"),
            questionThreeImage!!,
            1
        )

        val questionFour = Question(
            "Se feito corretamente, qual dos grupos musculares a seguir são o foco do exercício abaixo?",
            listOf("Peitoral e ombro", "Peitoral e triceps", "Triceps e ombro", "Triceps e biceps"),
            questionFourImage!!,
            2
        )

        val questionFive = Question(
            "No exercício mostrado abaixo, qual grupo muscular é trabalhado?",
            listOf("Triceps", "Panturrilha", "Antebraço", "Biceps"),
            questionFiveImage!!,
            4
        )

        val questionSix = Question(
            "O objetivo principal deste exercício é...",
            listOf("Aumento de força", "Resistência", "Definição muscular", "Aumento de fôlego"),
            questionSixImage!!,
            1
        )

        val questionSeven = Question(
            "Esta variação de agachamento tem foco em qual grupo muscular?",
            listOf("Glúteos", "Quadriceps", "Panturilha", "Posterior de coxa"),
            questionSevenImage!!,
            1
        )

        val questionEight = Question(
            "O movimento apresentado abaixo trabalha qual grupo muscular?",
            listOf("Antebraço", "Biceps", "Triceps", "Ombro"),
            questionEightImage!!,
            4
        )

        val questionNine = Question(
            "Esta variação de agachamento tem foco em qual grupo muscular?",
            listOf("Glúteos", "Quadriceps", "Panturilha", "Posterior de coxa"),
            questionNineImage!!,
            2
        )

        val questionTen = Question(
            "Este exercício feito na polia, trabalha...",
            listOf("O peitoral", "As costas", "O trapézio", "O triceps"),
            questionTenImage!!,
            2
        )

        val questions = mutableListOf(questionOne, questionTwo, questionThree,
                questionFour, questionFive, questionSix,
                questionSeven, questionEight, questionNine,
                questionTen)

        return FunctionResponse.Success(questions)
    }
}