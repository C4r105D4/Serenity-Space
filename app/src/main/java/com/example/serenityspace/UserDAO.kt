package com.example.serenityspace

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

/**
 * Clase que maneja las operaciones de base de datos para la tabla de usuarios.
 * Proporciona métodos para insertar usuarios y verificar credenciales.
 */
class UserDAO(context: Context?) {

    // Referencia a la base de datos SQLite
    private var database: SQLiteDatabase? = null
    // Helper que maneja la creación y actualización de la base de datos
    private val dbHelper: SQLiteOpenHelper

    /**
     * Constructor de la clase UserDAO.
     * Inicializa el helper de base de datos para interactuar con SQLite.
     * @param context El contexto de la aplicación que se utiliza para acceder a recursos y servicios.
     */
    init {
        dbHelper = DatabaseHelper(context)
    }

    /**
     * Abre la base de datos en modo escritura.
     * Este método debe llamarse antes de realizar operaciones que modifiquen la base de datos.
     */
    fun open() {
        database = dbHelper.writableDatabase
    }

    /**
     * Cierra la conexión con la base de datos.
     * Es importante cerrar la base de datos para liberar recursos cuando ya no es necesaria.
     */
    fun close() {
        dbHelper.close()
    }

    /**
     * Inserta un nuevo usuario en la base de datos.
     * @param name El nombre del usuario.
     * @param surname El apellido del usuario.
     * @param age La edad del usuario.
     * @param email El correo electrónico del usuario.
     * @param password La contraseña del usuario.
     * @return El ID del nuevo usuario insertado, o -1 si ocurrió un error.
     */
    fun addUser(name: String?, surname: String?, age: Int, email: String?, password: String?): Long {
        // Almacena los valores a insertar en la tabla de usuarios
        val values = ContentValues()
        values.put(DatabaseHelper.COLUMN_NAME, name)
        values.put(DatabaseHelper.COLUMN_SURNAME, surname)
        values.put(DatabaseHelper.COLUMN_AGE, age)
        values.put(DatabaseHelper.COLUMN_EMAIL, email)
        values.put(DatabaseHelper.COLUMN_PASSWORD, password)

        // Inserta los valores en la tabla y retorna el ID del nuevo registro
        return database!!.insert(DatabaseHelper.TABLE_USERS, null, values)
    }

    /**
     * Verifica las credenciales de un usuario.
     * Compara el correo electrónico y la contraseña proporcionados con los valores almacenados en la base de datos.
     * @param email El correo electrónico del usuario.
     * @param password La contraseña del usuario.
     * @return true si las credenciales son válidas, false de lo contrario.
     */
    fun checkUser(email: String, password: String): Boolean {
        // Obtener la base de datos en modo lectura para realizar la consulta
        val db = dbHelper.readableDatabase

        // Definir la cláusula WHERE para la consulta
        val selection = "${DatabaseHelper.COLUMN_EMAIL} = ? AND ${DatabaseHelper.COLUMN_PASSWORD} = ?"
        // Definir los valores que se utilizarán en la selección
        val selectionArgs = arrayOf(email, password)

        // Ejecutar la consulta y obtener un cursor con los resultados
        val cursor = db.query(
            DatabaseHelper.TABLE_USERS,  // Tabla de usuarios
            null,  // Seleccionamos todas las columnas
            selection,  // La condición WHERE
            selectionArgs,  // Los argumentos para la condición WHERE
            null,  // No agrupamos las filas
            null,  // No aplicamos un filtro de grupos
            null   // No aplicamos un orden específico
        )

        // Contar el número de filas que coinciden con la consulta
        val count = cursor.count
        cursor.close()  // Cerrar el cursor para liberar recursos

        // Devolver true si al menos un usuario coincide con las credenciales proporcionadas
        return count > 0
    }
}