/**
 * Student Grade Tracker - Simple, Clean & Responsive Frontend Controller
 */

// Initial demonstration records
const DEFAULT_STUDENTS = [
  { name: "Emma Watson", score: 95.5 },
  { name: "James Rodriguez", score: 88.0 },
  { name: "Sophia Chen", score: 92.5 },
  { name: "Liam O'Connor", score: 74.0 },
  { name: "Amara Patel", score: 85.0 },
  { name: "Lucas Silva", score: 68.5 },
  { name: "Noah Taylor", score: 54.0 },
  { name: "Isabella Rossi", score: 98.5 }
];

class GradeTrackerApp {
  constructor() {
    this.students = [];
    this.editIndex = -1;
    this.isServerConnected = false;

    this.initElements();
    this.attachEvents();
    this.loadData();
  }

  initElements() {
    this.kpiAverage = document.getElementById("kpiAverage");
    this.kpiHighest = document.getElementById("kpiHighest");
    this.kpiHighestStudent = document.getElementById("kpiHighestStudent");
    this.kpiLowest = document.getElementById("kpiLowest");
    this.kpiLowestStudent = document.getElementById("kpiLowestStudent");
    this.kpiTotal = document.getElementById("kpiTotal");

    this.gradeForm = document.getElementById("gradeForm");
    this.formTitle = document.getElementById("formTitle");
    this.studentName = document.getElementById("studentName");
    this.studentScore = document.getElementById("studentScore");
    this.btnSubmitForm = document.getElementById("btnSubmitForm");
    this.btnCancelEdit = document.getElementById("btnCancelEdit");

    this.tableBody = document.getElementById("studentsTableBody");
    this.recordCount = document.getElementById("recordCount");
    this.emptyMessage = document.getElementById("emptyMessage");

    this.reportModal = document.getElementById("reportModal");
    this.reportContent = document.getElementById("reportContent");
    this.toast = document.getElementById("toast");
  }

  attachEvents() {
    this.gradeForm.addEventListener("submit", (e) => this.handleFormSubmit(e));
    this.btnCancelEdit.addEventListener("click", () => this.cancelEdit());

    document.getElementById("btnOpenReport").addEventListener("click", () => this.openReportModal());
    document.getElementById("btnCloseReportModal").addEventListener("click", () => this.reportModal.close());
    document.getElementById("btnCloseReport").addEventListener("click", () => this.reportModal.close());
    document.getElementById("btnCopyReport").addEventListener("click", () => this.copyReport());
    document.getElementById("btnDownloadReport").addEventListener("click", () => this.downloadReport());

    document.getElementById("btnResetData").addEventListener("click", () => this.resetData());
  }

  async loadData() {
    try {
      const res = await fetch("/api/students");
      if (res.ok) {
        const data = await res.json();
        this.students = data.map(s => ({ name: s.name, score: Number(s.score) }));
        this.isServerConnected = true;
      } else {
        throw new Error("API not available");
      }
    } catch {
      // Fallback mode for direct file:// execution
      this.isServerConnected = false;
      const cached = localStorage.getItem("sgt_students");
      if (cached) {
        try {
          this.students = JSON.parse(cached);
        } catch {
          this.students = [...DEFAULT_STUDENTS];
        }
      } else {
        this.students = [...DEFAULT_STUDENTS];
      }
    }

    this.render();
  }

  // --- Grade Calculation Engine ---

  getScoresArray() {
    return this.students.map(s => s.score);
  }

  calculateAverage() {
    const scores = this.getScoresArray();
    if (scores.length === 0) return 0.0;
    const sum = scores.reduce((a, b) => a + b, 0);
    return Math.round((sum / scores.length) * 100) / 100;
  }

  calculateHighest() {
    const scores = this.getScoresArray();
    if (scores.length === 0) return 0.0;
    return Math.max(...scores);
  }

  calculateLowest() {
    const scores = this.getScoresArray();
    if (scores.length === 0) return 0.0;
    return Math.min(...scores);
  }

  getHighestStudent() {
    if (this.students.length === 0) return null;
    let top = this.students[0];
    for (let i = 1; i < this.students.length; i++) {
      if (this.students[i].score > top.score) top = this.students[i];
    }
    return top;
  }

  getLowestStudent() {
    if (this.students.length === 0) return null;
    let low = this.students[0];
    for (let i = 1; i < this.students.length; i++) {
      if (this.students[i].score < low.score) low = this.students[i];
    }
    return low;
  }

