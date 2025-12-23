# 🌐 Gateway Service

API Gateway do sistema Hospital API, responsável por rotear requisições para os microsserviços backend.

## 🛠️ Tecnologias

- Java 21
- Spring Boot 3.5.8
- Spring Cloud Gateway 2025.0.0
- Spring Security + OAuth2
- WebFlux (programação reativa)

## 📋 Responsabilidades

- Roteamento de requisições para microsserviços
- Validação de tokens JWT com Keycloak
- Controle de acesso baseado em roles
- Ponto único de entrada para clientes

## 🔀 Rotas Configuradas

| Caminho          | Destino          | Porta | Descrição                        |
|------------------|------------------|-------|----------------------------------|
| /api/schedule/** | Schedule Service | 8083  | Agendamentos e pacientes         |
| /api/clinic/**   | Clinic Service   | 8081  | Consultas e médicos              |
| /api/lab/**      | Lab Service      | 8082  | Exames laboratoriais             |

Todas as rotas removem o prefixo `/api/<service>` antes de encaminhar para o microsserviço.

### 📝 Exemplo
- Cliente acessa: `http://localhost:8080/api/schedule/patients`
- Gateway roteia para: `http://schedule:8083/patients`

## ⚙️ Variáveis de Ambiente

| Variável                  | Descrição                           | Padrão                                                     |
|---------------------------|-------------------------------------|------------------------------------------------------------|
| SERVER_PORT               | Porta do gateway                    | 8080                                                       |
| SCHEDULE_SERVICE_URL      | URL do Schedule Service             | http://localhost:8083                                      |
| CLINIC_SERVICE_URL        | URL do Clinic Service               | http://localhost:8081                                      |
| LAB_SERVICE_URL           | URL do Lab Service                  | http://localhost:8082                                      |
| KEYCLOAK_ISSUER_URI       | URI do emissor Keycloak             | http://localhost:8180/realms/hospital                      |
| KEYCLOAK_JWK_SET_URI      | URI do JWK Set do Keycloak          | http://localhost:8180/realms/hospital/protocol/openid-connect/certs |

## 🚀 Como Executar

### 🐳 Com Docker Compose (Recomendado)
```bash
# Na raiz do projeto
docker-compose up -d gateway
```

### 💻 Localmente
```bash
# Configurar variáveis de ambiente
export SERVER_PORT=8080
export SCHEDULE_SERVICE_URL=http://localhost:8083
export CLINIC_SERVICE_URL=http://localhost:8081
export LAB_SERVICE_URL=http://localhost:8082
export KEYCLOAK_ISSUER_URI=http://localhost:8180/realms/hospital
export KEYCLOAK_JWK_SET_URI=http://localhost:8180/realms/hospital/protocol/openid-connect/certs

# Executar
./mvnw spring-boot:run
```

## 🔐 Segurança

O Gateway valida todos os tokens JWT recebidos no header `Authorization: Bearer <token>` contra o Keycloak.

### 🎫 Obter Token
```bash
curl -X POST http://localhost:8180/realms/hospital/protocol/openid-connect/token \
  -H "Content-Type: application/x-www-form-urlencoded" \
  -d "client_id=schedule-client" \
  -d "client_secret=XlRQC5MaUMHXpFgAz0Mc6wA0IX9x5Hzr" \
  -d "grant_type=client_credentials"
```

### 🔑 Usar Token
```bash
curl -H "Authorization: Bearer <token>" \
  http://localhost:8080/api/schedule/patients
```

## ❤️ Healthcheck

```bash
curl http://localhost:8080/actuator/health
```

## 🔨 Build

```bash
./mvnw clean package
```

## 📋 Logs

### 🐳 Docker
```bash
docker-compose logs -f gateway
```

### 💻 Local
Logs aparecem no console onde o serviço foi executado.