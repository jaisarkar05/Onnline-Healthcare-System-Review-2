# 🌐 Online Healthcare Management System

A web-based healthcare management system built using **Java Servlets, JSP, JDBC, and MySQL**.  
The system supports **patients, doctors, and admins**, each with dedicated functionality.

---

## 🚀 Features

### 👤 Patient
- Register & login  
- Book appointments  
- View upcoming appointments  
- Cancel appointments  
- View completed appointment
- visit notes & prescriptions  

### 👨‍⚕️ Doctor
- Secure login  
- Set & manage availability  
- View assigned appointments  
- Add medical notes & prescriptions  
- Mark appointments as completed  

### 👩‍💼 Admin
- Create doctor and admin accounts  
- Activate / deactivate users  
- View all users  
- View all appointments  
- Prevent deactivated users from logging in  

✔ Admin users **can never be disabled**  
✔ Deactivated users see — **“Account Deactivated. Contact Admin.”**

### ✉ Email Simulation
Instead of real emails, notifications are printed into console logs.

---

## 🏗 Tech Stack

| Layer | Technology |
|------|-----------|
Frontend | JSP, HTML, CSS  
Backend | Java Servlets, JDBC  
Database | MySQL  
Server | Apache Tomcat  
IDE | Eclipse  

---

## 📂 Project Structure

OnlineHealthcareManagementSystem/
└── src/main/java
└── com.healthcare
├── dao
├── model
├── servlet
├── thread
└── util
└── src/main/webapp
├── WEB-INF/*.jsp
├── css/style.css

---

# ⚙️ Setup Instructions

###  Requirements
- JDK 21
- Eclipse IDE
- Apache Tomcat 10.1
- MySQL Server

---

###  Import Project into Eclipse
File → Import → Existing Projects into Workspace


Select the downloaded project folder.

---

###  Configure Database Connection

Open: src/main/java/com/healthcare/util/DBConnection.java

Update MySQL credentials if necessary:

private static final String URL = "jdbc:mysql://localhost:3306/healthcare_db";
private static final String USERNAME = "root";
private static final String PASSWORD = "your_password";

# Run this file
Run the included script: "database.sql" or paste manually into MySQL:


# Create First Admin (Required)

INSERT INTO users(name,email,password,role,status)
VALUES ('Super Admin','admin@gmail.com','admin123','ADMIN','ACTIVE');

# Run The Project
Start Tomcat and open:


http://localhost:8080/OnlineHealthcareManagementSystem/login.jsp
# 📸 Screenshots:
Login

Patient Dashboard

Doctor Dashboard

Admin Dashboard


# Credits
Project developed by Jai Sarkar as a learning project for JSP/Servlets & MySQL.
