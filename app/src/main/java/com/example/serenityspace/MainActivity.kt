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

class MainActivity : AppCompatActivity() {
    private var editTextEmail: EditText? = null
    private var editTextPassword: EditText? = null
    private var buttonLogin: Button? = null
    private var buttonRegister: Button? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        this.enableEdgeToEdge()
        setContentView(R.layout.activity_main)

        // Optimización del rendimiento de la interfaz de usuario
        val mainView = findViewById<View>(R.id.main)
        if (mainView != null) {
            ViewCompat.setOnApplyWindowInsetsListener(mainView) { v: View, insets: WindowInsetsCompat ->
                val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
                v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
                insets
            }

            // Añadir un ViewTreeObserver para optimización adicional del rendimiento
            mainView.getViewTreeObserver().addOnGlobalLayoutListener(object : OnGlobalLayoutListener {
                override fun onGlobalLayout() {
                    // Aquí puedes añadir tareas que deban realizarse después de que la vista esté completamente cargada
                    mainView.getViewTreeObserver().removeOnGlobalLayoutListener(this)
                }
            })
        }

        // Inicializar las vistas y los botones
        editTextEmail = findViewById(R.id.editTextEmail)
        editTextPassword = findViewById(R.id.editTextPassword)
        buttonLogin = findViewById(R.id.buttonLogin)
        buttonRegister = findViewById(R.id.buttonRegister)

        // Configurar el OnClickListener para el botón de inicio de sesión
        buttonLogin.setOnClickListener(View.OnClickListener { loginUser() })

        // Configurar el OnClickListener para el botón de "¿No tienes cuenta? Regístrate"
        buttonRegister.setOnClickListener(View.OnClickListener { // Iniciar la actividad de registro
            val intent = Intent(this@MainActivity, Registro::class.java)
            startActivity(intent)
        })
    }

    // Método para iniciar sesión
    private fun loginUser() {
        val email = editTextEmail!!.getText().toString().trim { it <= ' ' }
        val password = editTextPassword!!.getText().toString().trim { it <= ' ' }

        // Verificar si los campos de correo electrónico y contraseña están vacíos
        if (TextUtils.isEmpty(email) || TextUtils.isEmpty(password)) {
            Toast.makeText(this, "Por favor, ingresa tu correo electrónico y contraseña", Toast.LENGTH_SHORT).show()
            return
        }

        // Realizar la verificación de las credenciales del usuario en la base de datos
        val isValidCredentials = checkCredentials(email, password)

        // Verificar si las credenciales son válidas
        if (isValidCredentials) {
            // Credenciales válidas: iniciar la HomeActivity
            startActivity(Intent(this@MainActivity, HomeActivity::class.java))
            finish() // Opcional: cerrar la MainActivity para evitar que el usuario vuelva atrás
        } else {
            // Credenciales inválidas: mostrar un mensaje de error
            Toast.makeText(this, "Correo electrónico o contraseña incorrectos", Toast.LENGTH_SHORT).show()
        }
    }

    // Método para realizar la verificación de las credenciales del usuario en la base de datos
    private fun checkCredentials(email: String, password: String): Boolean {
        // Aquí debes implementar la lógica para verificar las credenciales del usuario en la base de datos
        // Consulta la base de datos para verificar si existe un usuario con el correo electrónico y la contraseña proporcionados
        // Devuelve true si las credenciales son válidas, false de lo contrario

        // Este es solo un ejemplo de implementación ficticia
        // Debes reemplazarlo con tu propia lógica de acceso a la base de datos
        // Por ejemplo, podrías usar el método addUser() de UserDAO para verificar las credenciales
        val userDAO = UserDAO(this)
        userDAO.open()
        val isValidCredentials = userDAO.checkUser(email, password)
        userDAO.close()
        return isValidCredentials
    }
}