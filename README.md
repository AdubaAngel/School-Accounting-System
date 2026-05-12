# MarvAngel Kids School Accounting System

A production web application for private school fee management, student records, and financial reporting – built with Spring Boot and PostgreSQL.

📘 [Visit MarvAngel Kids on Facebook](https://www.facebook.com/marvangelkids/)

---

## Demo Videos

Watch these short demos to see the system in action:

| Topic | Watch on YouTube | Description |
| :---- | :---- | :---- |
| **Staff Management** | [▶️ Watch Demo](https://youtu.be/cP2k9BZBy-0) | Adding new staff users (Accountant & Auditor roles) |
| **Parent Management** | [▶️ Watch Demo](https://youtu.be/PsC_FMVjUPU) | Adding and managing parents/guardians |
| **Student Management** | [▶️ Watch Demo](https://youtu.be/doJVGQbJoTM) | Adding students, assigning classes, and linking to parents |

---

## Screenshots

### Owner Dashboard

![Owner Dashboard](/images/owner-dashboard.png)

### Accountant Dashboard

![Accountant Dashboard](/images/accounting-dashboard.png)

### Auditor Dashboard
![Auditor Dashboard](/images/auditor-dashboard.png)

---

## Overview

This system manages student enrollment, fee collection, payment tracking, and financial reporting for **MarvAngel Kids School**. It serves three user roles: **Owner**, **Accountant**, and **Auditor** – each with specific permissions.

Built based on real requirements gathered directly from the school owner.

---

## Features

### Student Management
- Add students and assign to classes (Preschool 1–2, KG 1–2, Grades 1–6)
- Link multiple students to a parent/guardian
- View students filtered by class level
- Edit and delete student records

### Parent Management
- Add parent/guardian contact information (name, email, phone)
- Link multiple students to the same parent
- Edit and delete parent records (with student safety check)

### User Management (Owner Only)
- Add new staff users (ACCOUNTANT or AUDITOR roles)
- View all staff users
- Edit user details (name, role)
- Deactivate and reactivate users
- Owner account protection (cannot be deactivated or deleted)

### Payment Processing (Coming Soon)
- Record bank transfer payments (3 school bank accounts)
- Support partial payments (installments)
- Automatic balance calculation
- Overpayments become credits applied to future terms

### Receipts (Coming Soon)
- PDF receipts generated automatically when payment completes
- Receipts emailed to parents
- Owner's digital signature on every receipt

### Security Features
- BCrypt password hashing
- Role-based access control (OWNER, ACCOUNTANT, AUDITOR)
- Method-level security with `@PreAuthorize`
- Session management with timeout
- CSRF protection enabled
- HTTP-only cookies

---

## Technology Stack

| Layer | Technology |
| :----- | :---------- |
| Language | Java 17 |
| Framework | Spring Boot 3 |
| Security | Spring Security (role-based) |
| Database | PostgreSQL |
| ORM | Spring Data JPA / Hibernate |
| Templates | Thymeleaf |
| Build Tool | Maven |

---

## User Roles

| Role | Permissions |
| :---- | :----------- |
| **Owner** | Full access – manage users, fee structures, students, parents, view all data |
| **Accountant** | Record payments, view reports, view parents and students |
| **Auditor** | Read-only access to reports, parents, and students |

---

## Setup Instructions

### Prerequisites
- Java 17
- PostgreSQL (port 3007)
- Maven

### Database Setup

```sql
CREATE DATABASE school_accounting;
-- Then run the schema.sql file
```

### Application Properties

```properties
spring.datasource.url=jdbc:postgresql://localhost:3007/school_accounting
spring.datasource.username=postgres
spring.datasource.password=your_password
spring.jpa.hibernate.ddl-auto=validate
server.port=8081
```

### Run the Application

```bash
./mvnw spring-boot:run
```

Access the application at: http://localhost:8081

### Default Test Credentials

| Username | Password | Role |
| :-------- | :-------- | :---- |
| owner1 | MarvAngel540 | OWNER |
| accountant1 | MarvAngel540 | ACCOUNTANT |
| auditor1 | MarvAngel540 | AUDITOR |

### Key Business Rules
- Fees are fixed per class level – same amount for all students in that class
- Three terms per academic year
- No cash payments – bank transfer only
- No refunds – overpayments become credits for next term
- Receipts issued only when payment is completed
- Daily closure locks all transactions for that day
- Every edit is tracked in audit logs

### Project Status

#### Completed
- Requirements gathering with school owner
- Database schema design and implementation
- User authentication with BCrypt
- Role-based security (OWNER, ACCOUNTANT, AUDITOR)
- Parent Management (Full CRUD)
- Student Management (Full CRUD)
- User Management (Full CRUD with deactivation)
- Dashboard with role-based menu
- Fee Structure entity and repository

#### In Progress
- Fee Structure web interface (add, edit, list)
- Payment Recording
- Receipt Generation (PDF + Email)
- Daily Closure
- Audit Logging

### License
Built for MarvAngel Kids School production use.

### Author
Custom solution for private school accounting system.

📘 Visit MarvAngel Kids on Facebook


