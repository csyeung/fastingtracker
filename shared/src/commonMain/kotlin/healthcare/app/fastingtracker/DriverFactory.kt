package healthcare.app.fastingtracker

import com.jonathan.fastingtracker.FastingTrackerDatabase
import com.squareup.sqldelight.db.SqlDriver

expect class DriverFactory {
    fun createDriver(): SqlDriver
}

fun createDatabase(driverFactory: DriverFactory): FastingTrackerDatabase {
    val driver = driverFactory.createDriver()
    return FastingTrackerDatabase(driver)
}
