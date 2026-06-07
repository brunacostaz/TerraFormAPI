# TerraForm API

**Gerenciamento de hortas espaciais - painel de controle para estufas herméticas no sistema solar.**

TerraForm API é a camada backend da solução TerraForm, desenvolvida em Java com Spring Boot para apoiar um aplicativo mobile de monitoramento e controle operacional de estufas espaciais. A aplicação mobile representa a interface do astronauta-agricultor; esta API fornece os serviços, regras de negócio, persistência, segurança, simulação e integração SOAP consumidos pela camada de app.

O projeto foi estruturado como uma **arquitetura orientada a serviços**, com responsabilidades separadas entre serviços de estufas, estoque, química, simulação, alertas, logs, planetas e segurança. Cada serviço possui contrato próprio via REST ou SOAP, mantendo baixo acoplamento e facilitando reutilização e integração.

![Java](https://img.shields.io/badge/Java-21-ED8B00?logo=openjdk&logoColor=white)
![Docker](https://img.shields.io/badge/Docker-ready-2496ED?logo=docker&logoColor=white)
![Spring Web](https://img.shields.io/badge/Spring_Web-6DB33F?logo=spring&logoColor=white)
![Spring Data JPA](https://img.shields.io/badge/Spring_Data_JPA-6DB33F?logo=spring&logoColor=white)
![Spring Security](https://img.shields.io/badge/Spring_Security-6DB33F?logo=springsecurity&logoColor=white)
![Spring Validation](https://img.shields.io/badge/Spring_Validation-6DB33F?logo=spring&logoColor=white)
![Spring Web Services](https://img.shields.io/badge/Spring_Web_Services-6DB33F?logo=spring&logoColor=white)
![JUnit 5](https://img.shields.io/badge/JUnit_5-25A162?logo=junit5&logoColor=white)
![Mockito](https://img.shields.io/badge/Mockito-78A746?logoColor=white)

---

## Sumário

- [O Problema](#o-problema)
- [A Solução](#a-solução)
- [Funcionalidades da API](#funcionalidades-da-api)
- [Arquitetura de Serviços](#arquitetura-de-serviços)
- [Tecnologias Utilizadas](#tecnologias-utilizadas)
- [Segurança da API](#segurança-da-api)
- [Como Rodar o Projeto](#como-rodar-o-projeto)
  - [1. Pré-requisitos](#1-pré-requisitos)
  - [2. Clonar o repositório](#2-clonar-o-repositório)
  - [3. Subir a aplicação com Docker](#3-subir-a-aplicação-com-docker)
  - [4. Acessar a API](#4-acessar-a-api)
  - [5. Usar autenticação no Insomnia/Postman/Swagger](#5-usar-autenticação-no-insomniapostmanswagger)
  - [6. Testar no Insomnia](#6-testar-no-insomnia)
  - [7. Encerrar a aplicação](#7-encerrar-a-aplicação)
- [Serviços da Aplicação](#serviços-da-aplicação)
  - [Health](#health)
  - [Planet Service](#planet-service)
  - [Greenhouse Service](#greenhouse-service)
  - [Inventory Service](#inventory-service)
  - [Chemical Reaction Service](#chemical-reaction-service)
  - [Synthesis Service e Compound Application Service](#synthesis-service-e-compound-application-service)
  - [Log Service](#log-service)
  - [SOAP Chemical Synthesis Service](#soap-chemical-synthesis-service)
  - [Integração REST -> SOAP](#integração-rest---soap)
  - [Simulação e Gravidade](#simulação-e-gravidade)
- [Testes Automatizados](#testes-automatizados)
- [Observacoes para o App Mobile](#observacoes-para-o-app-mobile)


---

## O Problema

Cultivar alimentos no espaço é extremamente complexo:

- Ausência de ciclos hídricos naturais.
- Atmosferas hostis, sem concentrações adequadas de nitrogênio e oxigênio.
- Gravidade variável afetando a fisiologia das plantas e a distribuição de fluidos.
- Distância entre a Terra e as bases espaciais tornando intervenções manuais lentas ou inviáveis.
- Necessidade de monitoramento contínuo para evitar perda de colheita em ambientes herméticos.

---

## A Solução

TerraForm centraliza o controle operacional de estufas hermeticamente isoladas instaladas em diferentes planetas e luas do sistema solar. O usuário assume o papel de um operador agrícola que monitora solo, ar, atmosfera, crescimento da planta, estoque e reações químicas a partir de um aplicativo mobile.

TerraForm é uma ferramenta operacional, inspirada em painéis SCADA, com uma abordagem científica para controle de ambiente, estoque e simulação agrícola em condições espaciais.

---

## Funcionalidades da API

- Dashboard completo da estufa com solo, ar, nutrientes, atmosfera, planta, alertas e estoque.
- CRUD de estufas herméticas independentes.
- Cadastro de estufas em Lua, Marte, Europa, Titã e Terra.
- Cálculo de fator gravitacional: `gravityFactor = 0.7 + gravidade * 0.3`.
- Simulação automática de consumo de recursos e crescimento das plantas.
- Alertas ativos calculados para estados críticos ou de atenção.
- Gestão de estoque com recursos brutos e compostos sintetizados.
- Reposição de estoque por estufa.
- Síntese química de `H2O`, `NH3`, `CaCO3` e `H2CO3` com equações balanceadas.
- Aplicação de compostos no solo ou no ar.
- Fluxo **Nutrir Tudo** para aplicar vários compostos em lote.
- Logs operacionais agrupáveis por estufa, planeta ou visão global.
- Web Service SOAP com WSDL para consulta e processamento de síntese química.
- Integração REST -> SOAP no fluxo de síntese.
- Segurança com Spring Security e autenticação Basic Auth por perfis.
- Documentação interativa via Swagger.
- Execução simplificada com Docker.

---

## Arquitetura de Serviços

A API foi dividida em serviços com responsabilidades claras:

| Serviço | Responsabilidade | 
|---|---|
| **Greenhouse Service** | cadastro, consulta, atualização e dashboard das estufas. |
| **Planet Service** | consulta de planetas/lua e suas características gravitacionais. |
| **Inventory Service** | controle de estoque independente por estufa. |
| **Chemical Reaction Service** | catálogo de reações químicas e regras de síntese. |
| **Synthesis Service** | processamento da síntese de compostos. |
| **Compound Application Service** | aplicação de compostos no solo/ar e fluxo Nutrir Tudo. |
| **Simulation Service** | consumo automático, crescimento da planta e degradação ambiental. |
| **Alert Service** | cálculo de alertas ativos a partir dos estados da estufa. |
| **Log Service** | histórico operacional das ações executadas. |
| **SOAP Chemical Synthesis Service** | contrato SOAP interoperável para consulta e processamento químico. |
| **Security Service** | autenticação, autorização e tratamento de erros de acesso. |
| **Gravity Service** | cálculo do fator gravitacional usado para ajustar as taxas de consumo de cada estufa conforme o planeta/lua. |
| **Agriculture Calculation Service** | cálculo da qualidade do solo, qualidade do ar, status do solo/ar e regras de crescimento/saúde da planta. |
| **TerraForm Mapper** | montagem dos DTOs de resposta da API, convertendo entidades internas em JSON adequado para o app mobile. |
| **Compound Effect Strategies** | conjunto de estratégias polimórficas que aplicam os efeitos de cada composto no solo ou no ar da estufa. |
| **Synthesis Application Service** | serviço de aplicação que conecta a API REST ao cliente SOAP no fluxo de síntese química. |
| **Chemical Synthesis SOAP Client** | cliente interno responsável por chamar o Web Service SOAP a partir da API REST. |

---

## Tecnologias Utilizadas

| Tecnologia | Uso no projeto |
|---|---|
| Java 21 | Linguagem principal da API |
| Spring Boot 3 | Base da aplicação backend |
| Spring Web | API REST |
| Spring Data JPA | Persistência e repositories |
| Hibernate | ORM |
| H2 Database | Banco de dados em memória para execução local |
| Spring Validation | Validação de DTOs e entradas da API |
| Spring Security | Autenticação e autorização |
| Spring Web Services | Web Service SOAP |
| WSDL/XSD | Contrato do serviço SOAP |
| springdoc-openapi | Swagger/OpenAPI |
| Docker | Empacotamento e execução local |
| Docker Compose | Orquestração local da API |
| JUnit 5 | Testes automatizados |
| Mockito | Mocks em testes unitários |
| Maven | Build e gerenciamento de dependências |

---

## Segurança da API

A API utiliza **Spring Security** com autenticação **Basic Auth** e controle de acesso por perfil.

| Usuário | Senha | Perfil | Permissões principais |
|---|---|---|---|
| `admin` | `terraform-admin` | `ADMIN` | Acesso total, incluindo `DELETE` e H2 Console |
| `operator` | `terraform-operator` | `OPERATOR` | Consulta, criação, atualização, síntese, reposição e aplicação |
| `viewer` | `terraform-viewer` | `VIEWER` | Somente consultas `GET` |

Regras principais:

- `GET /api/**`: permitido para `VIEWER`, `OPERATOR` e `ADMIN`.
- `POST /api/**`: permitido para `OPERATOR` e `ADMIN`.
- `PUT /api/**`: permitido para `OPERATOR` e `ADMIN`.
- `DELETE /api/**`: permitido apenas para `ADMIN`.
- `/h2-console/**`: permitido apenas para `ADMIN`.
- `/api/health`, Swagger e OpenAPI ficam públicos para facilitar testes e documentação.
- `/ws/**`: protegido para `OPERATOR` e `ADMIN`.

Erros de segurança são retornados em JSON:

```json
{
  "status": 403,
  "message": "Acesso negado. Seu usuário não possui permissão para executar esta operação.",
  "timestamp": "2026-06-06T12:00:00Z"
}
```
---

## Como Rodar o Projeto

### 1. Pré-requisitos

Instale:

- Git.
- Docker Desktop.
- IntelliJ IDEA.

Confirme se o Docker está funcionando:

```powershell
docker ps
```

Se o comando responder sem erro, o Docker está pronto.

### 2. Clonar o repositório

```powershell
git clone https://github.com/brunacostaz/TerraFormAPI.git
cd TerraFormAPI
```

Se você já estiver com o projeto na máquina:

```powershell
cd D:\TerraFormAPI
```

### 3. Subir a aplicação com Docker

```powershell
docker compose up --build
```

Na primeira execução, o Docker vai baixar dependências e construir a imagem da API. Isso pode demorar alguns minutos.

### 4. Acessar a API

| Recurso | URL |
|---|---|
| Health Check | `http://localhost:8080/api/health` |
| Swagger UI | `http://localhost:8080/swagger-ui/index.html` |
| OpenAPI JSON | `http://localhost:8080/v3/api-docs` |
| H2 Console | `http://localhost:8080/h2-console` |
| WSDL SOAP | `http://localhost:8080/ws/chemical-synthesis.wsdl` |

Configuração do H2:

```text
JDBC URL: jdbc:h2:mem:terraform
User: sa
Password:
```

### 5. Usar autenticação no Insomnia/Postman/Swagger

Para chamadas protegidas, use Basic Auth.

Exemplo para operador:

```text
Username: operator
Password: terraform-operator
```

No Swagger, clique em **Authorize**, selecione `basicAuth` e informe usuário e senha.

### 6. Testar no Insomnia

Com a API rodando em:

```text
http://localhost:8080
```

crie uma nova requisição no Insomnia e configure:

```text
Auth: Basic Auth
Username: operator
Password: terraform-operator
```

Para requisições `POST` e `PUT`, configure também:

```text
Header: Content-Type
Value: application/json
```

Fluxo recomendado para testar:

| Ordem | Metodo | URL | Objetivo |
|---:|---|---|---|
| 1 | GET | `http://localhost:8080/api/health` | Verificar se a API subiu. |
| 2 | GET | `http://localhost:8080/api/greenhouses` | Listar estufas cadastradas. |
| 3 | GET | `http://localhost:8080/api/greenhouses/1/dashboard` | Ver dashboard completo da estufa 1. |
| 4 | GET | `http://localhost:8080/api/greenhouses/1/inventory` | Conferir estoque antes das operações. |
| 5 | POST | `http://localhost:8080/api/greenhouses/1/inventory/restock` | Repor algum recurso. |
| 6 | POST | `http://localhost:8080/api/greenhouses/1/synthesis` | Sintetizar um composto via integração REST -> SOAP. |
| 7 | POST | `http://localhost:8080/api/greenhouses/1/compounds/apply` | Aplicar composto no solo ou no ar. |
| 8 | GET | `http://localhost:8080/api/logs` | Conferir os logs operacionais gerados. |

Exemplo de reposição:

```json
{
  "resourceCode": "H",
  "quantity": 15
}
```

Exemplo de síntese:

```json
{
  "compoundCode": "NH3",
  "units": 2
}
```

Exemplo de aplicação:

```json
{
  "compoundCode": "H2O",
  "target": "Solo",
  "quantity": 10
}
```

Para testar segurança:

- Use `viewer / terraform-viewer` em um `GET`: deve funcionar.
- Use `viewer / terraform-viewer` em um `POST`: deve retornar `403`.
- Use `operator / terraform-operator` em um `POST`: deve funcionar.
- Use `admin / terraform-admin` em um `DELETE`: deve funcionar.

### 7. Encerrar a aplicação

No terminal onde o Docker Compose está rodando, pressione:

```text
Ctrl + C
```

Depois execute:

```powershell
docker compose down
```

---
# Serviços da Aplicação

## Health

**Descrição do serviço:** verifica se a API está disponível.

| Metodo | Endpoint | Descricao |
|---|---|---|
| GET | `/api/health` | Retorna o status de disponibilidade da API. |

---

## Planet Service

**Descrição do serviço:** disponibiliza os planetas e luas suportados pela simulação, incluindo gravidade e fator de consumo.

| Metodo | Endpoint | Descricao |
|---|---|---|
| GET | `/api/planets` | Lista Lua, Marte, Europa, Titã e Terra com seus dados gravitacionais. |

---

## Greenhouse Service

**Descrição do serviço:** gerencia as estufas herméticas independentes. Cada estufa possui seu próprio solo, ar, planta, estoque, logs e simulação.

| Metodo | Endpoint | Descricao |
|---|---|---|
| GET | `/api/greenhouses` | Lista todas as estufas. Aceita filtro opcional por planeta. |
| GET | `/api/greenhouses/{id}` | Consulta uma estufa pelo identificador. |
| GET | `/api/greenhouses/{id}/dashboard` | Retorna o dashboard operacional completo da estufa. |
| POST | `/api/greenhouses` | Cria uma nova estufa hermetica. |
| PUT | `/api/greenhouses/{id}` | Atualiza os dados operacionais de uma estufa. |
| DELETE | `/api/greenhouses/{id}` | Remove uma estufa e seus registros associados. Requer perfil `ADMIN`. |

### Detalhes sobre o Endpoint:

```text
GET /api/greenhouses/{id}/dashboard
```

Campos importantes para o app:

| Campo | Uso no front |
|---|---|
| `status` | Status operacional informado. |
| `overallStatus` | Status geral calculado. |
| `soilStatus` | Status do solo. |
| `airStatus` | Status do ar. |
| `planet.gravityFactor` | Fator usado nas taxas de consumo. |
| `plant.health` | Saúde da planta. |
| `plant.healthStatus` | Estado da planta. |
| `activeAlerts` | Alertas ativos para exibição no topo. |
| `inventory` | Estoque independente da estufa. |
| `logs` | Logs operacionais recentes. |

### Body - criar ou atualizar estufa

Endpoint:

```text
POST /api/greenhouses
PUT  /api/greenhouses/{id}
```

Exemplo:

```json
{
  "name": "Estufa Marte A",
  "planetCode": "MARS",
  "status": "Operacional",
  "plantSpecies": "Alface romana",
  "plantPhase": "Germinação",
  "plantPhaseProgress": 12.5,
  "soilPh": 6.5,
  "soilHumidity": 62.0,
  "airOxygen": 21.0,
  "airCarbonDioxide": 0.05,
  "airHumidity": 58.0
}
```

---

## Inventory Service

**Descrição do serviço:** controla recursos brutos e compostos sintetizados de cada estufa. O estoque é independente por estufa.

| Metodo | Endpoint | Descricao |
|---|---|---|
| GET | `/api/greenhouses/{greenhouseId}/inventory` | Consulta o estoque completo de uma estufa. |
| POST | `/api/greenhouses/{greenhouseId}/inventory/restock` | Repoe um recurso bruto ou composto no estoque da estufa. |

### Body - reposição de estoque

Endpoint:

```text
POST /api/greenhouses/{greenhouseId}/inventory/restock
```

Exemplo:

```json
{
  "resourceCode": "H",
  "quantity": 15
}
```

Códigos comuns:

- Recursos brutos: `H`, `O`, `N`, `C`, `Ca`, `P`, `K`, `Mg`, `S`.
- Compostos sintetizados: `H2O`, `NH3`, `CaCO3`, `H2CO3`.

---

## Chemical Reaction Service

**Descrição do serviço:** disponibiliza as reações químicas reais e balanceadas usadas pela síntese.

| Metodo | Endpoint | Descricao |
|---|---|---|
| GET | `/api/reactions` | Lista todas as reações químicas disponíveis. |
| GET | `/api/reactions/{compoundCode}` | Consulta a reação de um composto específico. |

Reacoes principais:

| Composto | Equação |
|---|---|
| `H2O` | `2H2 + O2 -> 2H2O` |
| `NH3` | `N2 + 3H2 -> 2NH3` |
| `CaCO3` | `Ca + CO2 + 1/2O2 -> CaCO3` |
| `H2CO3` | `CO2 + H2O -> H2CO3` |

---

## Synthesis Service e Compound Application Service

**Descrição do serviço:** processa síntese química, aplicação de compostos no solo/ar e o fluxo Nutrir Tudo. A síntese REST **chama internamente o Web Service SOAP**, demonstrando integração entre serviços.

| Metodo | Endpoint | Descricao |
|---|---|---|
| POST | `/api/greenhouses/{greenhouseId}/synthesis` | Sintetiza um composto químico usando o serviço SOAP internamente. |
| POST | `/api/greenhouses/{greenhouseId}/compounds/apply` | Aplica um composto sintetizado no solo ou no ar. |
| POST | `/api/greenhouses/{greenhouseId}/compounds/batch-apply` | Executa o fluxo Nutrir Tudo, aplicando varios compostos em uma unica transacao. |

### Body - sintetizar composto

Endpoint:

```text
POST /api/greenhouses/{greenhouseId}/synthesis
```

Exemplo:

```json
{
  "compoundCode": "NH3",
  "units": 2
}
```

Validações:

- estufa precisa existir;
- composto precisa existir;
- `units` precisa ser maior que zero;
- estoque de reagentes precisa ser suficiente;
- reagentes são debitados;
- composto produzido é creditado;
- log de síntese é registrado.

Erro de estoque insuficiente:

```text
409 Conflict
```

```json
{
  "status": 409,
  "message": "Estoque insuficiente de N para sintetizar NH3.",
  "timestamp": "..."
}
```

### Body - aplicar composto

Endpoint:

```text
POST /api/greenhouses/{greenhouseId}/compounds/apply
```

Exemplo:

```json
{
  "compoundCode": "H2O",
  "target": "Solo",
  "quantity": 10
}
```

Alvos aceitos:

- `Solo` ou `SOIL`.
- `Ar` ou `AIR`.

Validações:

- composto precisa existir;
- alvo precisa ser permitido;
- estoque precisa ser suficiente;
- aplicação que não alteraria o estado preserva o estoque;
- qualidade do solo/ar é recalculada;
- log de aplicação é registrado.

### Body - Nutrir Tudo

Endpoint:

```text
POST /api/greenhouses/{greenhouseId}/compounds/batch-apply
```

Exemplo:

```json
{
  "applications": [
    {
      "compoundCode": "H2O",
      "target": "Solo",
      "quantity": 10
    },
    {
      "compoundCode": "NH3",
      "target": "Solo",
      "quantity": 5
    },
    {
      "compoundCode": "CaCO3",
      "target": "Solo",
      "quantity": 5
    }
  ]
}
```

O lote é validado antes de alterar a estufa. Se faltar estoque para qualquer item, nada é aplicado.

---

## Log Service

**Descrição do serviço:** registra histórico de ações relevantes executadas na API, como síntese, reposição, aplicação de compostos e nutrição em lote.

| Metodo | Endpoint | Descricao |
|---|---|---|
| GET | `/api/logs` | Lista logs operacionais. Permite filtros por estufa, planeta ou escopo global. |

Filtros opcionais:

```text
GET /api/logs?greenhouseId=1
GET /api/logs?planetCode=MARS
```

---

## SOAP Chemical Synthesis Service

**Descrição do serviço:** Web Service SOAP responsável por expor o contrato interoperável de síntese química. Ele permite consultar reações e processar sínteses usando XML.

WSDL:

```text
http://localhost:8080/ws/chemical-synthesis.wsdl
```

| Operacao SOAP | Descricao |
|---|---|
| `consultReaction` | Consulta a equação, finalidade, reagentes e alvos disponíveis de um composto. |
| `processSynthesis` | Processa a síntese de um composto para uma estufa específica. |

### Exemplo SOAP - consultar reação (consultReaction)

```xml
<soapenv:Envelope xmlns:soapenv="http://schemas.xmlsoap.org/soap/envelope/"
                  xmlns:chem="http://terraform.fiap.com.br/soap/chemical-synthesis">
  <soapenv:Header/>
  <soapenv:Body>
    <chem:consultReactionRequest>
      <chem:compoundCode>NH3</chem:compoundCode>
    </chem:consultReactionRequest>
  </soapenv:Body>
</soapenv:Envelope>
```

### Exemplo SOAP - processar síntese (processSynthesis)

```xml
<soapenv:Envelope xmlns:soapenv="http://schemas.xmlsoap.org/soap/envelope/"
                  xmlns:chem="http://terraform.fiap.com.br/soap/chemical-synthesis">
  <soapenv:Header/>
  <soapenv:Body>
    <chem:processSynthesisRequest>
      <chem:greenhouseId>1</chem:greenhouseId>
      <chem:compoundCode>NH3</chem:compoundCode>
      <chem:units>2</chem:units>
    </chem:processSynthesisRequest>
  </soapenv:Body>
</soapenv:Envelope>
```

O SOAP também é protegido por Basic Auth. Use, por exemplo:

```text
Username: operator
Password: terraform-operator
```

---

## Integração REST -> SOAP

O endpoint REST:

```text
POST /api/greenhouses/{greenhouseId}/synthesis
```

chama internamente o Web Service SOAP `processSynthesis`. Essa integração demonstra comunicação entre serviços com contratos diferentes:

```text
App Mobile -> API REST -> Cliente SOAP -> Web Service SOAP -> Servico de Sintese -> Banco H2
```

Quando ocorre erro de negocio, como estoque insuficiente, a API retorna status adequado:

```json
{
  "status": 409,
  "message": "Estoque insuficiente de N para sintetizar NH3.",
  "timestamp": "2026-06-06T12:00:00Z"
}
```

---

## Simulação e Gravidade

As estufas são hermeticamente isoladas. O solo nativo do planeta não interfere no cultivo. A gravidade é a variável planetária usada para alterar o consumo dos recursos:

```text
gravityFactor = 0.7 + gravidade * 0.3
consumo efetivo = taxa base * gravityFactor
```

| Corpo celeste | Gravidade | Fator aproximado |
|---|---:|---:|
| Europa | 0.13 g | 0.739 |
| Tita | 0.14 g | 0.742 |
| Lua | 0.17 g | 0.751 |
| Marte | 0.38 g | 0.814 |
| Terra | 1.00 g | 1.000 |

---

## Testes Automatizados

O projeto possui testes para regras de cálculo agrícola, gravidade, alertas, simulação, segurança, Nutrir Tudo e tratamento de erros REST/SOAP.

Se estiver usando IntelliJ:

1. Abra o projeto.
2. Aguarde o Maven carregar as dependências.
3. Abra a aba **Maven**.
4. Execute `Lifecycle > test`.

Outras opções seria rodar os testes dentro do Docker:

```powershell
docker run --rm -v D:\TerraFormAPI:/app -w /app maven:3.9.9-eclipse-temurin-21 mvn test
```

Ou também é possível rodar via Maven local:

```powershell
mvn test
```

---

## Observacoes para o App Mobile

O app mobile pode consumir diretamente os endpoints REST da API. A camada SOAP fica encapsulada no backend: o app chama REST, e o backend realiza a integração SOAP quando necessário.

Fluxo:

```text
Mobile App -> REST API TerraForm -> Servicos internos -> H2/SOAP
```

Isso evita que o app precise lidar com XML, WSDL ou detalhes da síntese SOAP.
