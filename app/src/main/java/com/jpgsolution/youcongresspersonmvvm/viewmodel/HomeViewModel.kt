package com.jpgsolution.youcongresspersonmvvm.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.jpgsolution.youcongresspersonmvvm.model.data.Congressperson
import com.jpgsolution.youcongresspersonmvvm.model.repository.Remote
import com.jpgsolution.youcongresspersonmvvm.model.repository.RemoteDataSource
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(remote: Remote): ViewModel() {
    private val remoteDataSource: RemoteDataSource = remote as RemoteDataSource

    fun getCongressPersons(){
        remoteDataSource.getCongressPerson()
    }

    val congressperson: LiveData<List<Congressperson>?> = remoteDataSource.congressperson
}