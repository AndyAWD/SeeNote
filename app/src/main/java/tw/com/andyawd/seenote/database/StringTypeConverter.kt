package tw.com.andyawd.seenote.database

import androidx.room.TypeConverter

class StringTypeConverter {
    @TypeConverter
    fun fromString(value: String?): List<String>? {
        return value?.split(",")?.map { it }
    }

    @TypeConverter
    fun toString(list: List<String>?): String? {
        return list?.joinToString(separator = ",")
    }
}