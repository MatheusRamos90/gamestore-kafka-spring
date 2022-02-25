package br.com.matheushramos.gamestoreconsumerbackend.repository

import br.com.matheushramos.gamestoreconsumerbackend.model.Customer
import org.springframework.data.mongodb.repository.MongoRepository
import org.springframework.stereotype.Repository

@Repository
interface CustomerRepository: MongoRepository<Customer, String> {

    fun existsByIdAndEmail(id: Int, email: String): Boolean

}