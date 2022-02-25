package br.com.matheushramos.gamestoreconsumerbackend.model

import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.Document
import java.math.BigDecimal

@Document
data class Game(
    @Id
    var id: String? = null,

    var name: String,
    var price: BigDecimal,
    var refCode: String
)