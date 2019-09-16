package com.example.login

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.ContactsContract
import android.text.TextUtils
import android.view.View
import android.widget.AbsListView
import android.widget.EditText
import android.widget.ProgressBar
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import java.lang.ref.PhantomReference

class RegisterActivity : AppCompatActivity() {

    private lateinit var txtName:EditText
    private lateinit var txtLastName:EditText
    private lateinit var txtEmail:EditText
    private lateinit var txtPassword:EditText
    private lateinit var ProgressBar:ProgressBar
    private lateinit var dbReference:DatabaseReference
    private lateinit var database:FirebaseDatabase
    private lateinit var auth:FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)

        txtName=findViewById(R.id.txtName)
        txtLastName= findViewById(R.id.txtLastName)
        txtEmail= findViewById(R.id.txtEmail)
        txtPassword= findViewById(R.id.txtpassword)


        ProgressBar= findViewById(R.id.prbar)

        database= FirebaseDatabase.getInstance()
        auth=FirebaseAuth.getInstance()

        dbReference=database.reference.child("User")

    }

    fun register(view:View){
        createNewAccount()

    }

    private  fun createNewAccount(){
        val name:String=txtName.text.toString()
        val lastname:String=txtLastName.text.toString()
        val email:String=txtEmail.text.toString()
        val password:String=txtPassword.text.toString()

        if (!TextUtils.isEmpty(name)&&!TextUtils.isEmpty(lastname)&&!TextUtils.isEmpty(email)&&!TextUtils.isEmpty(password)){
            ProgressBar.visibility= View.VISIBLE

            auth.createUserWithEmailAndPassword(email,password)
                .addOnCompleteListener(this){
                    task ->

                    if (task.isComplete){
                        val user:FirebaseUser?= auth.currentUser
                        verifyEmail(user)

                        val userBd=dbReference.child(user?.uid)

                        userBd.child("name").setValue(name)
                        userBd.child("lastname").setValue(lastname)
                        action()
                    }
                }

            }
    }

    private fun action(){
        startActivity(Intent(this,LoginActivity::class.java))

    }
    private fun verifyEmail(user: FirebaseUser?){
        user?.sendEmailVerification()
            ?.addOnCompleteListener(this){
                task ->

                if (task.isComplete){
                    Toast.makeText(this,"correo enviado",Toast.LENGTH_SHORT).show()
                }else {
                    Toast.makeText(this,"error al enviar el correo",Toast.LENGTH_SHORT).show()
                }
            }

    }
}