  getLetterGrade(score) {
    if (score >= 90.0) return "A";
    if (score >= 80.0) return "B";
    if (score >= 70.0) return "C";
    if (score >= 60.0) return "D";
    return "F";
  }

  // --- UI Rendering ---

  render() {
    // 1. Update KPI Cards
    const avg = this.calculateAverage();
    const highest = this.calculateHighest();
    const lowest = this.calculateLowest();
    const topStudent = this.getHighestStudent();
    const lowStudent = this.getLowestStudent();

    this.kpiAverage.textContent = `${avg.toFixed(2)}%`;
    this.kpiHighest.textContent = `${highest.toFixed(2)}%`;
    this.kpiHighestStudent.textContent = topStudent ? topStudent.name : "None";
    this.kpiLowest.textContent = `${lowest.toFixed(2)}%`;
    this.kpiLowestStudent.textContent = lowStudent ? lowStudent.name : "None";
    this.kpiTotal.textContent = this.students.length;

    // 2. Update Table
    this.tableBody.innerHTML = "";
    if (this.students.length === 0) {
      this.emptyMessage.classList.remove("hidden");
    } else {
      this.emptyMessage.classList.add("hidden");
    }

    this.students.forEach((s, idx) => {
      const tr = document.createElement("tr");
      const grade = this.getLetterGrade(s.score);
      const isPassed = s.score >= 60.0;

      tr.innerHTML = `
        <td style="color: var(--text-muted);">${idx + 1}</td>
        <td><strong>${s.name}</strong></td>
        <td>${s.score.toFixed(2)}%</td>
        <td><span class="badge badge-${grade.toLowerCase()}">${grade}</span></td>
        <td><span class="badge ${isPassed ? 'badge-pass' : 'badge-fail'}">${isPassed ? 'Passed' : 'Failed'}</span></td>
        <td style="text-align: right;">
          <button class="btn-action btn-edit" data-idx="${idx}">Edit</button>
          <button class="btn-action btn-danger btn-del" data-idx="${idx}">Delete</button>
        </td>
      `;

      this.tableBody.appendChild(tr);
    });

    this.recordCount.textContent = `${this.students.length} students`;

    // Row Action Events
    this.tableBody.querySelectorAll(".btn-edit").forEach(b => {
      b.addEventListener("click", () => this.startEdit(Number(b.dataset.idx)));
    });

    this.tableBody.querySelectorAll(".btn-del").forEach(b => {
      b.addEventListener("click", () => this.deleteStudent(Number(b.dataset.idx)));
    });
  }

  // --- Form & CRUD Actions ---

  async handleFormSubmit(e) {
    e.preventDefault();
    const name = this.studentName.value.trim();
    const score = parseFloat(this.studentScore.value);

    if (!name || isNaN(score) || score < 0 || score > 100) {
      alert("Please enter a valid student name and a score between 0.0 and 100.0.");
      return;
    }

    if (this.editIndex >= 0) {
      // Update existing student
      if (this.isServerConnected) {
        try {
          await fetch(`/api/students?index=${this.editIndex}`, {
            method: "PUT",
            headers: { "Content-Type": "application/json" },
            body: JSON.stringify({ name, score })
          });
        } catch {}
      }
      this.students[this.editIndex] = { name, score };
      this.showToast(`Updated record for ${name}.`);
      this.cancelEdit();
    } else {
      // Add new student
      if (this.isServerConnected) {
        try {
          await fetch("/api/students", {
            method: "POST",
            headers: { "Content-Type": "application/json" },
            body: JSON.stringify({ name, score })
          });
        } catch {}
      }
      this.students.push({ name, score });
      this.showToast(`Added ${name} (${score}%).`);
      this.gradeForm.reset();
    }

    this.saveLocal();
    this.render();
  }

  startEdit(index) {
    this.editIndex = index;
    const s = this.students[index];
    this.formTitle.textContent = "Edit Student Grade";
    this.studentName.value = s.name;
    this.studentScore.value = s.score;
    this.btnSubmitForm.textContent = "Save Changes";
    this.btnCancelEdit.classList.remove("hidden");
    this.studentName.focus();
  }

  cancelEdit() {
    this.editIndex = -1;
    this.formTitle.textContent = "Add Student Grade";
    this.gradeForm.reset();
    this.btnSubmitForm.textContent = "Add Student";
    this.btnCancelEdit.classList.add("hidden");
  }

