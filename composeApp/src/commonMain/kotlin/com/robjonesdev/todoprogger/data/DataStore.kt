package com.robjonesdev.todoprogger.data

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences

const val DATA_STORE_FILE_NAME = "prefs.preferences_pb"

expect fun createDataStore(context: Any?): DataStore<Preferences>
