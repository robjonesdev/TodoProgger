package com.robjonesdev.todoprogger.data

import androidx.room.TypeConverter
import com.robjonesdev.todoprogger.domain.models.ProgressEntry
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json

object TodoTypeConverters {
    private val json = Json { ignoreUnknownKeys = true }

    @TypeConverter
    fun fromProgressEntryList(value: List<ProgressEntry>): String {
        return json.encodeToString(value)
    }

    @TypeConverter
    fun toProgressEntryList(value: String): List<ProgressEntry> {
        return try {
            json.decodeFromString(value)
        } catch (e: Exception) {
            emptyList()
        }
    }
}
