package com.example.vozdux.data.local.util

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.longPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import com.example.vozdux.constants.DATASTORE_COUNTER_KEY
import com.example.vozdux.constants.DATASTORE_NAME
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class DataStoreManager @Inject constructor(
    private val context: Context
) {

    private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = DATASTORE_NAME)

    suspend fun getCounter() = context.dataStore.data.map { pref ->
        return@map pref[longPreferencesKey(DATASTORE_COUNTER_KEY)] ?: 0L
    }

    suspend fun setCounter(counter: Long) {
        context.dataStore.edit { pref ->
            pref[longPreferencesKey(DATASTORE_COUNTER_KEY)] = counter
        }
    }
}