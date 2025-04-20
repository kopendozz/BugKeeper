# BugKeeper - QA Automation Test Bed

![Build](https://github.com/kopendozz/BugKeeper/actions/workflows/ci.yml/badge.svg)
[![Quality Gate](https://sonarcloud.io/api/project_badges/measure?project=kopendozz_BugKeeper&metric=alert_status)](https://sonarcloud.io/summary/new_code?id=kopendozz_BugKeeper)

---

## License

This software is licensed under the [Apache License 2.0]
(http://www.apache.org/licenses/LICENSE-2.0.html).

---

## Overview

**BugKeeper** is a full-fledged MVC application designed as a **hands-on QA automation test bed**. It’s intentionally loaded with real-world challenges to help developers and testers explore best practices across UI, API, and integration automation.

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

| Layer       | Technology                                           |
|-------------|------------------------------------------------------|
| Backend     | Spring Boot, Spring MVC, Spring JPA, Spring Security |
| UI          | Thymeleaf, Bootstrap, AJAX                           |
| Integration | OpenFeign + Google Translate API                     |
| Testing     | JUnit, JaCoCo                                        |
| Analysis    | SonarCloud                                           |
| CI/CD       | GitHub Actions                                       |
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
        - `API_KEY` = your Google Translate API key from [GCP Console](https://console.cloud.google.com/apis/api/translate.googleapis.com/credentials)

4. **Run the Application**

   The application will be available at: [http://localhost:8080](http://localhost:8080)

---

## Continuous Integration

This project includes a full CI pipeline:

- Unit testing
- Code coverage via JaCoCo
- Static analysis via SonarCloud
- Quality gate badge on PRs
- GitHub Actions-based pipeline with caching

---

## Status

Actively maintained. Designed for learning, experimentation, and proving your QA/dev skills under real-world testability conditions.
