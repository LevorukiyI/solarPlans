package by.bsuir.myapplication

import kotlinx.coroutines.flow.Flow
import java.util.UUID

data class Weather(
    val temperature: Int,
    val humidity: Int,
    val raininess: String,
    val  cloudCover: String
)

data class Note(
    var goal: String,
    var date: String,
   // val weather: Weather,
    val id: UUID = UUID.randomUUID()
)