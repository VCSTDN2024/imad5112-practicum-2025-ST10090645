/* Reference List: https://advtechonline.sharepoint.com/:w:/r/sites/TertiaryStudents/_layouts/15/Doc.aspx?sourcedoc=%7BA1FF62F0-8E1A-47BC-99BD-CA07AE24427D%7D&file=IMAD5112_MM.docx&action=default&mobileredirect=true
                    https://mystudies.iie.edu.za/d2l/le/lessons/19195/topics/2482397*/
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
    private lateinit var etRatings : EditText
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
        etRatings = findViewById(R.id.etRating)
        btAdd = findViewById(R.id.btnAddToPlaylist)
        btExit = findViewById(R.id.btnExit)
        btStore = findViewById(R.id.btnInput)
        btViewList = findViewById(R.id.btnList)

        //Disabling functionality until user clicks the "Add to Playlist" button and is
        //prompted to input data
        etSongTitle.isEnabled = false
        etArtistName.isEnabled = false
        etComments.isEnabled = false
        etRatings.isEnabled = false
        btStore.isEnabled = false

        //Enabling Button that allows user to store data into playlist
        btStore.setOnClickListener {
            store()
        }

        //Enabling button that allows user to move to 2nd screen to view playlist
        btViewList.setOnClickListener {
            val intent = Intent(this, ViewDetails::class.java)
            startActivity(intent)
        }

        //Enabling button that allows user to start adding songs to playlist
        btAdd.setOnClickListener {
            etSongTitle.isEnabled = true
            etArtistName.isEnabled = true
            etComments.isEnabled = true
            etRatings.isEnabled = true
            btStore.isEnabled = true
            Toast.makeText(this, "Please enter song info.", Toast.LENGTH_SHORT).show()
            refreshStoreButton()
        }

        // Enabling button that allows users to exit the app
        btExit.setOnClickListener {
            finishAffinity()
            exitProcess(0)
        }
    }

    //Function that allows users to store data into the parallel arrays
    private fun store() {
        val title = etSongTitle.text.toString().trim()
        val name = etArtistName.text.toString().trim()
        val ratingStr = etRatings.text.toString().trim()
        val comment = etComments.text.toString().trim()

        //Various forms of error handling
        if (title.isEmpty() || name.isEmpty() || comment.isEmpty() || ratingStr.isEmpty()) {
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
            Toast.makeText(this, "Please enter a rating.", Toast.LENGTH_SHORT).show()
            return
        }

        val added = Processing.store(title, name, rating, comment)

        if (added) {
            Toast.makeText(this, "Song added. Total entries: ${Processing.totalCount}", Toast.LENGTH_SHORT).show()
            refreshStoreButton()
            clear()
        } else {
            Toast.makeText(this, "4 songs in playlist. Can't add more.", Toast.LENGTH_LONG).show()
            btStore.isEnabled = false
        }
    }

    //Ensuring users aren't able to add songs to playlist unless the choose to do so
    private fun refreshButtonStates() {
        etSongTitle.isEnabled = false
        etArtistName.isEnabled = false
        etComments.isEnabled = false
        etRatings.isEnabled = false
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

    //Function to clear all input fields after a song has been successfully stored
    private fun clear() {
        etSongTitle.setText("")
        etArtistName.setText("")
        etRatings.setText("")
        etComments.setText("")
    }

    //Same as above, ensuring users aren't able to add songs to playlist unless the choose to do so
    override fun onResume() {
        super.onResume()
        refreshButtonStates()
    }




    }