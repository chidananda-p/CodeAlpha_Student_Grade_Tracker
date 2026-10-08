# Student Grade Tracker
> **A Clean, Professional Java & Web Application for Student Grade Management**

---

## 1. Requirements Met

| Requirement | Implementation Details |
| :--- | :--- |
| **Manage Student Grades** | Input, update, delete, and manage student grades (`0.0` to `100.0`). |
| **Calculate Average, Highest, & Lowest** | High-performance calculation algorithms: `calculateAverage()`, `calculateHighest()`, `calculateLowest()`. |
| **Use Arrays or ArrayLists** | `ArrayList<Student>` for dynamic data storage, and `double[]` primitive arrays for calculation loops. |
| **Summary Report of All Students** | Formatted report detailing class average, top/lowest performers, and complete roster with grades. |
| **GUI using HTML, CSS, and JS** | Clean, neat, and modern web interface featuring KPI cards, input form, roster table with letter grade badges, and summary modal. |
| **No `.bat` Execution** | Directly compiled and run with standard `javac` and `java` commands. |

---

## 2. Project Structure

```
D:\Student Grade Tracker/
├── Student.java          # Student model (Name, Score, Letter Grade, Status)
├── GradeTracker.java     # ArrayList storage + double[] array statistical calculations
├── Main.java             # Terminal report printer & built-in Java HTTP server
├── web/
│   ├── index.html        # Clean, modern HTML5 GUI with KPI cards and table
│   ├── css/style.css     # Professional, neat Slate & Indigo CSS styling
│   └── js/app.js         # Frontend controller and API integration
└── README.md             # Project documentation
```

---

## 3. How to Compile & Run (No `.bat` Files)

### Step 1: Open Terminal in Project Folder
Open PowerShell or Command Prompt at:
```cmd
cd "D:\Student Grade Tracker"
```

### Step 2: Compile Java Files
```cmd
javac *.java
```

### Step 3: Run the Application
```cmd
java Main
```

What happens:
1. The **Summary Report** is printed immediately in your terminal.
2. The built-in Java server starts at `http://localhost:8080`.
3. Your default browser opens automatically to the modern web GUI!

---

## 4. Standalone Web GUI Mode (Optional)

You can also double-click `D:\Student Grade Tracker\web\index.html` directly in File Explorer to use the complete web interface offline in any browser.

---

## 5. Sample Terminal Summary Report

```
========================================================================
                    STUDENT GRADE SUMMARY REPORT                        
                    Generated on: 2026-09-17 13:46:26
========================================================================

------------------------------------------------------------------------
 1. OVERALL CLASS STATISTICS
------------------------------------------------------------------------
  * Total Students : 8
  * Class Average  : 82.00%
  * Highest Score  : 98.50% (Isabella Rossi)
  * Lowest Score   : 54.00% (Noah Taylor)

------------------------------------------------------------------------
 2. COMPLETE STUDENT ROSTER
------------------------------------------------------------------------
No.    | Student Name               | Score    | Grade  | Status  
------------------------------------------------------------------------
1      | Emma Watson                |  95.50%  | A      | Passed  
2      | James Rodriguez            |  88.00%  | B      | Passed  
3      | Sophia Chen                |  92.50%  | A      | Passed  
4      | Liam O'Connor              |  74.00%  | C      | Passed  
5      | Amara Patel                |  85.00%  | B      | Passed  
6      | Lucas Silva                |  68.50%  | D      | Passed  
7      | Noah Taylor                |  54.00%  | F      | Failed  
8      | Isabella Rossi             |  98.50%  | A      | Passed  
------------------------------------------------------------------------
                         END OF SUMMARY REPORT                          
========================================================================
```
