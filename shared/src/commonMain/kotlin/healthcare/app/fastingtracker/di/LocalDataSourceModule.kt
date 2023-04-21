package healthcare.app.fastingtracker.di

import healthcare.app.fastingtracker.Greeting
import org.koin.dsl.module

val localDataSourceModule = module {
    single { Greeting() }
}
