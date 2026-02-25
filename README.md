# eCabs Assessment — QA Automation Framework

A Maven-based test automation framework covering both API and Web UI testing.

| Layer | Technology |
|-------|-----------|
| Language | Java 17 |
| Build | Maven 3.x |
| BDD | Cucumber 7.14.0 |
| API testing | REST Assured 5.3.0 |
| Web UI testing | Selenium 4.18.1 |
| Runner | JUnit 4 |
| Driver management | WebDriverManager |

---

## Project Structure

```
src/test/
├── java/com/ecabs/
│   ├── api/
│   │   ├── config/          # ApiConfig (base URL, API key)
│   │   └── stepdefinitions/ # WeatherSteps, SecuritySteps, IntegrationSteps
│   ├── web/
│   │   ├── hooks/           # WebHooks (browser setup / teardown)
│   │   ├── pages/           # ContactPage (Page Object)
│   │   └── stepdefinitions/ # ContactSteps
│   ├── context/             # SharedResponse (shared state between steps)
│   └── runners/
│       ├── ApiRunnerTest.java
│       └── WebRunnerTest.java
└── resources/features/
    ├── api/
    │   ├── weather.feature     # Core weather endpoint scenarios
    │   ├── security.feature    # Auth / injection scenarios
    │   └── integration.feature # Cross-endpoint consistency check
    └── web/
        └── contact.feature     # DemoQA Practice Form scenarios
```

---

## Prerequisites

- **Java 17** JDK installed and `JAVA_HOME` set
- **Maven 3.x** installed and on `PATH`
- **Google Chrome** or **Firefox** browser installed
- Active internet connection (OpenWeather API calls + DemoQA site)

> WebDriverManager automatically downloads the matching browser driver — no manual driver setup required.

---

## Configuration

### API

| System property | Default | Description |
|-----------------|---------|-------------|
| `baseUrl` | `https://api.openweathermap.org/data/2.5` | OpenWeather API base URL |

### Web

| System property | Default | Description |
|-----------------|---------|-------------|
| `browser` | `chrome` | Browser to use (`chrome` or `firefox`) |
| `headless` | `false` | Run browser in headless mode |

---

## Running Tests

### All tests
```bash
mvn clean test
```

### API tests only
```bash
mvn clean test -Dtest=ApiRunnerTest
```

### Web tests only
```bash
mvn clean test -Dtest=WebRunnerTest
```

### Custom API key
```bash
mvn clean test -Dtest=ApiRunnerTest -DapiKey=YOUR_KEY_HERE
```

### Firefox, headless
```bash
mvn clean test -Dtest=WebRunnerTest -Dbrowser=firefox -Dheadless=true
```

### Chrome, headless
```bash
mvn clean test -Dtest=WebRunnerTest -Dheadless=true
```

---

## Test Reports

After a test run, reports are written to the `target/` directory:

| Report | Path |
|--------|------|
| API HTML report | `target/cucumber-api-report.html` |
| Web HTML report | `target/cucumber-web-report.html` |
| Surefire XML | `target/surefire-reports/` |

Open the `.html` files in any browser to view a full Cucumber scenario report.

---

## Test Suites

### API — OpenWeather (`features/api/`)

**weather.feature**
- Get current weather for a valid city (Malta) — expects HTTP 200
- Get weather for an invalid city — expects HTTP 404 with `"city not found"`
- Retrieve 5-day forecast — expects non-empty list
- Boundary-value city names (single char, empty, very long, special chars)

**security.feature**
- Invalid API key — expects HTTP 401
- Missing API key — expects HTTP 401
- SQL injection in city name — expects HTTP 404 (safe handling)

**integration.feature**
- Cross-endpoint check: today's forecast temperature must be within ±3 °C of the current weather temperature

### Web UI — DemoQA Practice Form (`features/web/`)

**contact.feature**
- Submit the form with valid data and verify the confirmation modal
- Attempt submission without mandatory fields and verify required-field indicators

---

## Assumptions & Limitations

- Web test data (name, email, etc.) is hard-coded in `ContactPage.java`.
- The web suite targets **DemoQA** (`https://demoqa.com/automation-practice-form`).
