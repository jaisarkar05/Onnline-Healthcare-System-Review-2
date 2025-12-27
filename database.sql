CREATE DATABASE IF NOT EXISTS healthcare_db;
USE healthcare_db;

CREATE TABLE users (
    user_id INT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(100) NOT NULL,
    email VARCHAR(100) NOT NULL UNIQUE,
    password VARCHAR(100) NOT NULL,
    role ENUM('ADMIN','DOCTOR','PATIENT') NOT NULL,
    status VARCHAR(10) DEFAULT 'ACTIVE',
    availability_status VARCHAR(20) DEFAULT 'AVAILABLE'
);

CREATE TABLE patients (
    patient_id INT PRIMARY KEY,
    age INT,
    gender VARCHAR(10),
    FOREIGN KEY (patient_id) REFERENCES users(user_id)
        ON DELETE CASCADE
);

CREATE TABLE doctors (
    doctor_id INT PRIMARY KEY,
    specialization VARCHAR(100),
    availability VARCHAR(100),
    FOREIGN KEY (doctor_id) REFERENCES users(user_id)
        ON DELETE CASCADE
);

CREATE TABLE doctor_availability (
    availability_id INT AUTO_INCREMENT PRIMARY KEY,
    doctor_id INT NOT NULL,
    day_of_week VARCHAR(10) NOT NULL,
    start_time TIME NOT NULL,
    end_time TIME NOT NULL,
    active TINYINT(1) DEFAULT 1,
    FOREIGN KEY (doctor_id) REFERENCES users(user_id)
        ON DELETE CASCADE
);

CREATE TABLE appointments (
    appointment_id INT AUTO_INCREMENT PRIMARY KEY,
    patient_id INT,
    doctor_id INT,
    appointment_date DATE,
    appointment_time TIME,
    status VARCHAR(50),
    doctor_notes TEXT,
    prescription TEXT,

    FOREIGN KEY (patient_id) REFERENCES users(user_id)
        ON DELETE CASCADE,

    FOREIGN KEY (doctor_id) REFERENCES users(user_id)
        ON DELETE CASCADE
);

CREATE TABLE medical_records (
    record_id INT AUTO_INCREMENT PRIMARY KEY,
    appointment_id INT NOT NULL,
    doctor_notes TEXT,
    prescription TEXT,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,

    FOREIGN KEY (appointment_id) REFERENCES appointments(appointment_id)
        ON DELETE CASCADE
);
