package com.jpgsolution.youcongresspersonmvvm.di.module

import com.jpgsolution.youcongresspersonmvvm.model.repository.Remote
import com.jpgsolution.youcongresspersonmvvm.model.repository.RemoteDataSource
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent

@Module
@InstallIn(ViewModelComponent::class)
interface RemoteDatasourceModule {

    @Binds
    fun remoteDataSource(remoteDataSource: RemoteDataSource): Remote
}