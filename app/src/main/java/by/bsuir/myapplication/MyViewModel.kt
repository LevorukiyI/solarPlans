package by.bsuir.myapplication

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import by.bsuir.myapplication.database.entity.DatabaseRepository
import by.bsuir.myapplication.database.entity.Mapper
import by.bsuir.myapplication.database.entity.MyDatabase
import by.bsuir.myapplication.database.entity.Note
import by.bsuir.myapplication.database.entity.NoteEntity
import by.bsuir.myapplication.database.entity.NotesDataSourceDAO
import by.bsuir.myapplication.database.entity.NotesMapper
import by.bsuir.myapplication.database.entity.Weather
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
import org.koin.android.ext.android.inject
import org.koin.java.KoinJavaComponent
import org.koin.java.KoinJavaComponent.inject
import java.util.UUID

interface NotesDataSource {
    fun getNotes(): Flow<List<Note>>
    fun getNote(id: UUID?): Flow<Note?>

    suspend fun upsert(note: Note)
    suspend fun delete(id: UUID)
}

class RoomNotesDataSource(private val notesDAO: NotesDataSourceDAO, private val mapper: Mapper<NoteEntity, Note>) : NotesDataSourceDAO {
    override fun getNotes(): Flow<List<Note>> {
        return notesDAO.getNotes().map { list -> list.map { mapper.toDTO(it) } }
    }

    override fun getNote(id: UUID?): Flow<Note?> {
        return notesDAO.getNote(id).map { it?.let { mapper.toDTO(it) } }
    }

    override suspend fun upsert(note: NoteEntity) {
        notesDAO.upsert(note)
    }

    suspend fun upsert(note: Note) {
        notesDAO.upsert(mapper.toEntity(note))
    }

    override suspend fun delete(id: UUID) {
        notesDAO.delete(id)
    }
}

object InMemoryNotesDataSource: NotesDataSource{

    private val DefaultNotes = listOf(
        Note("Make 3 PMIS labs", "12112023", Weather(19, 12, "30", "3")),
        Note("Make 4 PMIS labs", "12112023", Weather(19, 12, "30", "3")),
        Note("Make 5 PMIS labs", "12112023", Weather(19, 12, "30", "3")))

    private val notes = DefaultNotes.associateBy { it.id }.toMutableMap()

    private val _notesFlow = MutableSharedFlow<Map<UUID, Note>>(1)

    override fun getNotes(): Flow<List<Note>> {
        GlobalScope.launch(Dispatchers.Default) {
            while (true) {
                _notesFlow.emit(notes)
                delay(5000L)
            }
        }
        return _notesFlow.asSharedFlow().map { it.values.toList() }
    }

    override fun getNote(id: UUID?): Flow<Note?> {
        GlobalScope.launch(Dispatchers.Default) {
            while (true) {
                _notesFlow.emit(notes)
                delay(5000L)
            }
        }

        return _notesFlow.asSharedFlow().map { it[id] }
    }

    override suspend fun upsert(note: Note) {
        notes[note.id] = note
    }

    override suspend fun delete(id: UUID) {
        notes.remove(id)
    }
}

interface NotesRepository {
    fun getNotes(): Flow<List<Note>>
    fun getNote(id: UUID?): Flow<Note?>

    suspend fun upsert(note: Note)
    suspend fun delete(id: UUID)
}

object NotesRepositoryImpl : NotesRepository {

    private val dataSource: NotesDataSource = InMemoryNotesDataSource

    override fun getNotes(): Flow<List<Note>> {
        return dataSource.getNotes()
    }


    override fun getNote(id: UUID?): Flow<Note?> {

        return dataSource.getNote(id)
    }

    override suspend fun upsert(note: Note) {
        dataSource.upsert(note)
    }

    override suspend fun delete(id: UUID) {
        dataSource.delete(id)
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


class AddEditViewModel(application: Application) : AndroidViewModel(application) {

    private val notesDAO: NotesDataSourceDAO by KoinJavaComponent.inject(NotesDataSourceDAO::class.java)

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
            val result = notesDAO.getNote(noteId).first()
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
                    notesDAO.upsert(
                        NotesMapper.toEntity(Note(
                            id = UUID.fromString(noteId),
                            goal = _uiState.value.goal,
                            date = _uiState.value.date,
                            weather = _uiState.value.weather
                        ))

                    )
                } else {
                    notesDAO.upsert(
                        NotesMapper.toEntity(
                        Note(
                            goal = _uiState.value.goal,
                            date = _uiState.value.date,
                            weather = _uiState.value.weather
                        )
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
                    notesDAO.delete(UUID.fromString(noteId))
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

    private val notesDAO: NotesDataSourceDAO by KoinJavaComponent.inject(NotesDataSourceDAO::class.java)
    private val notes = notesDAO.getNotes()
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

            withLoading { notesDAO.delete(noteId) }
        }
    }

}
