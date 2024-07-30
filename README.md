# Project Management System

## 📰 Video
  - [Complete walkthrough of the project](https://codingburgas-my.sharepoint.com/personal/isbachvarov21_codingburgas_bg/_layouts/15/stream.aspx?id=%2Fpersonal%2Fisbachvarov21_codingburgas_bg%2FDocuments%2FMicrosoft%20Teams%20Chat%20Files%2F2024-06-29%2013-28-49%20-%20Trim.mkv&referrer=StreamWebApp.Web&referrerScenario=AddressBarCopied.view.e410f584-dded-4f33-b9ae-793fa326f95a&ga=1 )

## Table of Contents

1. [Introduction](#introduction)
2. [Features](#features)
3. [Requirements](#requirements)
4. [Installation](#installation)
5. [Configuration](#configuration)
6. [Running the Application](#running-the-application)
7. [Usage](#usage)
8. [FAQ](#faq)

## Introduction

This is a Project Management System that allows you to manage projects, tasks, and comments. The system includes features like Gantt charts for task tracking and user authentication.

## Features

- Project creation and management
- Task creation, assignment, and tracking
- Gantt chart visualization
- Commenting on tasks
- User authentication and roles

## Requirements

- Java Development Kit (JDK) 8 or higher
- Apache Maven 3.6.3 or higher
- MySQL or another compatible database

## Installation

1. Clone the repository:

    ```bash
    git clone https://github.com/yourusername/yourrepository.git
    cd yourrepository
    ```

2. Install the dependencies:

    ```bash
    mvn clean install
    ```

## Configuration

1. Configure the database:

    Open `src/main/resources/application.properties` and set up your database:

    ```properties
    spring.datasource.url=jdbc:mysql://localhost:3306/yourdatabase
    spring.datasource.username=yourusername
    spring.datasource.password=yourpassword
    spring.jpa.hibernate.ddl-auto=update
    ```

## Running the Application

1. Start the application:

    ```bash
    mvn spring-boot:run
    ```

    or

    ```bash
    java -jar target/yourapplication.jar
    ```

## Usage

1. Access the web application:

    Open your browser and go to:

    ```
    http://localhost:8080
    ```

2. Manage projects and tasks:
    - Create projects
    - Add tasks to projects
    - Track tasks using the Gantt chart
    - Add comments to tasks

## FAQ

### How do I change the default port?

You can change the default port by adding the following line to `application.properties`:

```properties
server.port=9090
