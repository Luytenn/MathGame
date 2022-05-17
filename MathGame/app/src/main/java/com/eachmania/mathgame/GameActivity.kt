package com.eachmania.mathgame

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.CountDownTimer
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import java.util.*
import kotlin.random.Random

class GameActivity : AppCompatActivity() {

    lateinit var textScore : TextView
    lateinit var textLife : TextView
    lateinit var textTime : TextView

    lateinit var textQuestion : TextView
    lateinit var editTextAnswer : EditText

    lateinit var buttonOK : Button
    lateinit var buttonNext : Button

    var correctAnswer = 0
    var userScore = 0
    var userLife = 3
    var tipoOperacion : String = ""

    lateinit var timer : CountDownTimer
    private val startTimerMillis : Long = 20000
    var timeLeftInMillis : Long = startTimerMillis

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_game)

        tipoOperacion = SharedPreferences().getSomeStringValue(Constante.PREF_OPERACION).toString()


        if(tipoOperacion.equals("Addition"))
        {
            supportActionBar!!.title = "Addition"
        }
        else if(tipoOperacion.equals("Subtraction")){
            supportActionBar!!.title = "Subtraction"
        }
        else
        {
            supportActionBar!!.title = "Multiplication"
        }


        val sf = SharedPreferences()

        Log.d("infomacion", "TIPO DE OPERACION : "+ sf.getSomeStringValue(Constante.PREF_OPERACION))

        textScore = findViewById(R.id.textViewScore)
        textLife = findViewById(R.id.textViewLife)
        textTime = findViewById(R.id.textViewTime)
        textQuestion = findViewById(R.id.textViewQuestion)
        editTextAnswer = findViewById(R.id.editTextAnswer)
        buttonOK = findViewById(R.id.buttonOK)
        buttonNext = findViewById(R.id.buttonNext)



        gameContinue()

        buttonOK.setOnClickListener {

            val input = editTextAnswer.text.toString()

            if(input == "")
            {
                Toast.makeText(applicationContext,"Please write an answer or click the next button",
                Toast.LENGTH_LONG) .show()
            }
            else
            {
                pauseTimer()

                var userAnswer = input.toInt()

                if(correctAnswer == userAnswer)
                {
                    userScore = userScore + 10
                    textQuestion.text = "Congratulations, your answer is correct"
                    textScore.text = userScore.toString()
                }
                else
                {
                    userLife--
                    textQuestion.text = "Sorry, your answer is wrong"
                    textLife.text = userLife.toString()

                }


            }



        }


        buttonNext.setOnClickListener(object: View.OnClickListener {
            override fun onClick(v: View){

                pauseTimer()
                resetTimer()
                gameContinue()
                editTextAnswer.setText("")
            }


        })

    }

    fun gameContinue()
    {

        val number1 = Random.nextInt(0,100)
        val number2 = Random.nextInt(0,100)

        typeOfOperation(tipoOperacion,number1,number2)

        startTimer()

    }


    fun typeOfOperation(tipo: String, number1: Int, number2: Int){

        if(tipo.equals("Addition"))
        {
            textQuestion.text = "$number1 + $number2"

            correctAnswer = number1 + number2
        }
        else if(tipo.equals("Subtraction"))
        {
            textQuestion.text = "$number1 - $number2"

            correctAnswer = number1 - number2
        }
        else
        {
            textQuestion.text = "$number1 X $number2"

            correctAnswer = number1 * number2
        }




    }

    fun startTimer()
    {


        timer = object : CountDownTimer(timeLeftInMillis, 100){
            override fun onTick(millisUntilFinished: Long) {
                timeLeftInMillis = millisUntilFinished
                updateText()
            }

            override fun onFinish() {

                pauseTimer()
                resetTimer()
                updateText()

                userLife--
                textLife.text = userLife.toString()
                textQuestion.text = "Sorry, Time is up!"


            }



        }.start()

    }

    fun updateText()
    {
        val remainingTime : Int =  (timeLeftInMillis / 1000).toInt()
        textTime.text = String.format(Locale.getDefault(),"%02d",remainingTime)
    }

    fun pauseTimer()
    {
        timer.cancel()
    }

    fun resetTimer()
    {
        timeLeftInMillis = startTimerMillis
        updateText()
    }


}