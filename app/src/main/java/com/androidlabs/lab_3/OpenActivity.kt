package com.androidlabs.lab_3

import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import java.io.File
import java.io.FileInputStream
import java.io.IOException

class OpenActivity : AppCompatActivity() {

    private lateinit var resultTextView: TextView
    private lateinit var deleteButton: Button
    private lateinit var backButton: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_open)

        resultTextView = findViewById(R.id.result_text_view)
        deleteButton = findViewById(R.id.delete_button)
        backButton = findViewById(R.id.back_button)

        val data = readDataFromFile()
        resultTextView.text = data

        deleteButton.setOnClickListener { deleteDataFile() }

        backButton.setOnClickListener { finish() }
    }

    private fun readDataFromFile(): String {
        val file = File(filesDir, "user_data.txt")
        if (file.exists()) {
            try {
                FileInputStream(file).use { fis ->
                    val data = fis.bufferedReader().use { it.readText() }
                    return data
                }
            } catch (e: IOException) {
                e.printStackTrace()
            }
        }
        return "No data found"
    }

    private fun deleteDataFile() {
        val file = File(filesDir, "user_data.txt")
        if (file.exists()) {
            file.delete()
            resultTextView.text = "Data deleted"
        } else {
            resultTextView.text = "No data to delete"
        }
    }
}
