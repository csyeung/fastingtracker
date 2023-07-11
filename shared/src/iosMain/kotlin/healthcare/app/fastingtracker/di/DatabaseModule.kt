package healthcare.app.fastingtracker.di

import healthcare.app.fastingtracker.DriverFactory
import org.koin.dsl.module

val databaseModule = module {
    single { DriverFactory() }
}
