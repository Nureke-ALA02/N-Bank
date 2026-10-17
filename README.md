# N-Bank

A simple online banking REST API built with Java and Spring Boot. The application allows users to register, log in, create bank accounts, check balances, deposit money, and withdraw money.

## Technologies

- **Java 22**
- **Spring Boot 4.1.1**
- **Maven**
- **Spring Web** — REST API endpoints
- **Spring Data JPA** — database access
- **Hibernate** — ORM
- **PostgreSQL** — relational database
- **JUnit 5 and Mockito** — testing
- **IntelliJ IDEA** — development environment
- **Postman** — API testing

## Project Description

N-Bank is an educational banking application that provides basic banking operations through a REST API.

Users can:

- Register with personal information.
- Log in using their email and password.
- Create a bank account.
- Check their account balance.
- Deposit money.
- Withdraw money.
- Validate email addresses and prevent duplicate emails and IINs.

The application uses PostgreSQL to store data. Spring Data JPA and Hibernate are used to interact with the database.

## Project Structure

```text
src
├── main
│   ├── java/com/bank
│   │   ├── NBankApplication.java
│   │   ├── controller
│   │   │   ├── UserController.java
│   │   │   └── AccountController.java
│   │   ├── model
│   │   │   ├── User.java
│   │   │   ├── Account.java
│   │   │   └── Transaction.java
│   │   ├── repository
│   │   ├── service
│   │   │   ├── UserService.java
│   │   │   └── AccountService.java
│   │   └── ...
│   └── resources
│       └── application.properties
└── test
    └── java/com/bank
        ├── NBankApplicationTests.java
        ├── controller
        │   ├── AccountControllerTest.java
        │   └── UserControllerTest.java
        └── service
            ├── AccountServiceTest.java
            └── UserServiceTest.java
```

The exact package contents may vary as the project develops.

## Prerequisites

Before running the application, install:

- Java 22 or a compatible JDK configured for the project.
- Maven.
- PostgreSQL.
- IntelliJ IDEA or another Java IDE.
- Postman (optional, for testing API endpoints).

## Database Configuration

1. Start PostgreSQL.
2. Create a database named `N-Bank`.
3. Open `src/main/resources/application.properties`.
4. Configure the database URL, username, and password.

Example configuration:

```properties
spring.datasource.url=jdbc:postgresql://localhost:5432/N-Bank
spring.datasource.username=YOUR_USERNAME
spring.datasource.password=YOUR_PASSWORD

spring.jpa.hibernate.ddl-auto=update
spring.jpa.open-in-view=false
```

Replace `YOUR_USERNAME` and `YOUR_PASSWORD` with your PostgreSQL credentials. Keep real credentials out of public repositories.

## How to Run

### 1. Clone the repository

```bash
git clone <repository-url>
cd N-Bank
```

Replace `<repository-url>` with the actual Git repository URL.

### 2. Open the project

Open the project folder in IntelliJ IDEA and allow Maven to import the dependencies.

### 3. Start PostgreSQL

Make sure PostgreSQL is running and the database configuration is correct.

### 4. Run the application

Run `NBankApplication.java` from IntelliJ IDEA, or use Maven:

```bash
mvn spring-boot:run
```

The application starts at:

`http://localhost:8080`

## API Endpoints

All endpoints can be tested using Postman.

### User Registration

**Method:** `POST`

```text
http://localhost:8080/users/register
```

Parameters:

- `iin`
- `firstName`
- `lastName`
- `birthDate`
- `email`
- `password`

The birth date must use the format `YYYY-MM-DD`, for example `2004-05-02`.

The email must contain `@`, and the email and IIN must be unique.

### User Login

**Method:** `POST`

```text
http://localhost:8080/users/login
```

Parameters:

- `email`
- `password`

### Create a Bank Account

**Method:** `POST`

```text
http://localhost:8080/accounts/create/1
```

The number `1` is the user ID. The user must exist before an account can be created.

### Check Account Balance

**Method:** `GET`

```text
http://localhost:8080/accounts/1/balance
```

Returns the balance of account `1`.

### Deposit Money

**Method:** `POST`

```text
http://localhost:8080/accounts/1/deposit?amount=5000
```

Example response:

```text
Счёт пополнен на 5000.0 ₸
```

The deposit amount must be greater than zero.

### Withdraw Money

**Method:** `POST`

```text
http://localhost:8080/accounts/1/withdraw?amount=1000
```

Example response:

```text
Снято 1000.0 ₸
```

The withdrawal amount must be greater than zero and cannot exceed the available balance.

## Example Workflow

1. Register a user using `POST /users/register`.
2. Create an account using `POST /accounts/create/1`.
3. Check the balance using `GET /accounts/1/balance`.
4. Deposit 5000 using `POST /accounts/1/deposit?amount=5000`.
5. Check the balance again.
6. Withdraw 1000 using `POST /accounts/1/withdraw?amount=1000`.
7. Check the final balance.

If the initial balance is zero and both operations succeed, the final balance will be `4000`.

## Validation

The application includes basic validation rules:

- Email must contain `@`.
- Email must be unique.
- IIN must be unique.
- Deposit amounts must be positive.
- Withdrawal amounts must be positive.
- Withdrawals cannot exceed the account balance.
- A user must exist before a bank account can be created.
- Invalid login credentials are rejected.

## Testing

The project includes tests for controllers, services, and application startup.

Run all tests with:

```bash
mvn test
```

To build the project and run its tests:

```bash
mvn verify
```

The test suite uses JUnit 5 and Mockito. All tests should pass before considering the build successful.

## Future Improvements

- Password hashing and secure credential storage.
- Authentication and authorization.
- DTOs and request validation.
- Improved error handling and consistent HTTP status codes.
- Transaction history.
- Integration tests with a dedicated test database.
- Frontend application.

## Author

N-Bank is an educational Java and Spring Boot project.
