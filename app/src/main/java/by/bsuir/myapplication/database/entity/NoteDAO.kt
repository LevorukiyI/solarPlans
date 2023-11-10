package by.bsuir.myapplication.database.entity

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow
import java.util.UUID

@Dao
interface NotesDataSource {
    @Query("SELECT * From notes")
    fun getNotes(): List<Note>

    @Query("SELECT * From notes Where 'id'=:id")
    fun getNote(id: UUID?): Note?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun upsert(note: NoteEntity)

    @Query("DELETE From notes Where 'id'=:id")
    suspend fun delete(id: UUID)
}

