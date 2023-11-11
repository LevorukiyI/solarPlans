package by.bsuir.myapplication.database.entity

import android.content.Context
import java.util.UUID
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class DatabaseRepository private constructor(private val database: MyDatabase) {


    companion object {
        private var INSTANCE: DatabaseRepository? = null

        fun get(context: Context): DatabaseRepository {
            if (INSTANCE == null) {
                INSTANCE = DatabaseRepository(MyDatabase.get(context))
            }
            return INSTANCE as DatabaseRepository
        }
    }

    val allNotes: Flow<List<Note>> = database.notesDAO().getNotes()

    fun getNotes(): Flow<List<Note>> =
        database.notesDAO().getNotes()

    fun getNote(id: UUID?): Flow<Note?> =
        database.notesDAO().getNote(id).map { it?.let { NotesMapper.toDTO(it) } }

    suspend fun upsert(note: Note) =
        database.notesDAO().upsert(NotesMapper.toEntity(note))

    suspend fun delete(id: UUID) =
        database.notesDAO().delete(id)

}