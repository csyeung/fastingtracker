package healthcare.app.fastingtracker.android

import android.app.Application
import healthcare.app.fastingtracker.android.di.repositoryModule
import healthcare.app.fastingtracker.android.di.viewModelModule
import healthcare.app.fastingtracker.di.appModules
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

/**
 * @author Jonathan YEUNG
 * @since 2023-04-21
 */
class FastingTracker : Application() {
    override fun onCreate() {
        super.onCreate()
        initKoin()
    }

    private fun initKoin() {
        startKoin {
            androidContext(this@FastingTracker)
            modules(appModules)
            modules(viewModelModule)
            modules(repositoryModule)
        }
    }
}
