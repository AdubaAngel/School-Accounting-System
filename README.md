# School Accounting & Student Record System

A production web application for private school fee management, student records, and financial reporting – built with Spring Boot and PostgreSQL.

## Overview

This system manages student enrollment, fee collection, payment tracking, and financial reporting for a growing private school. It serves three user roles: **Owner**, **Accountant**, and **Auditor** – each with specific permissions.

Built based on real requirements gathered directly from the school owner, not theoretical assumptions.

## Features

### Student Management

- Add students and assign to classes (Preschool 1–2, KG 1–2, Grades 1–6)
- Link multiple students to a parent/guardian
- Track enrollment status (active/archived)
- View students filtered by class level

### Parent Management

- Add parent/guardian contact information (name, email, phone)
- Link multiple students to the same parent
- Parent email used for receipt delivery

### Payment Processing (Planned)

- Record bank transfer payments (no cash – 3 school bank accounts)
- Support partial payments (installments)
- Automatic balance calculation
- Overpayments become credits applied to future terms (no refunds)

### Receipts (Planned)

- PDF receipts generated automatically when payment completes
- Receipts emailed to parents
- Receipts can be resent (identical to original)
- Owner's digital signature appears on every receipt

### Reporting & Compliance (Planned)

- Daily closure – Accountant locks each day's transactions
- After closure: no edits or deletions allowed
- Daily closure report: total collected, breakdown by bank account and class
- Full audit log – every change tracked (who, what, when)
- Owner can view all edits; Auditor has read-only access

### User Roles

| Role | Permissions |
| ------ | ------------- |
| **Owner** | Full access – manage users, view all data, see edit history |
| **Accountant** | Record payments, issue receipts, close daily transactions |
| **Auditor** | Read-only access to all data and reports |

## Technology Stack

| Layer | Technology |
| ----- | ---------- |
| Language | Java 17 |
| Framework | Spring Boot 3 |
| Security | Spring Security (role-based) |
| Database | PostgreSQL 18 |
| ORM | Spring Data JPA / Hibernate |
| Templates | Thymeleaf |
| Build Tool | Maven |
| IDE | VS Code |

## Database Schema

The system uses 9 tables:

| Table | Purpose |
| ----- | ------- |
| `users` | User authentication and roles |
| `parents` | Guardian contact information |
| `students` | Student records linked to parents |
| `fee_structure` | Fee amounts per class, term, academic year |
| `payments` | Individual payment transactions |
| `payment_allocations` | Tracks installments and balances |
| `credit_balances` | Overpayments applied to future terms |
| `daily_closures` | Locked daily transaction reports |
| `audit_logs` | Complete change history |

## Project Status

### Completed

- [x] Requirements gathering with school owner
- [x] Database schema design and implementation (9 tables)
- [x] PostgreSQL configuration (port 3007)
- [x] Spring Boot project initialization
- [x] Database connection established
- [x] `SchoolUser` entity and repository
- [x] `CustomUserDetailsService` (database-backed authentication)
- [x] `SecurityConfig` with role-based URL rules
- [x] Login page and dashboard with role-based menu hiding
- [x] `Parent` entity and repository
- [x] `Student` entity and repository with `@ManyToOne` relationship
- [x] Student Management: Add student form, save student, view by class
- [x] `add-student.html` and `students-by-class.html` templates

### In Progress

- [ ] Parent Management: Add parent form, list parents
- [ ] Parent Management templates (`add-parent.html`, `parents-list.html`)
- [ ] Fee Structure entity and management
- [ ] Payment Recording interface
- [ ] Installment tracking and balance calculation
- [ ] PDF receipt generation
- [ ] Email integration (SMTP)
- [ ] Daily closure mechanism
- [ ] Audit logging system
- [ ] Reports dashboard (outstanding payments, income by term/class)
- [ ] Switch from `NoOpPasswordEncoder` to `BCryptPasswordEncoder` (pre-deployment)

### Future Plans

- [ ] Edit/delete functionality for parents and students
- [ ] Bulk email receipt sending
- [ ] Export reports to Excel/CSV
- [ ] Parent portal (parents view their children's payment history)
- [ ] Online payment integration
- [ ] SMS payment reminders
- [ ] Mobile-responsive design
- [ ] Docker containerization for easy deployment

## Setup Instructions

### Prerequisites

- Java 17
- PostgreSQL (port 3007 configured)
- Maven

### Database Setup

```sql
CREATE DATABASE school_accounting;
-- Then run the schema.sql file provided in the repository
Application Properties
Configure src/main/resources/application.properties:

properties
spring.datasource.url=jdbc:postgresql://localhost:3007/school_accounting
spring.datasource.username=postgres
spring.datasource.password=your_password
spring.jpa.hibernate.ddl-auto=validate
server.port=8081
Run the Application
bash
./mvnw spring-boot:run
Access the application at: http://localhost:8081

Authentication
Default test users (passwords in plain text for development – will be hashed before deployment):

| Username | Password | Role |
|----------|----------|------|
| owner1 | temp123 | OWNER |
| accountant1 | temp123 | ACCOUNTANT |
| auditor1 | temp123 | AUDITOR |
Key Business Rules
Fees are fixed per class level – same amount for all students in that class

Three terms per academic year

No cash payments – bank transfer only

No refunds – overpayments become credits for next term

Receipts issued only when payment is completed (not on partial payments)

Daily closure locks all transactions for that day

Every edit is tracked in audit logs

Current Development Focus
Parent Management – Web interface for owners to add and manage parents

Payment Recording – Core accounting functionality

Receipt Generation – PDF + email delivery

License
This project is built for a real school's production use.

Author
Built as a custom solution for a private school accounting system.

text
