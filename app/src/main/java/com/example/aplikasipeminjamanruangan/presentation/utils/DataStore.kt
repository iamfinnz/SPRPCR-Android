package com.example.aplikasipeminjamanruangan.presentation.utils

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class DataStore(private val context: Context) {
    companion object {
        private val Context.dataStore: DataStore<Preferences> by preferencesDataStore("storeData")
        val jMulai = stringPreferencesKey("JMulai")
        val jSelesai = stringPreferencesKey("JSelesai")
        val tgl = stringPreferencesKey("tgl")
    }

    val getData: Flow<Triple<String?, String?, String?>> = context.dataStore.data
        .map { preferences ->
            Triple(preferences[jMulai] ?: "", preferences[jSelesai] ?: "", preferences[tgl] ?: "")
        }

    suspend fun saveData(jMulaiValue: String, jSelesaiValue: String, tglValue: String) {
        context.dataStore.edit { preferences ->
            preferences[jMulai] = jMulaiValue
            preferences[jSelesai] = jSelesaiValue
            preferences[tgl] = tglValue
        }
    }

    fun isKeyStored(key: Preferences.Key<String>): Flow<Boolean>  =
        context.dataStore.data.map {
                preference -> preference.contains(key)
        }
}

