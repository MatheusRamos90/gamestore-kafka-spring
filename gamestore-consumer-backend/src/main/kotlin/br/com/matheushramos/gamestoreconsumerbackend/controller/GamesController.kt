package br.com.matheushramos.gamestoreconsumerbackend.controller

import br.com.matheushramos.gamestoreconsumerbackend.model.Game
import br.com.matheushramos.gamestoreconsumerbackend.service.GameService
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import javax.validation.Valid

@RestController
@RequestMapping("api/game")
class GamesController(private val gameService: GameService) {

    @PostMapping
    fun create(@RequestBody @Valid request: Game): ResponseEntity<Game> =
        ResponseEntity.status(HttpStatus.CREATED).body(gameService.create(request))

}