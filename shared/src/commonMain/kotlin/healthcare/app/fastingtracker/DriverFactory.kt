package healthcare.app.fastingtracker

import app.cash.sqldelight.db.SqlDriver
import com.jonathan.fastingtracker.FastingTrackerDatabase

expect class DriverFactory {
    fun createDriver(): SqlDriver
}

fun createDatabase(driverFactory: DriverFactory): FastingTrackerDatabase {
    val driver = driverFactory.createDriver()
    val database = FastingTrackerDatabase(driver)

    // Database related functions

    return database
}
