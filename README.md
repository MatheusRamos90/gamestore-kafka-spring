## gamestore-kafka-spring
Projeto para estudos com Apache Kafka, utilizando dois projetos backend, um para produzir e outro para consumir mensagens.

#### Baseado numa loja de games que os clientes possam comprar seus jogos. 
Não será desenvolvido CRUD, nem nada do tipo.

O Apache Kafka é excelente para produzir e consumir grandes volumes de mensagens tudo através de stream de dados.
Ele foi criado pelo pessoal do LinkedIn e hoje é utilizado por grandes empresas do mundo.

Para mais informações do Kafka consulte a documentação:
https://kafka.apache.org/

Tecnologias empregadas:
#### Kotlin, Spring (Boot, Data, Kafka), JPA, Apache Kafka, MongoDB, Flyway, Docker

### Observação:
Para melhorar a visualização de mensagens foi colocado em um container Docker o gerenciador do Confluent, porém é bom salientar que o mesmo é disponibilizado de forma TRIAL.
Para mais informações, consulte o site: https://developer.confluent.io/

### Docker (local)
Com o Docker já instalado...
Com o terminal aberto, acesse as pastas dos projetos backend e rode o comando "docker build -t <seu_usuario_dockerhub>/<nome_da_imagem_do_projeto_backend> ."  
Para subir os containers e as intâncias localmente dos brokers do Kafka, Zookeeper, Mongo e dos projetos backend, rode o comando "docker-compose up" na pasta raiz do projeto.