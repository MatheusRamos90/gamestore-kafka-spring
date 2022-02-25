package br.com.matheushramos.gamestoreconsumerbackend.consumer.dtos

data class PurchaseGameDTO(
    var customerId: String,
    var games: MutableList<String>
)