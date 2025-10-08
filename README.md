# 🚀 Exercícios Práticos com Java + Spring Boot + Redis

Este repositório reúne diversos exemplos práticos desenvolvidos para **aprender e dominar o uso do Redis** integrado com **Spring Boot**.  
Os exercícios exploram diferentes padrões e casos de uso reais, como cache, controle de sessão, rate limiting, filas e pub/sub.

---

## 🧰 Tecnologias Utilizadas
- **Java 21+**
- **Spring Boot**
- **Spring Data Redis**
- **Redis (Docker)**
- **Postman** `/ para testes de API
`
---

## ✅ 1. Cache de Usuário — *Cache Aside Pattern*

### 📘 Descrição
Cria um endpoint `GET /users/{id}` que utiliza o **Redis como cache** para reduzir consultas ao banco de dados.

### 🔁 Fluxo
1. A API verifica se o usuário está armazenado no Redis.
2. Se **encontrar**, retorna direto do cache.
3. Se **não encontrar**, busca no banco e grava no Redis com **TTL de 5 minutos**.

### 🎯 Objetivo
Treinar o **Cache Aside Pattern** — padrão clássico de cache, amplamente usado em sistemas de alta performance.

---

## ✅ _2. Login com Session Store (Spring Security + JWT + Redis)_

### 📘 Descrição
Autenticação segura usando **Spring Security**, **JWT** e **Redis** como **Session Store**, simulando um login moderno e escalável.

---

### 🔁 Fluxo
1. O usuário faz login via `POST /login` enviando email e senha.
2. A API autentica e gera um **token JWT**.
3. O token é salvo no **Redis**:
    - chave: `user:{email}`
    - valor: token JWT
    - expira em 10 minutos.
4. Nas próximas requisições, o token é validado pelo filtro `JwtAuthenticationFilter`.
5. O **Redis** também controla o **rate limit** de cada usuário.

---

### 🎯 Objetivo
Treinar autenticação **stateless** com **JWT + Redis**, garantindo **segurança, cache e controle de acesso eficiente**.

---

## ✅ 3. Rate Limiting — *Controle de Requisições*

### 📘 Descrição
Cria um endpoint `GET /user/me` protegido por **limite de requisições** por ID.

### 🔁 Fluxo
1. Cada requisição é identificada pelo **ID do cliente**.
2. Cria uma chave `user:{ip}:requests` no Redis.
3. A cada acesso, faz `INCR rate:{ip}`.
4. Se ultrapassar **100 requisições em 1 minuto**, retorna **HTTP 429 – Too Many Requests**.

### 🎯 Objetivo
Treinar controle de tráfego e prevenção de abuso usando **contadores e TTL no Redis**.

---

## ✅ 4. Fila de Mensagens — *Pub/Sub*

### 📘 Descrição
Implementa comunicação assíncrona entre dois serviços utilizando o **padrão Publish/Subscribe do Redis**.

### 🔁 Fluxo
- **Publisher Service**
    - Endpoint `POST /sendMessage`
    - Publica mensagens no canal `chat`.
- **Subscriber Service**
    - Fica ouvindo o canal `chat`
    - Exibe mensagens recebidas em tempo real.

### 🎯 Objetivo
Treinar o padrão **Pub/Sub** para comunicação em tempo real e desacoplada entre serviços.

---

## 🧪 Como Executar

### 1️⃣ Subir o Redis via Docker
```bash
docker run -p 6379:6379 redis
```

### 2️⃣ Rodar a Aplicação Spring Boot
```bash
mvn spring-boot:run
```

### 3️⃣ Testar os Endpoints
Use **Postman**, **cURL** ou o navegador para testar os endpoints descritos acima.

---

## 🧠 Conceitos Aprendidos
- Cache Aside Pattern (cache sob demanda)
- Session Store com expiração
- Rate Limiting distribuído
- Contadores (`INCR`)
- Expiração (`EXPIRE`)
- Publish/Subscribe (mensageria em tempo real)

---

## 👨‍💻 Autor
**Caique Pires**  
Desenvolvedor Java | Estudante de Back-end | Entusiasta de sistemas escaláveis  