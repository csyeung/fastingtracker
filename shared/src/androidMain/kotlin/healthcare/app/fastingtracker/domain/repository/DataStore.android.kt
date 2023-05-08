package healthcare.app.fastingtracker.domain.repository

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences

fun getDataStore(context: Context): DataStore<Preferences> = getDataStore(
    producePath = {
        context.filesDir.resolve(dataStoreFileName).absolutePath
    }
)