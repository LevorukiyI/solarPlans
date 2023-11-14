package by.bsuir.myapplication.database.entity

import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module

val databaseModule = module {
    single { MyDatabase.get(androidContext().applicationContext)  }
    single { get<MyDatabase>().notesDAO() }
}

val appModule = module {
    includes(databaseModule)
}

