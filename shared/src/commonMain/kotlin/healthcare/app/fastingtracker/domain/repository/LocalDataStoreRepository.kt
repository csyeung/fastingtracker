package healthcare.app.fastingtracker.domain.repository

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

interface LocalDataStoreRepository {
    val startTrackingTime: Flow<String>
    fun saveStartTrackingTime(trackingTime: String)
    fun clearStartTrackingTime()
    suspend fun hasStartTrackingTime(): Boolean
}

class LocalDataStoreRepositoryImpl(
    private val dataStore: DataStore<Preferences>
) : LocalDataStoreRepository {
    companion object {
        private const val KEY_START_TRACKING_TIME = "start_tracking_time"
    }

    private val scope = CoroutineScope(Dispatchers.Default)

    /**
     * Data Store Key
     */
    private val startTrackingTimeKey = stringPreferencesKey(KEY_START_TRACKING_TIME)

    override val startTrackingTime: Flow<String> = dataStore.data.map {
        it[startTrackingTimeKey] ?: ""
    }

    override fun saveStartTrackingTime(trackingTime: String) {
        scope.launch {
            dataStore.edit {
                it[startTrackingTimeKey] = trackingTime
            }
        }
    }

    override fun clearStartTrackingTime() {
        scope.launch {
            dataStore.edit {
                if (it.contains(startTrackingTimeKey)) {
                    it.remove(startTrackingTimeKey)
                }
            }
        }
    }

    override suspend fun hasStartTrackingTime(): Boolean {
        return dataStore.data.stateIn(scope).value.contains(startTrackingTimeKey)
    }
}