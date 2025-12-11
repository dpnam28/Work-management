-- Base faculties
INSERT INTO faculties (id, faculty_name, faculty_code) VALUES
  (1, 'Engineering Faculty', 'ENG'),
  (2, 'Business Faculty', 'BUS');

-- Majors tied to faculties
INSERT INTO majors (id, faculty_id, major_name, major_code) VALUES
  (1, 1, 'Software Engineering', 'SE'),
  (2, 1, 'Information Systems', 'IS');

-- Academic semesters
INSERT INTO semesters (id, semester_name, start_date, end_date) VALUES
  (1, 'Spring 2025', '2025-02-01', '2025-06-15'),
  (2, 'Fall 2025', '2025-08-15', '2025-12-30');

-- Subjects under faculties
INSERT INTO subjects (id, id_faculty, subject_name, subject_code, credits) VALUES
  (1, 1, 'Algorithms and Data Structures', 'ALG101', 3.0),
  (2, 1, 'Database Systems', 'DBS201', 3.0);

-- Class subjects per semester
INSERT INTO class_subjects (id, class_code, id_subject, id_semester, max_student) VALUES
  (1, 'ALG101-01', 1, 1, 40),
  (2, 'DBS201-01', 2, 1, 35);

-- Pre-created users (password: Password123)
INSERT INTO users (id, username, email, password, full_name, phone, role) VALUES
  (1, 'headteacher', 'head@university.edu', '$2a$10$Dow1GgAbkXtEOi33ECszO.W8t5cjox96D65gis6pZeRAEIJ5zsx5m', 'Dr. Alice Head', '0900000001', 'TEACHER'),
  (2, 'lecturer', 'lecturer@university.edu', '$2a$10$Dow1GgAbkXtEOi33ECszO.W8t5cjox96D65gis6pZeRAEIJ5zsx5m', 'Mr. Bob Lecturer', '0900000002', 'TEACHER'),
  (3, 'student01', 'student01@university.edu', '$2a$10$Dow1GgAbkXtEOi33ECszO.W8t5cjox96D65gis6pZeRAEIJ5zsx5m', 'Charlie Student', '0900000003', 'STUDENT');

-- Teacher profiles (match users)
INSERT INTO teachers (id_teacher, faculty_id, teacher_code, position) VALUES
  (1, 1, 'T-HEAD-001', 'HEAD'),
  (2, 1, 'T-LEC-002', 'LECTURER');

-- Student profile referencing major
INSERT INTO students (id_student, student_code, entry_year, id_major) VALUES
  (3, 'S2025-001', 2025, 1);

-- Teaching assignments (HEAD assigns lecturer to teach ALG101-01)
INSERT INTO teaching_assignments (id_teacher, id_class_subject) VALUES
  (2, 1);

-- Initial enrollment (student already registered ALG101-01)
INSERT INTO enrollments (id_student, id_class_subject) VALUES
  (3, 1);
