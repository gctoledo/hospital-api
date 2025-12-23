# 👨‍⚕️ Clinic Service

Microsserviço responsável pelo gerenciamento de consultas médicas e cadastro de médicos.

## 🛠️ Tecnologias

- Java 21
- Spring Boot 3.5.8
- Spring Data JPA
- Spring Security + OAuth2
- OpenFeign (comunicação com Lab Service)
- RabbitMQ (mensageria)
- MySQL 8.0
- Flyway (migrações)
- Lombok

## 📋 Responsabilidades

- Cadastro e gerenciamento de médicos
- Reserva e gerenciamento de consultas
- Atendimento de consultas com diagnóstico
- Verificação de disponibilidade de médicos
- Solicitação de exames durante atendimento

## 🔌 Endpoints

### 👨‍⚕️ Médicos

| Método | Endpoint                  | Descrição                      | Autenticação        |
|--------|---------------------------|--------------------------------|---------------------|
| GET    | /doctors/{id}             | Buscar médico por ID           | Qualquer autenticado|
| GET    | /doctors/{id}/consultations| Buscar consultas do médico    | DOCTOR, ADMIN       |
| POST   | /doctors                  | Criar médico                   | ADMIN               |
| POST   | /doctors/available        | Buscar médicos disponíveis     | Qualquer autenticado|
| PUT    | /doctors/{id}             | Atualizar médico               | ADMIN               |

### 🩺 Consultas

| Método | Endpoint                        | Descrição                    | Autenticação          |
|--------|---------------------------------|------------------------------|-----------------------|
| GET    | /consultations                  | Listar consultas (com filtro)| Qualquer autenticado  |
| GET    | /consultations/patient/{cpf}    | Buscar consultas por CPF     | Qualquer autenticado  |
| POST   | /consultations/reserve          | Reservar consulta            | Qualquer autenticado  |
| POST   | /consultations/{id}/attend      | Atender consulta             | PATIENT, DOCTOR, ADMIN|
| PUT    | /consultations/{id}/update/date | Atualizar data da consulta   | PATIENT, DOCTOR, ADMIN|

## ⚙️ Variáveis de Ambiente

| Variável                  | Descrição                           | Padrão                                                     |
|---------------------------|-------------------------------------|------------------------------------------------------------|
| SERVER_PORT               | Porta do serviço                    | 8081                                                       |
| DATASOURCE_URL            | URL do banco MySQL                  | jdbc:mysql://localhost:3308/clinic_db?createDatabaseIfNotExist=true |
| DATASOURCE_USERNAME       | Usuário do banco                    | root                                                       |
| DATASOURCE_PASSWORD       | Senha do banco                      | root                                                       |
| KEYCLOAK_ISSUER_URI       | URI do emissor Keycloak             | http://localhost:8180/realms/hospital                      |
| KEYCLOAK_JWK_SET_URI      | URI do JWK Set                      | http://localhost:8180/realms/hospital/protocol/openid-connect/certs |
| KEYCLOAK_TOKEN_URI        | URI de token do Keycloak            | http://localhost:8180/realms/hospital/protocol/openid-connect/token |
| CLINIC_CLIENT_SECRET      | Client secret para Keycloak         | 78CTyi3vnLr1A5d9DNUht5JUANC9zC39                           |
| LAB_URL                   | URL do Lab Service                  | http://localhost:8082                                      |
| RABBITMQ_HOST             | Host do RabbitMQ                    | localhost                                                  |
| RABBITMQ_PORT             | Porta do RabbitMQ                   | 5672                                                       |
| RABBITMQ_USERNAME         | Usuário RabbitMQ                    | guest                                                      |
| RABBITMQ_PASSWORD         | Senha RabbitMQ                      | guest                                                      |

## 🚀 Como Executar

### 🐳 Com Docker Compose (Recomendado)
```bash
# Na raiz do projeto
docker-compose up -d clinic
```

### 💻 Localmente
```bash
# 1. Garantir que dependências estão rodando
docker-compose up -d clinic-db rabbitmq keycloak

# 2. Configurar variáveis de ambiente (ver seção acima)

# 3. Executar serviço
./mvnw spring-boot:run
```

