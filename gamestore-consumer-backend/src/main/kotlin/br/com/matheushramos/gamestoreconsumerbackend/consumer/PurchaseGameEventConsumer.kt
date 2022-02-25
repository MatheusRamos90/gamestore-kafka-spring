package br.com.matheushramos.gamestoreconsumerbackend.consumer

import br.com.matheushramos.gamestoreconsumerbackend.service.PurchaseService
import org.apache.kafka.clients.consumer.ConsumerRecord
import org.slf4j.LoggerFactory
import org.springframework.kafka.annotation.KafkaListener
import org.springframework.stereotype.Component

@Component
class PurchaseGameEventConsumer(private val purchaseService: PurchaseService) {

    private val log = LoggerFactory.getLogger(this.javaClass)

    @KafkaListener(topics = ["gamestore"], groupId = "gamestore-listener-group")
    fun onMessage(consumerRecord: ConsumerRecord<String, String>) {
        log.info("ConsumerRecord received is: $consumerRecord")
        purchaseService.savePurchaseEvent(consumerRecord)
    }
}