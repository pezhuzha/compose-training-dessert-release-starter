package com.example.dessertrelease.data

import android.util.Log
import android.widget.LinearLayout
import androidx.datastore.core.DataStore
import androidx.datastore.core.IOException
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.emptyPreferences
import androidx.datastore.preferences.core.preferencesOf
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map


class UserPreferencesRepository(
    private val dataStore: DataStore<Preferences>

) {
    val isLinearLayout: Flow<Boolean> = dataStore.data.catch { if (it is IOException){
        Log.e(TAG,"Error reading preferences.",it);
        emit(emptyPreferences());
    }else{
        throw it;
    } }.map {preferences->
        preferences[IS_LINEAR_LAYOUT]?:true;};
    private companion object{
        val IS_LINEAR_LAYOUT = booleanPreferencesKey("is_linear_layout");
        const val TAG= "UserPreferencesRepo"
    }
    suspend fun saveLayoutPreference(isLinearLayout: Boolean){
        dataStore.edit { preferences->
            preferences[IS_LINEAR_LAYOUT]=isLinearLayout;
        }
    }
}