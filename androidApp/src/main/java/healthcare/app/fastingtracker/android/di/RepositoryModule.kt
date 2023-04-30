package healthcare.app.fastingtracker.android.di

import healthcare.app.fastingtracker.domain.repository.LocalDataStoreRepository
import healthcare.app.fastingtracker.domain.repository.getDataStore
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module

val repositoryModule = module {
    factory { LocalDataStoreRepository(getDataStore(context = androidContext())) }
}
