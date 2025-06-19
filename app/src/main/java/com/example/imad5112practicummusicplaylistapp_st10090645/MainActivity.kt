package com.example.imad5112practicummusicplaylistapp_st10090645

import android.content.Intent
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.EditText
import android.widget.Spinner
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import kotlin.system.exitProcess

class MainActivity : AppCompatActivity() {
// Initializing several variables
    private lateinit var etSongTitle : EditText
    private lateinit var etArtistName : EditText
    private lateinit var etComments : EditText
    private lateinit var spnRatings : Spinner
    private lateinit var btAdd : Button
    private lateinit var btExit : Button
    private lateinit var btStore : Button
    private lateinit var btViewList : Button
// Allowing data stored and held within arrays to persist through multiple activities
    companion object {
        var Processing: Processing = Processing()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)

        etSongTitle = findViewById(R.id.etTitle)
        etArtistName = findViewById(R.id.etArtist)
        etComments = findViewById(R.id.etComment)
        spnRatings = findViewById(R.id.spnRating)
        btAdd = findViewById(R.id.btnAddToPlaylist)
        btExit = findViewById(R.id.btnExit)
        btStore = findViewById(R.id.btnInput)
        btViewList = findViewById(R.id.btnList)

        //Enabling spinners using data from strings.xml
        ArrayAdapter.createFromResource(
            this,
            R.array.rating,
            android.R.layout.simple_spinner_item
        ).also { adapter ->
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            spnRatings.adapter = adapter
        }

        //Disabling functionality until user clicks the "Add to Playlist" button and is
        //prompted to input data
        etSongTitle.isEnabled = false
        etArtistName.isEnabled = false
        etComments.isEnabled = false
        spnRatings.isEnabled = false
        btStore.isEnabled = false

        btStore.setOnClickListener {
            store()
        }

        btViewList.setOnClickListener {
            val intent = Intent(this, ViewDetails::class.java)
            intent.putExtra("Processing", Processing)
            startActivity(intent)
        }

        btAdd.setOnClickListener {
            etSongTitle.isEnabled = true
            etArtistName.isEnabled = true
            etComments.isEnabled = true
            spnRatings.isEnabled = true
            btStore.isEnabled = true
            refreshStoreButton()
        }

        btExit.setOnClickListener {
            finishAffinity()
            exitProcess(0)
        }
    }
    private fun store() {
        val title = etSongTitle.text.toString().trim()
        val name = etArtistName.text.toString().trim()
        val ratingStr = spnRatings.selectedItem.toString()
        val comment = etComments.text.toString()

        if (title.isEmpty() || name.isEmpty() || comment.isEmpty()) {
            Toast.makeText(this, "Please fill in all fields!", Toast.LENGTH_SHORT).show()
            return
        }

        val rating: Int
        try {
            rating = ratingStr.toInt()
            if (rating < 1 || rating > 5) {
                Toast.makeText(this, "Rating needs to be between 1 and 5.", Toast.LENGTH_SHORT).show()
                return
            }

        } catch (e: NumberFormatException) {
            Toast.makeText(this, "Please select a rating.", Toast.LENGTH_SHORT).show()
            return
        }

        val added = Processing.storeInfoInList(title, name, rating, comment)

        if (added) {
            Toast.makeText(this, "Song added. Total entries: ${Processing.totalCount}", Toast.LENGTH_SHORT).show()
            refreshStoreButton()
            clear()
        } else {
            Toast.makeText(this, "4 songs in playlist. Can't add more.", Toast.LENGTH_LONG).show()
            btStore.isEnabled = false
        }
    }

    private fun refreshButtonStates() {
        etSongTitle.isEnabled = false
        etArtistName.isEnabled = false
        etComments.isEnabled = false
        spnRatings.isEnabled = false
        btStore.isEnabled = false
    }

    private fun refreshStoreButton() {
        if (Processing.totalCount >= 4) {
            btStore.isEnabled = false
            Toast.makeText(this, "4 songs in playlist. Can't add more.", Toast.LENGTH_LONG).show()
        } else {
            btStore.isEnabled = true
        }
    }

    private fun clear() {
        etSongTitle.setText("")
        etArtistName.setText("")
        spnRatings.setSelection(0)
        etComments.setSelection(0)
    }

    override fun onResume() {
        super.onResume()
        refreshButtonStates()
    }




    }