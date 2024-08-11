package data.local.preferences

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.PreferenceDataStoreFactory
import androidx.datastore.preferences.core.Preferences
import kotlinx.coroutines.InternalCoroutinesApi
import kotlinx.coroutines.internal.SynchronizedObject
import kotlinx.coroutines.internal.synchronized
import okio.Path.Companion.toPath

internal const val dataStoreFileName = "prefs.preferences_pb"

@OptIn(InternalCoroutinesApi::class)
object AppSettings{
    private lateinit var dataStore: DataStore<Preferences>
    private val lock = SynchronizedObject()

    fun getDataStore(producePath : () -> String): DataStore<Preferences> {
        return synchronized(lock){
            if (AppSettings::dataStore.isInitialized){
                dataStore
            }else{
                PreferenceDataStoreFactory.createWithPath(
                    produceFile = {producePath().toPath()}
                ).also { dataStore  = it }
            }
        }
    }
}