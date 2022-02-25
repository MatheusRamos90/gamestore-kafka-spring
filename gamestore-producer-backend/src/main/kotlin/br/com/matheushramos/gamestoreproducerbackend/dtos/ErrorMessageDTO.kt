package br.com.matheushramos.gamestoreproducerbackend.dtos

data class ErrorMessageDTO(
    var message: String,
    var codeStatus: Int,
    var errors: List<ErrorFieldMessageDTO>? = null
)