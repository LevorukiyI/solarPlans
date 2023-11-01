package by.bsuir.myapplication.database.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.UUID

@Entity(tableName = "Weather")
data class Weather(
    val temperature: Int,
    val humidity: Int,
    val raininess: String,
    @PrimaryKey(autoGenerate = true)
    val  cloudCover: String
)

@Entity(tableName = "notes")
data class Notes(
    var goal: String,
    var date: String,
    val weather: Weather,
    @PrimaryKey(autoGenerate = true)
    val id: UUID = UUID.randomUUID()
)