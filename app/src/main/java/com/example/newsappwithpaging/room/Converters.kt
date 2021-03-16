package com.example.newsappwithpaging.room

import androidx.room.TypeConverter
import com.example.newsappwithpaging.model.Source

/**
 * Room can only handle primitive data types and String; thus we need type converter to tell room
 * how to interpret user-defined type
 */
class Converters {

    @TypeConverter
    fun fromSource(source: Source): String {
        return source.name
    }

    @TypeConverter
    fun toSource(name: String): Source {
        return Source(name, name) // we don't care id
    }
}