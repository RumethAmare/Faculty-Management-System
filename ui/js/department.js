// API Configuration
const API_URL = 'http://localhost:8080/faculty-management-system/api/departments';

let departments = [];
let editingDeptId = null;

// Initialize
document.addEventListener('DOMContentLoaded', function() {
    loadDepartments();
    setupEventListeners();
});

function setupEventListeners() {
    document.getElementById('departmentForm').addEventListener('submit', handleFormSubmit);
    document.getElementById('searchInput').addEventListener('input', handleSearch);
}

function handleFormSubmit(e) {
    e.preventDefault();
    
    const id = document.getElementById('deptId').value;
    const name = document.getElementById('deptName').value;
    const hod = document.getElementById('hod').value;
    const staffCount = parseInt(document.getElementById('staffCount').value);
    
    const editMode = document.getElementById('editMode').value === 'true';
    
    if (editMode) {
        updateDepartment(editingDeptId, { id, name, hod, staffCount });
    } else {
        addDepartment({ id, name, hod, staffCount });
    }
    
    resetForm();
}

function addDepartment(department) {
    fetch(API_URL, {
        method: 'POST',
        headers: { 'Content-Type': 'application/json' },
        body: JSON.stringify(department)
    })
    .then(response => response.json())
    .then(data => {
        if (data.success) {
            alert(data.message);
            loadDepartments();
        } else {
            alert(data.message);
        }
    })
    .catch(error => {
        console.error('Error:', error);
        alert('Failed to add department');
    });
}

function updateDepartment(oldId, department) {
    fetch(API_URL, {
        method: 'PUT',
        headers: { 'Content-Type': 'application/json' },
        body: JSON.stringify(department)
    })
    .then(response => response.json())
    .then(data => {
        if (data.success) {
            alert(data.message);
            loadDepartments();
        } else {
            alert(data.message);
        }
    })
    .catch(error => {
        console.error('Error:', error);
        alert('Failed to update department');
    });
}

function deleteDepartment(id) {
    if (confirm('Are you sure you want to delete this department?')) {
        fetch(`${API_URL}?id=${id}`, {
            method: 'DELETE'
        })
        .then(response => response.json())
        .then(data => {
            if (data.success) {
                alert(data.message);
                loadDepartments();
            } else {
                alert(data.message);
            }
        })
        .catch(error => {
            console.error('Error:', error);
            alert('Failed to delete department');
        });
    }
}

function editDepartment(id) {
    const department = departments.find(d => d.id === id);
    if (department) {
        document.getElementById('deptId').value = department.id;
        document.getElementById('deptName').value = department.name;
        document.getElementById('hod').value = department.hod;
        document.getElementById('staffCount').value = department.staffCount;
        
        document.getElementById('editMode').value = 'true';
        document.getElementById('form-title').textContent = 'Edit Department';
        editingDeptId = id;
        
        // Scroll to form
        document.querySelector('.form-section').scrollIntoView({ behavior: 'smooth' });
    }
}

function loadDepartments() {
    fetch(API_URL)
        .then(response => response.json())
        .then(data => {
            departments = data;
            const tbody = document.getElementById('departmentTableBody');
            tbody.innerHTML = '';
            
            departments.forEach(department => {
                const row = createDepartmentRow(department);
                tbody.appendChild(row);
            });
        })
        .catch(error => {
            console.error('Error loading departments:', error);
            alert('Failed to load departments from database');
        });
}

function createDepartmentRow(department) {
    const tr = document.createElement('tr');
    tr.innerHTML = `
        <td>${department.id}</td>
        <td>${department.name}</td>
        <td>${department.hod}</td>
        <td>${department.staffCount}</td>
        <td>
            <button class="btn btn-edit" onclick="editDepartment('${department.id}')">Edit</button>
            <button class="btn btn-delete" onclick="deleteDepartment('${department.id}')">Delete</button>
        </td>
    `;
    return tr;
}

function handleSearch(e) {
    const searchTerm = e.target.value.toLowerCase();
    const filteredDepartments = departments.filter(department => 
        department.id.toLowerCase().includes(searchTerm) ||
        department.name.toLowerCase().includes(searchTerm) ||
        department.hod.toLowerCase().includes(searchTerm)
    );
    
    const tbody = document.getElementById('departmentTableBody');
    tbody.innerHTML = '';
    
    filteredDepartments.forEach(department => {
        const row = createDepartmentRow(department);
        tbody.appendChild(row);
    });
}

function resetForm() {
    document.getElementById('departmentForm').reset();
    document.getElementById('editMode').value = 'false';
    document.getElementById('form-title').textContent = 'Add New Department';
    editingDeptId = null;
}
