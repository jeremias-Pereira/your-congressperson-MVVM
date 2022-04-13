package com.jpgsolution.youcongresspersonmvvm.viewmodel

import androidx.lifecycle.*
import com.jpgsolution.youcongresspersonmvvm.model.data.congressPersonDetail.CongresspersonDetails
import com.jpgsolution.youcongresspersonmvvm.model.repository.Remote
import com.jpgsolution.youcongresspersonmvvm.model.repository.RemoteDataSource
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class DetailViewModel @Inject constructor(remote: Remote): ViewModel() {

    private val remoteDataSource: RemoteDataSource = remote as RemoteDataSource

    fun getCongressperson(id: String){
        remoteDataSource.getCongressPersonDetails(id)
    }

    val congressperson: LiveData<CongresspersonDetails> = remoteDataSource.congresspersonDetail

}