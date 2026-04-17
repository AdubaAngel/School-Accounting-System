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

### Payment Processing
- Record bank transfer payments (no cash – 3 school bank accounts)
- Support partial payments (installments)
- Automatic balance calculation
- Overpayments become credits applied to future terms (no refunds)

### Receipts
- PDF receipts generated automatically when payment completes
- Receipts emailed to parents
- Receipts can be resent (identical to original)
- Owner's digital signature appears on every receipt

### Reporting & Compliance
- Daily closure – Accountant locks each day's transactions
- After closure: no edits or deletions allowed
- Daily closure report: total collected, breakdown by bank account and class
- Full audit log – every change tracked (who, what, when)
- Owner can view all edits; Auditor has read-only access

### User Roles

| Role | Permissions |
|------|-------------|
| **Owner** | Full access – manage users, view all data, see edit history |
| **Accountant** | Record payments, issue receipts, close daily transactions |
| **Auditor** | Read-only access to all data and reports |

## Technology Stack

| Layer | Technology |
|-------|------------|
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
|-------|---------|
| `school_user` | User authentication and roles |
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
- [x] Database schema design and implementation
- [x] PostgreSQL configuration (port 3007)
- [x] Spring Boot project initialization
- [x] Database connection established
- [x] `SchoolUser` entity and repository
- [x] `CustomUserDetailsService` (database-backed authentication)

### In Progress
- [ ] `SecurityConfig` – role-based URL rules
- [ ] Additional entities (`Parent`, `Student`, `Payment`, `FeeStructure`)
- [ ] Web interface (Thymeleaf templates)
- [ ] Payment recording logic with installment tracking
- [ ] Balance calculation and credit management
- [ ] PDF receipt generation
- [ ] Email integration for receipts
- [ ] Daily closure mechanism
- [ ] Audit logging system
- [ ] Reports dashboard

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
Run the Application
bash
./mvnw spring-boot:run
Access the application at: http://localhost:8081

Authentication
Default test users (passwords to be hashed with BCrypt):

Username	Password	Role
owner1	temp123	OWNER
accountant1	temp123	ACCOUNTANT
auditor1	temp123	AUDITOR
Key Business Rules
Fees are fixed per class level – same amount for all students in that class

Three terms per academic year

No cash payments – bank transfer only

No refunds – overpayments become credits for next term

Receipts issued only when payment is completed (not on partial payments)

Daily closure locks all transactions for that day

Every edit is tracked in audit logs

License
This project is built for a real school's production use.

Author
Built as a custom solution for a private school accounting system.

text

---
