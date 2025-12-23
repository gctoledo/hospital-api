# 🏥 Hospital API

Sistema de gestão hospitalar baseado em arquitetura de microsserviços, desenvolvido com Spring Boot e Spring Cloud.

---

## 📖 Documentação dos Microsserviços

Cada microsserviço possui sua própria documentação detalhada:

- **[🌐 Gateway Service](gateway/README.md)** - API Gateway e roteamento
- **[📅 Schedule Service](schedule/README.md)** - Agendamentos e pacientes
- **[👨‍⚕️ Clinic Service](clinic/README.md)** - Consultas e médicos
- **[🔬 Lab Service](procedures/README.md)** - Exames laboratoriais

---

## 🛠️ Tecnologias e Versões

### 💻 Backend
- **Java**: 21
- **Spring Boot**: 3.5.8
- **Spring Cloud**: 2025.0.0
- **Maven**: 3.x

### 📦 Principais Dependências
- **Spring Cloud Gateway**: API Gateway e roteamento
- **Spring Security + OAuth2**: Autenticação e autorização
- **Spring Data JPA**: Persistência de dados
- **Spring AMQP**: Integração com RabbitMQ
- **OpenFeign**: Comunicação entre microsserviços
- **Flyway**: Migração de banco de dados
- **Lombok**: Redução de boilerplate

### 🔧 Infraestrutura
- **MySQL**: 8.0
- **RabbitMQ**: 4.2.0-management
- **Keycloak**: 23.0.7
- **Docker & Docker Compose**: Containerização

## 🏗️ Arquitetura

O projeto é composto por 4 microsserviços principais:

### 1️⃣ 🌐 Gateway Service (Porta 8080)
API Gateway responsável por rotear requisições para os microsserviços.

**Rotas:**
- `/api/schedule/**` → Schedule Service
- `/api/clinic/**` → Clinic Service
- `/api/lab/**` → Lab Service

### 2️⃣ 📅 Schedule Service (Porta 8083)
Gerencia agendamentos de consultas e exames, além do cadastro de pacientes.

**Principais Endpoints:**
- `GET /patients` - Listar pacientes
- `POST /patients` - Criar paciente (ADMIN)
- `GET /consultations/{cpf}` - Buscar consultas por CPF
- `POST /consultations` - Agendar consulta
- `GET /exams/{cpf}` - Buscar exames por CPF
- `POST /exams` - Agendar exame
- `PUT /consultations/{id}/update/date` - Atualizar data da consulta
- `PUT /exams/{id}/update/date` - Atualizar data do exame

### 3️⃣ 👨‍⚕️ Clinic Service (Porta 8081)
Gerencia consultas médicas e médicos.

**Principais Endpoints:**
- `GET /consultations` - Listar consultas (filtro por especialidade opcional)
- `GET /consultations/patient/{cpf}` - Buscar consultas por CPF
- `POST /consultations/reserve` - Reservar consulta
- `POST /consultations/{id}/attend` - Atender consulta
- `GET /doctors/{id}` - Buscar médico
- `POST /doctors` - Criar médico (ADMIN)
- `POST /doctors/available` - Buscar médicos disponíveis

### 4️⃣ 🔬 Lab Service (Porta 8082)
Gerencia exames laboratoriais.

**Principais Endpoints:**
- `GET /exams/patient/{cpf}` - Buscar exames por CPF
- `POST /exams/reserve` - Reservar exame (DOCTOR, ADMIN)
- `POST /exams` - Criar exame
- `PATCH /exams/{id}/confirm/date` - Confirmar data do exame
- `PUT /exams/{id}/update/date` - Atualizar data do exame

## 🔄 Fluxo Principal

### 1️⃣ 📅 Agendamento de Consulta
```
Cliente → Gateway → Schedule Service → Clinic Service
                          ↓
                      RabbitMQ (mensageria assíncrona)
```

1. Paciente solicita agendamento via Schedule Service
2. Schedule Service reserva a consulta no Clinic Service (via OpenFeign)
3. Consulta é criada e confirmação é enviada
4. Notificação é enviada via RabbitMQ

### 2️⃣ 🩺 Atendimento e Solicitação de Exame
```
Cliente → Gateway → Clinic Service → Lab Service
                          ↓
                      RabbitMQ
```

1. Médico atende consulta via Clinic Service
2. Durante o atendimento, pode solicitar exames
3. Reserva de exame é criada no Lab Service
4. Paciente confirma data do exame via Schedule Service

### 3️⃣ 🔐 Autenticação
```
Cliente → Gateway → Keycloak (validação JWT)
            ↓
      Microsserviço (autorizado)
```

Todas as requisições passam pelo Gateway que valida o token JWT com o Keycloak.

## 🚀 Como Executar

