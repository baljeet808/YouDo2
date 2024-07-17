package common

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences

fun createDatastore(): DataStore<Preferences>{
    return createDatastore {
        val directory = NSFileManager.defaultManager.URLForDirectory(
            directory = NSDocumentDirectory,
            inDomain = NSUserDomainMask,
            appropriateForURL = null,
            create = false,
            error = null
        )
        requireNotNull(directory).path + "/$DATASTORE_FILE_NAME"
    }
}