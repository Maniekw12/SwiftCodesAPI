# Internship Home exercise

## 1. Used Technologies

- **Java 17**
    - **Spring Boot** for developing REST APIs.
    - **Spring Data JPA** for database interaction.
    - **Apache Commons CSV** for handling `.csv` files.
    - **Jakarta Validation** for input validation.
    - **Lombok** for reducing boilerplate code (e.g., getters, setters, constructors, etc.).

- **PostgreSQL** as the database (with `postgresql` dependency).

- **Gradle**
    - Used as the build tool for dependency management and task automation.

- **Docker + Docker Compose** for containerization.

- **Tests**
    - Built-in Spring testing framework (`Spring Boot Test`)
    - **Mockito** for mocking dependencies in unit tests.
    - **JUnit Platform Launcher** for advanced test execution.
## 2. Application Set-up and Running

### How to Run the Application with Docker and Docker Compose

#### 1. Ensure Docker and Docker Compose are Installed
Make sure you have Docker and Docker Compose installed on your system. If not, follow the installation instructions for your operating system from the [Docker website](https://www.docker.com/).

#### 2. Clone the Repository
Clone the repository containing the `docker-compose.yml` and `Dockerfile`.

```bash
git clone https://github.com/Maniekw12/SwiftCodesAPI.git
```

#### 3. Start the Containers
Run the following command to start the PostgreSQL and application containers:

> [!WARNING]
> Make sure that ports 8080 and 5432 are not in use. They are used by the application and  database.
```bash
docker compose up --build
```

This command will:
- Build the application using the `Dockerfile`.
- Start the `postgres` container with the specified environment variables.
- Start the `application` container, linking it to the PostgreSQL database.

#### 4. Access the Application
Once the containers are running, you can access the application at:

```
http://localhost:8080
```

The PostgreSQL database will be available at `localhost:5432` with the credentials provided in the `docker-compose.yml` file.
#### 5. Stop the Containers
To stop the containers, press `Ctrl+C` in the terminal where the `docker-compose` command is running or use:

```bash
docker compose down
```
## 3. Endpoints
#### Endpoint 1: Retrieve details of a single SWIFT code

**GET** `/v1/swift-codes/{swiftCode}`

**Response Structure for headquarter SWIFT code:**

```json
{
  "address": "LE PARK PALACE 27 AVENUE DE LA COSTA MONACO, MONACO, 98000",
  "bankName": "SOCIETE GENERALE (FORMERLY SOCIETE DE BANQUE MONACO)",
  "countryISO2": "MC",
  "countryName": "MONACO",
  "swiftCode": "SDBMMCM2XXX",
  "branches": [
    {
      "address": "LE PARK PALACE 27 AVENUE DE LA COSTA MONACO, MONACO, 98000",
      "bankName": "SOCIETE GENERALE (FORMERLY SOCIETE DE BANQUE MONACO)",
      "countryISO2": "MC",
      "countryName": "MONACO",
      "swiftCode": "SDBMMCM2TPS",
      "isHeadquarter": false
    }
  ],
  "isHeadquarter": true
}

```
**Response Structure for branch swift code:**
```json
{
  "address": "LE PARK PALACE 27 AVENUE DE LA COSTA MONACO, MONACO, 98000",
  "bankName": "SOCIETE GENERALE (FORMERLY SOCIETE DE BANQUE MONACO)",
  "countryISO2": "MC",
  "countryName": "MONACO",
  "swiftCode": "SDBMMCM2TPS",
  "isHeadquarter": false
}
```
#### Endpoint 2: Return all SWIFT codes with details for a specific country (both headquarters and branches).
**GET** `/country/{countryCode}}`
**Response Structure**
```json
{
  "countryISO2": "PL",
  "countryName": "POLAND",
  "swiftCodes": [
    {
      "address": "STRZEGOMSKA 42C  WROCLAW, DOLNOSLASKIE, 53-611",
      "bankName": "SANTANDER CONSUMER BANK SPOLKA AKCYJNA",
      "countryISO2": "PL",
      "countryName": "POLAND",
      "swiftCode": "AIPOPLP1XXX",
      "isHeadquarter": true
    },
    {
      "address": "WARSZAWA, MAZOWIECKIE",
      "bankName": "ALIOR BANK SPOLKA AKCYJNA",
      "countryISO2": "PL",
      "countryName": "POLAND",
      "swiftCode": "ALBPPLP1BMW",
      "isHeadquarter": false
    }
  ]
}
```

#### Endpoint 3: Adds new SWIFT code entries to the database for a specific country.

> [!WARNING]
> Assumption: isHeadquarter parameter have to be consistent with swift code.

**POST** ` /v1/swift-codes:`
**Request Structure**
```json

{
  "address": "string",
  "bankName": "string",
  "countryISO2": "string",
  "countryName": "string",
  "isHeadquarter": "boolean",
  "swiftCode": "string"
}
```
**Response Structure:**

- If an object with the specified `swiftCode` already exists in the database:

```json
{
    "message": "Swift code: {swiftCode} already exists."
}
```
- If the `swiftCode` is invalid:

```json
{
    "message": "Invalid Swift Code: {swiftCode}."
}
```

- If isHeadquarter is is successfully added:
```json
{
  "message": "Swift code created: {swiftCode}"
}
```


- If the object is successfully added:
```json
{
    "message": "Swift code created: {swiftCode}"
}
```
#### Endpoint 4: Deletes swift-code data if swiftCode matches the one in the database.
> [!WARNING]
> Assumption: The description of this endpoint was misleading and mentioned about multiple parameters while endpoint definition only contains one. 
> I used the definition with single path variable.

**DELETE** ` /v1/swift-codes/{swift-code}`

**Response Structure:**
- If an object with the specified `swiftCode` exists in the database:
```json
{
    "message": "Swift code deleted: {swiftCode}"
}
```
- If the `swiftCode` is invalid:
```json
{
    "message": "Invalid Swift Code: {swiftCode}."
}
```

- If an object with the specified `swiftCode` is not present in the database:
```json
{
    "message": "Swift code {swiftCode} not found"
}
```



#### CSV Data Parser.
During application startup, the `DataParser` component is automatically executed to initialize the database with Swift code data from a CSV file.
Parses a CSV file (`swift_codes.csv`) to populate a database with Swift code data.

### Features

- **Uses Apache Commons CSV** to read and process the CSV file.
- **Required columns**:
    - `COUNTRY ISO2 CODE`
    - `SWIFT CODE`
    - `BANK NAME`
    - `ADDRESS`
    - `HOME TOWN`
    - `COUNTRY NAME`

### Data Validation

- Skips rows with:
    - Invalid Swift codes.
    - Invalid country ISO2 codes.
    - Invalid country names.
    - Empty bank names.
- If the address is empty but home town is available, the home town is used instead.

### Data Processing

- Converts key fields to uppercase:
    - `Swift codes`
    - `Country ISO2 codes`
    - `Bank names`
    - `Addresses`
    - `Country names`
- Determines if the Swift code belongs to a **headquarters**.

### Persistence

- Saves valid records to the database using:
  ```java
  SwiftCodeRepository.saveAll()
  ```


### Application Tests

#### 1. Controller Tests (`SwiftCodeControllerTest`)
- **Purpose**: Validate the controller layer responsible for handling user requests.
- **Scope**:
    - Ensure correct HTTP responses (e.g., GET, POST requests).
    - Verify HTTP status codes for success and failure cases.
    - Test controller methods invoking services and handling input data.

#### 2. Mapper Tests (`SwiftCodeMapperTest`)
- **Purpose**: Ensure the correct conversion of data between entities and DTOs.
- **Scope**:
    - Validate mapping of input data to entities.
    - Test edge cases with missing or invalid data.
    - Confirm error handling during data conversion.

#### 3. Service Tests (`SwiftCodeServiceTest`)
- **Purpose**: Validate the business logic implemented in the service layer.
- **Scope**:
    - Test methods responsible for processing Swift codes and related logic.
    - Verify interaction with the repository layer.
    - Ensure proper handling of exceptions and edge cases.

#### 4. Application Tests (`RemitlyInternshipApplicationTests`)
- **Purpose**: Verify the overall application context initialization and integration.
- **Scope**:
    - Ensure the Spring context loads successfully.
    - Test application-level configurations and profiles.
    - Confirm the integration of different components (e.g., controllers, services, repositories).
### **Running Specific Test Cases**
To run a specific test class, use the following command:

```bash
./gradlew test --tests SwiftCodeControllerTest
```


