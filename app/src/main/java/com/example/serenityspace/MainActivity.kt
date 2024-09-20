package com.example.serenityspace

import android.content.Intent
import android.os.Bundle
import android.text.TextUtils
import android.view.View
import android.view.ViewTreeObserver.OnGlobalLayoutListener
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

// MainActivity es la pantalla principal de inicio de sesión de la aplicación.
// Extiende AppCompatActivity y maneja el inicio de sesión y registro de usuarios.
class MainActivity : AppCompatActivity() {

    // Definición de variables para los elementos de la interfaz de usuario
    private var editTextEmail: EditText? = null
    private var editTextPassword: EditText? = null
    private var buttonLogin: Button? = null
    private var buttonRegister: Button? = null

    // Método onCreate se llama cuando la actividad es creada
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Habilita la experiencia visual de borde a borde para mejorar la integración de la UI con el sistema
        this.enableEdgeToEdge()

        // Define el diseño de la pantalla desde el archivo XML
        setContentView(R.layout.activity_main)

        // Optimización de la interfaz de usuario para manejar correctamente las barras del sistema
        val mainView = findViewById<View>(R.id.main)
        if (mainView != null) {
            // Ajuste de los márgenes para las barras de estado y navegación
            ViewCompat.setOnApplyWindowInsetsListener(mainView) { v: View, insets: WindowInsetsCompat ->
                val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
                v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
                insets
            }

            // Listener para ejecutar código cuando la vista está completamente cargada
            mainView.viewTreeObserver.addOnGlobalLayoutListener(object : OnGlobalLayoutListener {
                override fun onGlobalLayout() {
                    // Este bloque de código se ejecuta una vez que el diseño ha sido completamente cargado
                    mainView.viewTreeObserver.removeOnGlobalLayoutListener(this)
                }
            })
        }

        // Inicializar los elementos de la interfaz de usuario
        editTextEmail = findViewById(R.id.editTextEmail)
        editTextPassword = findViewById(R.id.editTextPassword)
        buttonLogin = findViewById(R.id.buttonLogin)
        buttonRegister = findViewById(R.id.buttonRegister)

        // Configurar el botón de inicio de sesión
        buttonLogin?.setOnClickListener { loginUser() }

        // Configurar el botón de registro para redirigir a la actividad de registro
        buttonRegister?.setOnClickListener {
            val intent = Intent(this@MainActivity, Registro::class.java)
            startActivity(intent)
        }
    }

    // Método que maneja la lógica de inicio de sesión del usuario
    private fun loginUser() {
        // Obtener los valores de correo electrónico y contraseña de los EditTexts
        val email = editTextEmail!!.text.toString().trim { it <= ' ' }
        val password = editTextPassword!!.text.toString().trim { it <= ' ' }

        // Verificar si los campos de correo electrónico y contraseña están vacíos
        if (TextUtils.isEmpty(email) || TextUtils.isEmpty(password)) {
            // Mostrar un mensaje si faltan datos
            Toast.makeText(this, "Por favor, ingresa tu correo electrónico y contraseña", Toast.LENGTH_SHORT).show()
            return
        }

        // Verificar las credenciales del usuario en la base de datos
        val isValidCredentials = checkCredentials(email, password)

        // Si las credenciales son correctas, iniciar la HomeActivity
        if (isValidCredentials) {
            startActivity(Intent(this@MainActivity, HomeActivity::class.java))
            finish() // Finalizar MainActivity para evitar regresar a la pantalla de login
        } else {
            // Si las credenciales son incorrectas, mostrar un mensaje de error
            Toast.makeText(this, "Correo electrónico o contraseña incorrectos", Toast.LENGTH_SHORT).show()
        }
    }

    // Método para verificar las credenciales del usuario en la base de datos
    private fun checkCredentials(email: String, password: String): Boolean {
        // Aquí es donde se implementa la lógica para verificar las credenciales del usuario
        // en la base de datos. En este caso, estamos usando un DAO (Data Access Object).
        val userDAO = UserDAO(this)
        userDAO.open()
        val isValidCredentials = userDAO.checkUser(email, password)
        userDAO.close()
        return isValidCredentials
    }
}