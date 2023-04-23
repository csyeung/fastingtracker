package healthcare.app.fastingtracker.di

import healthcare.app.fastingtracker.Greeting
import org.koin.dsl.module

val useCaseModule = module {
    single { Greeting(get()) }
}