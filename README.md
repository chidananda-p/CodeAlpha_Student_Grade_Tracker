🎓 Student Grade Tracker

A modern and lightweight Student Grade Management System built with Core Java, Object-Oriented Programming, Java Collections, Java HTTP Server, HTML5, CSS3, and Vanilla JavaScript.

The application provides a professional web dashboard for managing student grades, automatically calculating academic statistics, assigning letter grades, determining pass/fail status, and generating a detailed Student Grade Summary Report.

📌 Overview

Student Grade Tracker is an academic grade management application designed to demonstrate how fundamental Java concepts can be used to build a complete working web application without relying on heavyweight frameworks or external databases.

The project combines a Core Java backend with a responsive HTML/CSS/JavaScript frontend.

Key capabilities
👨‍🎓 Add student records
✏️ Edit student records
🗑️ Delete student records
📊 Calculate class statistics automatically
🏆 Identify highest-scoring student
📉 Identify lowest-scoring student
🏅 Automatically calculate letter grades
✅ Determine pass/fail status
📄 Generate detailed summary reports
📋 Copy reports to clipboard
💾 Download reports as .txt
🔄 Reset demonstration data
📱 Responsive and modern user interface
🖥️ Application Preview
📊 Student Grade Tracker Dashboard

The main dashboard provides a clean interface for managing students and monitoring overall class performance.

Dashboard includes
Class Average
Highest Score
Lowest Score
Total Students
Add Student functionality
Student roster
Automatic grade calculation
Pass/Fail indicators
Edit functionality
Delete functionality
Reset Demo functionality
Summary Report generation
📄 Student Grade Summary Report

The application generates a detailed Student Grade Summary Report containing class statistics and the complete student roster.

Report includes
Report generation date and time
Total number of students
Class average
Highest score
Highest-scoring student
Lowest score
Lowest-scoring student
Complete student roster
Individual scores
Letter grades
Pass/Fail status

The report can be:

📋 Copied to Clipboard
💾 Downloaded as a Text Report
✨ Features
👨‍🎓 Student Management

The application provides basic CRUD operations for student records.

➕ Add Student

Users can enter:

Student name
Grade score

The application validates the information before adding the record.

✏️ Edit Student

Existing student records can be modified without deleting the student.

🗑️ Delete Student

Students can be removed directly from the student roster.

👀 View Student Roster

All students are displayed in a structured table containing:

Field	Description
#	Student number
Student Name	Student's name
Score	Numerical score
Grade	Automatically calculated letter grade
Status	Passed / Failed
Actions	Edit / Delete
📊 Automatic Grade Calculation

The system automatically converts numerical scores into letter grades.

Score	Grade
90 – 100	A
80 – 89.99	B
70 – 79.99	C
60 – 69.99	D
0 – 59.99	F
Passing Criteria

A student is considered Passed when:

Score >= 60

Otherwise:

Score < 60

the student is marked as Failed.

📈 Class Statistics

The dashboard automatically calculates overall class performance.

The application displays:

Total Students
Class Average
Highest Score
Highest-Scoring Student
Lowest Score
Lowest-Scoring Student
Example
Total Students : 8
Class Average  : 82.00%
Highest Score  : 98.50% (Isabella Rossi)
Lowest Score   : 54.00% (Nehal)

Statistics are recalculated whenever student records are added, edited, or deleted.

📄 Summary Report

The Summary Report feature generates a structured report containing the overall class performance and complete student roster.

Example
======================================================================
                     STUDENT GRADE SUMMARY REPORT
                    Generated on: 10/8/2026, 9:53:23 PM
======================================================================

----------------------------------------------------------------------
1. OVERALL CLASS STATISTICS
----------------------------------------------------------------------

* Total Students : 8
* Class Average  : 82.00%
* Highest Score  : 98.50% (Isabella Rossi)
* Lowest Score   : 54.00% (Nehal)

----------------------------------------------------------------------
2. COMPLETE STUDENT ROSTER
----------------------------------------------------------------------

