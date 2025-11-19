package com.example.projetolenardo

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import kotlin.jvm.java
import kotlin.math.pow
import kotlin.text.format
import kotlin.text.isEmpty
import kotlin.text.toDouble
import kotlin.text.toInt

class Tela3Activity : AppCompatActivity() {

    private lateinit var editAltura: EditText
    private lateinit var editPeso: EditText
    private lateinit var editIdade: EditText
    private lateinit var btnCalcular: Button
    private lateinit var txtResultado: TextView
    private lateinit var txtClassificacao: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_tela3)

        // Inicializar views
        editAltura = findViewById(R.id.editAltura)
        editPeso = findViewById(R.id.editPeso)
        editIdade = findViewById(R.id.editIdade)
        btnCalcular = findViewById(R.id.btnCalcular)
        txtResultado = findViewById(R.id.txtResultado)
        txtClassificacao = findViewById(R.id.txtClassificacao)

        btnCalcular.setOnClickListener {
            calcularIMC()
        }

        val btnVolta2 = findViewById<Button>(R.id.btnVolta2)

        btnVolta2.setOnClickListener {
            startActivity(Intent(this, Tela2Activity::class.java))
        }

    }

    private fun calcularIMC() {
        val alturaStr = editAltura.text.toString()
        val pesoStr = editPeso.text.toString()
        val idadeStr = editIdade.text.toString()

        // Validar campos
        if (alturaStr.isEmpty() || pesoStr.isEmpty() || idadeStr.isEmpty()) {
            txtResultado.text = "Preencha todos os campos!"
            txtClassificacao.text = ""
            return
        }

        try {
            val altura = alturaStr.toDouble()
            val peso = pesoStr.toDouble()
            val idade = idadeStr.toInt()

            // Validar valores
            if (altura <= 0 || peso <= 0 || idade <= 0) {
                txtResultado.text = "Digite valores válidos!"
                txtClassificacao.text = ""
                return
            }

            // Calcular IMC
            val imc = peso / altura.pow(2)

            // Classificar IMC
            val classificacao = when {
                imc < 18.5 -> "Abaixo do peso"
                imc < 25 -> "Peso normal"
                imc < 30 -> "Sobrepeso"
                imc < 35 -> "Obesidade Grau I"
                imc < 40 -> "Obesidade Grau II"
                else -> "Obesidade Grau III"
            }

            // Mostrar resultado
            txtResultado.text = String.format("Seu IMC: %.2f", imc)
            txtClassificacao.text = classificacao
            txtClassificacao.setTextColor(getCorClassificacao(imc))

        } catch (e: NumberFormatException) {
            txtResultado.text = "Valores inválidos!"
            txtClassificacao.text = ""
        }
    }

    private fun getCorClassificacao(imc: Double): Int {
        return when {
            imc < 18.5 -> getColor(android.R.color.holo_orange_light)
            imc < 25 -> getColor(android.R.color.holo_green_dark)
            imc < 30 -> getColor(android.R.color.holo_orange_dark)
            else -> getColor(android.R.color.holo_red_dark)
        }
    }
}