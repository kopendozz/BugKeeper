# BugKeeper - QA Automation Test Bed

## License

This software is licensed under the [Apache License 2.0]
(http://www.apache.org/licenses/LICENSE-2.0.html).

## Requirements

*   Java **21**
*   Gradle **8.7**
*   Docker
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
7. Set `DB_PASS` environment variable to value set for `<secure_password>` placeholder
8. Run configuration

Once booted the application will be available at [`http://localhost:8080`](http://localhost:8080)

## Functional Features

* **CRUD Operations:** Test application logic and data manipulation (Create, Read, Update, Delete) through well-defined REST API endpoints built with **Spring MVC**.
* **Authentication & Authorization:** Secure REST API endpoints are implemented using **Spring Security**, allowing one to test the authentication and authorization functionalities.
* **Google Translate** Integration implemented via **OpenFeign**: to showcase testing components in isolation by substituting the real integrating with mocks using application profiles.
* **Thymeleaf, Bootstrap & AJAX UI:** The user interface leverages **Thymeleaf** for templating, **Bootstrap** for styling, and **AJAX** for asynchronous communication, providing a test bed for testing of UI interactions.
* **File Upload Testing:** Explore best practices for handling multi-part file uploads and downloads during testing, simulating real-world upload scenarios.

## Testability Features

Building applications with testability in mind is crucial for a robust testing strategy. This project exemplifies this approach by implementing several key features that enhance testability.  We'll explore these features and their implementation details below.

### App versioning

The application version should be readily available for users to see, both within the user interface (UI)  - such as the footer - and through an API. This transparency is crucial for several reasons. First, it ensures that testers and QA engineers are verifying the correct build, containing the intended changes and fixes. Second, including the app version in bug reports is a best practice. This information is valuable not only for identifying release candidates (RCs) but also for pinpointing when an issue first arose in the development process.

### Documented APIs

Thorough API documentation, ideally using a tool like Swagger, is a best practice for several reasons.

* **Clarity and Accessibility:** It clearly communicates the available APIs and how to interact with them. This is valuable for both external systems consuming your data and internal teams automating tests.
* **Enhanced Security:** It helps mitigate **2** of **OWASP top 10** security risks. 
  * **API2: Broken Authentication:** If an API is undocumented, it might not be properly secured with authentication mechanisms, making it vulnerable to unauthorized access. 
  * **API9: Improper Inventory Management:** Undocumented APIs are essentially "forgotten" APIs. This lack of proper management can lead to them remaining unpatched and exposed to vulnerabilities.
