Copy code
# Money Transfer API

## Overview

This application is a simple Java web service designed to simulate money transfer operations between two bank accounts. It is built using **Spring Boot** and provides RESTful endpoints to manage and analyze transactions. The service includes scheduled tasks for analyzing transactions and generating summaries.

### Key Features

1. **DATABASE Setup**:
  - The application uses Mysql database with the configuration setup inside application.properties file

2. **REST Endpoints**:
   - **Transfer Request**: Accepts and processes transfer requests.
   - **Retrieve Transactions**: Retrieves a list of transactions with optional filtering by status, account number, and date range.

3. **Scheduled Operations**:
   - **Transaction Analysis**: Runs at a specified time each day to analyze transactions and mark them as commission-worthy with a 20% commission of the transaction fee.
   - **Daily Summary Generation**: Produces daily transaction summaries at a specified time.

4. **Transaction Properties**:
   - Reference, amount, transaction fee, billed amount, description, date created, status, status message, commission-worthy status, commission value, account number, etc.

5. **A swagger documention is embeded and it iss accessible on http://localhost:8083/swagger-ui/index.html


## Installation and Setup

### Step 1: Clone the Repository

git clone git@github.com:moshTech/dot-coding-test.git
cd dot-coding-test

### Step 2: Build the Application

Use Maven to build the application: mvn spring-boot:run

### Step 3: Run the Application
mvn spring-boot:run

Alternatively, you can run the generated JAR file:

java -jar target/dot-assessment-test-0.0.1-SNAPSHOT.jar
