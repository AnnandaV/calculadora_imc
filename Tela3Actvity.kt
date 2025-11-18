package com.example.calculator

import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {

    private lateinit var tvDisplay: TextView
    private var currentNumber = ""
    private var operator = ""
    private var firstNumber = 0.0
    private var isNewOperation = true

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        tvDisplay = findViewById(R.id.tvDisplay)

        setupNumberButtons()
        setupOperatorButtons()
        setupFunctionButtons()
    }

    private fun setupNumberButtons() {
        val numberButtons = listOf(
            R.id.btn0, R.id.btn1, R.id.btn2, R.id.btn3, R.id.btn4,
            R.id.btn5, R.id.btn6, R.id.btn7, R.id.btn8, R.id.btn9
        )

        numberButtons.forEach { id ->
            findViewById<Button>(id).setOnClickListener { view ->
                val button = view as Button
                numberPressed(button.text.toString())
            }
        }

        findViewById<Button>(R.id.btnDot).setOnClickListener {
            dotPressed()
        }
    }

    private fun setupOperatorButtons() {
        findViewById<Button>(R.id.btnAdd).setOnClickListener {
            operatorPressed("+")
        }
        findViewById<Button>(R.id.btnSubtract).setOnClickListener {
            operatorPressed("-")
        }
        findViewById<Button>(R.id.btnMultiply).setOnClickListener {
            operatorPressed("×")
        }
        findViewById<Button>(R.id.btnDivide).setOnClickListener {
            operatorPressed("÷")
        }
        findViewById<Button>(R.id.btnPercent).setOnClickListener {
            percentPressed()
        }
        findViewById<Button>(R.id.btnEquals).setOnClickListener {
            equalsPressed()
        }
    }

    private fun setupFunctionButtons() {
        findViewById<Button>(R.id.btnClear).setOnClickListener {
            clearAll()
        }
        findViewById<Button>(R.id.btnDelete).setOnClickListener {
            deleteLastCharacter()
        }
        findViewById<Button>(R.id.btnPlusMinus).setOnClickListener {
            toggleSign()
        }
    }

    private fun numberPressed(number: String) {
        if (isNewOperation) {
            currentNumber = ""
            isNewOperation = false
        }

        if (currentNumber == "0" && number != "0") {
            currentNumber = number
        } else {
            currentNumber += number
        }

        updateDisplay(currentNumber)
    }

    private fun dotPressed() {
        if (isNewOperation) {
            currentNumber = "0"
            isNewOperation = false
        }

        if (!currentNumber.contains(".")) {
            if (currentNumber.isEmpty()) {
                currentNumber = "0"
            }
            currentNumber += "."
            updateDisplay(currentNumber)
        }
    }

    private fun operatorPressed(op: String) {
        if (currentNumber.isNotEmpty()) {
            if (operator.isNotEmpty()) {
                equalsPressed()
            } else {
                firstNumber = currentNumber.toDoubleOrNull() ?: 0.0
            }
        }

        operator = op
        isNewOperation = true
    }

    private fun equalsPressed() {
        if (operator.isEmpty() || currentNumber.isEmpty()) {
            return
        }

        val secondNumber = currentNumber.toDoubleOrNull() ?: 0.0
        val result = when (operator) {
            "+" -> firstNumber + secondNumber
            "-" -> firstNumber - secondNumber
            "×" -> firstNumber * secondNumber
            "÷" -> {
                if (secondNumber != 0.0) {
                    firstNumber / secondNumber
                } else {
                    updateDisplay("Erro")
                    clearAll()
                    return
                }
            }
            else -> secondNumber
        }

        currentNumber = formatResult(result)
        firstNumber = result
        operator = ""
        isNewOperation = true
        updateDisplay(currentNumber)
    }

    private fun percentPressed() {
        if (currentNumber.isNotEmpty()) {
            val number = currentNumber.toDoubleOrNull() ?: 0.0
            val result = number / 100
            currentNumber = formatResult(result)
            updateDisplay(currentNumber)
            isNewOperation = true
        }
    }

    private fun clearAll() {
        currentNumber = ""
        operator = ""
        firstNumber = 0.0
        isNewOperation = true
        updateDisplay("0")
    }

    private fun deleteLastCharacter() {
        if (currentNumber.isNotEmpty() && !isNewOperation) {
            currentNumber = currentNumber.dropLast(1)
            if (currentNumber.isEmpty()) {
                updateDisplay("0")
            } else {
                updateDisplay(currentNumber)
            }
        }
    }

    private fun toggleSign() {
        if (currentNumber.isNotEmpty() && currentNumber != "0") {
            if (currentNumber.startsWith("-")) {
                currentNumber = currentNumber.substring(1)
            } else {
                currentNumber = "-$currentNumber"
            }
            updateDisplay(currentNumber)
        }
    }

    private fun formatResult(result: Double): String {
        return if (result == result.toLong().toDouble()) {
            result.toLong().toString()
        } else {
            String.format("%.8f", result).trimEnd('0').trimEnd('.')
        }
    }

    private fun updateDisplay(value: String) {
        tvDisplay.text = value
    }
}
