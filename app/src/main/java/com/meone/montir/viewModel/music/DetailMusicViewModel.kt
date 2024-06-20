package com.meone.montir.viewModel.music

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.meone.montir.data.pref.MusicModel
import com.meone.montir.data.repository.MusicRepository
import kotlinx.coroutines.launch

class DetailMusicViewModel(private val repository: MusicRepository) : ViewModel() {

    fun saveMusic(musicModel: MusicModel) {
        viewModelScope.launch {
            repository.saveMusic(musicModel)
        }
    }

    fun getMusic(): LiveData<MusicModel> {
        return repository.getMusic().asLiveData()
    }
}