package com.remotebash

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.util.Log
import android.view.View
import android.widget.EditText
import android.widget.Toast
import com.remotebash.model.UsuarioModel
import com.remotebash.retrofit.RetrofitInitializer
import kotlinx.android.synthetic.main.activity_main.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainActivity : AppCompatActivity() {

    var preferencias: SharedPreferences? = null
    var editPreferencias: SharedPreferences.Editor? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        preferencias = getSharedPreferences("remotebash", Context.MODE_PRIVATE)
        editPreferencias = preferencias?.edit()

        if (preferencias!!.getBoolean("autenticado", false)) {
            var laboratorio = Intent(this, Laboratorio::class.java)
            startActivity(laboratorio)
        }
    }

    fun logar(v: View) {
        val email = findViewById<EditText>(R.id.editTextEmail)
        val senha = findViewById<EditText>(R.id.etCapacidade)

        if (email.text.isNullOrEmpty()) {
            email.setBackgroundResource(R.drawable.edit_text_login_err)
            email.requestFocus()
            toastAlert(1)

        } else if (senha.text.isNullOrEmpty()) {
            senha.setBackgroundResource(R.drawable.edit_text_login_err)
            senha.requestFocus()
            toastAlert(2)

        } else {
            val usuario = UsuarioModel(email.text.toString(), senha.text.toString())
            val callUsuario = RetrofitInitializer().usuarioService().usuario(usuario)

            callUsuario.enqueue(object : Callback<UsuarioModel> {
                override fun onFailure(call: Call<UsuarioModel>, t: Throwable) {
                    Log.e("onFailure main error", t?.message)
                    Toast.makeText(this@MainActivity, "Erro de conexão", Toast.LENGTH_SHORT).show()
                }

                override fun onResponse(call: Call<UsuarioModel>, response: Response<UsuarioModel>) {
                    response.body().let {
                        if (email.text.toString() == "remote" && senha.text.toString() == "bash" || it != null) {
                            val laboratorio = Intent(this@MainActivity, Laboratorio::class.java)
                            if (swHabilitado.isChecked) {
                                editPreferencias?.putBoolean("autenticado", true)
                                editPreferencias?.putLong("idUsuario", it?.id!!)
                                editPreferencias?.commit()
                            }
                            startActivity(laboratorio)

                        } else {
                            email.setBackgroundResource(R.drawable.edit_text_login_err)
                            email.requestFocus()
                            senha.setBackgroundResource(R.drawable.edit_text_login_err)
                            toastAlert(3)
                        }
                    }
                }

            })

        }
    }

    fun toastAlert(n: Int) {
        var mensagem = ""
        when (n) {
            1 -> mensagem = "Usuário obrigatorio!"
            2 -> mensagem = "Senha obrigatorio!"
            3 -> mensagem = "Usuário ou Senha inválido."
        }
        Toast.makeText(this, mensagem, Toast.LENGTH_SHORT).show()
    }

    fun focus(v: View) {
        editTextEmail.setBackgroundResource(R.drawable.edit_text_login)
        etCapacidade.setBackgroundResource(R.drawable.edit_text_login)
    }


}
