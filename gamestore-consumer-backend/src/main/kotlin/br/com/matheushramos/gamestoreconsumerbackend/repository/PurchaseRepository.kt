package br.com.matheushramos.gamestoreconsumerbackend.repository

import br.com.matheushramos.gamestoreconsumerbackend.model.Purchase
import org.springframework.data.mongodb.repository.MongoRepository
import org.springframework.stereotype.Repository

@Repository
interface PurchaseRepository: MongoRepository<Purchase, String>