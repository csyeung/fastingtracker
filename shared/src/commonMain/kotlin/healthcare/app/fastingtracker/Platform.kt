package healthcare.app.fastingtracker

import org.koin.core.module.Module

expect class Platform() {
    val currentTime: String
}

expect fun getModule(): Module

@OptIn(ExperimentalMultiplatform::class)
@OptionalExpectation
@Target(AnnotationTarget.CLASS)
@Retention(AnnotationRetention.BINARY)
expect annotation class CommonParcelize()

expect interface CommonParcelable