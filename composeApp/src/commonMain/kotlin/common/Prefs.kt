package common

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.PreferenceDataStoreFactory
import androidx.datastore.preferences.core.Preferences
import okio.Path.Companion.toPath


fun createDatastore(producePath : () -> String): DataStore<Preferences> {
    return PreferenceDataStoreFactory.createWithPath (
        produceFile = {producePath().toPath()}
    )
}

internal const val DATASTORE_FILE_NAME = "prefs.preferences_pb"