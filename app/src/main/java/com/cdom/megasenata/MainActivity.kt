package com.cdom.megasenata

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
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
            val numero = numerosEdit.text.toString()

            val aposta = gerarNumeros(numero)
            salvarNumeros(aposta)
            mostrarNumeros(aposta)
        }
    }

    private fun mostrarApostaAntiga(apostaAntiga: String?) {
        if(apostaAntiga == null) return

        txtResultado = binding.txtResultado
        txtResultado.text = "Última aposta: $apostaAntiga"

    }

    private fun gerarNumeros(numero: String): String? {
        val setDeNumeros = mutableSetOf<Int>()
        val random = Random()
        val qntNumeros = if(numero.isEmpty()) 0 else numero.toInt()

        if(qntNumeros > 15 || qntNumeros < 6) {
            Toast.makeText(
                this,
                "Insira um número entre 6 e 15",
                Toast.LENGTH_LONG
            ).show()
            return null
        }

        while(true) {
            val numeroRandom = random.nextInt(60)
            setDeNumeros.add(numeroRandom + 1)

            if(setDeNumeros.size == numero.toInt()){
                break
            }
        }
        return setDeNumeros.sorted().joinToString(" - ")
    }

    private fun salvarNumeros(aposta: String?) {
        val editorShrPref = sharedPreferences.edit()
        editorShrPref.apply {
            putString("resultado", aposta)
            commit()
        }
    }

    private fun mostrarNumeros(aposta: String?) {
        if(aposta == null) return

        txtResultado = binding.txtResultado
        txtResultado.text = aposta
    }
}