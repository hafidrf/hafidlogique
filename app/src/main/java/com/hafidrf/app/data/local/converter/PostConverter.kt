package com.hafidrf.app.data.local.converter

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.hafidrf.app.data.model.User
import java.lang.reflect.Type

class PostConverter {
    var gson = Gson()

    @TypeConverter
    fun stringToOwner(data: String?): User? {
        if (data == null) {
            return null
        }
        val listType: Type = object : TypeToken<User>() {}.type
        return gson.fromJson<User>(data, listType)
    }


    @TypeConverter
    fun ownerToString(someObjects: User?): String? {
        return gson.toJson(someObjects)
    }

    @TypeConverter
    fun stringToList(data: String?): List<String>? {
        if (data == null) {
            return null
        }
        val listType: Type = object : TypeToken<List<String>>() {}.type
        return gson.fromJson<List<String>>(data, listType)
    }

    @TypeConverter
    fun listStringToString(data: List<String>?): String? {
        if (data == null) {
            return null
        }
        val listType: Type = object : TypeToken<List<String>>() {}.type
        return gson.toJson(data).toString()
    }


}