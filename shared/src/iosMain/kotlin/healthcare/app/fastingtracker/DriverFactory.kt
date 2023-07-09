package healthcare.app.fastingtracker

import app.cash.sqldelight.async.coroutines.synchronous
import app.cash.sqldelight.db.SqlDriver
import app.cash.sqldelight.driver.native.NativeSqliteDriver
import com.jonathan.fastingtracker.FastingTrackerDatabase

actual class DriverFactory {
    actual fun createDriver(): SqlDriver {
        return NativeSqliteDriver(
            schema = FastingTrackerDatabase.Schema.synchronous(),
            name = "fastingtracker.db"
        )
    }
}
