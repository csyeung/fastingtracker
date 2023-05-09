package healthcare.app.fastingtracker

import android.os.Parcelable
import healthcare.app.fastingtracker.domain.repository.LocalDataStoreRepository
import healthcare.app.fastingtracker.domain.repository.LocalDataStoreRepositoryImpl
import healthcare.app.fastingtracker.domain.repository.getDataStore
import healthcare.app.fastingtracker.domain.usecase.Greeting
import kotlinx.parcelize.Parcelize
import org.koin.dsl.module
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import org.koin.android.ext.koin.androidContext

actual class Platform actual constructor() {
    companion object {
        private const val DEFAULT_FORMATTER = "yyyy-MM-dd HH:mm"
    }

    actual val currentTime: String = LocalDateTime.now().format(DateTimeFormatter.ofPattern(
        DEFAULT_FORMATTER))
}

actual fun getModule() = module {
    single { Greeting(get(), get()) }
    factory<LocalDataStoreRepository> { LocalDataStoreRepositoryImpl(getDataStore(context = androidContext())) }
}

actual typealias CommonParcelize = Parcelize
actual typealias CommonParcelable = Parcelable