package com.example.mathgame

import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import kotlin.random.Random

class MainActivity : AppCompatActivity() {
    private lateinit var num1: TextView
    private lateinit var num2: TextView
    private lateinit var respostaCorreta: TextView
    private lateinit var resposta: EditText
    private lateinit var background: View
    private lateinit var proximo: Button
    private lateinit var textoPontos: TextView
    private var contadorJogos = 0
    private var pontos = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)


        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }


        num1 = findViewById(R.id.numX)
        num2 = findViewById(R.id.numY)
        respostaCorreta = findViewById(R.id.respostaCorreta)
        resposta = findViewById(R.id.inputResposta)
        proximo = findViewById(R.id.proximo)
        background = findViewById(R.id.main)
        textoPontos = findViewById(R.id.textoPontos)

        // Gera uma nova conta
        gerarConta()
    }

    private fun gerarConta() {
        if (contadorJogos < 5) {
            val X = Random.nextInt(0, 100)
            val Y = Random.nextInt(0, 100)
            val res = X + Y
            num1.text = "$X"
            num2.text = "$Y"
            respostaCorreta.text = res.toString()
            resposta.text.clear()
            respostaCorreta.visibility = View.INVISIBLE
            proximo.visibility = View.INVISIBLE
            background.setBackgroundColor(ContextCompat.getColor(this, android.R.color.white))


            contadorJogos++
            textoPontos.text = "Jogo: $contadorJogos | Pontos: $pontos"
        } else {
            // Limite de 5 jogos atingido
            resposta.isEnabled = false // Desativa a entrada de resposta
            proximo.visibility = View.INVISIBLE // Esconde o botão "Próximo"
            respostaCorreta.text = "Você completou 5 jogos!"; // Mensagem final
            respostaCorreta.visibility = View.VISIBLE
        }
    }

    fun conferir(view: View) {
        val respostaUsuario = resposta.text.toString().toIntOrNull()
        val respostaCerta = respostaCorreta.text.toString().toIntOrNull()


        if (respostaUsuario != null && respostaCerta != null) {
            if (respostaUsuario == respostaCerta) {
                respostaCorreta.visibility = View.INVISIBLE
                proximo.visibility = View.VISIBLE
                background.setBackgroundColor(
                    ContextCompat.getColor(
                        this,
                        android.R.color.holo_green_light
                    )
                )
                pontos+=20
                textoPontos.text = "Jogo: $contadorJogos | Pontos: $pontos"
            } else {
                respostaCorreta.visibility = View.VISIBLE
                proximo.visibility = View.VISIBLE
                background.setBackgroundColor(
                    ContextCompat.getColor(
                        this,
                        android.R.color.holo_red_light
                    )
                )
            }
        } else {
           
            resposta.error = "Insira um número válido"
        }
    }

    fun proximo(view: View) {
        gerarConta() // Gera uma nova conta
    }
}
