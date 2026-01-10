// API Configuration
const API_URL = 'http://localhost:8080/faculty-management-system/api/lecturers';

let lecturers = [];
let editingLecturerId = null;

// Initialize
document.addEventListener('DOMContentLoaded', function() {
    loadLecturers();
    setupEventListeners();
});

function setupEventListeners() {
    document.getElementById('lecturerForm').addEventListener('submit', handleFormSubmit);
    document.getElementById('searchInput').addEventListener('input', handleSearch);
}

function handleFormSubmit(e) {
    e.preventDefault();
    
    const id = document.getElementById('lecturerId').value;
    const name = document.getElementById('lecturerName').value;
    const email = document.getElementById('email').value;
    const department = document.getElementById('department').value;
    
    const editMode = document.getElementById('editMode').value === 'true';
    
    if (editMode) {
        updateLecturer(editingLecturerId, { id, name, email, department });
    } else {
        addLecturer({ id, name, email, department });
    }
    
    resetForm();
}

function addLecturer(lecturer) {
    fetch(API_URL, {
        method: 'POST',
        headers: { 'Content-Type': 'application/json' },
        body: JSON.stringify(lecturer)
    })
    .then(response => response.json())
    .then(data => {
        if (data.success) {
            alert(data.message);
            loadLecturers();
        } else {
            alert(data.message);
        }
    })
    .catch(error => {
        console.error('Error:', error);
        alert('Failed to add lecturer');
    });
}

function updateLecturer(oldId, lecturer) {
    fetch(API_URL, {
        method: 'PUT',
        headers: { 'Content-Type': 'application/json' },
        body: JSON.stringify(lecturer)
    })
    .then(response => response.json())
    .then(data => {
        if (data.success) {
            alert(data.message);
            loadLecturers();
        } else {
            alert(data.message);
        }
    })
    .catch(error => {
        console.error('Error:', error);
        alert('Failed to update lecturer');
    });
}

function deleteLecturer(id) {
    if (confirm('Are you sure you want to delete this lecturer?')) {
        fetch(`${API_URL}?id=${id}`, {
            method: 'DELETE'
        })
        .then(response => response.json())
        .then(data => {
            if (data.success) {
                alert(data.message);
                loadLecturers();
            } else {
                alert(data.message);
            }
        })
        .catch(error => {
            console.error('Error:', error);
            alert('Failed to delete lecturer');
        });
    }
}

function editLecturer(id) {
    const lecturer = lecturers.find(l => l.id === id);
    if (lecturer) {
        document.getElementById('lecturerId').value = lecturer.id;
        document.getElementById('lecturerName').value = lecturer.name;
        document.getElementById('email').value = lecturer.email;
        document.getElementById('department').value = lecturer.department;
        
        document.getElementById('editMode').value = 'true';
        document.getElementById('form-title').textContent = 'Edit Lecturer';
        editingLecturerId = id;
        
        // Scroll to form
        document.querySelector('.form-section').scrollIntoView({ behavior: 'smooth' });
    }
}

function loadLecturers() {
    fetch(API_URL)
        .then(response => response.json())
        .then(data => {
            lecturers = data;
            const tbody = document.getElementById('lecturerTableBody');
            tbody.innerHTML = '';
            
            lecturers.forEach(lecturer => {
                const row = createLecturerRow(lecturer);
                tbody.appendChild(row);
            });
        })
        .catch(error => {
            console.error('Error loading lecturers:', error);
            alert('Failed to load lecturers from database');
        });
}

function createLecturerRow(lecturer) {
    const tr = document.createElement('tr');
    tr.innerHTML = `
        <td>${lecturer.id}</td>
        <td>${lecturer.name}</td>
        <td>${lecturer.email}</td>
        <td>${lecturer.department}</td>
        <td>
            <button class="btn btn-edit" onclick="editLecturer('${lecturer.id}')">Edit</button>
            <button class="btn btn-delete" onclick="deleteLecturer('${lecturer.id}')">Delete</button>
        </td>
    `;
    return tr;
}

function handleSearch(e) {
    const searchTerm = e.target.value.toLowerCase();
    const filteredLecturers = lecturers.filter(lecturer => 
        lecturer.id.toLowerCase().includes(searchTerm) ||
        lecturer.name.toLowerCase().includes(searchTerm) ||
        lecturer.email.toLowerCase().includes(searchTerm) ||
        lecturer.department.toLowerCase().includes(searchTerm)
    );
    
    const tbody = document.getElementById('lecturerTableBody');
    tbody.innerHTML = '';
    
    filteredLecturers.forEach(lecturer => {
        const row = createLecturerRow(lecturer);
        tbody.appendChild(row);
    });
}

function resetForm() {
    document.getElementById('lecturerForm').reset();
    document.getElementById('editMode').value = 'false';
    document.getElementById('form-title').textContent = 'Add New Lecturer';
    editingLecturerId = null;
}
