package br.com.matheushramos.gamestoreconsumerbackend.service

import br.com.matheushramos.gamestoreconsumerbackend.model.Customer
import br.com.matheushramos.gamestoreconsumerbackend.repository.CustomerRepository
import org.springframework.stereotype.Service

@Service
class CustomerService(private val customerRepository: CustomerRepository) {

    fun create(request: Customer): Customer = customerRepository.save(request)

}