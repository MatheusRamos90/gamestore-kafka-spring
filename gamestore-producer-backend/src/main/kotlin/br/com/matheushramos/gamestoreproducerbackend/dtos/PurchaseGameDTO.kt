package br.com.matheushramos.gamestoreproducerbackend.dtos

import javax.validation.Valid
import javax.validation.constraints.NotBlank
import javax.validation.constraints.NotNull

data class PurchaseGameDTO(
    @field: NotBlank(message = "purchasegame.customerId is required")
    @field: NotNull(message = "purchasegame.customerId is required")
    var customerId: String,

    @field: NotNull(message = "purchasegame.games is required")
    @field: Valid
    var games: MutableList<String>
)