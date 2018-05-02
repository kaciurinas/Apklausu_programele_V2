package com.example.rokas.apklausuprogramele.ui

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.ViewGroup
import android.widget.Button
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import com.example.rokas.apklausuprogramele.R
import com.example.rokas.apklausuprogramele.network.ApiClient
import com.example.rokas.apklausuprogramele.network.models.Answer
import com.example.rokas.apklausuprogramele.network.models.Question

class SurveyActivity : AppCompatActivity() {

    val QUESTION_ID = 24

    lateinit var surveyNrText: TextView
    lateinit var answerLinearLayout: LinearLayout

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_survey)

        surveyNrText= findViewById(R.id.surveyNr)
        answerLinearLayout = findViewById(R.id.answersLinearLayout)

        val activeSurvey = intent.getStringExtra("activeSurvey")
        surveyNrText.text = activeSurvey

        loadData { questionList ->
            startSurvery(questionList)
        }
    }

    fun startSurvery(questionList: List<Question>) {
        if (questionList.isNotEmpty()) {
            bind(questionList.first(), { question, answer ->
                saveAnswer(question, answer)
                startSurvery(questionList.drop(1))
            })
        } else {
            surveyEnded()
        }
    }

    fun loadData(callback: (List<Question>) -> Unit) {
        ApiClient.getQuestions(this, QUESTION_ID, { questionsList ->
            callback(questionsList)
        }, { errorMessage ->
            Toast.makeText(this, errorMessage, Toast.LENGTH_SHORT).show()
        })
    }

    fun saveAnswer(question: Question, answer: Answer) {
        // TODO implement save API
    }

    fun surveyEnded() {
        finish()
    }

    fun bind(question : Question,
             onAnswered: (Question, Answer) -> Unit) {
        surveyNrText.text = question.text
        answerLinearLayout.removeAllViews()

        ApiClient.loadAnswers(this, question.id, { answerList ->
            answerList.forEach { answerModel ->
                val button = Button(this)
                button.layoutParams = ViewGroup.LayoutParams(
                        ViewGroup.LayoutParams.MATCH_PARENT,
                        ViewGroup.LayoutParams.WRAP_CONTENT
                )
                button.text = answerModel.text
                button.setOnClickListener {
                    onAnswered(question, answerModel)
                }
                answerLinearLayout.addView(button)
            }
        }, { errorMessage ->
            Toast.makeText(this, errorMessage, Toast.LENGTH_SHORT).show()
        })
    }
}


