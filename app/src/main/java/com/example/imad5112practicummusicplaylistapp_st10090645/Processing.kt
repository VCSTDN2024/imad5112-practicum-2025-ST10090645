package com.example.imad5112practicummusicplaylistapp_st10090645

import android.os.Parcel
import android.os.Parcelable

class Processing() : Parcelable {

    private val maxNumbers = 4
    private val songs: Array<String?> = arrayOfNulls(maxNumbers)
    private val artists: Array<String?> = arrayOfNulls(maxNumbers)
    private val additComments: Array<String?> = arrayOfNulls(maxNumbers)
    private val rat: Array<Int?> = arrayOfNulls(maxNumbers)

    var totalCount: Int = 0

    constructor(parcel: Parcel) : this() {
        parcel.readStringArray(songs)
        parcel.readStringArray(artists)
        parcel.readStringArray(additComments)
        parcel.readArray(Int::class.java.classLoader)?.let {
            for (i in it.indices) rat[i] = it[i] as? Int
        }
        totalCount = parcel.readInt()
    }

    fun storeInfoInList(title: String, name: String, rating: Int, comment: String): Boolean {
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

    fun createdFormattedList(): List<String> {
        val formattedList = mutableListOf<String>()

        if (totalCount == 0) {
            formattedList.add("Need to add some songs first!")
            return formattedList
        }

        for (i in 0 until totalCount) {
            val songName = songs[i]?.toString() ?: "N/A"
            val artistName = artists[i]?.toString() ?: "N/A"
            val rating = rat[i]?.toString() ?: "N/A"
            val addComments = additComments[i]?.toString() ?: "N/A"

            formattedList.add(
                "Title of Song: $songName\n" +
                        "Artist Name: $artistName\n" +
                        "Rating: $rating\n" +
                        "Comment: $addComments\n" +
                        "-----------------------------------"
            )
        }
        return formattedList
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeStringArray(songs)
        parcel.writeStringArray(artists)
        parcel.writeArray(rat as Array<out Any?>)
        parcel.writeStringArray(additComments)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<Processing> {
        override fun createFromParcel(parcel: Parcel): Processing {
            return Processing(parcel)
        }
    }

    override fun newArray(size: Int): Array<Processing?> {
        return arrayOfNulls(size)
    }
}

