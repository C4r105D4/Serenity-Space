package com.example.serenityspace

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity

// Definir la data class Psicologo
data class Psicologo(val nombre: String, val especializacion: String)

class HomeActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)

        // Definir los psicólogos
        val psicologos = listOf(
            Psicologo("Dr. Camilo Múnera", "Depresión"),
            Psicologo("Dr. Carlos Jiménez", "Ansiedad"),
            Psicologo("Dr. Kevin Miranda", "Problemas familiares y de pareja")
        )

        // Asociar botones con IDs y los psicólogos correspondientes
        val buttonDepresion = findViewById<Button>(R.id.button_depresion)
        val buttonAnsiedad = findViewById<Button>(R.id.button_ansiedad)
        val buttonFamiliar = findViewById<Button>(R.id.button_familiar)

        // Crear un OnClickListener para cada botón que pase los datos del psicólogo
        buttonDepresion.setOnClickListener {
            openContacto(psicologos[0])
        }

        buttonAnsiedad.setOnClickListener {
            openContacto(psicologos[1])
        }

        buttonFamiliar.setOnClickListener {
            openContacto(psicologos[2])
        }
    }

    // Función para abrir la actividad Contacto y pasar los datos del psicólogo
    private fun openContacto(psicologo: Psicologo) {
        val intent = Intent(this@HomeActivity, Contacto::class.java)
        intent.putExtra("nombre", psicologo.nombre)
        intent.putExtra("especializacion", psicologo.especializacion)
        startActivity(intent)
    }
}