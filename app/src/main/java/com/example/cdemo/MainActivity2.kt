package com.example.cdemo

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity2 : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main2)
      /*  textView3.setOnClickListener {
            val i = Intent(applicationContext, MainActivity2::class.java)
            startActivity(i)
        }*/
    }
}