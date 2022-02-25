package br.com.matheushramos.gamestoreproducerbackend.exception

data class BadRequestException(override var message: String, var code: Int): Exception()