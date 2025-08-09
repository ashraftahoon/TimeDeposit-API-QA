# Time Deposit API QA Automation

## Overview
This repository contains:
- Automated API tests using Java, Maven,Testng and REST Assured
- Allure reporting for test results
- GitHub Actions CI/CD pipeline for automated testing and report publishing
- Mocking External Services: API tests support mocking external dependencies such as the payment gateway. using wiremock

## Technologies & Languages
# Backend:
-Language: C#
-Framework: .NET 9 (ASP.NET Core Web API)
-ORM: Entity Framework Core
-Database: PostgreSQL 15

# API Testing:
-Language: Java
-Testing Framework: REST Assured
-Build Tool: Maven
-Reporting: Allure
-Docker , Docker compose 

# CI/CD:
-Platform: GitHub Actions
-Docker containers for database and services

# Other Tools:
-Java Development Kit (JDK) 21 (Temurin distribution)
-Allure Commandline for test report generation

## How to Run Locally
# Prerequisites
Make sure you have installed:
-.NET 9 SDK
-PostgreSQL 15
-Java 21 (Temurin)
-Maven

## Steps
1. Clone the repository
2. Setup the PostgreSQL database
3. Apply EF Core migrations and run backend
**cd backend
dotnet restore
dotnet ef database update
dotnet run --urls "http://localhost:8080"**
5. Run API tests (in a new terminal at project root) By **mvn clean test**
6. Generate and view Allure report By **allure serve**

## CI/CD Execution (GitHub Actions)
# Automated Pipeline Triggers
- **On every push** to main branch
- **On pull requests** (PR validation)

## Mocking & Switching API Modes
The API tests can be run against:
1. Mock Mode – All external dependencies (e.g., payment gateway) are replaced with predefined mock responses. by useing commend **mvn test -DuseMock=true**
2. Local Mode – Tests run against your locally hosted backend (http://localhost:8080).
3. Real API Mode – Tests run against the deployed API in a staging or production environmen  **mvn test -DuseMock=false**

## Allure Report Access
After the GitHub Actions workflow runs, the Allure report is available at:
**https://ashraftahoon.github.io/TimeDeposit-API-QA/**

