package healthcare.app.fastingtracker

import android.content.Context
import app.cash.sqldelight.async.coroutines.synchronous
import app.cash.sqldelight.db.SqlDriver
import app.cash.sqldelight.driver.android.AndroidSqliteDriver
import com.jonathan.fastingtracker.FastingTrackerDatabase

actual class DriverFactory(private val context: Context) {
    actual fun createDriver(): SqlDriver {
        return AndroidSqliteDriver(
            schema = FastingTrackerDatabase.Schema.synchronous(),
            context = context,
            name = "fastingtracker.db"
        )
    }
}
