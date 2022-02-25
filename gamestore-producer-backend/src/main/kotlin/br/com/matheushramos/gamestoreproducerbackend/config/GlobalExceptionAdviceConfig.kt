package br.com.matheushramos.gamestoreproducerbackend.config

import br.com.matheushramos.gamestoreproducerbackend.dtos.ErrorFieldMessageDTO
import br.com.matheushramos.gamestoreproducerbackend.dtos.ErrorMessageDTO
import br.com.matheushramos.gamestoreproducerbackend.exception.BadRequestException
import org.slf4j.LoggerFactory
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.MethodArgumentNotValidException
import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.context.request.WebRequest

@ControllerAdvice
class GlobalExceptionAdviceConfig {

    private val log = LoggerFactory.getLogger(this.javaClass)

    @ExceptionHandler(MethodArgumentNotValidException::class)
    fun handleMethodArgumentNotValidException(ex: MethodArgumentNotValidException, request: WebRequest): ResponseEntity<ErrorMessageDTO> {
        log.error("MethodArgumentNotValidException exception message: ${ex.message}")
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ErrorMessageDTO(
            ex.message,
            HttpStatus.BAD_REQUEST.value(),
            ex.bindingResult.fieldErrors.map { ErrorFieldMessageDTO(it.defaultMessage ?: "invalid", it.field) }))
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