No.   | Student Name          | Score   | Grade | Status
----------------------------------------------------------------------
1     | Eshwar                | 95.50%  | A     | Passed
2     | Jaydev                | 88.00%  | B     | Passed
3     | Sonu                  | 92.50%  | A     | Passed
4     | Lohith                | 74.00%  | C     | Passed
5     | Amar                  | 85.00%  | B     | Passed
6     | Lucky                 | 68.50%  | D     | Passed
7     | Nehal                 | 54.00%  | F     | Failed
8     | Isabella Rossi        | 98.50%  | A     | Passed
🏗️ System Architecture
┌──────────────────────────────────────────────┐
│                WEB FRONTEND                  │
│                                              │
│          HTML + CSS + JavaScript             │
└──────────────────────┬───────────────────────┘
                       │
                       │ HTTP / JSON
                       ▼
┌──────────────────────────────────────────────┐
│               JAVA BACKEND                   │
│                                              │
│          Java HTTP Server                    │
│        com.sun.net.httpserver                │
│                                              │
│  ┌────────────────────────────────────────┐  │
│  │             API Handler                │  │
│  │                                        │  │
│  │  GET / POST / PUT / DELETE             │  │
│  └────────────────────┬───────────────────┘  │
│                       │                      │
│                       ▼                      │
│  ┌────────────────────────────────────────┐  │
│  │           GradeTracker                 │  │
│  │                                        │  │
│  │     Business Logic & Statistics        │  │
│  └────────────────────┬───────────────────┘  │
│                       │                      │
│                       ▼                      │
│  ┌────────────────────────────────────────┐  │
│  │              Student                   │  │
│  │             Data Model                 │  │
│  └────────────────────────────────────────┘  │
└──────────────────────────────────────────────┘
🔄 Application Workflow
                    ┌──────────────────┐
                    │ Start Application│
                    └────────┬─────────┘
                             │
                             ▼
                    ┌──────────────────┐
                    │ Java HTTP Server │
                    └────────┬─────────┘
                             │
                             ▼
                    ┌──────────────────┐
                    │  Web Dashboard   │
                    └────────┬─────────┘
                             │
             ┌───────────────┼───────────────┐
             │               │               │
             ▼               ▼               ▼
        Add Student     Edit Student   Delete Student
             │               │               │
             └───────────────┼───────────────┘
                             │
                             ▼
                 ┌─────────────────────┐
                 │ Automatic Calculation│
                 └──────────┬──────────┘
                            │
             ┌──────────────┼──────────────┐
             ▼              ▼              ▼
          Average        Highest        Lowest
             │              │              │
             └──────────────┼──────────────┘
                            │
                            ▼
                  ┌──────────────────┐
                  │  Summary Report  │
                  └────────┬─────────┘
                           │
                    ┌──────┴──────┐
                    ▼             ▼
                 Copy          Download
📁 Project Structure
StudentGradeTracker/
│
├── Main.java
├── GradeTracker.java
├── Student.java
│
├── web/
│   ├── index.html
│   │
│   ├── css/
│   │   └── style.css
│   │
│   └── js/
│       └── app.js
│
├── screenshots/
│   ├── dashboard.png
│   └── summary-report.png
│
└── README.md
🧩 Core Components
Student.java

Represents an individual student.

Responsibilities
Store student information
Store numerical score
Validate student data
Calculate letter grade
Determine pass/fail status
Round scores to two decimal places
GradeTracker.java

Contains the main business logic.

Responsibilities
Manage student records
Add students
Update students
Remove students
Calculate class average
Find highest score
Find lowest score
Identify highest-scoring student
Identify lowest-scoring student
Generate summary reports
Manage demonstration data

The application uses:

ArrayList<Student>

as its primary in-memory collection.

Main.java

Acts as the application entry point and Java HTTP server.

Responsibilities
Start the HTTP server
Listen on port 8080
Handle API requests
Serve frontend files
Process JSON requests
Return JSON responses
Open the application in the default browser
web/index.html

Defines the structure of the web dashboard.

Contains:

Application header
Statistics cards
Student form
Student roster
Edit/Delete actions
Summary Report dialog
Action buttons
web/css/style.css

Controls the application's user interface.

Includes styling for:

Dashboard layout
Statistics cards
Forms
Tables
Buttons
Grade badges
Pass/Fail badges
Dialogs
Notifications
Responsive layouts
web/js/app.js

