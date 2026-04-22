# 🏨 Hotel Management System (AI-Powered Microservices)

A microservices-based hotel management system enhanced with AI capabilities using **Spring AI**, **PgVector**, and **RabbitMQ**.  
This project focuses on building a scalable backend with **semantic search**, **recommendation system**, and **AI-driven insights**.

## 🚀 Features

### 👤 User Side
- Explore hotels with AI-based recommendations
- Semantic search (natural language queries)
- View AI-generated hotel summaries (pros/cons)
- Add ratings & feedback
- Personalized recommendations (based on behavior)

### 🛠 Admin Panel
- Manage hotels
- Manage users
- Monitor reviews and ratings

### 🤖 AI Capabilities
- Generate embeddings using Google GenAI
- Store vectors in PostgreSQL (pgvector)
- Semantic similarity search
- AI-generated summaries from reviews
- Hybrid recommendation system (vector + filters)

---

## 🏗 Architecture
[ Hotel Service ] [ Rating Service ]
│ │
└──────▶ RabbitMQ ◀─────┘
│
[ AI Service ]
│
PostgreSQL (pgvector)

### Services:
- **Hotel Service** → manages hotel data
- **Rating Service** → handles user reviews
- **AI Service** → embeddings, vector search, recommendations

---

## ⚙️ Tech Stack

- **Backend:** Spring Boot 3, Spring Cloud
- **AI:** Spring AI + Google GenAI (Gemini Embeddings)
- **Database:** PostgreSQL + pgvector
- **Messaging:** RabbitMQ
- **ORM:** Spring Data JPA
- **Build Tool:** Maven

---

## 📦 Key Concepts Implemented

### 🔹 Event-Driven Architecture
- Services communicate via RabbitMQ
- Loose coupling between business logic and AI processing

### 🔹 Vector Store
- Uses `PgVectorStore` from Spring AI
- Stores:
  - Hotel embeddings
  - Review embeddings
  - AI summaries

### 🔹 Embedding Strategy
- Hotels → structured metadata + description
- Ratings → user feedback + sentiment
- Summaries → aggregated intelligence


---

## 🔧 Configuration

### application.yml (AI Service)
```yaml
spring:
  ai:
    google:
      genai:
        api-key: ${GEMINI_KEY}
        embedding:
          api-key: ${GEMINI_KEY}
          text:
            options:
              model: gemini-embedding-001
              dimensions: 768

    vectorstore:
      pgvector:
        initialize-schema: true
        dimensions: 768
        distance-type: COSINE_DISTANCE
