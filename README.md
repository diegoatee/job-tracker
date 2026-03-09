# job-tracker

A Spring Boot REST API for tracking companies and job applications.

Built with Java 17 · Spring Boot · Spring Data JPA · PostgreSQL · Flyway

---

## What This App Does

job-tracker exposes a REST API with two resources:

- **Companies** — organizations you are interested in working for
- **Applications** — job applications you have submitted to those companies

Each application is linked to a company and tracks its current status through a pipeline from wishlist to offer.

---

## Your Learning Path

This repo is the starting point for a hands-on learning journey. You will build up the full deployment stack layer by layer — each phase teaches one new concept on top of the previous one.

| Phase | What You Learn | What You Add |
|-------|---------------|--------------|
| 1 | Run the app locally with Maven | Nothing — just get it working |
| 2 | Docker — package the app as a container | `Dockerfile` |
| 3 | Docker Compose — run app + database together | `docker-compose.yml` |
| 4 | Kubernetes — deploy to a local cluster | `k8s/` manifests |
| 5 | AWS EKS — deploy to a real cloud cluster | Connect to the shared cluster |

By the end you will have taken a plain Java app all the way from running on your laptop to running in production on AWS Kubernetes.

---

## Prerequisites

Before starting Phase 1, complete the Developer Environment Setup Guide your father shared with you. You will need:

- WSL2 + Ubuntu running on Windows
- Zsh + Oh My Zsh
- Java 17 + Maven
- Docker Desktop with WSL2 integration
- kubectl
- AWS CLI configured with your credentials

---

## Phase 1 — Run Locally with Maven

The app requires a PostgreSQL database to start. The easiest way to get one running locally is with Docker:

```bash
docker run -d \
  --name jobtracker-db \
  -e POSTGRES_DB=jobtracker \
  -e POSTGRES_USER=demo \
  -e POSTGRES_PASSWORD=changeme \
  -p 5432:5432 \
  postgres:16
```

Then run the app:

```bash
mvn spring-boot:run
```

Verify it started:

```bash
curl http://localhost:8080/actuator/health
```

You should see a full health response with `"status": "UP"` at the top level, including database connectivity confirmed under the `db` component.

### Try the API

Create a company:
```bash
curl -s -X POST http://localhost:8080/api/companies \
  -H "Content-Type: application/json" \
  -d '{"name":"Acme Corp","industry":"Tech","website":"https://acme.com","notes":"Dream job"}' \
  | jq .
```

Get all companies:
```bash
curl -s http://localhost:8080/api/companies | jq .
```

---

## REST API Reference

### Companies

| Method | Endpoint | Description |
|--------|----------|-------------|
| GET | `/api/companies` | Get all companies |
| GET | `/api/companies/{id}` | Get company by ID |
| POST | `/api/companies` | Create a company |
| PUT | `/api/companies/{id}` | Update a company |
| DELETE | `/api/companies/{id}` | Delete a company |

### Applications

| Method | Endpoint | Description |
|--------|----------|-------------|
| GET | `/api/applications` | Get all applications |
| GET | `/api/applications/{id}` | Get application by ID |
| GET | `/api/applications/company/{id}` | Get applications by company |
| POST | `/api/applications` | Create an application |
| PUT | `/api/applications/{id}` | Update an application |
| DELETE | `/api/applications/{id}` | Delete an application |

---

## Project Structure

```
src/
├── main/
│   ├── java/com/example/demofirst/
│   │   ├── controller/       # REST endpoints
│   │   ├── service/          # Business logic
│   │   ├── repository/       # Database access (Spring Data JPA)
│   │   ├── entity/           # JPA entities mapped to DB tables
│   │   └── interceptor/      # Request logging
│   └── resources/
│       ├── db/migration/     # Flyway SQL migration scripts
│       └── application.properties
└── test/
    └── java/                 # Unit and integration tests
```

### Key files

- `V1__Create_companies_table.sql` — creates the companies table
- `V2__Create_applications_table.sql` — creates the applications table with FK to companies
- `application.properties` — local development configuration

---

## Database Migrations

This project uses Flyway to manage the database schema. Every time the app starts, Flyway checks which migration scripts have already run and executes any new ones automatically.

You never alter tables manually. To make a schema change, add a new file:

```
src/main/resources/db/migration/V3__Your_description_here.sql
```

Flyway will run it exactly once on the next app startup.

---

## Running Tests

```bash
mvn test
```

Tests use an in-memory H2 database — no PostgreSQL required to run the test suite.

---

## What Comes Next

Once you have the app running locally in Phase 1, your next challenge is Phase 2 — write a `Dockerfile` that packages the app into a container image. No instructions will be given — use what you learn about Docker to figure it out. Your father is available as a resource when you get stuck.
```
