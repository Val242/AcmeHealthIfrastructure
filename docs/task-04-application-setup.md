# Task 4 Documentation — Spring Boot Account Service Deployment

## Overview

The objective of Task 4 was to develop and deploy a Spring Boot microservice architecture with:

* Two application servers (`app01` and `app02`)
* One database server (`db01`)
* MariaDB as the database system
* Spring Boot as the application framework
* systemd for application process management

The final architecture separates the application layer from the database layer, similar to a production environment.

---

# Architecture

```
                 +----------------+
                 |     db01       |
                 |    MariaDB     |
                 |    :3306       |
                 +----------------+
                     ^        ^
                     |        |
                     |        |
        +------------+        +------------+
        |                                 |
+---------------+                 +---------------+
|     app01     |                 |     app02     |
| Spring Boot  |                 | Spring Boot  |
|    :8080     |                 |    :8080     |
+---------------+                 +---------------+

```

---

# Application Development

## Project

Spring Boot project:

```
account-service
```

Technology stack:

* Java 23 (development)
* Java 17 LTS (server runtime)
* Spring Boot 4.1
* Spring Data JPA
* Hibernate
* MariaDB Connector
* Maven

---

# Database Setup

Database server:

```
db01
```

Database:

```
accounts
```

Database user:

```
appuser
```

The application connects using:

```
jdbc:mariadb://db01/accounts
```

Connection was verified successfully from both application servers.

---

# Data Model

Main entity:

```
Account
```

Fields:

* id
* accountNumber
* accountName
* accountType
* balance
* status
* createdAt
* updatedAt

Example record:

```
ACC-10001
Valentine Ebong
SAVINGS
5000.00
ACTIVE
```

---

# API Implementation

Implemented account endpoints:

## Get all accounts

```
GET /accounts
```

Example:

```bash
curl http://localhost:8080/accounts
```

Response:

```json
[
 {
  "accountName":"Valentine Ebong",
  "accountNumber":"ACC-10001",
  "accountType":"SAVINGS",
  "balance":5000.00,
  "status":"ACTIVE"
 }
]
```

---

# Building the Application

The application was packaged using Maven:

```bash
mvn clean package
```

Generated artifact:

```
target/account-service-0.0.1-SNAPSHOT.jar
```

Build result:

```
BUILD SUCCESS
```

---

# Deployment

The JAR file was transferred to the application servers.

Example:

```bash
scp account-service-0.0.1-SNAPSHOT.jar devops@app01:/home/devops/account-service.jar
```

and:

```bash
scp account-service-0.0.1-SNAPSHOT.jar devops@app02:/home/devops/account-service.jar
```

---

# Running the Application

Initial test:

```bash
java -jar account-service.jar
```

The application successfully started:

```
Tomcat started on port 8080
```

Database connection:

```
HikariPool-1 - Start completed
```

---

# Systemd Service Configuration

The Spring Boot application was converted into a Linux service.

Service file:

```
/etc/systemd/system/account-service.service
```

Operations performed:

Reload systemd:

```bash
sudo systemctl daemon-reload
```

Start service:

```bash
sudo systemctl start account-service
```

Enable on boot:

```bash
sudo systemctl enable account-service
```

The application now:

* Starts automatically after VM reboot
* Runs as a managed Linux service
* Can be restarted using systemctl

---

# Network Verification

Connectivity tests completed successfully.

## app02 → app01

Command:

```bash
curl http://app01:8080/accounts
```

Result:

Successful response from app01.

---

## app01/app02 → db01

Verified through Spring Boot startup logs:

```
Database JDBC URL:
jdbc:mariadb://db01/accounts
```

---

# Current Status

Task 4 completion:

✅ Spring Boot service developed
✅ Database server configured
✅ Application servers configured
✅ JAR deployment completed
✅ systemd service configured
✅ Application tested on app01
✅ Application tested on app02
✅ Inter-server communication verified

---

# Remaining Infrastructure Tasks

Future steps:

1. Configure `web01` with Nginx reverse proxy

2. Load balance traffic between:

   * app01
   * app02

3. Configure RabbitMQ on `rmq01`

4. Configure firewall rules

5. Automate deployment using CI/CD

---

## Conclusion

The Account Service has successfully been deployed in a distributed server environment. The setup now represents a basic production-style architecture where the application layer and database layer are separated and communicate through a private network.
