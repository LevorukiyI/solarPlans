package by.bsuir.myapplication

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
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
    fun getNotes(): Flow<List<Notes>>
    fun getNote(id: UUID?): Flow<Notes?>

    suspend fun upsert(note: Notes)
    suspend fun delete(id: UUID)
}

object InMemoryNotesDataSource: NotesDataSource{

    private val DefaultNotes = listOf(
        Notes("Make 3 PMIS labs", "12112023", Weather(19, 12, "30", "3")),
        Notes("Make 4 PMIS labs", "12112023", Weather(19, 12, "30", "3")),
        Notes("Make 5 PMIS labs", "12112023", Weather(19, 12, "30", "3")))

    private val notes = DefaultNotes.associateBy { it.id }.toMutableMap()

    private val _notesFlow = MutableSharedFlow<Map<UUID, Notes>>(1)

    override fun getNotes(): Flow<List<Notes>> {
        GlobalScope.launch(Dispatchers.Default) {
            while (true) {
                _notesFlow.emit(notes)
                delay(5000L)
            }
        }
        return _notesFlow.asSharedFlow().map { it.values.toList() }
    }

    override fun getNote(id: UUID?): Flow<Notes?> {
        GlobalScope.launch(Dispatchers.Default) {
            while (true) {
                _notesFlow.emit(notes)
                delay(5000L)
            }
        }

        return _notesFlow.asSharedFlow().map { it[id] }
    }

    override suspend fun upsert(note: Notes) {
        notes[note.id] = note
    }

    override suspend fun delete(id: UUID) {
        notes.remove(id)
    }
}

interface NotesRepository {
    fun getNotes(): Flow<List<Notes>>
    fun getNote(id: UUID?): Flow<Notes?>

    suspend fun upsert(note: Notes)
    suspend fun delete(id: UUID)
}

object NotesRepositoryImpl : NotesRepository {

    private val dataSource: NotesDataSource = InMemoryNotesDataSource

    override fun getNotes(): Flow<List<Notes>> {
        return dataSource.getNotes()
    }


    override fun getNote(id: UUID?): Flow<Notes?> {

        return dataSource.getNote(id)
    }

    override suspend fun upsert(note: Notes) {
        dataSource.upsert(note)
    }

    override suspend fun delete(id: UUID) {
        dataSource.delete(id)
    }
}

data class NotesListUiState(
    val notes: List<Notes> = emptyList(),
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


class AddEditViewModel() : ViewModel() {

    private val repository: NotesRepository = NotesRepositoryImpl

    private var noteId: String? = null

    private val _uiState = MutableStateFlow(NoteUiState())
    val uiState: StateFlow<NoteUiState> = _uiState.asStateFlow()

    init{
       // if (noteId != null) {
        //    loadArticle(UUID.fromString(noteId))
        //}
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
            val result = repository.getNote(noteId).first()
            if (result == null) {
                _uiState.update { it.copy(isLoading = false) }
            } else {

                _uiState.update {
                    it.copy(
                        isLoading = false,
                        goal = result.goal,
                        date = result.date,
                        weather = result.weather
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
                    repository.upsert(
                        Notes(
                            id = UUID.fromString(noteId),
                            goal = _uiState.value.goal,
                            date = _uiState.value.date,
                            weather = _uiState.value.weather
                        )
                    )
                } else {
                    repository.upsert(
                        Notes(
                            goal = _uiState.value.goal,
                            date = _uiState.value.date,
                            weather = _uiState.value.weather
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
                    repository.delete(UUID.fromString(noteId))
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

class HomeViewModel : ViewModel() {

    private val repository: NotesRepository = NotesRepositoryImpl
    private val notes = repository.getNotes()

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

            withLoading { repository.delete(noteId) }
        }
    }

}