### ✅ Pré-requisitos
- Java 21
- Maven 3.x
- Docker e Docker Compose (para execução com containers)

### 🐳 Executando com Docker (Recomendado)

1. Clone o repositório:
```bash
git clone https://git.gft.com/gltd/hospital-api
cd hospital-api
```

2. Inicie todos os serviços:
```bash
docker-compose up -d --build
```

> **ATENÇÃO:** Após o container do Keycloak ser inicializado, ele demora alguns minutos para importar toda a configuração. Confirme acessando http://localhost:8180 se o serviço já está funcionando.

3. Aguarde todos os containers iniciarem (pode levar alguns minutos). Verifique o status:
```bash
docker-compose ps
```

4. Acesse os serviços:
- **Gateway**: http://localhost:8080
- **Schedule Service**: http://localhost:8083
- **Clinic Service**: http://localhost:8081
- **Lab Service**: http://localhost:8082
- **Keycloak Admin**: http://localhost:8180 (admin/admin)
- **RabbitMQ Management**: http://localhost:15672 (guest/guest)

5. Para parar os serviços:
```bash
docker-compose down
```

6. Para parar e remover volumes (limpar banco de dados):
```bash
docker-compose down -v
```

### 💻 Executando Localmente (sem Docker)

#### 1️⃣ Iniciar Infraestrutura
Inicie apenas os bancos de dados, RabbitMQ e Keycloak:

```bash
docker-compose up -d rabbitmq schedule-db clinic-db lab-db keycloak-db keycloak
```

#### 2️⃣ Configurar variáveis de ambiente
Cada microsserviço precisa das seguintes variáveis (ajuste conforme necessário):

**Schedule Service:**
```bash
export SERVER_PORT=8083
export DATASOURCE_URL=jdbc:mysql://localhost:3307/schedule_db?createDatabaseIfNotExist=true
export DATASOURCE_USERNAME=root
export DATASOURCE_PASSWORD=root
export KEYCLOAK_ISSUER_URI=http://localhost:8180/realms/hospital
export KEYCLOAK_JWK_SET_URI=http://localhost:8180/realms/hospital/protocol/openid-connect/certs
export KEYCLOAK_TOKEN_URI=http://localhost:8180/realms/hospital/protocol/openid-connect/token
export SCHEDULE_CLIENT_SECRET=XlRQC5MaUMHXpFgAz0Mc6wA0IX9x5Hzr
export CLINIC_URL=http://localhost:8081
export LAB_URL=http://localhost:8082
export RABBITMQ_HOST=localhost
export RABBITMQ_PORT=5672
export RABBITMQ_USERNAME=guest
export RABBITMQ_PASSWORD=guest
```

**Clinic Service:**
```bash
export SERVER_PORT=8081
export DATASOURCE_URL=jdbc:mysql://localhost:3308/clinic_db?createDatabaseIfNotExist=true
export DATASOURCE_USERNAME=root
export DATASOURCE_PASSWORD=root
export KEYCLOAK_ISSUER_URI=http://localhost:8180/realms/hospital
export KEYCLOAK_JWK_SET_URI=http://localhost:8180/realms/hospital/protocol/openid-connect/certs
export KEYCLOAK_TOKEN_URI=http://localhost:8180/realms/hospital/protocol/openid-connect/token
export CLINIC_CLIENT_SECRET=78CTyi3vnLr1A5d9DNUht5JUANC9zC39
export LAB_URL=http://localhost:8082
export RABBITMQ_HOST=localhost
export RABBITMQ_PORT=5672
export RABBITMQ_USERNAME=guest
export RABBITMQ_PASSWORD=guest
```

**Lab Service:**
```bash
export SERVER_PORT=8082
export DATASOURCE_URL=jdbc:mysql://localhost:3309/lab_db?createDatabaseIfNotExist=true
export DATASOURCE_USERNAME=root
export DATASOURCE_PASSWORD=root
export KEYCLOAK_ISSUER_URI=http://localhost:8180/realms/hospital
export KEYCLOAK_JWK_SET_URI=http://localhost:8180/realms/hospital/protocol/openid-connect/certs
export KEYCLOAK_TOKEN_URI=http://localhost:8180/realms/hospital/protocol/openid-connect/token
export LAB_CLIENT_SECRET=IDlJM9G8b8pz5WTkVUylXhNPIqp2w80f
export RABBITMQ_HOST=localhost
export RABBITMQ_PORT=5672
export RABBITMQ_USERNAME=guest
export RABBITMQ_PASSWORD=guest
```

