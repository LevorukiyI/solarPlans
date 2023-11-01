package by.bsuir.myapplication.database.entity

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import by.bsuir.myapplication.NotesDataSource


    @Database(entities = [Notes::class], version = 1)
    abstract class MyDatabase : RoomDatabase() {
        companion object {
            private var INSTANCE: MyDatabase? = null

            fun get(context: Context): MyDatabase {
                if (INSTANCE == null) {
                    INSTANCE =
                        Room.databaseBuilder(context, MyDatabase::class.java, "database").build()
                }
                return INSTANCE as MyDatabase
            }

        }
        abstract fun notesDAO(): NotesDataSource

    }


