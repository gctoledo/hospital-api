# Schedule Service

Microsserviço responsável pelo gerenciamento de agendamentos de consultas e exames, além do cadastro de pacientes.

## Tecnologias

- Java 21
- Spring Boot 3.5.8
- Spring Data JPA
- Spring Security + OAuth2
- OpenFeign (comunicação com outros serviços)
- RabbitMQ (mensageria)
- MySQL 8.0
- Flyway (migrações)
- Lombok

## Responsabilidades

- Cadastro e gerenciamento de pacientes
- Agendamento de consultas (integração com Clinic Service)
- Agendamento de exames (integração com Lab Service)
- Atualização de datas de agendamentos
- Consulta de agendamentos por CPF

## Endpoints

### Pacientes

| Método | Endpoint          | Descrição                | Autenticação        |
|--------|-------------------|--------------------------|---------------------|
| GET    | /patients         | Listar todos pacientes   | Qualquer autenticado|
| GET    | /patients/{id}    | Buscar paciente por ID   | Qualquer autenticado|
| POST   | /patients         | Criar paciente           | ADMIN               |
| PUT    | /patients/{id}    | Atualizar paciente       | ADMIN               |

### Agendamentos

| Método | Endpoint                           | Descrição                      | Autenticação          |
|--------|------------------------------------|--------------------------------|-----------------------|
| GET    | /consultations/{cpf}               | Buscar consultas por CPF       | PATIENT, DOCTOR, ADMIN|
| GET    | /exams/{cpf}                       | Buscar exames por CPF          | PATIENT, DOCTOR, ADMIN|
| POST   | /consultations                     | Agendar consulta               | PATIENT, DOCTOR, ADMIN|
| POST   | /exams                             | Agendar exame                  | PATIENT, DOCTOR, ADMIN|
| PUT    | /consultations/{id}/update/date    | Atualizar data da consulta     | PATIENT, DOCTOR, ADMIN|
| PUT    | /exams/{id}/update/date            | Atualizar data do exame        | PATIENT, DOCTOR, ADMIN|

## Variáveis de Ambiente

| Variável                  | Descrição                           | Padrão                                                     |
|---------------------------|-------------------------------------|------------------------------------------------------------|
| SERVER_PORT               | Porta do serviço                    | 8083                                                       |
| DATASOURCE_URL            | URL do banco MySQL                  | jdbc:mysql://localhost:3307/schedule_db?createDatabaseIfNotExist=true |
| DATASOURCE_USERNAME       | Usuário do banco                    | root                                                       |
| DATASOURCE_PASSWORD       | Senha do banco                      | root                                                       |
| KEYCLOAK_ISSUER_URI       | URI do emissor Keycloak             | http://localhost:8180/realms/hospital                      |
| KEYCLOAK_JWK_SET_URI      | URI do JWK Set                      | http://localhost:8180/realms/hospital/protocol/openid-connect/certs |
| KEYCLOAK_TOKEN_URI        | URI de token do Keycloak            | http://localhost:8180/realms/hospital/protocol/openid-connect/token |
| SCHEDULE_CLIENT_SECRET    | Client secret para Keycloak         | XlRQC5MaUMHXpFgAz0Mc6wA0IX9x5Hzr                           |
| CLINIC_URL                | URL do Clinic Service               | http://localhost:8081                                      |
| LAB_URL                   | URL do Lab Service                  | http://localhost:8082                                      |
| RABBITMQ_HOST             | Host do RabbitMQ                    | localhost                                                  |
| RABBITMQ_PORT             | Porta do RabbitMQ                   | 5672                                                       |
| RABBITMQ_USERNAME         | Usuário RabbitMQ                    | guest                                                      |
| RABBITMQ_PASSWORD         | Senha RabbitMQ                      | guest                                                      |

## Como Executar

### Com Docker Compose (Recomendado)
```bash
# Na raiz do projeto
docker-compose up -d schedule
```

### Localmente
```bash
# 1. Garantir que dependências estão rodando
docker-compose up -d schedule-db rabbitmq keycloak

# 2. Configurar variáveis de ambiente (ver seção acima)

# 3. Executar serviço
./mvnw spring-boot:run
```

## Integrações

### Clinic Service (via OpenFeign)
- Reserva de consultas
- Atualização de datas de consultas

### Lab Service (via OpenFeign)
- Reserva de exames
- Atualização de datas de exames

### RabbitMQ
- Publicação de eventos de agendamento
- Consumo de atualizações de status

## Banco de Dados

O serviço utiliza MySQL com migrações gerenciadas pelo Flyway.

**Porta:** 3307 (quando rodando via Docker)
**Database:** schedule_db

### Migrations
As migrations Flyway estão em: `src/main/resources/db/migration/`

### Seeds (Dados Iniciais)
O banco é inicializado automaticamente com 5 pacientes de exemplo:
- Gabriel Costa Toledo (CPF: 00875560067)
- João Pedro Oliveira (CPF: 23456789012)
- Ana Carolina Souza (CPF: 34567890123)
- Carlos Eduardo Lima (CPF: 45678901234)
- Patricia Fernandes Costa (CPF: 56789012345)

## Exemplo de Uso

### Criar Paciente
```bash
curl -X POST http://localhost:8083/patients \
  -H "Authorization: Bearer <token-admin>" \
  -H "Content-Type: application/json" \
  -d '{
    "name": "João Silva",
    "cpf": "12345678900",
    "email": "joao@example.com",
    "phone": "11999999999"
  }'
```

### Agendar Consulta
```bash
curl -X POST http://localhost:8083/consultations \
  -H "Authorization: Bearer <token>" \
  -H "Content-Type: application/json" \
  -d '{
    "patientId": 1,
    "doctorId": 1,
    "dateTime": "2025-12-25T10:00:00",
    "specialty": "CARDIOLOGY"
  }'
```

### Buscar Consultas por CPF
```bash
curl -X GET http://localhost:8083/consultations/12345678900 \
  -H "Authorization: Bearer <token>"
```

## Build

```bash
./mvnw clean package
```

## Testes

```bash
./mvnw test
```

## Logs

### Docker
```bash
docker-compose logs -f schedule
```