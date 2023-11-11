package by.bsuir.myapplication.database.entity

import android.content.Context
import java.util.UUID
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class DatabaseRepository(private val notesDAO: NotesDataSourceDAO) {

    val allNotes: Flow<List<Note>> = notesDAO.getNotes()

    fun getNotes(): Flow<List<Note>> =
        notesDAO.getNotes()

    fun getNote(id: UUID?): Flow<Note?> =
        notesDAO.getNote(id).map { it?.let { NotesMapper.toDTO(it) } }

    suspend fun upsert(note: Note) =
        notesDAO.upsert(NotesMapper.toEntity(note))

    suspend fun delete(id: UUID) =
        notesDAO.delete(id)

}