package com.jpgsolution.youcongresspersonmvvm.model.data.congressPersonDetail


data class UltimoStatus(
    val email: String,
    val gabinete: Gabinete,
    val id: Int,
    val nomeEleitoral: String,
    val siglaPartido: String,
    val siglaUf: String,
    val urlFoto: String
)