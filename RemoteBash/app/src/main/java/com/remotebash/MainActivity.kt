package com.remotebash

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.view.View
import android.widget.EditText
import android.widget.Toast
import com.remotebash.retrofit.RetrofitInitializer
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        RetrofitInitializer()

    }

    private var doubleBackToExit = false
    override fun onBackPressed() {
        if (doubleBackToExit){
            super.onBackPressed()
            return
        }

        this.doubleBackToExit = true
        Toast.makeText(this, "Toque novamente para sair", Toast.LENGTH_SHORT).show()

        Handler().postDelayed(Runnable { doubleBackToExit = false },1500)


    }

    fun logar(v: View){
        var email = findViewById<EditText>(R.id.editTextEmail)
        var senha = findViewById<EditText>(R.id.editTextSenha)

        if(email.text.isNullOrEmpty()){
            email.setBackgroundResource(R.drawable.edit_text_login_err)
            email.requestFocus()
            toastAlert(1)
        }
        else if(senha.text.isNullOrEmpty()){
            senha.setBackgroundResource(R.drawable.edit_text_login_err)
            senha.requestFocus()
            toastAlert(2)
        }
        else{
            if(email.text.toString().equals("remote") && senha.text.toString().equals("bash")){
                val laboratorio = Intent(this, Laboratorio::class.java)

                startActivity(laboratorio)
            }
            else{
                email.setBackgroundResource(R.drawable.edit_text_login_err)
                email.requestFocus()
                senha.setBackgroundResource(R.drawable.edit_text_login_err)
                toastAlert(3)
            }
        }
    }

    fun toastAlert(n:Int){
        var mensagem = ""
        when(n){
            1 -> mensagem = "Usuário obrigatorio!"
            2 -> mensagem = "Senha obrigatorio!"
            3 -> mensagem = "Usuário ou Senha inválido."
        }
        Toast.makeText(this, mensagem, Toast.LENGTH_SHORT).show()
    }

    fun focusUsuario(v: View){
        editTextEmail.setBackgroundResource(R.drawable.edit_text_login)
    }
    fun focusSenha(v: View){
        editTextSenha.setBackgroundResource(R.drawable.edit_text_login)
    }

}
