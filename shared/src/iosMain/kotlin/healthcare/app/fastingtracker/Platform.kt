package healthcare.app.fastingtracker

import healthcare.app.fastingtracker.domain.repository.LocalDataStoreRepository
import healthcare.app.fastingtracker.domain.repository.LocalDataStoreRepositoryImpl
import healthcare.app.fastingtracker.domain.repository.getDataStore
import healthcare.app.fastingtracker.domain.usecase.Greeting
import org.koin.dsl.module
import platform.Foundation.NSDate
import platform.Foundation.now

actual class Platform actual constructor() {
    actual val currentTime: String = NSDate.now().toString()
}

actual fun getModule() = module {
    single { Greeting(get(), get()) }
    factory<LocalDataStoreRepository> { LocalDataStoreRepositoryImpl(getDataStore()) }
}

actual interface CommonParcelable