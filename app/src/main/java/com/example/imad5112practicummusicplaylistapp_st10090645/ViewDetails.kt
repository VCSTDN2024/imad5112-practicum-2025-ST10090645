package com.example.imad5112practicummusicplaylistapp_st10090645

import android.content.Intent
import android.os.Bundle
import android.webkit.RenderProcessGoneDetail
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.ListView
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import kotlin.system.exitProcess

class ViewDetails : AppCompatActivity() {

    private lateinit var lvList: ListView
    private lateinit var btBack: Button
    private lateinit var btCalcAvg: Button
    private lateinit var btShowList: Button
    private lateinit var tvAverage: TextView
    private lateinit var btExitAppl: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_view_details)

        lvList = findViewById(R.id.lvPlaylist)
        btBack = findViewById(R.id.btnBack)
        btCalcAvg = findViewById(R.id.btnCalcAvg)
        btShowList = findViewById(R.id.btnShowList)
        tvAverage = findViewById(R.id.tvAvg)
        btExitAppl = findViewById(R.id.btnDetaExit)

        val Processing = MainActivity.Processing

        btShowList.setOnClickListener {
            if (Processing != null) {
                val formattedEntries = Processing.generateFormattedEntries()
                val adapter =
                    ArrayAdapter(this, android.R.layout.simple_list_item_1, formattedEntries)
                lvList.adapter = adapter
            } else {
                lvList.adapter = ArrayAdapter(
                    this,
                    android.R.layout.simple_list_item_1,
                    listOf("Error: No data to display.")
                )
            }
        }

        btBack.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            intent.flags =
                Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_SINGLE_TOP
            startActivity(intent)
            finish()
        }
        btBack.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_SINGLE_TOP
            startActivity(intent)
            finish()
        }

        btExitAppl.setOnClickListener {
            finishAffinity()
            exitProcess(0)
        }

        btCalcAvg.setOnClickListener {
            if (Processing != null) {
                val avgRat = String.format("%.2f", Processing.calculateAverageRating())
                tvAverage.text = "The average rating of songs in your playlist is: $avgRat"


            } else {

                tvAverage.text = "The average rating of songs in your playlist is: N/A"
            }


    }
}

    }

