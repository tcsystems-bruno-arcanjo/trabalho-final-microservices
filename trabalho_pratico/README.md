# Microservices & Serverless Architecture - Trabalho final

## Integrantes

- Bruno Manoel Quintas de Arcanjo
- Odimar da Gama Barbosa

## Pré-requisitos

- Conexão de internet
- Maven
- Docker

Para realizar os testes das APIs sugerimos utilizar o Postman

## Configuração

### Crie os artefatos e as imagens do projeto utilizando o Maven e Docker

`Dentro da pasta trabalho final crie as imagens dos bancos de dados master e slave:`
> docker build -t fit/postgres-replication:1.0.0-SNAPSHOT ./database

`Acesse a pasta 'database-updater' e crie a imagem do serviço de atualização do banco de dados:`
> cd database-updater
>
> mvn clean package -DskipTests docker:build

`Acesse a pasta 'conta-corrente' e crie a imagem do serviço da conta corrente:`
> cd ../conta-corrente
>
> mvn clean package -DskipTests docker:build

`Acesse a pasta 'cartao-credito' e crie a imagem do serviço do cartão de crédito:`
> cd ../cartao-credito
>
> mvn clean package -DskipTests docker:build

`Acesse a pasta 'investimento' e crie a imagem do serviço do investimento:`
> cd ../investimento
>
> mvn clean package -DskipTests docker:build

`Acesse a pasta 'gateway' e crie a imagem do serviço do gateway:`
> cd ../gateway
>
> mvn clean package -DskipTests docker:build

## Instalação

`Volte para a raiz dos projetos e inicie os containers utilizando o Docker Compose:`
'
> cd ..
>
> docker-compose up -d

## Testes
Utilizando o Postman importe as requisições para a API dentro da posta postman.

É necessário realizar primeiro a autenticação na API gateway para receber o token. Esse token deve ser configurado como 'Authentication' do tipo Bearer em todas as requisições.
