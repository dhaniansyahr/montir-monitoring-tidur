package com.meone.montir.data.repository

import com.meone.montir.data.pref.MusicModel
import com.meone.montir.data.pref.MusicPreference
import kotlinx.coroutines.flow.Flow

class MusicRepository private constructor(
    private val musicPreference: MusicPreference,
) {

    suspend fun saveMusic(music: MusicModel) {
        musicPreference.saveMusic(music)
    }

    fun getMusic(): Flow<MusicModel> {
        return musicPreference.getMusic()
    }

    companion object {
        @Volatile
        private var instance: MusicRepository? = null
        fun getInstance(
            musicPreference: MusicPreference,
        ): MusicRepository =
            instance ?: synchronized(this) {
                instance ?: MusicRepository(musicPreference)
            }.also { instance = it }
    }
}