package br.com.matheushramos.gamestoreconsumerbackend.model

import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.Document

@Document
data class Customer(
    @Id
    var id: String? = null,

    var name: String,
    var email: String
)