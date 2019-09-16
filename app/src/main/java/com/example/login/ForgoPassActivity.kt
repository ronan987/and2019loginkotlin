package com.example.login

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.view.View
import android.widget.EditText
import android.widget.ProgressBar
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth

class ForgoPassActivity : AppCompatActivity() {
    private lateinit var txtEmail: EditText
    private lateinit var auth:FirebaseAuth
    private lateinit var ProgressBar: ProgressBar

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_forgo_pass)

        txtEmail= findViewById(R.id.txtEmail)
        auth= FirebaseAuth.getInstance()

        ProgressBar= findViewById(R.id.prbar2)
    }

    fun send(view: View){
        val email = txtEmail.text.toString()

        if (!TextUtils.isEmpty(email)){
            auth.sendPasswordResetEmail(email)
                .addOnCompleteListener(this){
                    task ->

                    if (task.isSuccessful){
                        ProgressBar.visibility= View.VISIBLE
                        startActivity(Intent(this,LoginActivity::class.java))
                    }else{
                        Toast.makeText(this,"error al enviar el correo",Toast.LENGTH_SHORT).show()
                    }
                }

        }

    }
}
