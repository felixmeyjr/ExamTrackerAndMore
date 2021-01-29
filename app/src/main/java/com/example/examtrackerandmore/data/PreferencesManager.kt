package com.example.examtrackerandmore.data

import android.content.Context
import android.util.Log
import androidx.datastore.preferences.createDataStore
import androidx.datastore.preferences.edit
import androidx.datastore.preferences.emptyPreferences
import androidx.datastore.preferences.preferencesKey
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import java.io.IOException
import javax.inject.Inject
import javax.inject.Singleton


// TODO: 29.01.2021 add doc #8
/**
 * This class saves the option preferences with Jetpack Datastorage, which are currently:
 *
 * HIDE_COMPLETED
 *
 *
 */

private const val TAG = "PreferencesManager"

// todo create wrapper to return the map, if necessary for two objects
data class FilterPreferences(val hideCompleted: Boolean)

@Singleton
class PreferencesManager @Inject constructor(@ApplicationContext context: Context){

    private val dataStore = context.createDataStore("user_preferences")

    // Create preferences dataStore
    val preferencesFlow = dataStore.data
        .catch { exception ->
            if (exception is IOException) {
                Log.e(TAG, "Error reading preferences", exception)
                emit(emptyPreferences())
            } else {
                throw exception
            }
        }
        .map { preferences ->
            val hideCompleted = preferences[PreferencesKeys.HIDE_COMPLETED] ?: false // :? elvis operator, can contain NULL reference (when there is no value)
            FilterPreferences(hideCompleted)
        }

    // Update existing preferences
    suspend fun updateHideCompleted(hideCompleted: Boolean) {
        dataStore.edit { preferences ->
            preferences[PreferencesKeys.HIDE_COMPLETED] = hideCompleted
        }
    }



    // more readable as object
    private object PreferencesKeys {
        val HIDE_COMPLETED = preferencesKey<Boolean>("hide_completed")
    }
}