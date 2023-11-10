package by.bsuir.myapplication.database.entity

interface Mapper<T, R> {
    fun toDTO(from: T): R
    fun toEntity(from: R): T
}

object NotesMapper : Mapper<NoteEntity, Note> {
    override fun toDTO(from: NoteEntity): Note {
        return Note(
            goal = from.goal,
            date = from.date,
            weather = from.weather,
            id = from.id,
        )
    }

    override fun toEntity(from: Note): NoteEntity {
        return NoteEntity(
            goal = from.goal,
            date = from.date,
            weather = from.weather,
            id = from.id,
        )
    }
}