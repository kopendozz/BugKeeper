# BugKeeper - QA Automation Test Bed

## License

This software is licensed under the [Apache License 2.0]
(http://www.apache.org/licenses/LICENSE-2.0.html).

## Overview

This application serves as a hands-on learning ground for exploring best practices in software automation. It's a full-fledged MVC application featuring:

- **A rich user interface:** This complex UI with diverse elements like date pickers, tokenized input fields, dropdown menus, and data tables provides a perfect testing ground for mastering end-to-end UI automation techniques.
- **API interactions:** A well-defined set of CRUD REST APIs allows to explore best practices for API automated testing, including covering automated testing of authentication and authorization mechanisms.
- **Integration with an external system - Google Cloud Platform:** The ability to mock external integrations lets one isolate and test application components independently.
- **Challenging functionalities:** The application includes features that pose unique automation difficulties, such as file upload/download, providing valuable scenarios for tackling complex testing challenges.
- **Beyond Functional Automation:** This test bed goes further by showcasing practical solutions to enhance overall system's testability. This knowledge can be directly applied to establish a more robust and efficient quality assurance process.

## Technologies

* **Spring Boot:** standalone execution and improved configuration.
* **Spring MVC:** well-defined REST API endpoints.
* **Spring JPA:** ORM and repository abstraction.
* **Spring Security:** secure REST API endpoints.
* **Open Feign** integration with **Google Translate** public API.
* **Thymeleaf, Bootstrap & AJAX:** user interface leverages **Thymeleaf** for templating, **Bootstrap** for styling, and **AJAX** for asynchronous communication.

## Requirements

*   Java **21**
*   Gradle **8.7**
*   Docker
*   Google Cloud Platform Account
*   IDE e.g. Intellij IDEA / VSCode / Eclipse

## How to run via IDE

1. Pull the `PostgreSQL` image. Use the `docker pull` command to download the official `PostgreSQL` image from `Docker Hub`. By default, this pulls the latest version:

```bash
docker pull postgres
````
2. Run the container: Execute the `docker run` command to start a `PostgreSQL` container based on the downloaded image, replace `<secure_password>` placeholder with a secure password:

```bash
docker run --name bugkeeper -e POSTGRES_DB=bugkeeper -e POSTGRES_USER=bugkeeper -e POSTGRES_PASSWORD=<secure_password> -d -p 5432:5432 postgres
```

3. Clone the repo:

```bash
git clone https://github.com/kopendozz/BugKeeper.git
``` 
4. Open it in IDE
5. Create a new `Spring Boot` run configuration
6. Set active profile to `local`
7. Set `DB_PASS` environment variable with the value specified for `<secure_password>` placeholder
8. Create an API key at https://console.cloud.google.com/apis/api/translate.googleapis.com/credentials
9. Set `API_KEY` environment variable with the value of the created API key
10. Run configuration

Once booted the application will be available at [`http://localhost:8080`](http://localhost:8080)
