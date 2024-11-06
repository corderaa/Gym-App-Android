package com.example.gym_app

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.EditText
import android.widget.Spinner
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.firestore.FirebaseFirestore
import java.text.SimpleDateFormat
import java.util.Locale


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
            finish()
        }
        registerButton.setOnClickListener {

            val name = findViewById<EditText>(R.id.editTextText).text.toString()
            val lastName = findViewById<EditText>(R.id.editTextText2).text.toString()
            val mail = findViewById<EditText>(R.id.editTextTextEmailAddress).text.toString()
            val birthDate = findViewById<EditText>(R.id.editTextDate).text.toString()
            val login = findViewById<EditText>(R.id.editTextText3).text.toString()
            val password = findViewById<EditText>(R.id.editTextTextPassword).text.toString()
            val authority = spinner.selectedItem.toString()
            val simpleDateFormat = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())



            if (name.isBlank() || lastName.isBlank() || mail.isBlank() || birthDate.isBlank() || login.isBlank() || password.isBlank() || authority.isNullOrBlank()) {
                Toast.makeText(this, "Por favor, rellena todos los campos", Toast.LENGTH_SHORT)
                    .show()

            } else {
                val date = simpleDateFormat.parse(birthDate)
                val user = hashMapOf(
                    "name" to name,
                    "lastName" to lastName,
                    "mail" to mail,
                    "birthDate" to date.time,
                    "login" to login,
                    "password" to password,
                    "level" to 0,
                    "authority" to authority
                )
                try {
                    firestore.collection("users").whereEqualTo("login", login).get()
                        .addOnSuccessListener { result ->
                            if (!result.isEmpty)
                                Toast.makeText(this, "El login ya existe", Toast.LENGTH_SHORT)
                                    .show()
                            else {
                                firestore.collection("users").add(user).addOnSuccessListener {
                                    Toast.makeText(
                                        this, "Usuario registrado con exito", Toast.LENGTH_SHORT
                                    ).show()

                                    val intent = Intent(this, LoginActivity::class.java)
                                    intent.putExtra("password", password)
                                    intent.putExtra("login", login)
                                    startActivity(intent)
                                }.addOnFailureListener { e ->
                                    Toast.makeText(
                                        this,
                                        "Error al registrar el usuario: $e",
                                        Toast.LENGTH_SHORT
                                    ).show()
                                }
                            }
                        }

                } catch (e: Exception) {
                    Toast.makeText(this, "Error: $e", Toast.LENGTH_SHORT).show()
                }
            }
            finish()
        }
    }
}
