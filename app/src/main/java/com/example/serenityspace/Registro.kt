package com.example.serenityspace

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.os.Bundle
import android.text.TextUtils
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class Registro : AppCompatActivity() {
    private var editTextName: EditText? = null
    private var editTextSurname: EditText? = null
    private var editTextAge: EditText? = null
    private var editTextEmail: EditText? = null
    private var editTextPassword: EditText? = null
    private var buttonRegister: Button? = null
    private var userDAO: UserDAO? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_registro)
        editTextName = findViewById(R.id.editTextName)
        editTextSurname = findViewById(R.id.editTextSurname)
        editTextAge = findViewById(R.id.editTextAge)
        editTextEmail = findViewById(R.id.editTextEmail)
        editTextPassword = findViewById(R.id.editTextPassword)
        buttonRegister = findViewById(R.id.buttonRegister)
        userDAO = UserDAO(this)
        userDAO!!.open()
        buttonRegister.setOnClickListener(View.OnClickListener { registerUser() })
    }

    private fun registerUser() {
        val name = editTextName!!.getText().toString().trim { it <= ' ' }
        val surname = editTextSurname!!.getText().toString().trim { it <= ' ' }
        val ageStr = editTextAge!!.getText().toString().trim { it <= ' ' }
        val email = editTextEmail!!.getText().toString().trim { it <= ' ' }
        val password = editTextPassword!!.getText().toString().trim { it <= ' ' }
        if (TextUtils.isEmpty(name) || TextUtils.isEmpty(surname) || TextUtils.isEmpty(ageStr) ||
                TextUtils.isEmpty(email) || TextUtils.isEmpty(password)) {
            Toast.makeText(this, "Todos los campos son obligatorios", Toast.LENGTH_SHORT).show()
            return
        }
        val age = ageStr.toInt()
        val result = userDAO!!.addUser(name, surname, age, email, password)
        if (result != -1L) {
            Toast.makeText(this, "Registro exitoso", Toast.LENGTH_SHORT).show()
            // Redirigir a la actividad de inicio de sesión o a la página principal
            // startActivity(new Intent(Registro.this, LoginActivity.class));
            finish()
        } else {
            Toast.makeText(this, "Fallo al registrar", Toast.LENGTH_SHORT).show()
        }
    }

    override fun onDestroy() {
        userDAO!!.close()
        super.onDestroy()
    }

    // Clase UserDAO
    private inner class UserDAO(context: Context?) {
        private var database: SQLiteDatabase? = null
        private val dbHelper: SQLiteOpenHelper

        init {
            dbHelper = DatabaseHelper(context)
        }

        fun open() {
            database = dbHelper.writableDatabase
        }

        fun close() {
            dbHelper.close()
        }

        fun addUser(name: String?, surname: String?, age: Int, email: String?, password: String?): Long {
            val values = ContentValues()
            values.put(DatabaseHelper.Companion.COLUMN_NAME, name)
            values.put(DatabaseHelper.Companion.COLUMN_SURNAME, surname)
            values.put(DatabaseHelper.Companion.COLUMN_AGE, age)
            values.put(DatabaseHelper.Companion.COLUMN_EMAIL, email)
            values.put(DatabaseHelper.Companion.COLUMN_PASSWORD, password)
            return database!!.insert(DatabaseHelper.Companion.TABLE_USERS, null, values)
        }

        fun checkUser(email: String, password: String): Boolean {
            val db = dbHelper.readableDatabase
            val selection = DatabaseHelper.Companion.COLUMN_EMAIL + " = ? AND " + DatabaseHelper.Companion.COLUMN_PASSWORD + " = ?"
            val selectionArgs = arrayOf(email, password)
            val cursor = db.query(
                    DatabaseHelper.Companion.TABLE_USERS,
                    null,
                    selection,
                    selectionArgs,
                    null,
                    null,
                    null
            )
            val count = cursor.count
            cursor.close()
            return count > 0
        }
    }

    // Clase DatabaseHelper
    private inner class DatabaseHelper(context: Context?) : SQLiteOpenHelper(context, Companion.DATABASE_NAME, null, Companion.DATABASE_VERSION) {
        override fun onCreate(db: SQLiteDatabase) {
            db.execSQL(Companion.TABLE_CREATE)
        }

        override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
            db.execSQL("DROP TABLE IF EXISTS " + Companion.TABLE_USERS)
            onCreate(db)
        }

        companion object {
            private const val DATABASE_NAME = "serenityspace.db"
            private const val DATABASE_VERSION = 1
            const val TABLE_USERS = "users"
            const val COLUMN_ID = "_id"
            const val COLUMN_NAME = "name"
            const val COLUMN_SURNAME = "surname"
            const val COLUMN_AGE = "age"
            const val COLUMN_EMAIL = "email"
            const val COLUMN_PASSWORD = "password"
            private const val TABLE_CREATE = "CREATE TABLE " + TABLE_USERS + " (" +
                    COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    COLUMN_NAME + " TEXT, " +
                    COLUMN_SURNAME + " TEXT, " +
                    COLUMN_AGE + " INTEGER, " +
                    COLUMN_EMAIL + " TEXT, " +
                    COLUMN_PASSWORD + " TEXT);"
        }
    }
}