**Gateway Service:**
```bash
export SERVER_PORT=8080
export SCHEDULE_SERVICE_URL=http://localhost:8083
export CLINIC_SERVICE_URL=http://localhost:8081
export LAB_SERVICE_URL=http://localhost:8082
export KEYCLOAK_ISSUER_URI=http://localhost:8180/realms/hospital
export KEYCLOAK_JWK_SET_URI=http://localhost:8180/realms/hospital/protocol/openid-connect/certs
```

#### 3️⃣ Compilar e executar cada serviço

Em terminais separados, execute:

```bash
# Schedule Service
cd schedule
./mvnw spring-boot:run

# Clinic Service
cd clinic
./mvnw spring-boot:run

# Lab Service
cd procedures
./mvnw spring-boot:run

# Gateway Service
cd gateway
./mvnw spring-boot:run
```

## 🔐 Autenticação

O sistema utiliza Keycloak para autenticação OAuth2/OIDC.

### 🎫 Obter Token de Acesso

```bash
curl -X POST http://localhost:8180/realms/hospital/protocol/openid-connect/token \
  -H "Content-Type: application/x-www-form-urlencoded" \
  -d "client_id=schedule-client" \
  -d "client_secret=XlRQC5MaUMHXpFgAz0Mc6wA0IX9x5Hzr" \
  -d "grant_type=client_credentials"
```

### 🔑 Usar Token nas Requisições

```bash
curl -H "Authorization: Bearer <seu-token>" \
  http://localhost:8080/api/schedule/patients
```

## 📚 Documentação da API

### 📮 Postman Collection

Uma collection completa do Postman está disponível na raiz do projeto no arquivo `hospital-api.postman_collection.json`.

A collection inclui:
- Todas as rotas de todos os microsserviços
- Exemplos de requisições configuradas
- Variáveis pré-configuradas
- Exemplos de autenticação com Keycloak
- Requests organizados por serviço (Gateway, Schedule, Clinic, Lab)

> **ATENÇÃO:** As variaveis foram configuradas DENTRO da Collection, e não nas variaveis de ambiente do Workspace.

#### Como usar:

1. Abra o Postman
2. Clique em "Import"
3. Selecione o arquivo `Hospital-API.postman_collection.json` da raiz do projeto
4. Verifique se as variáveis de ambiente estão configuradas:
   - `GATEWAY_BASEURL`: http://localhost:8080/api (Gateway)
   - `KEYCLOAK_URL`: http://localhost:8180
   - `access_token`: (será preenchido após autenticação)
5. Execute o request de autenticação para obter o token
6. Use as demais requisições já configuradas

> **ATENÇÃO:** Após realizar a autenticação na rota de Login, será executado o script para preencher a variavel "access_token" automaticamente com o token gerado. Caso não preencha, basta adicionar o token manualmente na variável de ambiente para ser usada pelas outras rotas automaticamente.


## 👥 Roles e Permissões

- **ADMIN**: Acesso total ao sistema
- **DOCTOR**: Gerenciar consultas e solicitar exames
- **PATIENT**: Visualizar e agendar consultas/exames

## 💾 Bancos de Dados

Cada microsserviço possui seu próprio banco de dados MySQL:

- **schedule-db** (porta 3307): Dados do Schedule Service
- **clinic-db** (porta 3308): Dados do Clinic Service
- **lab-db** (porta 3309): Dados do Lab Service
- **keycloak-db** (porta 3310): Dados do Keycloak

## 📨 Mensageria

RabbitMQ é utilizado para comunicação assíncrona entre serviços:

- Notificações de agendamento
- Atualizações de status
- Eventos de domínio

Acesse o RabbitMQ Management em http://localhost:15672 (guest/guest)

## 📁 Estrutura do Projeto

```
hospital-api/
├── gateway/                              # API Gateway
├── schedule/                             # Serviço de Agendamento
├── clinic/                               # Serviço de Consultas
├── procedures/                           # Serviço de Laboratório (Lab)
├── keycloak/                             # Configurações do Keycloak
│   └── realm.json                        # Configuração do realm
├── docker-compose.yml                    # Orquestração de containers
├── postman/                              # Configurações do Postman
│   └── hospital-api.postman_colle...     # Collection
└── README.md                             # Este arquivo
```

## 💻 Desenvolvimento

### 🔨 Build de todos os serviços

```bash
# Schedule
cd schedule && ./mvnw clean package && cd ..

# Clinic
cd clinic && ./mvnw clean package && cd ..

# Lab
cd procedures && ./mvnw clean package && cd ..

# Gateway
cd gateway && ./mvnw clean package && cd ..
```

### 🐳 Rebuild com Docker

```bash
docker-compose up -d --build
```

## 📋 Logs

Para visualizar logs de um serviço específico:

```bash
docker-compose logs -f <service-name>

# Exemplos:
docker-compose logs -f gateway
docker-compose logs -f schedule
docker-compose logs -f clinic
docker-compose logs -f lab