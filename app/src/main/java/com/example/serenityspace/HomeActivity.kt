package com.example.serenityspace

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity

class HomeActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)

        // Obtener referencias a los botones
        val buttonDepresion = findViewById<Button>(R.id.button_depresion)
        val buttonAnsiedad = findViewById<Button>(R.id.button_ansiedad)
        val buttonFamiliar = findViewById<Button>(R.id.button_familiar)

        // Configurar OnClickListener para el botón "Psicólogo especialista en depresión"
        buttonDepresion.setOnClickListener { // Crear un Intent para abrir la actividad Contacto
            val intent = Intent(this@HomeActivity, Contacto::class.java)
            startActivity(intent)
        }

        // Configurar OnClickListener para el botón "Psicólogo especialista en ansiedad"
        buttonAnsiedad.setOnClickListener { // Crear un Intent para abrir la actividad Contacto2
            val intent = Intent(this@HomeActivity, Contacto2::class.java)
            startActivity(intent)
        }

        // Configurar OnClickListener para el botón "Psicólogo especialista en problemas familiares y de pareja"
        buttonFamiliar.setOnClickListener { // Crear un Intent para abrir la actividad Contacto3
            val intent = Intent(this@HomeActivity, Contacto3::class.java)
            startActivity(intent)
        }
    }
}
