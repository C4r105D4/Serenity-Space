package com.example.serenityspace

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

// La clase Registro es la pantalla de registro de la aplicación.
// Permite a los nuevos usuarios crear una cuenta ingresando su información personal.
class Registro : AppCompatActivity() {

    // Definición de variables para los elementos de la interfaz de usuario
    private lateinit var editTextName: EditText
    private lateinit var editTextSurname: EditText
    private lateinit var editTextAge: EditText
    private lateinit var editTextEmail: EditText
    private lateinit var editTextPassword: EditText
    private lateinit var buttonRegister: Button

    // Variable para manejar el acceso a la base de datos
    private var userDAO: UserDAO? = null

    // Método onCreate se llama cuando la actividad es creada
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // Configura el layout para la actividad de registro
        setContentView(R.layout.activity_registro)

        // Inicializar vistas (campos de texto y botón de registro)
        editTextName = findViewById(R.id.editTextName)
        editTextSurname = findViewById(R.id.editTextSurname)
        editTextAge = findViewById(R.id.editTextAge)
        editTextEmail = findViewById(R.id.editTextEmail)
        editTextPassword = findViewById(R.id.editTextPassword)
        buttonRegister = findViewById(R.id.buttonRegister)

        // Inicializar el objeto DAO para acceder a la base de datos
        userDAO = UserDAO(this)
        userDAO?.open()

        // Configurar el listener para el botón de registro
        buttonRegister.setOnClickListener { registerUser() }
    }

    // Método para registrar al usuario en la base de datos
    private fun registerUser() {
        // Obtener los valores de los campos de texto
        val name = editTextName.text.toString().trim()
        val surname = editTextSurname.text.toString().trim()
        val ageStr = editTextAge.text.toString().trim()
        val email = editTextEmail.text.toString().trim()
        val password = editTextPassword.text.toString().trim()

        // Validar que los campos no estén vacíos
        if (name.isEmpty() || surname.isEmpty() || ageStr.isEmpty() ||
            email.isEmpty() || password.isEmpty()) {
            Toast.makeText(this, "Todos los campos son obligatorios", Toast.LENGTH_SHORT).show()
            return
        }

        // Verificar que la edad sea un número válido
        val age = ageStr.toIntOrNull()
        if (age == null || age < 0) {
            Toast.makeText(this, "Introduce una edad válida", Toast.LENGTH_SHORT).show()
            return
        }

        // Insertar el nuevo usuario en la base de datos
        val result = userDAO?.addUser(name, surname, age, email, password)

        // Verificar si el registro fue exitoso
        if (result != -1L) {
            Toast.makeText(this, "Registro exitoso", Toast.LENGTH_SHORT).show()
            // Cerrar la actividad de registro después de un registro exitoso
            finish()
        } else {
            // Mostrar mensaje de error si el registro falla
            Toast.makeText(this, "Fallo al registrar", Toast.LENGTH_SHORT).show()
        }
    }

    // Método onDestroy se llama cuando la actividad es destruida
    override fun onDestroy() {
        // Cerrar la conexión a la base de datos cuando la actividad finaliza
        userDAO?.close()
        super.onDestroy()
    }
}