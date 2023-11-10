package by.bsuir.myapplication.database.entity

import android.content.Context
import java.util.UUID

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

    suspend fun saveNote(note: Note) =
        database.notesDAO().upsert(NotesMapper.toEntity(note))

    //suspend fun getNote(id: UUID) = database.notesDAO().getNote(id)?.let { NotesMapper.toDTO(it) }
    suspend fun getNote(id: UUID) = database.notesDAO().getNote(id)?.let { note -> NotesMapper.toDTO(note) }
}