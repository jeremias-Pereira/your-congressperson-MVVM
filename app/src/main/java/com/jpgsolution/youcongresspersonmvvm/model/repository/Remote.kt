package com.jpgsolution.youcongresspersonmvvm.model.repository

interface Remote {

    fun getCongressPerson()
    fun getCongressPersonDetails(id: String)
}