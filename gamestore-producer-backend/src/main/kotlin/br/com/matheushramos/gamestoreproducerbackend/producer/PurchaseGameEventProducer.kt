package br.com.matheushramos.gamestoreproducerbackend.producer

import br.com.matheushramos.gamestoreproducerbackend.dtos.PurchaseGameDTO
import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import lombok.extern.slf4j.Slf4j
import org.apache.kafka.clients.producer.ProducerRecord
import org.apache.kafka.common.header.internals.RecordHeader
import org.slf4j.LoggerFactory
import org.springframework.kafka.core.KafkaTemplate
import org.springframework.kafka.support.SendResult
import org.springframework.stereotype.Component
import java.util.*

@Component
@Slf4j
class PurchaseGameEventProducer(private val kafkaTemplate: KafkaTemplate<String, String>) {

    private val log = LoggerFactory.getLogger(this.javaClass)

    private val topic = "gamestore"

    fun sendPurchaseEvent(request: PurchaseGameDTO) {
        val purchaseIdKey = UUID.randomUUID().toString()
        val purchaseValue = jacksonObjectMapper().writeValueAsString(request)

        val listenableFuture = kafkaTemplate.send(buildProducerRecord(purchaseIdKey, purchaseValue))
        listenableFuture.addCallback({
            handleSuccess(purchaseIdKey, purchaseValue, it!!)
        }, {
            handleFailure(it)
        })
    }

    private fun buildProducerRecord(purchaseIdKey: String, purchaseValue: String): ProducerRecord<String, String> {
        // with header params
        val headerParams = listOf(RecordHeader("event-source", "scanner".toByteArray()))
        return ProducerRecord(topic, null, purchaseIdKey, purchaseValue, headerParams)
    }

    private fun handleSuccess(purchaseIdKey: String, purchaseValue: String, resultEvent: SendResult<String, String>) {
        log.info("Success! Message sended in Purchase Game => Key: $purchaseIdKey, Value: $purchaseValue, Partition: ${resultEvent.recordMetadata.partition()}, Offset: ${resultEvent.recordMetadata.offset()}, Header params: ${resultEvent.producerRecord.headers()}")
    }

    private fun handleFailure(t: Throwable) {
        log.error("There was exception on sending message => ${t.message}")
        try {
            throw t
        } catch (thrown: Throwable) {
            log.error("Throwable in sendPurchaseEvent => ${t.message}")
        }
    }


}