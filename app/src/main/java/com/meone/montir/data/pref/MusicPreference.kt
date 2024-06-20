package com.meone.montir.data.pref

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

val Context.musicDataStore: DataStore<Preferences> by preferencesDataStore(name = "music_pref")

class MusicPreference private constructor(private val dataStore: DataStore<Preferences>) {

    suspend fun saveMusic(music: MusicModel) {
        dataStore.edit { preferences ->
            preferences[MUSIC_NAME] = music.musicName
            preferences[MUSIC_DURATION] = music.musicDuration
            preferences[IS_PLAYING] = music.isPlaying
        }
    }

    fun getMusic(): Flow<MusicModel> {
        return dataStore.data.map { preferences ->
            MusicModel(
                preferences[MUSIC_NAME] ?: "",
                preferences[MUSIC_DURATION] ?: "",
                preferences[IS_PLAYING] ?: false,
            )
        }
    }

    companion object {
        @Volatile
        private var INSTANCE: MusicPreference? = null

        private val MUSIC_NAME = stringPreferencesKey("musicName")
        private val MUSIC_DURATION = stringPreferencesKey("musicDuration")
        private val IS_PLAYING = booleanPreferencesKey("isPlaying")

        fun getInstance(dataStore: DataStore<Preferences>): MusicPreference {
            return INSTANCE ?: synchronized(this) {
                val instance = MusicPreference(dataStore)
                INSTANCE = instance
                instance
            }
        }
    }
}