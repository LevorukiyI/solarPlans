package by.bsuir.myapplication


import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import by.bsuir.myapplication.database.entity.DatabaseRepository
import by.bsuir.myapplication.database.entity.Mapper

import by.bsuir.myapplication.database.entity.NoteEntity
import by.bsuir.myapplication.database.entity.NotesMapper

import kotlinx.coroutines.flow.Flow

import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow

import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.getAndUpdate
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

import java.util.UUID

interface NotesDataSource {
    fun getNotes(): Flow<List<Note>>
    fun getNote(id: UUID?): Flow<Note?>

    suspend fun upsert(note: Note)
    suspend fun delete(id: UUID)
}

class RoomNotesDataSource(private val repository: DatabaseRepository) : NotesDataSource {
    override fun getNotes(): Flow<List<Note>> {
        return repository.getNotes().map {list -> list.map { NotesMapper.toDTO(it)}}
    }

    override fun getNote(id: UUID?): Flow<Note?> {
        return repository.getNote(id).map { it?.let { NotesMapper.toDTO(it) } }
    }

    override suspend fun upsert(note: Note) {
        repository.upsert(NotesMapper.toEntity(note))
    }

    override suspend fun delete(id: UUID) {
        repository.delete(id)
    }
}

data class NotesListUiState(
    val notes: List<Note> = emptyList(),
    val isLoading: Boolean = false,
    val isError: Boolean = false
)


data class NoteUiState(
    val id: UUID = UUID.randomUUID(),
    val goal: String = "",
    val date: String = "",
    val weather: Weather = Weather(0,0,"",""),

    val isLoading: Boolean = false,
    val isNoteSaved: Boolean = false,
    val isNoteSaving: Boolean = false,
    val noteSavingError: String? = null
)


class AddEditViewModel(private val dataSource: NotesDataSource) : ViewModel() {

    private var noteId: String? = null

    private val _uiState = MutableStateFlow(NoteUiState())
    val uiState: StateFlow<NoteUiState> = _uiState.asStateFlow()

    init{
    }

    fun initViewModel(id: String?){
        if(id!=null)
            noteId = id.toString()
        if (noteId != null) {
            loadNote(UUID.fromString(noteId))
        }
    }

    private fun loadNote(noteId: UUID?) {

        _uiState.update { it.copy(isLoading = true) }
        viewModelScope.launch {
            val result = dataSource.getNote(noteId).first()
            if (result == null) {
                _uiState.update { it.copy(isLoading = false) }
            } else {

                _uiState.update {
                    it.copy(
                        isLoading = false,
                        goal = result.goal,
                        date = result.date,
                       // weather = result.weather
                    )
                }
            }
        }
    }


    fun saveNote() {
        viewModelScope.launch {
            try {
                _uiState.update { it.copy(isNoteSaving = true) }
                if (noteId != null) {
                    dataSource.upsert(
                       Note(
                            id = UUID.fromString(noteId),
                            goal = _uiState.value.goal,
                            date = _uiState.value.date,
                            //weather = _uiState.value.weather
                        )
                    )
                } else {
                    dataSource.upsert(
                        Note(
                            id = UUID.randomUUID(),
                            goal = _uiState.value.goal,
                            date = _uiState.value.date,
                           // weather = _uiState.value.weather
                        )
                    )
                }
                _uiState.update{it.copy(isNoteSaved = true)}

            } catch (e: Exception){
                _uiState.update { it.copy(noteSavingError = "Невозможно сохранить или изменить запись") }
            } finally {
                _uiState.update { it.copy(isNoteSaving = false) }
            }

        }


    }

    fun deleteNote() {
        viewModelScope.launch {
            try {
                _uiState.update { it.copy(isNoteSaving = true) }

                if(noteId!=null) {
                    dataSource.delete(UUID.fromString(noteId))
                }
                _uiState.update { it.copy(isNoteSaved = true) }
            }
            catch (e: Exception) {
                System.out.println(e)
                _uiState.update { it.copy(noteSavingError = "Упс... ошибочка") }
            }
            finally {
                _uiState.update { it.copy(isNoteSaving  = false) }
            }

        }
    }

    fun setNoteGoal(goal: String) {
        _uiState.update { it.copy(goal = goal) }
    }

    fun setNoteDate(date: String) {
        _uiState.update { it.copy(date = date) }
    }

    fun setNoteWeather(weather: Weather) {
        _uiState.update { it.copy(weather = weather) }
    }
}

class HomeViewModel(private val dataSource: NotesDataSource) : ViewModel() {
    private val notes = dataSource.getNotes()
    private val notesLoadingItems = MutableStateFlow(0)

    val uiState = combine(notes, notesLoadingItems) { notes, loadingItems ->
        NotesListUiState(
            notes = notes.toList(),
            isLoading = loadingItems > 0,
            isError = false
        )

    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000),
        initialValue = NotesListUiState(isLoading = true)
    )



    private suspend fun withLoading(block: suspend () -> Unit) {
        try {
            addLoadingElement()
            block()
        }
        finally {
            removeLoadingElement()
        }
    }

    private fun addLoadingElement() = notesLoadingItems.getAndUpdate { num -> num + 1 }
    private fun removeLoadingElement() = notesLoadingItems.getAndUpdate { num -> num - 1 }
    fun deleteNote(noteId: UUID){
        viewModelScope.launch {

            withLoading { dataSource.delete(noteId) }
        }
    }

}
