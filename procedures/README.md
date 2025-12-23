# Lab Service (Procedures)

Microsserviço responsável pelo gerenciamento de exames laboratoriais e procedimentos médicos.

## Tecnologias

- Java 21
- Spring Boot 3.5.8
- Spring Data JPA
- Spring Security + OAuth2
- MySQL 8.0
- Flyway (migrações)
- Lombok

## Responsabilidades

- Cadastro e gerenciamento de exames
- Reserva de exames (solicitados por médicos)
- Confirmação de datas de exames
- Atualização de status e resultados
- Consulta de exames por paciente

## Endpoints

| Método | Endpoint                        | Descrição                    | Autenticação          |
|--------|---------------------------------|------------------------------|-----------------------|
| GET    | /exams/patient/{cpf}            | Buscar exames por CPF        | Qualquer autenticado  |
| POST   | /exams/reserve                  | Reservar exame               | DOCTOR, ADMIN         |
| POST   | /exams                          | Criar exame                  | PATIENT, ADMIN        |
| PATCH  | /exams/{id}/confirm/date        | Confirmar data do exame      | PATIENT, ADMIN        |
| PUT    | /exams/{id}/update/date         | Atualizar data do exame      | PATIENT, ADMIN        |

## Variáveis de Ambiente

| Variável                  | Descrição                           | Padrão                                                     |
|---------------------------|-------------------------------------|------------------------------------------------------------|
| SERVER_PORT               | Porta do serviço                    | 8082                                                       |
| DATASOURCE_URL            | URL do banco MySQL                  | jdbc:mysql://localhost:3309/lab_db?createDatabaseIfNotExist=true |
| DATASOURCE_USERNAME       | Usuário do banco                    | root                                                       |
| DATASOURCE_PASSWORD       | Senha do banco                      | root                                                       |
| KEYCLOAK_ISSUER_URI       | URI do emissor Keycloak             | http://localhost:8180/realms/hospital                      |
| KEYCLOAK_JWK_SET_URI      | URI do JWK Set                      | http://localhost:8180/realms/hospital/protocol/openid-connect/certs |
| KEYCLOAK_TOKEN_URI        | URI de token do Keycloak            | http://localhost:8180/realms/hospital/protocol/openid-connect/token |
| LAB_CLIENT_SECRET         | Client secret para Keycloak         | IDlJM9G8b8pz5WTkVUylXhNPIqp2w80f                           |

## Como Executar

### Com Docker Compose (Recomendado)
```bash
# Na raiz do projeto
docker-compose up -d lab
```

### Localmente
```bash
# 1. Garantir que dependências estão rodando
docker-compose up -d lab-db keycloak

# 2. Configurar variáveis de ambiente (ver seção acima)

# 3. Executar serviço
./mvnw spring-boot:run
```

## Integrações

### Clinic Service
Recebe solicitações de exames durante atendimentos de consultas.

### Schedule Service
Comunica atualizações de status e datas de exames.

## Banco de Dados

O serviço utiliza MySQL com migrações gerenciadas pelo Flyway.

**Porta:** 3309 (quando rodando via Docker)
**Database:** lab_db

### Migrations
As migrations Flyway estão em: `src/main/resources/db/migration/`

### Seeds (Dados Iniciais)
O banco é inicializado automaticamente com 10 procedimentos/exames pré-cadastrados:

1. Ultrassom (30min - Complexidade Padrão)
2. Ressonância Magnética (60min - Alta Complexidade)
3. Tomografia (45min - Alta Complexidade)
4. Raio-X (15min - Complexidade Padrão)
5. Mamografia (20min - Alta Complexidade)
6. Hemograma Completo (15min - Complexidade Padrão)
7. Teste de COVID (15min - Complexidade Padrão)
8. Endoscopia (30min - Alta Complexidade)
9. Colonoscopia (45min - Alta Complexidade)
10. Eletrocardiograma (15min - Complexidade Padrão)

## Fluxo de Exames

### 1. Reserva de Exame (pelo médico)
```
Clinic Service → Lab Service (POST /exams/reserve)
Status: RESERVED (aguardando confirmação do paciente)
```

### 2. Confirmação pelo Paciente
```
Paciente → Schedule Service → Lab Service (PATCH /exams/{id}/confirm/date)
Status: SCHEDULED (agendado)
```

### 3. Realização do Exame
```
Laboratório realiza o exame
Status: COMPLETED
```

### 4. Resultado Disponível
```
Resultado é publicado e disponibilizado para consulta
```

## Tipos de Exames

Exemplos de exames suportados:
- Exames de sangue (hemograma, glicemia, etc.)
- Exames de imagem (raio-X, ultrassom, ressonância)
- Exames cardiológicos (ECG, holter, teste ergométrico)
- Exames de urina e fezes
- E outros conforme necessidade

## Exemplo de Uso

### Reservar Exame (Médico)
```bash
curl -X POST http://localhost:8082/exams/reserve \
  -H "Authorization: Bearer <token-doctor>" \
  -H "Content-Type: application/json" \
  -d '{
    "patientCpf": "12345678900",
    "examName": "Hemograma Completo",
    "consultationId": 1,
    "requestingDoctorId": 1
  }'
```

### Confirmar Data (Paciente)
```bash
curl -X PATCH http://localhost:8082/exams/1/confirm/date \
  -H "Authorization: Bearer <token>" \
  -H "Content-Type: application/json" \
  -d '{
    "dateTime": "2025-12-26T08:00:00"
  }'
```

### Buscar Exames do Paciente
```bash
curl -X GET http://localhost:8082/exams/patient/12345678900 \
  -H "Authorization: Bearer <token>"
```

### Criar Exame Diretamente (Paciente ou Admin)
```bash
curl -X POST http://localhost:8082/exams \
  -H "Authorization: Bearer <token>" \
  -H "Content-Type: application/json" \
  -d '{
    "patientCpf": "12345678900",
    "examName": "Raio-X Tórax",
    "dateTime": "2025-12-27T09:00:00"
  }'
```

### Atualizar Data do Exame
```bash
curl -X PUT http://localhost:8082/exams/1/update/date \
  -H "Authorization: Bearer <token>" \
  -H "Content-Type: application/json" \
  -d '{
    "newDateTime": "2025-12-28T10:00:00"
  }'
```

## Status de Exames

- **RESERVED**: Reservado pelo médico, aguardando confirmação
- **SCHEDULED**: Confirmado pelo paciente, agendado
- **IN_PROGRESS**: Exame em realização
- **COMPLETED**: Exame concluído
- **CANCELLED**: Exame cancelado

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
docker-compose logs -f lab
```