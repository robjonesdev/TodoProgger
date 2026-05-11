package com.robjonesdev.todoprogger.data

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.PreferenceDataStoreFactory
import okio.Path.Companion.toPath
import java.io.File
import java.util.concurrent.locks.ReentrantLock
import kotlin.concurrent.withLock

private var instance: DataStore<Preferences>? = null
private val lock = ReentrantLock()

actual fun createDataStore(context: Any?): DataStore<Preferences> {
    return lock.withLock {
        instance ?: run {
            require(context is Context) { "Android DataStore requires a Context" }
            val localInstance = PreferenceDataStoreFactory.createWithPath(
                produceFile = {
                    val file = File(context.applicationContext.filesDir, "datastore/$DATA_STORE_FILE_NAME")
                    file.parentFile?.mkdirs()
                    file.absolutePath.toPath()
                }
            )
            instance = localInstance
            localInstance
        }
    }
}
