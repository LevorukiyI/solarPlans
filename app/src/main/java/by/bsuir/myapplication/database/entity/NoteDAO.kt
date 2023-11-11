package by.bsuir.myapplication.database.entity

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow
import java.util.UUID

@Dao
interface NotesDataSourceDAO {
    @Query("SELECT * From notes")
    fun getNotes(): Flow<List<Note>>

    @Query("SELECT * From notes Where 'id'=:id")
    fun getNote(id: UUID?): Flow<Note?>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun upsert(note: NoteEntity)

    @Query("DELETE From notes Where 'id'=:id")
    suspend fun delete(id: UUID)
}

