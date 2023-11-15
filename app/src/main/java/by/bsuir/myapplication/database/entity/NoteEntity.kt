package by.bsuir.myapplication.database.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverters
import by.bsuir.myapplication.database.entity.Constants.NOTE_TABLE
import by.bsuir.myapplication.database.entity.Constants.WEATHER_TABLE
import java.util.UUID

//@Entity(tableName = WEATHER_TABLE)
//data class WeatherEntity(
//    val temperature: Int,
//    val humidity: Int,
//    val raininess: String,
//    @PrimaryKey(autoGenerate = true)
//    val  cloudCover: String
//)

@Entity(tableName = NOTE_TABLE)
data class NoteEntity(
    @ColumnInfo(name = "goal")
    var goal: String,
    @ColumnInfo(name = "date")
    var date: String,
    //val weather: Weather,
    @PrimaryKey
    @ColumnInfo(name = "id")
    val id: UUID
)