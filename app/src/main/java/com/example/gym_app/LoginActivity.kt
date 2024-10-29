package com.example.gym_app

import android.content.ContentValues.TAG
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.nfc.Tag
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.CheckBox
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.firestore.FirebaseFirestore


class LoginActivity : AppCompatActivity() {

    private lateinit var firestore: FirebaseFirestore


    override fun onCreate(savedInstanceState: Bundle?) {

        firestore = FirebaseFirestore.getInstance()
        super.onCreate(savedInstanceState)
        setContentView(R.layout.login)

        val registerButton: Button = findViewById(R.id.registerButton)
        val goingToMenu: Button = findViewById(R.id.menu)
        val rememberMe: CheckBox = findViewById(R.id.rememberMe)
        val userText: EditText = findViewById(R.id.userText)
        val passwordText: EditText = findViewById(R.id.passwordText)
        val sharedP: SharedPreferences = getSharedPreferences("data", Context.MODE_PRIVATE)
        val loginButton: Button = findViewById(R.id.loginButton)

        userText.setText(sharedP.getString("user", intent.getStringExtra("login")))
        passwordText.setText(sharedP.getString("password", intent.getStringExtra("password")))
        if (userText.text.isEmpty() && passwordText.text.isEmpty()) {
            rememberMe.isChecked = false
        } else {
            rememberMe.isChecked = true
        }

        goingToMenu.setOnClickListener {
            val intent = Intent(this, WorkoutsActivity::class.java)
            intent.putExtra("login", intent.getStringExtra("login"))
            startActivity(intent)
        }

        loginButton.setOnClickListener {
            try {
                firestore.collection("users").whereEqualTo("login", userText.text.toString()).get()
                    .addOnSuccessListener { result ->
                        if (!result.isEmpty) {
                            for (document in result) {
                                Log.d(TAG, "GETTING ${document.id} => ${document.data}")
                                if (document.get("login")
                                        ?.equals(userText.text.toString()) == true && document.get("password")
                                        ?.equals(passwordText.text.toString()) == true
                                ) {
                                    Toast.makeText(
                                        this, "login correcto", Toast.LENGTH_SHORT
                                    ).show()
                                    Log.d(TAG, "logged in")
                                    goToMenu(rememberMe, sharedP, passwordText, userText)
                                } else {
                                    Toast.makeText(
                                        this, "Usuario o contraseña incorrectas", Toast.LENGTH_SHORT
                                    ).show()
                                }
                            }
                        }else{
                            Toast.makeText(
                                this, "El usuario no existe", Toast.LENGTH_SHORT
                            ).show()
                        }
                    }
                    .addOnFailureListener { e ->
                        Toast.makeText(
                            this, "Error al intentar iniciar sesión: $e", Toast.LENGTH_SHORT
                        ).show()
                        Log.d(TAG, "ERROOR")
                    }

            } catch (e: SecurityException) {
                // TODO: COMPROBAR MENSAJE DE ERROR
                Toast.makeText(
                    this, "El usuario no existe $e", Toast.LENGTH_SHORT
                ).show()
                Log.d(TAG, "ERROOR")
            }

        }
        registerButton.setOnClickListener {
            val intent = Intent(this, RegisterActivity::class.java)
            startActivity(intent)
        }
    }

    private fun goToMenu(
        rememberMe: CheckBox,
        sharedP: SharedPreferences,
        passwordText: TextView,
        userText: TextView
    ) {
        val intent = Intent(this, WorkoutsActivity::class.java)
        startActivity(intent)
        if (rememberMe.isChecked) {
            val editor: SharedPreferences.Editor = sharedP.edit()
            editor.putString("user", userText.text.toString().trim())
            editor.putString("password", passwordText.text.toString().trim())
            editor.apply()
        } else {
            val editor: SharedPreferences.Editor = sharedP.edit()
            editor.remove("user")
            editor.remove("password")
            editor.apply()
        }
    }
}