## 🔗 Integrações

### 🔬 Lab Service (via OpenFeign)
Durante o atendimento de uma consulta, o médico pode solicitar exames. O Clinic Service se comunica com o Lab Service para reservar os exames necessários.

### 📨 RabbitMQ
- Publicação de eventos de consulta realizada
- Consumo de atualizações de status
- Notificações de agendamento

## 💾 Banco de Dados

O serviço utiliza MySQL com migrações gerenciadas pelo Flyway.

**Porta:** 3308 (quando rodando via Docker)
**Database:** clinic_db

### 🗄️ Migrations
As migrations Flyway estão em: `src/main/resources/db/migration/`

### 🌱 Seeds (Dados Iniciais)
O banco é inicializado automaticamente com dados de exemplo:

**Médicos:**
- Michel Pinto (CRM/SP-123456) - Clínico Geral
- Henrique Paoletti (CRM/RJ-789012) - Cardiologia
- Clecio Rocha (CRM/MG-345678) - Pneumologia

**Doenças:** 14 doenças cadastradas com diferentes níveis de severidade (Emergência, Alta, Padrão, Baixa)

**Sintomas:** 22 sintomas catalogados incluindo febre, tosse, dor no peito, falta de ar, entre outros

**Relacionamentos:** Mapeamento completo entre doenças e sintomas com níveis de especificidade para auxiliar no diagnóstico

## 🔄 Fluxo de Atendimento

1. Consulta é agendada via Schedule Service
2. Médico acessa a consulta
3. Durante atendimento, médico registra:
   - Sintomas
   - Diagnóstico
   - Prescrição
   - Exames necessários (opcional)
4. Se exames forem solicitados, sistema reserva no Lab Service
5. Consulta é marcada como atendida
6. Evento é publicado no RabbitMQ

## 🏥 Especialidades Disponíveis

- CARDIOLOGY (Cardiologia)
- DERMATOLOGY (Dermatologia)
- PEDIATRICS (Pediatria)
- ORTHOPEDICS (Ortopedia)
- NEUROLOGY (Neurologia)
- E outras conforme configuração

## 📝 Exemplo de Uso

### 👨‍⚕️ Criar Médico
```bash
curl -X POST http://localhost:8081/doctors \
  -H "Authorization: Bearer <token-admin>" \
  -H "Content-Type: application/json" \
  -d '{
    "name": "Dr. Maria Santos",
    "crm": "123456",
    "specialty": "CARDIOLOGY",
    "email": "maria@example.com",
    "phone": "11988888888"
  }'
```

### 🔍 Buscar Médicos Disponíveis
```bash
curl -X POST http://localhost:8081/doctors/available \
  -H "Authorization: Bearer <token>" \
  -H "Content-Type: application/json" \
  -d '{
    "specialty": "CARDIOLOGY",
    "dateTime": "2025-12-25T10:00:00"
  }'
```

### 📅 Reservar Consulta
```bash
curl -X POST http://localhost:8081/consultations/reserve \
  -H "Authorization: Bearer <token>" \
  -H "Content-Type: application/json" \
  -d '{
    "patientCpf": "12345678900",
    "doctorId": 1,
    "dateTime": "2025-12-25T10:00:00",
    "specialty": "CARDIOLOGY"
  }'
```

### 🩺 Atender Consulta
```bash
curl -X POST http://localhost:8081/consultations/1/attend \
  -H "Authorization: Bearer <token>" \
  -H "Content-Type: application/json" \
  -d '{
    "symptoms": "Dor no peito",
    "diagnosis": "Arritmia cardíaca",
    "prescription": "Medicamento X, 2x ao dia",
    "requestedExams": ["Eletrocardiograma", "Holter 24h"]
  }'
```

### 📋 Listar Consultas por Especialidade
```bash
curl -X GET "http://localhost:8081/consultations?specialty=CARDIOLOGY" \
  -H "Authorization: Bearer <token>"
```

## 🔨 Build

```bash
./mvnw clean package
```

## 📋 Logs

### 🐳 Docker
```bash
docker-compose logs -f clinic
```