  async deleteStudent(index) {
    const s = this.students[index];
    if (!confirm(`Are you sure you want to delete ${s.name}?`)) return;

    if (this.isServerConnected) {
      try {
        await fetch(`/api/students?index=${index}`, { method: "DELETE" });
      } catch {}
    }

    this.students.splice(index, 1);
    if (this.editIndex === index) {
      this.cancelEdit();
    }
    this.saveLocal();
    this.render();
    this.showToast(`Deleted ${s.name}.`);
  }

  // --- Summary Report ---

  async openReportModal() {
    let report = "";
    if (this.isServerConnected) {
      try {
        const res = await fetch("/api/report");
        if (res.ok) {
          report = await res.text();
        }
      } catch {}
    }

    if (!report) {
      report = this.generateLocalReport();
    }

    this.reportContent.textContent = report;
    this.reportModal.showModal();
  }

  generateLocalReport() {
    const date = new Date().toLocaleString();
    const avg = this.calculateAverage();
    const highest = this.calculateHighest();
    const lowest = this.calculateLowest();
    const top = this.getHighestStudent();
    const low = this.getLowestStudent();

    let txt = "========================================================================\n";
    txt += "                    STUDENT GRADE SUMMARY REPORT                        \n";
    txt += `                    Generated on: ${date}\n`;
    txt += "========================================================================\n\n";

    txt += "------------------------------------------------------------------------\n";
    txt += " 1. OVERALL CLASS STATISTICS\n";
    txt += "------------------------------------------------------------------------\n";
    txt += `  * Total Students : ${this.students.length}\n`;
    txt += `  * Class Average  : ${avg.toFixed(2)}%\n`;
    txt += `  * Highest Score  : ${highest.toFixed(2)}% (${top ? top.name : 'N/A'})\n`;
    txt += `  * Lowest Score   : ${lowest.toFixed(2)}% (${low ? low.name : 'N/A'})\n\n`;

    txt += "------------------------------------------------------------------------\n";
    txt += " 2. COMPLETE STUDENT ROSTER\n";
    txt += "------------------------------------------------------------------------\n";
    txt += "No.    | Student Name               | Score    | Grade  | Status  \n";
    txt += "------------------------------------------------------------------------\n";

    this.students.forEach((s, i) => {
      const no = String(i + 1).padEnd(6, " ");
      const name = (s.name.length > 26 ? s.name.substring(0, 24) + ".." : s.name).padEnd(26, " ");
      const score = `${s.score.toFixed(2)}%`.padStart(8, " ");
      const grade = this.getLetterGrade(s.score).padEnd(6, " ");
      const status = (s.score >= 60.0 ? "Passed" : "Failed").padEnd(8, " ");
      txt += `${no} | ${name} | ${score} | ${grade} | ${status}\n`;
    });

    txt += "------------------------------------------------------------------------\n";
    txt += "                         END OF SUMMARY REPORT                          \n";
    txt += "========================================================================\n";

    return txt;
  }

  copyReport() {
    navigator.clipboard.writeText(this.reportContent.textContent).then(() => {
      this.showToast("Summary report copied to clipboard!");
    });
  }

  downloadReport() {
    const blob = new Blob([this.reportContent.textContent], { type: "text/plain;charset=utf-8" });
    const url = URL.createObjectURL(blob);
    const a = document.createElement("a");
    a.href = url;
    a.download = "Student_Grade_Summary_Report.txt";
    a.click();
    URL.revokeObjectURL(url);
    this.showToast("Report downloaded successfully.");
  }

  async resetData() {
    if (!confirm("Reset to default demonstration grades?")) return;

    if (this.isServerConnected) {
      try {
        await fetch("/api/reset", { method: "POST" });
        await this.loadData();
      } catch {
        this.students = [...DEFAULT_STUDENTS];
      }
    } else {
      this.students = [...DEFAULT_STUDENTS];
    }

    this.saveLocal();
    this.render();
    this.showToast("Reset demo grades successfully.");
  }

  saveLocal() {
    localStorage.setItem("sgt_students", JSON.stringify(this.students));
  }

  showToast(msg) {
    this.toast.textContent = msg;
    this.toast.classList.remove("hidden");
    setTimeout(() => {
      this.toast.classList.add("hidden");
    }, 2800);
  }
}

// Initialize on page load
document.addEventListener("DOMContentLoaded", () => {
  window.app = new GradeTrackerApp();
});
