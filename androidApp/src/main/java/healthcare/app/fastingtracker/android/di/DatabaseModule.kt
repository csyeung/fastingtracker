package healthcare.app.fastingtracker.android.di

import healthcare.app.fastingtracker.DriverFactory
import healthcare.app.fastingtracker.createDatabase
import org.koin.dsl.module

val databaseModule = module {
    single { createDatabase(DriverFactory(get())) }
}