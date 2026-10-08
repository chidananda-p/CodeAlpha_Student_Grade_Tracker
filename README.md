# 🎓 Student Grade Tracker

A lightweight and professional **Student Grade Management System** built using **Core Java, Object-Oriented Programming, Java Collections, Java HTTP Server, HTML, CSS, and Vanilla JavaScript**.

The application provides a modern web dashboard for managing student grades, automatically calculating class statistics, determining letter grades and pass/fail status, and generating a comprehensive student grade summary report.

---

## 📌 Overview

**Student Grade Tracker** is an academic grade management application designed to demonstrate how a complete web-based application can be developed using **Core Java without relying on heavyweight frameworks or external databases**.

The project combines a Java backend with a responsive frontend to provide a simple but practical grade management experience.

### Key capabilities

- 👨‍🎓 Add student records
- ✏️ Edit student records
- 🗑️ Delete student records
- 📊 Automatically calculate class statistics
- 🏆 Identify highest and lowest scores
- 🏅 Automatically assign letter grades
- ✅ Determine pass/fail status
- 📄 Generate a complete summary report
- 📋 Copy reports to clipboard
- 💾 Download reports as text files
- 📱 Responsive and modern user interface

---

# 🖥️ Application Preview

## 📊 Student Grade Tracker Dashboard

The main dashboard provides a clean interface for managing students and monitoring overall class performance.

![Student Grade Tracker Dashboard](screenshots/dashboard.png)

### Dashboard Features

- **Class Average**
- **Highest Score**
- **Lowest Score**
- **Total Students**
- Add Student form
- Student roster
- Automatic grade calculation
- Pass/Fail status
- Edit student records
- Delete student records
- Reset demonstration data

---

## 📄 Student Grade Summary Report

The application can generate a detailed **Student Grade Summary Report** containing overall class statistics and the complete student roster.

![Student Grade Summary Report](screenshots/summary-report.png)

The report includes:

- Report generation date and time
- Total number of students
- Class average
- Highest score and student
- Lowest score and student
- Complete student roster
- Individual scores
- Letter grades
- Pass/Fail status

The report can also be:

- 📋 Copied to the clipboard
- 💾 Downloaded as a `.txt` file

---

# ✨ Features

## 👨‍🎓 Student Management

The application provides complete basic CRUD functionality for student records.

### Add Student

Users can enter:

- Student name
- Grade score

The application validates the input before adding the student.

### Edit Student

Existing student records can be modified without deleting and recreating them.

### Delete Student

Students can be removed from the roster using the Delete action.

### View Students

All students are displayed in a structured and responsive table.

---

# 📊 Automatic Grade Calculation

The application automatically converts numerical scores into letter grades.

| Score Range | Grade |
|-------------:|:-----:|
| 90 – 100 | A |
| 80 – 89.99 | B |
| 70 – 79.99 | C |
| 60 – 69.99 | D |
| 0 – 59.99 | F |

### Passing Criteria

A student is considered **Passed** when:

```text
Score >= 60
