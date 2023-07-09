package healthcare.app.fastingtracker

import android.content.Context
import com.jonathan.fastingtracker.FastingTrackerDatabase
import com.squareup.sqldelight.android.AndroidSqliteDriver
import com.squareup.sqldelight.db.SqlDriver

actual class DriverFactory(private val context: Context) {
    actual fun createDriver(): SqlDriver {
        return AndroidSqliteDriver(
            schema = FastingTrackerDatabase.Schema,
            context = context,
            name = "fastingtracker.db"
        )
    }
}
