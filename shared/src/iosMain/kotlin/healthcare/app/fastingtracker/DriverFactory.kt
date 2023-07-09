package healthcare.app.fastingtracker

import com.jonathan.fastingtracker.FastingTrackerDatabase
import com.squareup.sqldelight.db.SqlDriver
import com.squareup.sqldelight.drivers.native.NativeSqliteDriver

actual class DriverFactory {
    actual fun createDriver(): SqlDriver {
        return NativeSqliteDriver(
            schema = FastingTrackerDatabase.Schema,
            name = "fastingtracker.db"
        )
    }
}
