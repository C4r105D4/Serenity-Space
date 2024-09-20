package com.example.serenityspace

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

// La clase DatabaseHelper extiende SQLiteOpenHelper, lo que permite manejar la base de datos SQLite.
// Esta clase es responsable de la creación y actualización de la base de datos.
class DatabaseHelper(context: Context?) : SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {

    // Método que se llama cuando la base de datos es creada por primera vez.
    // Se ejecuta la sentencia SQL que crea la tabla "users" con las columnas especificadas.
    override fun onCreate(db: SQLiteDatabase) {
        db.execSQL(TABLE_CREATE)
    }

    // Método que se llama cuando se detecta un cambio en la versión de la base de datos.
    // Esto permite actualizar la estructura de la base de datos sin perder los datos existentes.
    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        // Elimina la tabla "users" si ya existe, y luego la vuelve a crear.
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_USERS)
        onCreate(db)
    }

    // Bloque companion object, utilizado para definir constantes y propiedades estáticas.
    companion object {
        // Nombre de la base de datos.
        private const val DATABASE_NAME = "serenityspace.db"

        // Versión de la base de datos. Cambia cuando se hace una modificación en la estructura.
        private const val DATABASE_VERSION = 1

        // Nombre de la tabla "users".
        const val TABLE_USERS = "users"

        // Columnas de la tabla "users".
        const val COLUMN_ID = "_id"
        const val COLUMN_NAME = "name"
        const val COLUMN_SURNAME = "surname"
        const val COLUMN_AGE = "age"
        const val COLUMN_EMAIL = "email"
        const val COLUMN_PASSWORD = "password"

        // Sentencia SQL para crear la tabla "users" con sus respectivas columnas y tipos de datos.
        private const val TABLE_CREATE = "CREATE TABLE " + TABLE_USERS + " (" +
                COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +  // Clave primaria autoincremental.
                COLUMN_NAME + " TEXT, " +                             // Columna para el nombre.
                COLUMN_SURNAME + " TEXT, " +                          // Columna para el apellido.
                COLUMN_AGE + " INTEGER, " +                           // Columna para la edad.
                COLUMN_EMAIL + " TEXT, " +                            // Columna para el email.
                COLUMN_PASSWORD + " TEXT);"                           // Columna para la contraseña.
    }
}
