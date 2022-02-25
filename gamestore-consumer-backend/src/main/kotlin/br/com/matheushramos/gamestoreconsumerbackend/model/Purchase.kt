package br.com.matheushramos.gamestoreconsumerbackend.model

import lombok.AllArgsConstructor
import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.Document

@AllArgsConstructor
@Document
data class Purchase (
    @Id
    var id: String? = null,

    var customer: Customer,
    var games: MutableList<Game>
)