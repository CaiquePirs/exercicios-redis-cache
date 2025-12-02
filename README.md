# 🚀 Practical Exercises with Java + Spring Boot + Redis

This repository brings together practical examples developed to **learn and master the use of Redis** integrated with **Spring Boot**.  
The exercises explore different patterns and real use cases, such as cache, session control, rate limiting, queues, and pub/sub.

---

## 🧰 Technologies Used
- **Java 21**
- **Spring Boot**
- **Spring Data Redis**
- **Redis (Docker)**
- **Postman** `/ for API testing
`
---

## ✅ 1. User Cache — *Cache Aside Pattern*

### 📘 Description
Creates an endpoint `GET /users/{id}` that uses **Redis as cache** to reduce database queries.

### 🔁 Flow
1. The API checks if the user is stored in Redis.
2. If **found**, it returns directly from the cache.
3. If **not found**, it queries the database and writes to Redis with a **TTL of 5 minutes**.

### 🎯 Goal
Practice the **Cache Aside Pattern** — a classic caching pattern widely used in high-performance systems.

---

## ✅ _2. Login with Session Store (Spring Security + JWT + Redis)_

### 📘 Description
Secure authentication using **Spring Security**, **JWT**, and **Redis** as a **Session Store**, simulating a modern and scalable login.

### 🔁 Flow
1. The user logs in via `POST /login` sending email and password.
2. The API authenticates and generates a **JWT token**.
3. The token is saved in **Redis**:
    - key: `user:{email}`
    - value: JWT token
    - expires in 10 minutes.
4. On subsequent requests, the token is validated by the `JwtAuthenticationFilter`.
5. **Redis** also controls the **rate limit** of each user.

### 🎯 Goal
Practice **stateless authentication** with **JWT + Redis**, ensuring **security, caching, and efficient access control**.

---

## ✅ 3. Rate Limiting — *Request Control*

### 📘 Description
Creates an endpoint `GET /user/me` protected by **request limit** per ID.

### 🔁 Flow
1. Each request is identified by the **client ID**.
2. Creates a key `user:{ip}:requests` in Redis.
3. On each access, it performs `INCR rate:{ip}`.
4. If it exceeds **100 requests in 1 minute**, it returns **HTTP 429 – Too Many Requests**.

### 🎯 Goal
Practice traffic control and abuse prevention using **counters and TTL in Redis**.

---

## ✅ 4. Message Queue — *Pub/Sub*

### 📘 Description
Implements asynchronous communication between two services using the **Redis Publish/Subscribe pattern**.

### 🔁 Flow
- **Publisher Service**
    - Endpoint `POST /sendMessage`
    - Publishes messages to the `chat` channel.
- **Subscriber Service**
    - Listens to the `chat` channel
    - Displays received messages in real time.

### 🎯 Goal
Practice the **Pub/Sub** pattern for real-time and decoupled communication between services.

---

## 🧪 How to Run

### 1️⃣ Start Redis via Docker
```bash
docker run -p 6379:6379 redis
2️⃣ Run the Spring Boot Application
bash
mvn spring-boot:run
3️⃣ Test the Endpoints
Use Postman, cURL, or the browser to test the endpoints described above.
```

### 🧠 Concepts Learned
- Cache Aside Pattern (on-demand caching)
- Session Store with expiration
- Distributed Rate Limiting
- Counters (INCR)
- Expiration (EXPIRE)
- Publish/Subscribe (real-time messaging)

### 👨‍💻 Author
Caique Pires Java Developer | Back-end Student | Enthusiast of scalable systems
