# TerraForm API

Backend Java/Spring Boot do TerraForm, uma solucao academica para monitoramento e controle de estufas espaciais hermeticas.

## Stack

- Java 21
- Spring Boot 3
- Spring Web
- Spring Data JPA
- Spring Validation
- Spring Security
- H2 Database
- Docker

## Como Rodar Com Docker

Na pasta do projeto:

```powershell
docker compose up --build
```

Aplicacao:

```text
http://localhost:8080
```

H2 Console:

```text
http://localhost:8080/h2-console
```

Configuracao do H2:

```text
JDBC URL: jdbc:h2:mem:terraform
User: sa
Password:
```

## Endpoints Iniciais

```text
GET    /api/health
GET    /api/planets
GET    /api/greenhouses
GET    /api/greenhouses/{id}
GET    /api/greenhouses/{id}/dashboard
GET    /api/greenhouses/{id}/inventory
GET    /api/logs
GET    /api/reactions
GET    /api/reactions/{compoundCode}
POST   /api/greenhouses
POST   /api/greenhouses/{id}/synthesis
POST   /api/greenhouses/{id}/compounds/apply
PUT    /api/greenhouses/{id}
DELETE /api/greenhouses/{id}
```

## Testes

Veja o passo a passo em:

```text
docs/COMO_TESTAR.md
```

Comando principal:

```powershell
docker run --rm -v D:\Fiap\projetos\TerraFormAPI:/app -w /app maven:3.9.9-eclipse-temurin-21 mvn test
```

## Observacao

Esta primeira versao ainda nao implementa JWT e SOAP. A estrutura ja foi preparada para crescer mantendo a separacao de responsabilidades entre controllers, services, repositories, security e soap.
