package com.example.phinconattendance.data.datastore

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.intPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject
import javax.inject.Singleton

val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "attendance")

@Singleton
class DataStoreManager @Inject constructor(@ApplicationContext appContext: Context) {

    private val dataStore = appContext.dataStore

    fun getOnboardingStatus(): Flow<Boolean> {
        return dataStore.data.map { preferences ->
            preferences[ONBOARDING_KEY] ?: false
        }
    }

    suspend fun saveOnboardingStatus() {
        dataStore.edit { preferences ->
            preferences[ONBOARDING_KEY] = true
        }
    }

    fun getCheckInStatus(): Flow<CheckInModel> {
        return dataStore.data.map { preferences ->
            CheckInModel(preferences[CHECK_IN_KEY] ?: false, preferences[POSITION_KEY] ?: -1)
        }
    }

    suspend fun saveCheckInStatus(checkInModel: CheckInModel) {
        dataStore.edit { preferences ->
            preferences[CHECK_IN_KEY] = checkInModel.isCheckIn
            preferences[POSITION_KEY] = checkInModel.position
        }
    }

    suspend fun saveCheckOutStatus() {
        dataStore.edit { preferences ->
            preferences[CHECK_IN_KEY] = false
            preferences[POSITION_KEY] = -1
        }
    }

    companion object {
        private val ONBOARDING_KEY = booleanPreferencesKey("status")
        private val CHECK_IN_KEY = booleanPreferencesKey("check_in")
        private val POSITION_KEY = intPreferencesKey("position")
    }
}