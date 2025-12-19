# Online Quiz Application

A full-stack web application for creating and taking online quizzes, featuring role-based access control (Admin/Teacher vs. Student), real-time scoring, and result tracking.

## Features

*   **Role-Based Access**:
    *   **Teachers/Admins**: Create quizzes, add questions, view all user submissions.
    *   **Students**: Browse quizzes, take timed quizzes, view their own score history.
*   **Authentication**: Secure Registration and Login using JWT (JSON Web Tokens).
*   **Quiz Management**: Create quizzes with multiple-choice questions.
*   **Quiz Taking**: Interactive quiz interface with timer.
*   **User Profile**: View past quiz performance.
*   **Admin Dashboard**: Centralized hub for quiz and user management.

## Tech Stack

*   **Backend**: Java, Spring Boot, Spring Data JPA, Hibernate.
*   **Database**: MySQL.
*   **Frontend**: React.js, React Router, Axios.
*   **Styling**: CSS3.
*   **Containerization**: Docker.
*   **Deployment**: Ready for platforms like Railway.app.

## Prerequisites

*   Java 17 or higher
*   Maven 3.6+
*   Node.js 16+
*   MySQL Server

## Local Setup

### 1. Database Setup
Create a MySQL database named `Quiz_DB`:
```sql
CREATE DATABASE Quiz_DB;
```
Update `src/main/resources/application.properties` if your MySQL username/password differs from `root/root`.

### 2. Backend Setup
Navigate to the root directory:
```bash
mvn clean install
mvn spring-boot:run
```
The backend will start on `http://localhost:8080`.

### 3. Frontend Setup
Navigate to the frontend directory:
```bash
cd user_interface
npm install
npm start
```
The frontend will start on `http://localhost:3000`.

## Deployment (Railway.app)

This project is configured for easy deployment on Railway.

1.  **Push to GitHub**: Ensure this project is in a GitHub repository.
2.  **Login to Railway**: Go to [Railway.app](https://railway.app/) and login with GitHub.
3.  **Create Project**: Click "New Project" -> "Deploy from GitHub repo" -> Select this repo.
4.  **Add Database**:
    *   Right-click on the canvas -> New -> Database -> MySQL.
    *   This will create a `MYSQL_URL` variable automatically.
5.  **Configure Backend Service**:
    *   Railway should detect the `Dockerfile` in the root.
    *   Go to "Variables" and add:
        *   `SPRING_DATASOURCE_URL`: `${MYSQL_URL}`
        *   `SPRING_DATASOURCE_USERNAME`: `root`
        *   `SPRING_DATASOURCE_PASSWORD`: (Copy from MySQL service variables)
        *   `PORT`: `8080`
6.  **Configure Frontend Service**:
    *   Add a new service linked to the same repo, but set the **Root Directory** to `/user_interface` in Settings.
    *   Go to "Variables" and add:
        *   `REACT_APP_API_URL`: `https://<your-backend-service-domain>` (Do not add a trailing slash).
7.  **Generate Domain**: go to settings of frontend service and generate domain to access the application.

## Project Structure

*   `src/main/java`: Spring Boot Backend source code.
*   `user_interface`: React Frontend source code.
*   `Dockerfile`: Backend Docker configuration.
*   `user_interface/Dockerfile`: Frontend Docker configuration.
*   `railway.json`: Railway specific deployment configuration.
