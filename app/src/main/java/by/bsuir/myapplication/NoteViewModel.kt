package by.bsuir.myapplication

import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.compose.runtime.toMutableStateList
import androidx.lifecycle.ViewModel
import java.util.UUID

data class Weather(
    val temperature: Int,
    val humidity: Int,
    val raininess: String,
    val  cloudCover: String
)

data class Notes(
    val goal: String,
    val date: String,
    val weather: Weather,
    val id: UUID = UUID.randomUUID()
)

class NoteViewModel(): ViewModel() {
    val items: SnapshotStateList<Notes> = DefaultNotes.toMutableStateList()

    fun onClickRemoveNote(note: Notes) = items.remove(note)

    private companion object {

        private val DefaultNotes = listOf(
            Notes("Пизда", "12.2.2023", Weather(19, 12, "30", "3")),
            Notes("Пизда еще больше", "12.2.2023", Weather(19, 12, "20", "4")),
            Notes("Пизда", "12.2.2023", Weather(19, 12, "30", "3")),
            Notes("Пизда", "12.2.2023", Weather(19, 12, "30", "3")),
            Notes("Пизда", "12.2.2023", Weather(19, 12, "30", "3")),
            Notes("Пиздsddd sd sd sd sdddd sd sd sd dsdsdd sd sd dd fdа", "12.2.2023", Weather(19, 12, "30", "3")),
            Notes("Пизда", "12.2.2023", Weather(19, 12, "30", "3")),
            Notes("Пиздаa", "12.2.2023", Weather(19, 12, "30", "3")),

        )
    }
}