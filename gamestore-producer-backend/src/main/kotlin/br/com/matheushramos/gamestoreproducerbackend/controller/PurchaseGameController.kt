package br.com.matheushramos.gamestoreproducerbackend.controller

import br.com.matheushramos.gamestoreproducerbackend.dtos.PurchaseGameDTO
import br.com.matheushramos.gamestoreproducerbackend.producer.PurchaseGameEventProducer
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import javax.validation.Valid

@RestController
@RequestMapping("api/producer")
class PurchaseGameController(private val producer: PurchaseGameEventProducer) {

    @PostMapping("/purchase")
    @ResponseStatus(HttpStatus.CREATED)
    fun postPurchase(@RequestBody @Valid request: PurchaseGameDTO): ResponseEntity<PurchaseGameDTO> {
        producer.sendPurchaseEvent(request)
        return ResponseEntity.status(HttpStatus.CREATED).body(request)
    }

}