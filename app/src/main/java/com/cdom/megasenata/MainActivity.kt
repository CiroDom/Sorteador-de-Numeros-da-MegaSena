package com.cdom.megasenata

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.cdom.megasenata.databinding.ActivityMainBinding
import java.util.*


class MainActivity : AppCompatActivity() {
    private val binding by lazy {
        ActivityMainBinding.inflate(layoutInflater)
    }
    private lateinit var txtResultado: TextView
    private lateinit var sharedPreferences: SharedPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        val numerosEdit = binding.editNumero
        val botaoGerador = binding.btnGeradorNumeros

        sharedPreferences = getSharedPreferences("preferences", Context.MODE_PRIVATE)
        val resultadoSalvo = sharedPreferences.getString("resultado", null)

        mostrarApostaAntiga(resultadoSalvo)
        botaoGerador.setOnClickListener{
            val texto = numerosEdit.text.toString()

            val aposta = gerarNumeros(texto)
            salvarNumeros(aposta)
            mostrarNumeros(aposta)
        }
    }

    private fun mostrarApostaAntiga(apostaAntiga: String?) {
        txtResultado = binding.txtResultado

        txtResultado.text.let {
            "última aposta: $apostaAntiga"
        }
    }

    private fun gerarNumeros(texto: String): String? {
        val setDeNumeros = mutableSetOf<Int>()
        val random = Random()
        val qntNumeros = if(texto.isEmpty()) 0 else texto.toInt()

        if(qntNumeros > 15 || qntNumeros < 6) {
            Toast.makeText(
                this,
                "Insira um número entre 6 e 15",
                Toast.LENGTH_LONG
            ).show()
            return null
        }

        while(true) {
            val numero = random.nextInt(60)
            setDeNumeros.add(numero + 1)

            if(setDeNumeros.size == texto.toInt()){
                return setDeNumeros.sorted().joinToString(" - ")
                break
            }
        }
    }

    private fun salvarNumeros(aposta: String?) {
        val editorShrPref = sharedPreferences.edit()
        editorShrPref.apply {
            putString("resultado", aposta)
            commit()
        }
    }

    private fun mostrarNumeros(aposta: String?) {
        txtResultado = binding.txtResultado

        txtResultado.text.let {
            aposta
        }
    }
}