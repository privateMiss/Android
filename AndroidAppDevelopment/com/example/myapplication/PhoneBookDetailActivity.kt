package com.example.myapplication

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import org.w3c.dom.Text

class PhoneBookDetailActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_phone_book_detail)

        getPersonInfoAndDraw()

        val phonebook_back : ImageView = findViewById(R.id.phonebook_back)
        phonebook_back.setOnClickListener {
            onBackPressed()
        }

    }

    fun getPersonInfoAndDraw() {
        val name = intent.getStringExtra("name")
        val number = intent.getStringExtra("number")

        val person_detail_name : TextView = findViewById(R.id.person_detail_name)
        val person_detail_number : TextView = findViewById(R.id.person_detail_number)

        person_detail_name.setText(name)
        person_detail_number.setText(number)
    }
}