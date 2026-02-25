# eCabs Assessment — QA Automation Framework
[![QA Automation CI](https://github.com/qa-luanalves/qa-automation-assessment-ecabs/actions/workflows/ci.yml/badge.svg)](https://github.com/qa-luanalves/qa-automation-assessment-ecabs/actions/workflows/ci.yml)

A Maven-based test automation framework covering both API and Web UI testing.

## Overview

This project contains an automated test suite for:

-  Weather API (OpenWeather)
-  Web UI Form Automation (DemoQA)

Built using:

- Java 17
- Maven
- Cucumber (BDD)
- Selenium WebDriver
- REST Assured
- WebDriverManager
- GitHub Actions (CI/CD)

---

## Project Structure

```
.github/
└── workflows/
    └── ci.yml                   # GitHub Actions CI pipeline
src/test/
├── java/com/ecabs/
│   ├── api/
│   │   ├── config/              # ApiConfig (base URL, API key)
│   │   └── stepdefinitions/     # WeatherSteps, SecuritySteps, IntegrationSteps
│   ├── web/
│   │   ├── hooks/               # WebHooks (browser setup / teardown)
│   │   ├── pages/               # ContactPage (Page Object)
│   │   └── stepdefinitions/     # ContactSteps
│   ├── context/                 # SharedResponse (shared state between steps)
│   └── runners/
│       ├── ApiTestRunner.java
│       └── WebTestRunner.java
└── resources/features/
    ├── api/
    │   ├── weather.feature      # Core weather endpoint scenarios
    │   ├── security.feature     # Auth / injection scenarios
    │   └── integration.feature  # Cross-endpoint consistency check
    └── web/
        └── contact.feature      # DemoQA Practice Form scenarios
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

The OpenWeather API key must be provided as an environment variable.

- Windows: `setx OPEN_WEATHER_KEY "your_api_key"`
- Mac / Linux: `export OPEN_WEATHER_KEY="your_api_key"`
- In CI, the key is stored securely as a GitHub Secret: **OPEN_WEATHER_KEY**
  
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

### API Tests
```bash
mvn clean test -Dtest=ApiTestRunner -DapiKey=YOUR_KEY_HERE
```

### Run Web Tests - Firefox (Headless)
```bash
mvn clean test -Dtest=WebTestRunner -Dbrowser=firefox -Dheadless=true
```

### Run Web Tests - Chrome (Headless)
```bash
mvn clean test -Dtest=WebTestRunner -Dbrowser=chrome -Dheadless=true
```

---

## Test Reports

After a test run, reports are written to the `target/` directory:

| Report                | Path                               |
|-----------------------|------------------------------------|
| API HTML report       | `target/cucumber-api-report.html`  |
| Web HTML (Chrome)     | `target/cucumber-web-chrome.html`  |
| Web HTML (Firefox)    | `target/cucumber-web-firefox.html` |
| Surefire XML          | `target/surefire-reports/`         |

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

## CI/CD Pipeline

The project uses **GitHub Actions** (`.github/workflows/ci.yml`) for continuous integration.

**Triggers:** push or pull request to `main`, or manual dispatch (`workflow_dispatch`)

**Environment:** Ubuntu latest · Java 17 (Temurin) · Maven dependency cache (keyed by `pom.xml`)

**Pipeline steps:**

| Step | Command | Notes |
|------|---------|-------|
| Run API Tests | `mvn clean test -Dtest=ApiTestRunner` | Uses `OPEN_WEATHER_KEY` GitHub Secret |
| Run Web Tests (Chrome) | `mvn test -Dtest=WebTestRunner -Dbrowser=chrome -Dheadless=true` | Runs even if API tests fail (`if: always()`) |
| Run Web Tests (Firefox) | `mvn test -Dtest=WebTestRunner -Dbrowser=firefox -Dheadless=true` | Runs even if previous steps fail |
| Upload Reports | `actions/upload-artifact@v4` | Artifact name: `cucumber-reports` |

**Artifacts published:**
- `target/cucumber-api-report.html`
- `target/cucumber-web-chrome.html`
- `target/cucumber-web-firefox.html`

---

## Assumptions & Limitations

- Web test data (name, email, etc.) is hard-coded in `ContactPage.java`.
- The web suite targets **DemoQA** (`https://demoqa.com/automation-practice-form`).
