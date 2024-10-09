package epm.xnox.fruitshop.data.repository

import android.content.Context
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import epm.xnox.fruitshop.domain.repository.PreferenceRepository
import kotlinx.coroutines.flow.first
import javax.inject.Inject

private const val PREFERENCES_NAME = "preferences"
private val Context.dataStore by preferencesDataStore(name = PREFERENCES_NAME)

class PreferencesRepositoryImpl @Inject constructor(private val context: Context) : PreferenceRepository {

    override suspend fun putPreferenceBooleanValue(key: String, value: Boolean) {
        val preferenceKey = booleanPreferencesKey(key)
        context.dataStore.edit { preferences ->
            preferences[preferenceKey] = value
        }
    }

    override suspend fun putPreferenceStringValue(key: String, value: String) {
        val preferenceKey = stringPreferencesKey(key)
        context.dataStore.edit { preferences ->
            preferences[preferenceKey] = value
        }
    }

    override suspend fun getPreferenceBooleanValue(key: String): Boolean {
        return try {
            val preferenceKey = booleanPreferencesKey(key)
            val preferences = context.dataStore.data.first()
            return preferences[preferenceKey] ?: false
        } catch (e: Exception) {
            e.printStackTrace()
            false
        }
    }

    override suspend fun getPreferenceStringValue(key: String): String? {
        return try {
            val preferenceKey = stringPreferencesKey(key)
            val preferences = context.dataStore.data.first()
            return preferences[preferenceKey] ?: ""
        } catch (e: Exception) {
            e.printStackTrace()
            ""
        }
    }
}