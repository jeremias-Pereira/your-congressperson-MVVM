package com.jpgsolution.youcongresspersonmvvm.model.repository

import com.jpgsolution.youcongresspersonmvvm.model.data.ListCongressperson
import com.jpgsolution.youcongresspersonmvvm.model.data.congressPersonDetail.CongresspersonDetails
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path

interface EndPoint {

    @GET("/api/v2/deputados")
    fun getAllCongressPerson(): Call<ListCongressperson>


    @GET("/api/v2/deputados/{id}")
    fun getCongressPersonDetail(@Path("id")id: String): Call<CongresspersonDetails>
}