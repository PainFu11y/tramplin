# Tramplin Backend

Backend part of the Tramplin platform — a system for finding internships, job vacancies, and facilitating interaction between candidates (seekers), employers, and administrators.

## Technologies

- Java 17
- Spring Boot 3.2.5
- Spring Security (JWT)
- Spring Data JPA (Hibernate)
- PostgreSQL
- Gradle
- Docker / Docker Compose
- Swagger (OpenAPI)

## Features

### Authentication
- Seeker & Employer registration with separate flows
- Login with JWT access / refresh tokens
- Token refresh endpoint

### Seeker
- View and update profile
- Privacy settings (profile, applications history, recommendations)
- View and apply to opportunities
- Add / remove opportunities to favourites
- View connections list with search
- Discover possible network (friends of friends + same university)

### Employer
- Company profile management
- Create, update, archive opportunities
- View applications per opportunity
- Update application status (accept / reject)

### Admin
- View pending company verifications
- Approve / reject company verification

## Project Setup

### 1. Clone repository
```bash
git clone <repo-url>
cd tramplin
```

### 2. Create `.env` file

Create a `.env` file in the project root:
```env
DB_URL=jdbc:postgresql://localhost:5432/tramplin_db?currentSchema=public
DB_USERNAME=postgres
DB_PASSWORD=your_password

JWT_SECRET=your_secret_key
JWT_EXPIRATION=86400000
JWT_REFRESH_EXPIRATION=604800000
```

### 3. Run via Docker
```bash
docker-compose up --build
```

The application will be available at:
```
http://localhost:8088
```

## Docker Configuration

`docker-compose.yml`:
```yaml
services:
  db:
    image: postgres:15
    container_name: tramplin_db
    environment:
      POSTGRES_DB: tramplin_db
      POSTGRES_USER: postgres
      POSTGRES_PASSWORD: your_password
    ports:
      - "5432:5432"
    volumes:
      - postgres_data:/var/lib/postgresql/data

  app:
    build: .
    container_name: tramplin_app
    ports:
      - "8088:8088"
    env_file:
      - .env
    depends_on:
      - db

volumes:
  postgres_data:
```

## API Documentation

Swagger UI is available after starting the application:
```
http://localhost:8088/swagger-ui/index.html
```

## Project Structure
```
src/main/java/com/tramplin/
├── config/
├── controller/
├── dto/
│   ├── application/
│   ├── connection/
│   ├── employer/
│   ├── opportunity/
│   ├── request/
│   ├── response/
│   └── seeker/
├── entity/
├── enums/
├── exception/
├── repository/
├── security/
├── service/
└── specification/
```