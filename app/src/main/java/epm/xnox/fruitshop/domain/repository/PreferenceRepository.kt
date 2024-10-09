package epm.xnox.fruitshop.domain.repository

interface PreferenceRepository {
    suspend fun putPreferenceBooleanValue(key: String, value: Boolean)
    suspend fun putPreferenceStringValue(key: String, value: String)
    suspend fun getPreferenceBooleanValue(key: String) : Boolean?
    suspend fun getPreferenceStringValue(key: String) : String?
}