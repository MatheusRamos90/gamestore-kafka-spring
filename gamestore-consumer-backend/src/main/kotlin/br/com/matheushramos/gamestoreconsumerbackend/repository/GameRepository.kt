package br.com.matheushramos.gamestoreconsumerbackend.repository

import br.com.matheushramos.gamestoreconsumerbackend.model.Game
import org.springframework.data.mongodb.repository.MongoRepository
import org.springframework.stereotype.Repository

@Repository
interface GameRepository: MongoRepository<Game, String>