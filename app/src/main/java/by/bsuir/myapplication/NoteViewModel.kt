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

    fun onClickAddNote(goal: String, date: String){
        val newNote =  Notes(goal, date, Weather(19, 12, "30", "3"))
        items.add(newNote)
    }

    fun onClickEditNote(goal: String, date: String, note: Notes){
    }

    private companion object {

        private val DefaultNotes = listOf(
            Notes("Make 3 PMIS labs", "10102023", Weather(19, 12, "30", "3"))
        )
    }
}