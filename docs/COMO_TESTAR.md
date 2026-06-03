# Como Testar o TerraForm API

## 1. Subir a aplicacao

Na pasta `D:\Fiap\projetos\TerraFormAPI`:

```powershell
docker compose up --build
```

Se quiser deixar rodando em segundo plano:

```powershell
docker compose up -d --build
```

## 2. Verificar se esta no ar

Abra no navegador:

```text
http://localhost:8080/api/health
```

Ou rode no PowerShell:

```powershell
Invoke-RestMethod -Uri http://localhost:8080/api/health
```

## 3. Testar endpoints principais

```powershell
Invoke-RestMethod -Uri http://localhost:8080/api/planets
Invoke-RestMethod -Uri http://localhost:8080/api/greenhouses
Invoke-RestMethod -Uri http://localhost:8080/api/greenhouses/1/dashboard
Invoke-RestMethod -Uri http://localhost:8080/api/greenhouses/1/inventory
Invoke-RestMethod -Uri http://localhost:8080/api/reactions
Invoke-RestMethod -Uri http://localhost:8080/api/logs
```

## 4. Testar sintese quimica

```powershell
$body = @{
  compoundCode = "NH3"
  units = 2
} | ConvertTo-Json

Invoke-RestMethod -Method Post -Uri http://localhost:8080/api/greenhouses/1/synthesis -ContentType "application/json" -Body $body
```

## 5. Testar aplicacao de composto

```powershell
$body = @{
  compoundCode = "H2O"
  target = "SOIL"
  quantity = 10
} | ConvertTo-Json

Invoke-RestMethod -Method Post -Uri http://localhost:8080/api/greenhouses/1/compounds/apply -ContentType "application/json" -Body $body
```

## 6. Acessar H2 Console

Abra:

```text
http://localhost:8080/h2-console
```

Use:

```text
JDBC URL: jdbc:h2:mem:terraform
User: sa
Password:
```

## 7. Rodar testes automatizados

Como Maven nao precisa estar instalado na maquina, rode os testes dentro do Docker:

```powershell
docker run --rm -v D:\Fiap\projetos\TerraFormAPI:/app -w /app maven:3.9.9-eclipse-temurin-21 mvn test
```

Resultado esperado:

```text
BUILD SUCCESS
```

## 8. Parar a aplicacao

```powershell
docker compose down
```
