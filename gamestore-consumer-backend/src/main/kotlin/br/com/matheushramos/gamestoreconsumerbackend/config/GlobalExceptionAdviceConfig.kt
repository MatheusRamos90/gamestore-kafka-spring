package br.com.matheushramos.gamestoreconsumerbackend.config

import br.com.matheushramos.gamestoreconsumerbackend.dtos.ErrorMessageDTO
import br.com.matheushramos.gamestoreconsumerbackend.exception.BadRequestException
import br.com.matheushramos.gamestoreconsumerbackend.exception.NotFoundException
import org.slf4j.LoggerFactory
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.context.request.WebRequest

@ControllerAdvice
class GlobalExceptionAdviceConfig {

    private val log = LoggerFactory.getLogger(this.javaClass)

    @ExceptionHandler(NotFoundException::class)
    fun handleNotFoundException(ex: NotFoundException, request: WebRequest): ResponseEntity<ErrorMessageDTO> {
        log.error("NotFoundException exception message: ${ex.message}")
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ErrorMessageDTO(
            ex.message,
            HttpStatus.NOT_FOUND.value(),
            null
        ))
    }

    @ExceptionHandler(BadRequestException::class)
    fun handleBadRequestException(ex: BadRequestException, request: WebRequest): ResponseEntity<ErrorMessageDTO> {
        log.error("BadRequestException exception message: ${ex.message}")
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ErrorMessageDTO(
            ex.message,
            HttpStatus.BAD_REQUEST.value(),
            null
        ))
    }

}