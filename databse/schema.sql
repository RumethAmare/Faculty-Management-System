CREATE DATABASE IF NOT EXISTS faculty_db;
USE faculty_db;

CREATE TABLE users (
    id INT AUTO_INCREMENT PRIMARY KEY,
    username VARCHAR(50) NOT NULL,
    password VARCHAR(50) NOT NULL,
    role ENUM('Admin', 'Student', 'Lecturer') NOT NULL
);

CREATE TABLE departments (
    dept_id VARCHAR(10) PRIMARY KEY,
    dept_name VARCHAR(100),
    hod_name VARCHAR(100),
    staff_count INT
);

CREATE TABLE degrees (
    degree_id VARCHAR(10) PRIMARY KEY,
    degree_name VARCHAR(100),
    dept_id VARCHAR(10),
    FOREIGN KEY (dept_id) REFERENCES departments(dept_id)
);

CREATE TABLE students (
    id VARCHAR(20) PRIMARY KEY,
    name VARCHAR(100),
    email VARCHAR(100),
    degree VARCHAR(100)
);

CREATE TABLE lecturers (
    id VARCHAR(20) PRIMARY KEY,
    name VARCHAR(100),
    email VARCHAR(100),
    department VARCHAR(100)
);

CREATE TABLE courses (
    course_id VARCHAR(10) PRIMARY KEY,
    course_name VARCHAR(100),
    credits INT,
    lecturer_id VARCHAR(20),
    FOREIGN KEY (lecturer_id) REFERENCES lecturers(id)
);

CREATE TABLE timetable (
    id INT AUTO_INCREMENT PRIMARY KEY,
    time_slot VARCHAR(50),
    monday VARCHAR(100),
    tuesday VARCHAR(100),
    wednesday VARCHAR(100),
    thursday VARCHAR(100),
    friday VARCHAR(100)
);