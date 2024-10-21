package com.example.gym_app

import android.content.Intent
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.EditText
import android.widget.Spinner
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import kotlin.math.log


class RegisterActivity : AppCompatActivity() {

    private lateinit var firestore: FirebaseFirestore

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.register)

        firestore = FirebaseFirestore.getInstance()

        val returnButton: Button = findViewById(R.id.buttonGoBack)
        val registerButton: Button = findViewById(R.id.buttonRegister)

        val spinner = findViewById<Spinner>(R.id.spinner)
        val list = resources.getStringArray(R.array.items)

        val adapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, list)
        spinner.adapter = adapter

        returnButton.setOnClickListener {
            val intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
        }
        registerButton.setOnClickListener {

            val name = findViewById<EditText>(R.id.editTextText).text.toString()
            val lastName = findViewById<EditText>(R.id.editTextText2).text.toString()
            val mail = findViewById<EditText>(R.id.editTextTextEmailAddress).text.toString()
            val birthDate = findViewById<EditText>(R.id.editTextDate).text.toString()
            val login = findViewById<EditText>(R.id.editTextText3).text.toString()
            val password = findViewById<EditText>(R.id.editTextTextPassword).text.toString()
            val authority = spinner.selectedItem.toString()

            if (name.isBlank() || lastName.isBlank() || mail.isBlank() || birthDate.isBlank() || login.isBlank() || password.isBlank() || authority.isNullOrBlank()) {
                Toast.makeText(this, "Por favor, rellena todos los campos", Toast.LENGTH_SHORT)
                    .show()
            } else {
                val user = hashMapOf(
                    "name" to name,
                    "lastName" to lastName,
                    "mail" to mail,
                    "birthDate" to birthDate,
                    "login" to login,
                    "password" to password,
                    "level" to 0,
                    "authority" to authority
                )
                firestore.collection("users").add(user).addOnSuccessListener {
                        Toast.makeText(
                            this, "Usuario registrado con exito", Toast.LENGTH_SHORT
                        ).show()

                        val intent = Intent(this, LoginActivity::class.java)
                        startActivity(intent)
                    }.addOnFailureListener { e ->
                        Toast.makeText(
                            this, "Error al registrar el usuario: $e", Toast.LENGTH_SHORT
                        ).show()
                    }
            }


        }
    }
}
