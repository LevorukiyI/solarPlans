package by.bsuir.myapplication

import java.util.UUID
import kotlinx.coroutines.flow.Flow

interface NoteDataSourse {
    fun getNotes(): Flow<List<Notes>>
    fun getNote(id: UUID): Flow<Notes?>

    suspend fun upsert(note: Notes)
    suspend fun delete(id: UUID)
}