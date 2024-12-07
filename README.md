# Student Feedback and Analysis Management

This is a Spring Boot-based web application that allows users (students and faculty) to provide and manage feedback on various aspects of academic courses. The project collects feedback, analyzes it, and provides insights to improve the quality of education and student satisfaction.

## Features
- **Student Feedback:** Students can submit feedback for courses, teachers, and overall learning experience.
- **Analysis & Reporting:** Administrators can view feedback analysis reports.
- **Admin Panel:** Admin can manage students, courses, and faculty information.
- **Dashboard:** Provides insights and statistics based on feedback data.

## Tech Stack
- **Backend:** Spring Boot
- **Database:** MySQL 
- **Frontend:** React 
- **Authentication:** Spring Security (JWT)

## Prerequisites

Before setting up the project, make sure you have the following installed:
- **JDK 17** or later (For Spring Boot application)
- **Maven** (For building the project)
- **IDE** (IntelliJ IDEA or Eclipse)
- **Database** (MySQL)

## Setup Instructions

### 1. Clone the Repository

Clone the repository to your local machine:

```bash
git clone https://github.com/udaykallam/Student-Feedback-And-Analysis-Management.git
cd student-feedback-analysis
```

### 2. Configure the Database

Modify the configuration in `src/main/resources/application.properties`.

For example, to configure MySQL, change the following:

```properties
# Change to MySQL Configuration:
# spring.datasource.url=jdbc:mysql://localhost:3306/feedback
# spring.datasource.username=root
# spring.datasource.password=your_password
# spring.jpa.hibernate.ddl-auto=update
```

If you're using **MySQL**, make sure to install MySQL and create a database (e.g., `feedback`).

### 3. Build the Project

Use **Maven** to build the project.

Command:

```bash
mvn clean install
```

### 4. Run the Application

To run the Spring Boot application, use the following command:

```bash
mvn spring-boot:run
```

The application should start, and you can access it by navigating to `http://localhost:8080` in your browser.


## Contributing

Feel free to contribute by submitting issues, creating pull requests, or offering suggestions.

### Steps for Contribution:
1. Fork the repository.
2. Create a new branch (`git checkout -b feature-name`).
3. Make your changes.
4. Commit your changes (`git commit -am 'Add new feature'`).
5. Push to the branch (`git push origin feature-name`).
6. Create a new Pull Request.

