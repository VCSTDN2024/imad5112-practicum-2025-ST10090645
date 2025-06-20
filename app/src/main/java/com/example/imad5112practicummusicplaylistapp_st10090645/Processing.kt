package com.example.imad5112practicummusicplaylistapp_st10090645

import android.os.Parcel
import android.os.Parcelable

class Processing() : Parcelable {

    //Paralled 1D arrays for storing data of the songs
    private val maxNumbers = 4
    private val songs: Array<String?> = arrayOfNulls(maxNumbers)
    private val artists: Array<String?> = arrayOfNulls(maxNumbers)
    private val additComments: Array<String?> = arrayOfNulls(maxNumbers)
    private val rat: Array<Int?> = arrayOfNulls(maxNumbers)

    var totalCount: Int = 0

    //Building the parcable to pass the data through activities
    constructor(parcel: Parcel) : this() {
        parcel.readStringArray(songs)
        parcel.readStringArray(artists)
        parcel.readStringArray(additComments)
        parcel.readArray(Int::class.java.classLoader)?.let {
            for (i in it.indices) rat[i] = it[i] as? Int
        }
        totalCount = parcel.readInt()
    }

    //Adds a new song to array if array is not filled.
    fun store(title: String, name: String, rating: Int, comment: String): Boolean {
        if (totalCount < maxNumbers) {
            songs[totalCount] = title
            artists[totalCount] = name
            rat[totalCount] = rating
            additComments[totalCount] = comment
            totalCount++
            return true
        }
        return false
    }

    //Function to create the actual playlist on second screen, utilizes a for... loop
    fun generateFormattedEntries(): List<String> {
        val formattedList = mutableListOf<String>()
        if (totalCount == 0) {
            formattedList.add("Need to add some songs first!")
            return formattedList
        }

        for (i in 0 until totalCount) {

            val songtitle = songs[i]?: "N/A"
            val artist = artists[i]?: "N/A"
            val ratings = rat[i]?.toString() ?: "N/A"
            val comments = additComments[i]?: "N/A"


            formattedList.add(
                "Title of Song: $songtitle\n" +
                        "Artist Name: $artist\n" +
                        "Rating: $ratings\n" +
                        "Comment: $comments\n" +
                        "-----------------------------------"
            )
        }
        return formattedList
    }

    //Function to calculate the average rating of songs, also utilizes a for... loop
    fun calculateAverageRating(): Double {
        if (totalCount == 0) return 0.0
        var totalMinutes = 0
        var actualEntries = 0
        for (i in 0 until totalCount) {
            rat[i]?.let {
                totalMinutes += it
                actualEntries++
            }
        }

        return if (actualEntries > 0) totalMinutes.toDouble() / actualEntries else 0.0
    }



    //Necessary to for the parcelable to run as intended
    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeStringArray(songs)
        parcel.writeStringArray(artists)
        parcel.writeArray(rat as Array<out Any?>)
        parcel.writeStringArray(additComments)
        parcel.writeInt(totalCount)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<Processing> {
        override fun createFromParcel(parcel: Parcel): Processing {
            return Processing(parcel)
        }

        override fun newArray(size: Int): Array<Processing?> {
            return arrayOfNulls(size)
        }
    }


}

