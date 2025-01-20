# StudentStay - Repository

StudentStay is a comprehensive application designed to streamline the dormitory room allocation process in universities. The project emphasizes automation, scalability, and user accessibility to enhance both administrative and student experiences.

## Table of Contents
- [Project Overview](#project-overview)
- [Features](#features)
- [Architecture](#architecture)
- [Technologies Used](#technologies-used)
- [User Roles](#user-roles)
- [Contributors](#contributors)

## Project Overview
StudentStay is a web and desktop application that digitizes and simplifies the process of dormitory room allocation. The application allows students to submit documents and reserve rooms based on ranking. It also provides administrators and committees with tools for validation, ranking management and application management.

### Key Objectives:
- Automate the dormitory application process.
- Ensure a secure and scalable system.

## Features
- **User Authentication:** Login using institutional email credentials via a gRPC server.
- **Document Management:** Upload, validate, and store documents securely.
- **Room Allocation:** Automated ranking-based room allocation with the ability to specify preferences and roommates.
- **Committee Tools:** Manual overrides for special cases.

## Architecture
StudentStay follows a microservices architecture to ensure modularity, scalability, and maintainability.

### Layers:
1. **Presentation Layer (Frontend):**
   - Built with Angular for responsive and user-friendly interfaces.
2. **Service Layer (Backend):**
   - Composed of Spring Boot microservices for handling specific functionalities.
3. **Data Layer (Database):**
   - PostgreSQL for secure and structured data storage.

### Key Components:
- **Authentication Service:** User login and token generation.
- **Document Service:** Automated document validation.
- **Room Allocation Service:** Ranking and room distribution logic.

## Technologies Used
- **Frontend:** Angular, Angular Material.
- **Backend:** Spring Boot, RESTful APIs.
- **Database:** PostgreSQL.
- **External Technologies** 
  - [Ocr Space](https://ocr.space/)
  - [Supabase](https://supabase.com/)
  - [Neon](https://neon.tech/)
  - [Redis](https://redis.io/cloud/)
- **Authentication:** Google RPC

## User Roles
1. **Unregistered User:** Access the login interface.
2. **Student:** 
   - Submit dormitory applications.
   - Track document and allocation status.
3. **Committee Member:**
   - Validate and manage student applications.
   - Handle manual overrides.
4. **Administrator:**
   - Update dormitory details and configurations.

## Contributors
- **Antăluțe Flavia**
- **Spiridon Bianca**
- **Zacordoneț Andrei**
- **Zaharia Teodor-Ștefan**
