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


# Threads:

✔ Email simulation using Thread
✔ JDBC Transaction Handling (appointments + medical record)
✔ Background execution without blocking UI

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
<img width="1440" height="900" alt="Screenshot 2025-12-27 at 9 31 20 PM" src="https://github.com/user-attachments/assets/ef884e6c-5f30-4ffd-950e-73e22ec500cb" />

<img width="2880" height="1800" alt="image" src="https://github.com/user-attachments/assets/2889f9a4-afc0-4836-b0aa-e5a9b7fb0814" />

<img width="2880" height="1800" alt="image" src="https://github.com/user-attachments/assets/4c2852bc-163d-49c5-9859-76e737e3f355" />

<img width="2880" height="1800" alt="image" src="https://github.com/user-attachments/assets/e59c817d-c2ef-4d2e-8ece-a7344c22c8ed" />

<img width="2880" height="1800" alt="image" src="https://github.com/user-attachments/assets/0c5f1b23-ab5b-4a74-86e8-80530be0f069" />

<img width="2880" height="1800" alt="image" src="https://github.com/user-attachments/assets/bd20da06-9a40-46bc-91ef-453706632f19" />

<img width="2880" height="1800" alt="image" src="https://github.com/user-attachments/assets/87e06fcf-179f-4bac-afac-56343c8e4a70" />

<img width="2880" height="1800" alt="image" src="https://github.com/user-attachments/assets/20a8811d-056f-4fa9-a113-d488d2c81f32" />

<img width="2880" height="1800" alt="image" src="https://github.com/user-attachments/assets/ff967756-bd94-4e9f-8573-2c85322f88b1" />

<img width="2880" height="1800" alt="image" src="https://github.com/user-attachments/assets/cb72bb4d-3b3c-41bb-bafa-472d88019ef5" />

<img width="2880" height="1800" alt="image" src="https://github.com/user-attachments/assets/fe7dcd43-92b1-44e1-84e8-13f944753c03" />

<img width="2880" height="1800" alt="image" src="https://github.com/user-attachments/assets/43feacf7-655a-48bb-9acc-6a85aa2d6271" />

<img width="2880" height="1800" alt="image" src="https://github.com/user-attachments/assets/6309d71a-0729-4c7c-a70d-4d7fbf0845ae" />

<img width="2880" height="1800" alt="image" src="https://github.com/user-attachments/assets/d3915ddb-31a3-4cde-84f1-2336dac75b3f" />


# Credits
Project developed by Jai Sarkar as a learning project for JSP/Servlets & MySQL.
