package com.example.projetolenardo

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.ArrayAdapter
import android.widget.AutoCompleteTextView
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import com.google.android.material.card.MaterialCardView
import java.text.DecimalFormat
import kotlin.jvm.java
import kotlin.text.isEmpty
import kotlin.text.toDoubleOrNull
import kotlin.text.toIntOrNull


class Tela4Activity : AppCompatActivity() {

    enum class Genero {
        MASCULINO,
        FEMININO
    }

    // --- Declarar TODOS os componentes do XML ---

    // Campos de input
    private lateinit var etPeso: EditText
    private lateinit var etAltura: EditText
    private lateinit var etIdade: EditText
    private lateinit var actvSexo: AutoCompleteTextView // <-- CORRIGIDO

    // Botões
    private lateinit var btnCalcular: Button
    private lateinit var btnVoltar: Button

    // Card de Resultado
    private lateinit var cardResultado: MaterialCardView // <-- CORRIGIDO
    private lateinit var tvResultadoTMB: TextView
    private lateinit var tvSedentario: TextView
    private lateinit var tvLevementeAtivo: TextView
    private lateinit var tvModeradamente: TextView
    private lateinit var tvMuitoAtivo: TextView
    private lateinit var tvExtremamenteAtivo: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_tela4)

        // --- Encontrar os componentes pelo ID ---
        etPeso = findViewById(R.id.etPeso)
        etAltura = findViewById(R.id.etAltura)
        etIdade = findViewById(R.id.etIdade)
        actvSexo = findViewById(R.id.actvSexo)
        btnCalcular = findViewById(R.id.btnCalcular)
        btnVoltar = findViewById(R.id.btnVoltar)
        cardResultado = findViewById(R.id.cardResultado)
        tvResultadoTMB = findViewById(R.id.tvResultadoTMB)
        tvSedentario = findViewById(R.id.tvSedentario)
        tvLevementeAtivo = findViewById(R.id.tvLevementeAtivo)
        tvModeradamente = findViewById(R.id.tvModeradamente)
        tvMuitoAtivo = findViewById(R.id.tvMuitoAtivo)
        tvExtremamenteAtivo = findViewById(R.id.tvExtremamenteAtivo)

        // --- Configurar o Spinner  ---
        val generos = arrayOf("Masculino", "Feminino")
        val adapter = ArrayAdapter(this, android.R.layout.simple_dropdown_item_1line, generos)
        actvSexo.setAdapter(adapter)
        actvSexo.setText(generos[0], false) // Define "Masculino" como padrão

        // --- Configurar os cliques dos botões ---
        btnCalcular.setOnClickListener {
            calcularTMB()
        }

        val btnVoltar = findViewById<Button>(R.id.btnVoltar)

        btnVoltar.setOnClickListener {
            startActivity(Intent(this, Tela2Activity::class.java))
        }
    }

    private fun calcularTMB() {
        // Pega os textos dos campos
        val pesoStr = etPeso.text.toString()
        val alturaStr = etAltura.text.toString()
        val idadeStr = etIdade.text.toString()
        val generoStr = actvSexo.text.toString() // Pega o gênero do spinner

        // Validação
        if (pesoStr.isEmpty() || alturaStr.isEmpty() || idadeStr.isEmpty()) {
            return
        }

        val peso = pesoStr.toDoubleOrNull()
        val altura = alturaStr.toDoubleOrNull()
        val idade = idadeStr.toIntOrNull()

        if (peso == null || altura == null || idade == null) {
            return
        }

        // Descobre o gênero
        val genero = if (generoStr == "Masculino") Genero.MASCULINO else Genero.FEMININO

        // Chama a lógica
        val tmb = executarCalculoTMB(peso, altura, idade, genero)

        // --- Preencher o Card de Resultado ---
        val formatador = DecimalFormat("#") // Sem casas decimais

        // 1. TMB Principal
        tvResultadoTMB.text = "${formatador.format(tmb)} kcal/dia"

        // 2. Níveis de Atividade (TDEE)
        tvSedentario.text = "Sedentário: ${formatador.format(tmb * 1.2)} kcal"
        tvLevementeAtivo.text = "Levemente ativo: ${formatador.format(tmb * 1.375)} kcal"
        tvModeradamente.text = "Moderadamente ativo: ${formatador.format(tmb * 1.55)} kcal"
        tvMuitoAtivo.text = "Muito ativo: ${formatador.format(tmb * 1.725)} kcal"
        tvExtremamenteAtivo.text = "Extremamente ativo: ${formatador.format(tmb * 1.9)} kcal"

        // 3. Tornar o Card visível!
        cardResultado.visibility = View.VISIBLE
    }

    /**
     * A fórmula de Mifflin-St Jeor
     */
    private fun executarCalculoTMB(
        peso: Double,
        altura: Double,
        idade: Int,
        genero: Genero
    ): Double {
        val partePeso = 10 * peso
        val parteAltura = 6.25 * altura
        val parteIdade = 5 * idade

        return if (genero == Genero.MASCULINO) {
            (partePeso + parteAltura - parteIdade + 5)
        } else { // FEMININO
            (partePeso + parteAltura - parteIdade - 161)
        }
    }
}
