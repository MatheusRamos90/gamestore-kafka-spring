package br.com.matheushramos.gamestoreconsumerbackend.service

import br.com.matheushramos.gamestoreconsumerbackend.model.Game
import br.com.matheushramos.gamestoreconsumerbackend.repository.GameRepository
import org.springframework.stereotype.Service

@Service
class GameService(private val gameRepository: GameRepository) {

    fun create(request: Game): Game = gameRepository.save(request)

}