package common

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences

fun createDatastore(context: Context): DataStore<Preferences>{
    return createDatastore {
        context.filesDir.resolve(DATASTORE_FILE_NAME).absolutePath
    }
}