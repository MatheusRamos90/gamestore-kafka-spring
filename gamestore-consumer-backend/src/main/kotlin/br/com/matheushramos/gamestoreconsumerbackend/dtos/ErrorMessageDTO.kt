package br.com.matheushramos.gamestoreconsumerbackend.dtos

data class ErrorMessageDTO(
    var message: String,
    var codeStatus: Int,
    var errors: List<ErrorFieldMessageDTO>? = null
)