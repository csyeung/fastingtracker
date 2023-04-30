package healthcare.app.fastingtracker.di

import healthcare.app.fastingtracker.domain.usecase.Greeting
import org.koin.dsl.module

val useCaseModule = module {
    single { Greeting(get(), get()) }
}
