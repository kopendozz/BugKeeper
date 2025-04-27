# BugKeeper - QA Automation Test Bed

![Build](https://github.com/kopendozz/BugKeeper/actions/workflows/ci.yml/badge.svg)
[![Quality Gate](https://sonarcloud.io/api/project_badges/measure?project=kopendozz_BugKeeper&metric=alert_status)](https://sonarcloud.io/summary/new_code?id=kopendozz_BugKeeper)
[![Coverage](https://sonarcloud.io/api/project_badges/measure?project=kopendozz_BugKeeper&metric=coverage)](https://sonarcloud.io/summary/new_code?id=kopendozz_BugKeeper)

---

## Overview

**BugKeeper** is a fully decoupled, token-secured, API-first bug tracking platform designed as a modern test and development playground.

---

### Key Features

- **Complex UI** – Includes rich elements like:
    - Date pickers
    - Tokenized inputs
    - Dropdowns
    - File upload/download
    - Data tables
- **API Coverage** – RESTful CRUD API with:
    - Authenticated endpoints
    - Testable authorization workflows
- **GCP Integration (Mocked)** – External system integration via Google Translate API
- **Designed for Testing Challenges** – File handling, async behaviors, edge-case UI flows
- **Beyond Functional Testing** – Focuses on system testability, robustness, and test design

---

## Technologies

| Layer        | Technology                                                                 |
|--------------|----------------------------------------------------------------------------|
| Backend      | Spring Boot 3.x, Spring Web (REST), Spring Security (JWT), Spring Data JPA |
| DTO Mapping  | MapStruct                                                                  |
| Database     | PostgreSQL (prod), H2 (test), Flyway (migrations)                          |
| Integration  | OpenFeign, Google Translate API                                            |
| Security     | JWT (stateless), BCrypt, Custom Filter, SecurityContext                    |
| Testing      | JUnit 5, Mockito, AssertJ, MockMvc, H2 (in-memory), JaCoCo                 |
| Code Quality | SonarCloud (rules, coverage, exclusions)                                   |
| CI/CD        | GitHub Actions                                                             |

---

## Requirements

- Java 21
- Gradle 8.7
- Docker
- Google Cloud Platform account
- IDE (IntelliJ IDEA / VSCode / Eclipse)

---

## How to run via IDE

1. **Start PostgreSQL via Docker**

```bash
docker pull postgres
````

```bash
docker run --name bugkeeper -e POSTGRES_DB=bugkeeper -e POSTGRES_USER=bugkeeper -e POSTGRES_PASSWORD=<secure_password> -d -p 5432:5432 postgres
```

2. **Clone and Open the Project**

```bash
git clone https://github.com/kopendozz/BugKeeper.git
cd BugKeeper
````

3. **Configure Environment**

   In your IDE:

    - Create a new Spring Boot run configuration
    - Set the **active profile** to `local`
    - Set the following environment variables:
        - `DB_PASS` = `<secure_password>`
        - `API_KEY` = your Google Translate API key
          from [GCP Console](https://console.cloud.google.com/apis/api/translate.googleapis.com/credentials)

4. **Run the Application**

   The application will be available at: [http://localhost:8080](http://localhost:8080)

---

## Continuous Integration

This project includes a full CI pipeline:

- Build, test, generate coverage report;
- Code analysis with SonarCloud;
- Build fails if test coverage is below 80%;
- Email notification on build failure;
