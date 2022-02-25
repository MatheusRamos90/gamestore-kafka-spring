package br.com.matheushramos.gamestoreconsumerbackend.controller

import br.com.matheushramos.gamestoreconsumerbackend.model.Customer
import br.com.matheushramos.gamestoreconsumerbackend.service.CustomerService
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import javax.validation.Valid

@RestController
@RequestMapping("api/customer")
class CustomerController(private val customerService: CustomerService) {

    @PostMapping
    fun create(@RequestBody @Valid request: Customer): ResponseEntity<Customer> =
        ResponseEntity.status(HttpStatus.CREATED).body(customerService.create(request))

}