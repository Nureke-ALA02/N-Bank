# N-Bank

A simple online banking application built with Java and Spring Boot.

## Technologies

* Java 22
* Spring Boot 4.1.1
* Maven
* Spring Web
* REST API
* IntelliJ IDEA
* Postman

## Project Description

N-Bank is a simple banking application that allows users to:

* Register an account
* Log in
* Create a bank account
* Check balance
* Deposit money
* Withdraw money
* Validate email during registration

The application currently stores data in memory using `ArrayList`.

> Data will be deleted when the application is restarted.

## Project Structure

```text
src/main/java/com/bank
├── NBankApplication.java
│
├── controller
│   ├── UserController.java
│   └── AccountController.java
│
├── model
│   ├── User.java
│   ├── Account.java
│   └── Transaction.java
│
├── service
│   ├── UserService.java
│   └── AccountService.java
│
└── storage
    ├── UserStorage.java
    └── AccountStorage.java
```

## How to Run

### 1. Clone the repository

```bash
git clone <repository-url>
```

### 2. Open the project

Open the project in IntelliJ IDEA.

### 3. Run the application

Run:

```text
NBankApplication.java
```

The application starts on:

```text
http://localhost:8080
```

## API Endpoints

### User Registration

**POST**

```text
/users/register
```

Full URL:

```text
http://localhost:8080/users/register
```
The email must contain `@`.

---

### User Login

**POST**

```text
/users/login
```

Full URL:

```text
http://localhost:8080/users/login
```
---

### Create Bank Account

**POST**

```text
/accounts/create/{userId}
```

Example:

```text
http://localhost:8080/accounts/create/1
```

Here `1` is the user ID.

---

### Check Balance

**GET**

```text
/accounts/{accountId}/balance
```

Example:

```text
http://localhost:8080/accounts/1/balance
```

Response:

```text
5000.0
```

---

### Deposit Money

**POST**

```text
/accounts/{accountId}/deposit?amount={amount}
```

Example:

```text
http://localhost:8080/accounts/1/deposit?amount=5000
```

Response:

```text
Счёт пополнен на 5000.0 ₸
```

---

### Withdraw Money

**POST**

```text
/accounts/{accountId}/withdraw?amount={amount}
```

Example:

```text
http://localhost:8080/accounts/1/withdraw?amount=1000
```

Response:

```text
Снято 1000.0 ₸
```

The user cannot withdraw more money than the current balance.

## Example Workflow

First register a user:

```text
POST /users/register
```

Then create an account:

```text
POST /accounts/create/1
```

Check the balance:

```text
GET /accounts/1/balance
```

Deposit money:

```text
POST /accounts/1/deposit?amount=5000
```

Check the balance again:

```text
GET /accounts/1/balance
```

Result:

```text
5000.0
```

Then withdraw money:

```text
POST /accounts/1/withdraw?amount=1000
```

New balance:

```text
4000.0
```

## Validation

The application performs basic validation:

* Email must contain `@`
* Email must be unique
* IIN must be unique
* Deposit amount must be greater than `0`
* Withdrawal amount must be greater than `0`
* Withdrawal amount cannot be greater than the account balance
* User must exist before creating an account

## Future Improvements

Possible future improvements:

* PostgreSQL database
* Spring Data JPA
* Password hashing
* Better validation
* DTOs
* Authentication and authorization
* Transaction history
* Global error handling
* Frontend application

## Author
N-Bank — Java Spring Boot educational project.
