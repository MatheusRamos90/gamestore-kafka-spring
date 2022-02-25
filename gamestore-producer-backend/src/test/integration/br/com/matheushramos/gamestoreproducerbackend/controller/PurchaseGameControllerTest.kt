package br.com.matheushramos.gamestoreproducerbackend.controller

import br.com.matheushramos.gamestoreproducerbackend.dtos.PurchaseGameDTO
import org.apache.kafka.clients.consumer.Consumer
import org.apache.kafka.common.serialization.StringDeserializer
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.web.client.TestRestTemplate
import org.springframework.http.*
import org.springframework.kafka.core.DefaultKafkaConsumerFactory
import org.springframework.kafka.test.EmbeddedKafkaBroker
import org.springframework.kafka.test.context.EmbeddedKafka
import org.springframework.kafka.test.utils.KafkaTestUtils
import org.springframework.test.context.TestPropertySource

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@EmbeddedKafka(topics = ["gamestore"], partitions = 3)
@TestPropertySource(properties = ["spring.kafka.producer.bootstrap-servers=\${spring.embedded.kafka.brokers}",
    "spring.kafka.admin.properties.bootstrap.servers=\${spring.embedded.kafka.brokers}"])
internal class PurchaseGameControllerTest(
    val testRestTemplate: TestRestTemplate,
    val embeddedKafkaBroker: EmbeddedKafkaBroker) {

    private lateinit var consumer: Consumer<String, String>

//    @BeforeEach
    fun setup() {
        val configs = HashMap(KafkaTestUtils.consumerProps("groupGamestore", "true", embeddedKafkaBroker))
        consumer = DefaultKafkaConsumerFactory(configs, StringDeserializer(), StringDeserializer()).createConsumer()
        embeddedKafkaBroker.consumeFromAllEmbeddedTopics(consumer)
    }

//    @AfterEach
    fun tearDown() = consumer.close()

//    @Test
//    @Timeout(5)
    fun postPurchaseEvent() {
        val purchase: PurchaseGameDTO? = null
        purchase?.customerId = "737b2d5c-1bf7-499b-96f2-37687af35285"
        purchase?.games = mutableListOf("ecb86443-0d74-43f6-b8bf-5ed9d472683d", "3200f328-88e8-4a25-9970-a19792de2dab")

        val headers: HttpHeaders? = null
        headers?.contentType = MediaType.APPLICATION_JSON

        val request = HttpEntity(purchase, headers)

        val response =
            testRestTemplate.exchange("api/producer/purchase", HttpMethod.POST, request, PurchaseGameDTO::class.java)

        assertEquals(HttpStatus.CREATED, response.statusCode)

    }

}