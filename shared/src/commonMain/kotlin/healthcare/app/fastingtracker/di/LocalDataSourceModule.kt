package healthcare.app.fastingtracker.di

import healthcare.app.fastingtracker.Platform
import org.koin.dsl.module

val localDataSourceModule = module {
    single { Platform() }
}
