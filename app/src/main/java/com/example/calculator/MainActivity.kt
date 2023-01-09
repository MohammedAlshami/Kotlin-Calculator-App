package com.example.calculator

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.core.content.ContextCompat
import org.mariuszgromada.math.mxparser.Expression
import java.text.DecimalFormat

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val input: TextView = findViewById(R.id.calInput)

        val output: TextView = findViewById(R.id.calOutput)

        //Clear the entire input query
        val clear: Button = findViewById(R.id.btnClear)

        // Remove last input query
        val back: Button = findViewById(R.id.btnBack)

        // Calculate the input and return the output
        val calculate: Button = findViewById(R.id.btnCalculate)

        val buttons = mapOf<String, Button>(
            "%" to findViewById(R.id.btnModulus),
            "÷" to findViewById(R.id.btnDivision),
            "×" to findViewById(R.id.btnMultiply),
            "-" to findViewById(R.id.btnSubtraction),
            "+" to findViewById(R.id.btnAddition),
            "." to findViewById(R.id.btnDot),
            "0" to findViewById(R.id.btn0),
            "1" to findViewById(R.id.btn1),
            "2" to findViewById(R.id.btn2),
            "3" to findViewById(R.id.btn3),
            "4" to findViewById(R.id.btn4),
            "5" to findViewById(R.id.btn5),
            "6" to findViewById(R.id.btn6),
            "7" to findViewById(R.id.btn7),
            "8" to findViewById(R.id.btn8),
            "9" to findViewById(R.id.btn9)
            )

        clear.setOnClickListener{
            input.text = ""
            output.text = ""
        }
        back.setOnClickListener {
            input.text = "${input.text}".dropLast(1)
        }

        // Setting the click listeners
        addToInput(input, buttons)

        // Getting the output when equal symbol is clicked
        calculate.setOnClickListener {
            output(input, output)
        }



    }

    @SuppressLint("SetTextI18n")
    private fun addToInput(input: TextView, buttons: Map<String, Button>) {
        for((key, value) in buttons){
            value.setOnClickListener {
                input.text = "${input.text}$key"
            }
        }

    }

    private fun output(input: TextView, output : TextView) {

        // Parsing the input
        var expression = input.text.replace(Regex("×"), "*")
        expression = expression.replace(Regex("÷"), "/")

        // Calculating and displaying the output
        try {
            val result = Expression(expression).calculate()

            if (result.isNaN()){
                output.text = "Error"
                output.setTextColor(ContextCompat.getColor(this, R.color.red))

            }
            else {
                output.text = DecimalFormat("0.00").format(result).toString()
                output.setTextColor(ContextCompat.getColor(this, R.color.green))
            }
        }catch (e: Exception){
            output.text = "Error - $e"
            output.setTextColor(ContextCompat.getColor(this, R.color.red))

        }


    }

}