package healthcare.app.fastingtracker.di

import healthcare.app.fastingtracker.getModule

val appModules = listOf(
    localDataSourceModule,
    getModule()
)
