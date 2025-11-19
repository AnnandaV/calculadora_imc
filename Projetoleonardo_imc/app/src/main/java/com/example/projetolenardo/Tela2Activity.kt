package com.example.projetolenardo

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import kotlin.jvm.java

class Tela2Activity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_tela2)

        val btnImc = findViewById<Button>(R.id.btnImc)
        val btnTmb = findViewById<Button>(R.id.btnTmb)
        val btnVolta = findViewById<Button>(R.id.btnVolta)

        btnImc.setOnClickListener {
            startActivity(Intent(this, Tela3Activity::class.java))
        }

        btnTmb.setOnClickListener {
            startActivity(Intent(this, Tela4Activity::class.java))
        }

        btnVolta.setOnClickListener {
            startActivity(Intent(this, MainActivity::class.java))
        }

    }
}