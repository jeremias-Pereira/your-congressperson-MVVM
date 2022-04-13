package com.jpgsolution.youcongresspersonmvvm.model.data

data class ListCongressperson(
    val dados: ArrayList<Congressperson>,
)

data class Congressperson(
    val id: String,
    val nome: String,
    val siglaPartido: String,
    val siglaUf: String,
    val urlFoto: String,
)