Controls frontend functionality.

Responsibilities
Communicate with the Java backend
Fetch student records
Add students
Edit students
Delete students
Update statistics
Render the student table
Generate reports
Copy reports to clipboard
Download text reports
Handle LocalStorage fallback
🔌 REST-Style API

The backend provides lightweight REST-style HTTP endpoints.

Get All Students
GET /api/students

Returns all student records.

Example
[
  {
    "name": "Eshwar",
    "score": 95.5,
    "grade": "A",
    "status": "Passed"
  }
]
Add Student
POST /api/students
Example Request
{
  "name": "Emma Watson",
  "score": 88.5
}
Update Student
PUT /api/students?index=0
Example Request
{
  "name": "Emma Watson",
  "score": 91.5
}
Delete Student
DELETE /api/students?index=0

Deletes the student at the specified index.

Get Class Summary
GET /api/summary
Example Response
{
  "total": 8,
  "average": 82.0,
  "highest": 98.5,
  "topStudent": "Isabella Rossi",
  "lowest": 54.0,
  "lowestStudent": "Nehal"
}
Generate Report
GET /api/report

Returns the formatted Student Grade Summary Report.

Reset Demonstration Data
POST /api/reset

Restores the default demonstration data.

🛠️ Technologies Used
Backend
Java
Core Java
Object-Oriented Programming
Java Collections
ArrayList
Java HTTP Server
JSON request/response handling
Java I/O
Java Time API
ExecutorService
Frontend
HTML5
CSS3
Vanilla JavaScript
Fetch API
LocalStorage API
DOM manipulation
HTML Dialog API
Responsive CSS
🚫 Frameworks and External Dependencies

The project intentionally avoids heavyweight frameworks.

Spring Boot       ❌
Hibernate         ❌
MySQL             ❌
MongoDB           ❌
React             ❌
Angular            ❌
Vue                ❌
Node.js            ❌

Instead, it demonstrates how a functional web application can be created using:

Core Java
    +
Java HTTP Server
    +
HTML
    +
CSS
    +
Vanilla JavaScript
💻 Requirements

To run the project locally, you need:

Java JDK 11 or later
Modern web browser
Git — optional

No database server is required.

No separate backend server is required.

🚀 Getting Started
1. Clone the Repository
git clone https://github.com/chidananda-p/CodeAlpha_Student_Grade_Tracker.git
2. Navigate to the Project
cd CodeAlpha_Student_Grade_Tracker
3. Compile the Java Source Files
javac Student.java GradeTracker.java Main.java
4. Start the Application
java Main

The application starts the Java HTTP server on:

http://localhost:8080

The application attempts to open the browser automatically.

If it does not open automatically, open:

http://localhost:8080

in your browser.

5. Stop the Application

Press:

Ctrl + C

in the terminal.

🧪 Input Validation

The application validates user input before processing it.

Student Name

The student name:

Cannot be empty
Is trimmed before processing
Score

The score:

Must be between 0 and 100
Supports decimal values
Is rounded to two decimal places
Valid Examples
95
88.5
72.25
60
Invalid Examples
-10
105
abc
💾 Data Storage

The current version primarily stores student records in memory using Java's ArrayList.

ArrayList<Student>

This approach keeps the application lightweight and allows the project to demonstrate Java Collections and business logic without requiring a database.

Important

Because the Java backend currently uses in-memory storage, server-side student records are not intended to provide permanent database-style persistence after restarting the server.

The frontend also provides LocalStorage fallback functionality for appropriate standalone usage.

📊 Sample Data

The application can be demonstrated using the following student records:

#	Student	Score	Grade	Status
1	Eshwar	95.50%	A	Passed
2	Jaydev	88.00%	B	Passed
3	Sonu	92.50%	A	Passed
4	Lohith	74.00%	C	Passed
5	Amar	85.00%	B	Passed
6	Lucky	68.50%	D	Passed
7	Nehal	54.00%	F	Failed
8	Isabella Rossi	98.50%	A	Passed
Example Statistics
Total Students : 8
Class Average  : 82.00%
Highest Score  : 98.50%
Lowest Score   : 54.00%
🧮 Grade Calculation Logic

