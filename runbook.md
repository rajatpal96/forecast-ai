# Runbook: Sunrise & Sunset Forecast Application

## Purpose
This runbook provides operational guidance for deploying, running, and troubleshooting the Sunrise & Sunset Forecast Application.

## Prerequisites
- Java 17+
- Gradle
- Azure OpenAI credentials

## Configuration
1. Set Azure OpenAI credentials in `src/main/resources/application.properties`:
   - `langchain4j.azure.openai.endpoint`
   - `langchain4j.azure.openai.key`
   - `langchain4j.azure.openai.deployment`
2. (Optional) Configure caching and logging as needed.

## Build & Deploy
- **Build:**
  ```bash
  ./gradlew build
  ```
- **Run:**
  ```bash
  ./gradlew bootRun
  ```
- **Test:**
  ```bash
  ./gradlew test
  ```

## API Operations
- **Endpoint:** `GET /api/sun-forecast?city={city}`
- **Expected Response:** JSON with city, sunrise, sunset, and enhanced message.

## Troubleshooting
- **Startup Failure:**
  - Check Azure OpenAI credentials and network connectivity.
  - Ensure all required beans are defined (see error logs for missing beans).
- **API Errors:**
  - Validate city input.
  - Check external API (Open-Meteo, geocoding) availability.
- **AI Response Issues:**
  - Verify LangChain4j and Azure OpenAI configuration.

## Maintenance
- Update dependencies regularly.
- Monitor API usage and error logs.
- Review unit test coverage after changes.

## Support
Ping Me directly or email

