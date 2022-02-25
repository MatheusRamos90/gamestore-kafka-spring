package br.com.matheushramos.gamestoreproducerbackend.config

import org.apache.kafka.clients.admin.NewTopic
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.Profile
import org.springframework.kafka.config.TopicBuilder

//@Configuration
//@Profile("local")
class KafkaConfig {

    @Bean
    fun gameStoreTopic(): NewTopic =
        TopicBuilder.name("gamestore")
            .partitions(3)
            .replicas(3)
            .build();

}