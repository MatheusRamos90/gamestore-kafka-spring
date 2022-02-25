package br.com.matheushramos.gamestoreconsumerbackend.exception

data class NotFoundException(override var message: String, var code: Int): Exception()