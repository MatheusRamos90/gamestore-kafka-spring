package br.com.matheushramos.gamestoreconsumerbackend.config

import br.com.matheushramos.gamestoreconsumerbackend.service.PurchaseService
import org.apache.kafka.clients.consumer.ConsumerRecord
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.ObjectProvider
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean
import org.springframework.boot.autoconfigure.kafka.ConcurrentKafkaListenerContainerFactoryConfigurer
import org.springframework.boot.autoconfigure.kafka.KafkaProperties
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.dao.RecoverableDataAccessException
import org.springframework.kafka.annotation.EnableKafka
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory
import org.springframework.kafka.core.ConsumerFactory
import org.springframework.kafka.core.DefaultKafkaConsumerFactory

@Configuration
@EnableKafka
class GamestoreConsumerConfig(
    private val kafkaProperties: KafkaProperties,
    private val purchaseService: PurchaseService
    ) {

    private val log = LoggerFactory.getLogger(this.javaClass)

    @Bean
    @ConditionalOnMissingBean(name = ["kafkaListenerContainerFactory"])
    fun kafkaListenerContainerFactory(configurer: ConcurrentKafkaListenerContainerFactoryConfigurer,
                                      kafkaConsumerFactory: ObjectProvider<ConsumerFactory<Any, Any>>): ConcurrentKafkaListenerContainerFactory<Any, Any> {
        val factory = ConcurrentKafkaListenerContainerFactory<Any, Any>()

        configurer.configure(factory, kafkaConsumerFactory.getIfAvailable {
            DefaultKafkaConsumerFactory(kafkaProperties.buildConsumerProperties())
        })

        factory.setConcurrency(3)
        factory.setErrorHandler { thrownException, data ->
            log.info("Exception in consumerConfig is ${thrownException.message} and the record is $data") }

        factory.setRecoveryCallback { context ->
            if (context.lastThrowable.cause is RecoverableDataAccessException) {
                log.info("Inside the recoverable logic")
                context.attributeNames().forEach {
                    log.info("Attribute name is: $it")
                    log.info("Attribute value is: ${context.getAttribute(it)}")
                }

                val consumerRecord = context.getAttribute("record") as ConsumerRecord<String, String>
                purchaseService.handleRecovery(consumerRecord)
            } else {
                log.info("Inside the non recoverable logic")
                throw RuntimeException(context.lastThrowable.message)
            }
        }

        return factory
    }

}