The application uses threshold-based grade calculation.

if (score >= 90.0) {
    return "A";
}

if (score >= 80.0) {
    return "B";
}

if (score >= 70.0) {
    return "C";
}

if (score >= 60.0) {
    return "D";
}

return "F";
Pass/Fail
score >= 60.0
🎯 Learning Objectives

This project demonstrates practical knowledge of:

Core Java
Classes
Objects
Methods
Constructors
Encapsulation
Exception handling
Input validation
Object-Oriented Programming
Data modeling
Encapsulation
Separation of responsibilities
Business logic organization
Java Collections
ArrayList
Iteration
Searching
Updating elements
Removing elements
Aggregate calculations
Backend Development
HTTP server creation
HTTP request handling
API routing
JSON responses
Static file serving
Multithreaded request handling
Frontend Development
HTML structure
CSS styling
Responsive layouts
JavaScript
DOM manipulation
Fetch API
LocalStorage
Clipboard API
📸 Screenshots
Main Dashboard("D:\CodeAlpha Ptojects\StudentGradeTracker\Screenshot 2026-10-08 215316.png")

The dashboard provides a complete overview of student performance and allows users to manage student records.

Summary Report("D:\CodeAlpha Ptojects\StudentGradeTracker\Screenshot 2026-10-08 215332.png")

The Summary Report provides a structured overview of class performance and individual student results.

🔮 Future Enhancements

The current version focuses on Core Java, Collections, HTTP communication, and lightweight web development.

Possible future improvements include:

 MySQL database integration
 Student ID / Roll Number
 Subject-wise marks
 Multiple subjects per student
 Semester-wise grade management
 Search functionality
 Student filtering
 Sorting by score
 Sorting by name
 Attendance management
 User authentication
 Admin dashboard
 PDF report generation
 CSV export/import
 Graphs and performance charts
 Student performance history
 JUnit automated testing
 Spring Boot implementation
 Cloud deployment
🤝 Contributing

Contributions, suggestions, and improvements are welcome.

Fork the Repository

Create your own fork of the repository on GitHub.

Create a Feature Branch
git checkout -b feature/your-feature
Make Your Changes

Implement and test your changes.

Commit Your Changes
git add .
git commit -m "Add: your feature"
Push the Branch
git push origin feature/your-feature

Then open a Pull Request.

📄 License

This project is primarily created for educational and academic purposes.

You are free to study, modify, and extend the project according to your requirements.

👨‍💻 Author
Chidananda P

Computer Science & Engineering Student

GitHub

https://github.com/chidananda-p

Project Repository

https://github.com/chidananda-p/CodeAlpha_Student_Grade_Tracker

⭐ Support

If you find this project useful for learning Core Java, OOP, Collections, HTTP servers, and web application development, consider giving the repository a ⭐ on GitHub.

📌 Project Highlights
╔══════════════════════════════════════════════╗
║          STUDENT GRADE TRACKER              ║
╠══════════════════════════════════════════════╣
║                                              ║
║  ✓ Core Java                                 ║
║  ✓ Object-Oriented Programming               ║
║  ✓ Java Collections / ArrayList              ║
║  ✓ CRUD Operations                           ║
║  ✓ Java HTTP Server                          ║
║  ✓ REST-Style API                            ║
║  ✓ JSON Communication                        ║
║  ✓ HTML5                                     ║
║  ✓ CSS3                                      ║
║  ✓ Vanilla JavaScript                        ║
║  ✓ Responsive User Interface                 ║
║  ✓ Automatic Grade Calculation               ║
║  ✓ Class Statistics                          ║
║  ✓ Summary Report Generation                 ║
║  ✓ Clipboard Support                         ║
║  ✓ Text Report Download                      ║
║  ✓ Input Validation                           ║
║                                              ║
║  Database         : Not Required             ║
║  Framework        : None                     ║
║  External Server  : Not Required             ║
║                                              ║
╚══════════════════════════════════════════════╝
🚀 Built With

Core Java + Java HTTP Server + HTML5 + CSS3 + Vanilla JavaScript

A lightweight academic grade management application demonstrating how fundamental Java programming concepts can be combined with web technologies to build a complete, functional, and professional application.
