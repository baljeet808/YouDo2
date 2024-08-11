package domain.repository_interfaces

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.emptyFlow
import kotlinx.coroutines.flow.map


class DataStoreRepository(
    private val dataStore: DataStore<Preferences>
) {
    companion object {
        val IS_USER_LOGGED_IN_KEY = booleanPreferencesKey("isUserLoggedIn")
        val HAS_ONBOARDED_KEY = booleanPreferencesKey("hasOnboardedKey")
        val IS_USER_PRO_KEY = booleanPreferencesKey("isUserPro")
        val USER_ID_KEY = stringPreferencesKey("userId")
        val USER_NAME_KEY = stringPreferencesKey("userName")
        val USER_EMAIL_KEY = stringPreferencesKey("userEmail")
        val USER_AVATAR_KEY = stringPreferencesKey("userAvatar")
    }

    suspend fun saveHasOnboarded(hasOnboarded: Boolean): Boolean =
        try {
            dataStore.edit { preferences ->
                preferences.set(key = HAS_ONBOARDED_KEY, value = hasOnboarded)
            }
            true
        } catch (e: Exception) {
            e.printStackTrace()
            false
        }

    fun hasOnboardedAsFlow(): Flow<Boolean> = dataStore.data
        .catch { emptyFlow<Boolean>() }
        .map { preferences ->
            preferences[HAS_ONBOARDED_KEY] ?: false
        }

    suspend fun saveIsUserLoggedIn(isUserLoggedIn: Boolean): Boolean =
        try {
            dataStore.edit { preferences ->
                preferences.set(key = IS_USER_LOGGED_IN_KEY, value = isUserLoggedIn)
            }
            true
        } catch (e: Exception) {
            e.printStackTrace()
            false
        }

    fun isUserLoggedInAsFlow(): Flow<Boolean> = dataStore.data
        .catch { emptyFlow<Boolean>() }
        .map { preferences ->
            preferences[IS_USER_LOGGED_IN_KEY] ?: false
        }

    suspend fun saveIsUserPro(isUserPro: Boolean): Boolean =
        try {
            dataStore.edit { preferences ->
                preferences.set(key = IS_USER_PRO_KEY, value = isUserPro)
            }
            true
        } catch (e: Exception) {
            e.printStackTrace()
            false
        }

    fun isUserProAsFlow(): Flow<Boolean> = dataStore.data
        .catch { emptyFlow<Boolean>() }
        .map { preferences ->
            preferences[IS_USER_PRO_KEY] ?: false
        }

    suspend fun saveUserId(userId: String): Boolean =
        try {
            dataStore.edit { preferences ->
                preferences.set(key = USER_ID_KEY, value = userId)
            }
            true
        }catch (e: Exception) {
            e.printStackTrace()
            false
        }

    fun userIdAsFlow(): Flow<String> = dataStore.data
        .catch { emptyFlow<String>() }
        .map { preferences ->
            preferences[USER_ID_KEY] ?: ""
        }

    suspend fun saveUserName(userName: String): Boolean =
        try {
            dataStore.edit { preferences ->
                preferences.set(key = USER_NAME_KEY, value = userName)
            }
            true
        }catch (e: Exception) {
            e.printStackTrace()
            false
        }

    fun userNameAsFlow(): Flow<String> = dataStore.data
        .catch { emptyFlow<String>() }
        .map { preferences ->
            preferences[USER_NAME_KEY] ?: ""
        }


    suspend fun saveUserEmail(email: String): Boolean =
        try {
            dataStore.edit { preferences ->
                preferences.set(key = USER_EMAIL_KEY, value = email)
            }
            true
        }catch (e: Exception) {
            e.printStackTrace()
            false
        }

    fun userEmailAsFlow(): Flow<String> = dataStore.data
        .catch { emptyFlow<String>() }
        .map { preferences ->
            preferences[USER_EMAIL_KEY] ?: ""
        }


    suspend fun saveUserAvatar(avatar: String): Boolean =
        try {
            dataStore.edit { preferences ->
                preferences.set(key = USER_AVATAR_KEY, value = avatar)
            }
            true
        }catch (e: Exception) {
            e.printStackTrace()
            false
        }

    fun userAvatarAsFlow(): Flow<String> = dataStore.data
        .catch { emptyFlow<String>() }
        .map { preferences ->
            preferences[USER_AVATAR_KEY] ?: ""
        }



}