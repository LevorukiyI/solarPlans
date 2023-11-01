package by.bsuir.myapplication

import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.compose.runtime.toMutableStateList
import androidx.lifecycle.ViewModel
import by.bsuir.myapplication.database.entity.Notes
import by.bsuir.myapplication.database.entity.Weather

class NoteViewModel(): ViewModel() {
    val items: SnapshotStateList<Notes> = DefaultNotes.toMutableStateList()

    fun onClickRemoveNote(note: Notes) = items.remove(note)

    fun onClickAddNote(goal: String, date: String){
        val newNote =  Notes(goal, date, Weather(19, 12, "30", "3"))
        items.add(newNote)
    }

    fun onClickEditNote(goal: String, date: String, index : Int){
        val note = items.get(index)
        note.goal = goal
        note.date = date
    }

    fun getItem(id: Int): Notes {
        return items.get(id)
    }

    private companion object {

        private val DefaultNotes = listOf(
            Notes("Make 3 PMIS labs", "12102023", Weather(19, 12, "30", "3"))
        )
    }
}