// API Configuration
const API_URL = 'http://localhost:8080/faculty-management-system/api/students';

let students = [];
let editingStudentId = null;

// Initialize
document.addEventListener('DOMContentLoaded', function() {
    loadStudents();
    setupEventListeners();
});

function setupEventListeners() {
    document.getElementById('studentForm').addEventListener('submit', handleFormSubmit);
    document.getElementById('searchInput').addEventListener('input', handleSearch);
}

function handleFormSubmit(e) {
    e.preventDefault();
    
    const id = document.getElementById('studentId').value;
    const name = document.getElementById('studentName').value;
    const email = document.getElementById('email').value;
    const degree = document.getElementById('degree').value;
    
    const editMode = document.getElementById('editMode').value === 'true';
    
    if (editMode) {
        updateStudent(editingStudentId, { id, name, email, degree });
    } else {
        addStudent({ id, name, email, degree });
    }
    
    resetForm();
}

function addStudent(student) {
    fetch(API_URL, {
        method: 'POST',
        headers: { 'Content-Type': 'application/json' },
        body: JSON.stringify(student)
    })
    .then(response => response.json())
    .then(data => {
        if (data.success) {
            alert(data.message);
            loadStudents();
        } else {
            alert(data.message);
        }
    })
    .catch(error => {
        console.error('Error:', error);
        alert('Failed to add student');
    });
}

function updateStudent(oldId, student) {
    fetch(API_URL, {
        method: 'PUT',
        headers: { 'Content-Type': 'application/json' },
        body: JSON.stringify(student)
    })
    .then(response => response.json())
    .then(data => {
        if (data.success) {
            alert(data.message);
            loadStudents();
        } else {
            alert(data.message);
        }
    })
    .catch(error => {
        console.error('Error:', error);
        alert('Failed to update student');
    });
}

function deleteStudent(id) {
    if (confirm('Are you sure you want to delete this student?')) {
        fetch(`${API_URL}?id=${id}`, {
            method: 'DELETE'
        })
        .then(response => response.json())
        .then(data => {
            if (data.success) {
                alert(data.message);
                loadStudents();
            } else {
                alert(data.message);
            }
        })
        .catch(error => {
            console.error('Error:', error);
            alert('Failed to delete student');
        });
    }
}

function editStudent(id) {
    const student = students.find(s => s.id === id);
    if (student) {
        document.getElementById('studentId').value = student.id;
        document.getElementById('studentName').value = student.name;
        document.getElementById('email').value = student.email;
        document.getElementById('degree').value = student.degree;
        
        document.getElementById('editMode').value = 'true';
        document.getElementById('form-title').textContent = 'Edit Student';
        editingStudentId = id;
        
        // Scroll to form
        document.querySelector('.form-section').scrollIntoView({ behavior: 'smooth' });
    }
}

function loadStudents() {
    fetch(API_URL)
        .then(response => response.json())
        .then(data => {
            students = data;
            const tbody = document.getElementById('studentTableBody');
            tbody.innerHTML = '';
            
            students.forEach(student => {
                const row = createStudentRow(student);
                tbody.appendChild(row);
            });
        })
        .catch(error => {
            console.error('Error loading students:', error);
            alert('Failed to load students from database');
        });
}

function createStudentRow(student) {
    const tr = document.createElement('tr');
    tr.innerHTML = `
        <td>${student.id}</td>
        <td>${student.name}</td>
        <td>${student.email}</td>
        <td>${student.degree}</td>
        <td>
            <button class="btn btn-edit" onclick="editStudent('${student.id}')">Edit</button>
            <button class="btn btn-delete" onclick="deleteStudent('${student.id}')">Delete</button>
        </td>
    `;
    return tr;
}

function handleSearch(e) {
    const searchTerm = e.target.value.toLowerCase();
    const filteredStudents = students.filter(student => 
        student.id.toLowerCase().includes(searchTerm) ||
        student.name.toLowerCase().includes(searchTerm) ||
        student.email.toLowerCase().includes(searchTerm) ||
        student.degree.toLowerCase().includes(searchTerm)
    );
    
    const tbody = document.getElementById('studentTableBody');
    tbody.innerHTML = '';
    
    filteredStudents.forEach(student => {
        const row = createStudentRow(student);
        tbody.appendChild(row);
    });
}

function resetForm() {
    document.getElementById('studentForm').reset();
    document.getElementById('editMode').value = 'false';
    document.getElementById('form-title').textContent = 'Add New Student';
    editingStudentId = null;
}
