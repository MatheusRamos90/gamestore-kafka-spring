package br.com.matheushramos.gamestoreconsumerbackend.exception

data class BadRequestException(override var message: String, var code: Int): Exception()