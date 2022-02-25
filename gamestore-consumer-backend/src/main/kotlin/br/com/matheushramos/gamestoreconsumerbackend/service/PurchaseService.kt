package br.com.matheushramos.gamestoreconsumerbackend.service

import br.com.matheushramos.gamestoreconsumerbackend.consumer.dtos.PurchaseGameDTO
import br.com.matheushramos.gamestoreconsumerbackend.exception.NotFoundException
import br.com.matheushramos.gamestoreconsumerbackend.model.Game
import br.com.matheushramos.gamestoreconsumerbackend.model.Purchase
import br.com.matheushramos.gamestoreconsumerbackend.repository.CustomerRepository
import br.com.matheushramos.gamestoreconsumerbackend.repository.GameRepository
import br.com.matheushramos.gamestoreconsumerbackend.repository.PurchaseRepository
import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import org.apache.kafka.clients.consumer.ConsumerRecord
import org.slf4j.LoggerFactory
import org.springframework.http.HttpStatus
import org.springframework.kafka.core.KafkaTemplate
import org.springframework.kafka.support.SendResult
import org.springframework.stereotype.Service

@Service
class PurchaseService(
    private val kafkaTemplate: KafkaTemplate<String, String>,
    private val purchaseRepository: PurchaseRepository,
    private val customerRepository: CustomerRepository,
    private val gameRepository: GameRepository
    ) {

    private val log = LoggerFactory.getLogger(this.javaClass)

    fun savePurchaseEvent(consumerRecord: ConsumerRecord<String, String>) {
        val purchaseGameEvent = jacksonObjectMapper().readValue(consumerRecord.value(), PurchaseGameDTO::class.java)
        log.info("PurchaseGameEvent value is : {} ", purchaseGameEvent)

        val gamesToSave: MutableList<Game> = arrayListOf()

        purchaseGameEvent.games.forEach {
            val game = gameRepository.findById(it).orElseThrow {
                val messageErrorNotFound = "Not found game with id $it"
                log.error(messageErrorNotFound)
                throw NotFoundException(messageErrorNotFound, HttpStatus.NOT_FOUND.value())
            }

            gamesToSave.add(game)
        }
        purchaseGameEvent.customerId.let {
            val customer = customerRepository.findById(it).orElseThrow {
                val messageErrorPurchaseNotFound = "Not found customer with id $it"
                log.error(messageErrorPurchaseNotFound)
                throw NotFoundException(messageErrorPurchaseNotFound, HttpStatus.NOT_FOUND.value())
            }

            val purchase = purchaseRepository.save(Purchase(null, customer, gamesToSave))
            log.info("Successfully!! Purchase was saved. {} ", purchase)
        }
    }

    fun handleRecovery(consumerRecord: ConsumerRecord<String, String>) {
        val key = consumerRecord.key()
        val message = consumerRecord.value()

        val listenableFuture = kafkaTemplate.sendDefault(key, message)
        listenableFuture.addCallback({
            handleSuccess(key, message, it!!)
        }, {
            handleFailure(it)
        })
    }

    private fun handleSuccess(purchaseIdKey: String, purchaseValue: String, resultEvent: SendResult<String, String>) {
        log.info("Success! Message sended in Purchase Game => Key: $purchaseIdKey, Value: $purchaseValue, Partition: ${resultEvent.recordMetadata.partition()}, Offset: ${resultEvent.recordMetadata.offset()}, Header params: ${resultEvent.producerRecord.headers()}")
    }

    private fun handleFailure(t: Throwable) {
        log.error("There was exception on sending message => ${t.message}")
        try {
            throw t
        } catch (thrown: Throwable) {
            log.error("Throwable in handleRecovery => ${t.message}")
        }
    }

}