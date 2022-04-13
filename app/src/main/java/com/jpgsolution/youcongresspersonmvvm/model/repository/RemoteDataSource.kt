package com.jpgsolution.youcongresspersonmvvm.model.repository

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.jpgsolution.youcongresspersonmvvm.R
import com.jpgsolution.youcongresspersonmvvm.model.data.Congressperson
import com.jpgsolution.youcongresspersonmvvm.model.data.ListCongressperson
import com.jpgsolution.youcongresspersonmvvm.model.data.congressPersonDetail.CongresspersonDetails
import dagger.hilt.android.qualifiers.ApplicationContext
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import javax.inject.Inject

class RemoteDataSource @Inject constructor(
    private val retrofit: Retrofit,
    @ApplicationContext private val context: Context
): Remote{

    private val _congressperson: MutableLiveData<List<Congressperson>?> by lazy(::MutableLiveData)
    val congressperson: LiveData<List<Congressperson>?> = _congressperson
    private val _congresspersonDetail: MutableLiveData<CongresspersonDetails> by lazy(::MutableLiveData)
    val congresspersonDetail: LiveData<CongresspersonDetails> = _congresspersonDetail

    override fun getCongressPerson(){
        val endPoint = retrofit.create(EndPoint::class.java)
        val callBack = endPoint.getAllCongressPerson()
        callBack.enqueue(object : Callback<ListCongressperson>{
            override fun onResponse(
                call: Call<ListCongressperson>,
                response: Response<ListCongressperson>
            ) {
                when (response.code()){
                    200 -> _congressperson.postValue(response.body()!!.dados)
                    else -> _congressperson.postValue(null)
                }
            }
            override fun onFailure(call: Call<ListCongressperson>, t: Throwable) {
                t.printStackTrace()
                //call.request()
                _congressperson.postValue(null)

            }
        })
    }

    override fun getCongressPersonDetails(id: String){
        val endPoint = retrofit.create(EndPoint::class.java)
        val callback = endPoint.getCongressPersonDetail(id)

        callback.enqueue(object : Callback<CongresspersonDetails>{
            override fun onResponse(
                call: Call<CongresspersonDetails>,
                response: Response<CongresspersonDetails>
            ) {
                if(response.isSuccessful && response.code() == 200){
                    val congressPersonTemp = response.body()!!
                    congressPersonTemp.dados.redeSocial.let {list ->
                        for ((index, item) in list.withIndex()){
                            if (!item.contains("https://"))
                                list[index] = "${context.resources.getStringArray(R.array.social_media_prefix)[index]}${item}"
                        }
                    }
                    _congresspersonDetail.postValue(congressPersonTemp)
                }else _congresspersonDetail.postValue(null)
            }
            override fun onFailure(call: Call<CongresspersonDetails>, t: Throwable) {
                t.printStackTrace()
                _congresspersonDetail.postValue(null)
            }
        })
    }
}