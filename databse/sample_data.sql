USE faculty_db;

-- Login Users
INSERT INTO users (username, password, role) VALUES 
('admin', 'admin123', 'Admin'),
('stu02', 'student123', 'Student'),
('lec01', 'lecturer123', 'Lecturer');

-- Departments
INSERT INTO departments VALUES ('D01', 'Computing & Technology', 'Prof. Saman Kumara', 15);

-- Degrees
INSERT INTO degrees VALUES ('DEG01', 'BSc in IT', 'D01');

-- Students
INSERT INTO students VALUES ('stu02', 'Paviru Lishara Pathirana', 'lplisharapathirana99@gmail.com', 'BSc in IT');

-- Lecturers
INSERT INTO lecturers VALUES ('lec01', 'Dr. Sunil Perera', 'sunil@faculty.ac.lk', 'Computing & Technology');

-- Courses
INSERT INTO courses VALUES ('CS101', 'OOP (Java)', 3, 'lec01');

-- Timetable
INSERT INTO timetable (time_slot, monday, tuesday, wednesday, thursday, friday) VALUES 
('08:30 - 10:30', 'OOP (Java)', 'Database', 'Networking', 'Maths', 'English');