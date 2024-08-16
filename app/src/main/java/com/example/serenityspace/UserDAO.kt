package com.example.serenityspace

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

/**
 * Clase que maneja las operaciones de base de datos para la tabla de usuarios.
 */
class UserDAO(context: Context?) {
    private var database: SQLiteDatabase? = null
    private val dbHelper: SQLiteOpenHelper

    /**
     * Constructor de la clase UserDAO.
     * @param context El contexto de la aplicación.
     */
    init {
        // Inicializar el helper de base de datos
        dbHelper = DatabaseHelper(context)
    }

    /**
     * Método para abrir la base de datos en modo escritura.
     */
    fun open() {
        database = dbHelper.writableDatabase
    }

    /**
     * Método para cerrar la base de datos.
     */
    fun close() {
        dbHelper.close()
    }

    /**
     * Método para agregar un nuevo usuario a la base de datos.
     * @param name Nombre del usuario.
     * @param surname Apellido del usuario.
     * @param age Edad del usuario.
     * @param email Correo electrónico del usuario.
     * @param password Contraseña del usuario.
     * @return El ID del nuevo usuario insertado en la base de datos.
     */
    fun addUser(name: String?, surname: String?, age: Int, email: String?, password: String?): Long {
        // Crear un objeto ContentValues para almacenar los valores a insertar en la tabla
        val values = ContentValues()
        values.put(DatabaseHelper.COLUMN_NAME, name)
        values.put(DatabaseHelper.COLUMN_SURNAME, surname)
        values.put(DatabaseHelper.COLUMN_AGE, age)
        values.put(DatabaseHelper.COLUMN_EMAIL, email)
        values.put(DatabaseHelper.COLUMN_PASSWORD, password)

        // Insertar el nuevo usuario en la base de datos
        return database!!.insert(DatabaseHelper.TABLE_USERS, null, values)
    }

    /**
     * Método para verificar las credenciales de un usuario en la base de datos.
     * @param email Correo electrónico del usuario.
     * @param password Contraseña del usuario.
     * @return true si las credenciales son válidas, false de lo contrario.
     */
    fun checkUser(email: String, password: String): Boolean {
        // Obtener una referencia a la base de datos en modo lectura
        val db = dbHelper.readableDatabase

        // Definir la cláusula WHERE para la consulta
        val selection = DatabaseHelper.COLUMN_EMAIL + " = ? AND " + DatabaseHelper.COLUMN_PASSWORD + " = ?"
        // Definir los valores para la selección desde la base de datos
        val selectionArgs = arrayOf(email, password)

        // Ejecutar la consulta en la base de datos
        val cursor = db.query( // La tabla que vamos a consultar
                DatabaseHelper.TABLE_USERS,  // Las columnas que deseas recuperar (null para todas)
                null,  // La cláusula WHERE sin la palabra clave WHERE
                selection,  // Selección de los valores
                selectionArgs,  // No agrupar las filas
                null,  // No filtar por grupos de filas
                null,  // No ordenar las filas
                null
        )

        // Obtener el número de filas coincidentes en el cursor
        val count = cursor.count
        // Cerrar el cursor para liberar los recursos
        cursor.close()
        // Devolver true si se encontró al menos una fila coincidente, false de lo contrario
        return count > 0
    }
}
