package com.example.patrika.db

import androidx.room.TypeConverter
import androidx.room.TypeConverters
import com.example.patrika.models.Source

class Conerters {

    @TypeConverter
    fun fromSource(source: Source):String{
        return source.name

    }
    @TypeConverter
    fun toSource(name:String):Source{
        return Source(name,name)
    }

}