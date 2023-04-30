package healthcare.app.fastingtracker.di

import healthcare.app.fastingtracker.domain.usecase.Greeting
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject
import org.koin.core.context.startKoin

/**
 * @author Jonathan YEUNG
 * @since 2023-04-23
 */
fun initKoin() {
    startKoin {
        modules(appModules)
    }
}

// Injection Bootstrap Helper
class Helper : KoinComponent {
    private val greeting: Greeting by inject()
    fun greet(): String = greeting.greet()
}
