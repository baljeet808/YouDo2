package data.local.preferences

import platform.Foundation.*
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import platform.Foundation.NSDocumentDirectory
import platform.Foundation.NSFileManager
import platform.Foundation.NSUserDomainMask
import platform.Foundation.NSURL

private const val DATA_STORE_FILE_NAME = "my_data_store.preferences_pb"

actual fun createDataStore(context: Any?): DataStore<Preferences> {
    return AppSettings.getDataStore(
        producePath = {
            val urls = NSFileManager.defaultManager.URLsForDirectory(NSDocumentDirectory, NSUserDomainMask)
            val url = urls.first() as? NSURL
            url?.URLByAppendingPathComponent(DATA_STORE_FILE_NAME)?.path
                ?: error("Could not get document directory")
        }
    )
}