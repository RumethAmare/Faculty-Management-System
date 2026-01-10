// API Configuration
const API_URL = 'http://localhost:8080/faculty-management-system/api/degrees';

let degrees = [];
let editingDegreeId = null;

// Initialize
document.addEventListener('DOMContentLoaded', function() {
    loadDegrees();
    setupEventListeners();
});

function setupEventListeners() {
    document.getElementById('degreeForm').addEventListener('submit', handleFormSubmit);
    document.getElementById('searchInput').addEventListener('input', handleSearch);
}

function handleFormSubmit(e) {
    e.preventDefault();
    
    const id = document.getElementById('degreeId').value;
    const name = document.getElementById('degreeName').value;
    const deptId = document.getElementById('deptId').value;
    
    const editMode = document.getElementById('editMode').value === 'true';
    
    if (editMode) {
        updateDegree(editingDegreeId, { id, name, deptId });
    } else {
        addDegree({ id, name, deptId });
    }
    
    resetForm();
}

function addDegree(degree) {
    fetch(API_URL, {
        method: 'POST',
        headers: { 'Content-Type': 'application/json' },
        body: JSON.stringify(degree)
    })
    .then(response => response.json())
    .then(data => {
        if (data.success) {
            alert(data.message);
            loadDegrees();
        } else {
            alert(data.message);
        }
    })
    .catch(error => {
        console.error('Error:', error);
        alert('Failed to add degree');
    });
}

function updateDegree(oldId, degree) {
    fetch(API_URL, {
        method: 'PUT',
        headers: { 'Content-Type': 'application/json' },
        body: JSON.stringify(degree)
    })
    .then(response => response.json())
    .then(data => {
        if (data.success) {
            alert(data.message);
            loadDegrees();
        } else {
            alert(data.message);
        }
    })
    .catch(error => {
        console.error('Error:', error);
        alert('Failed to update degree');
    });
}

function deleteDegree(id) {
    if (confirm('Are you sure you want to delete this degree?')) {
        fetch(`${API_URL}?id=${id}`, {
            method: 'DELETE'
        })
        .then(response => response.json())
        .then(data => {
            if (data.success) {
                alert(data.message);
                loadDegrees();
            } else {
                alert(data.message);
            }
        })
        .catch(error => {
            console.error('Error:', error);
            alert('Failed to delete degree');
        });
    }
}

function editDegree(id) {
    const degree = degrees.find(d => d.id === id);
    if (degree) {
        document.getElementById('degreeId').value = degree.id;
        document.getElementById('degreeName').value = degree.name;
        document.getElementById('deptId').value = degree.deptId;
        
        document.getElementById('editMode').value = 'true';
        document.getElementById('form-title').textContent = 'Edit Degree';
        editingDegreeId = id;
        
        // Scroll to form
        document.querySelector('.form-section').scrollIntoView({ behavior: 'smooth' });
    }
}

function loadDegrees() {
    fetch(API_URL)
        .then(response => response.json())
        .then(data => {
            degrees = data;
            const tbody = document.getElementById('degreeTableBody');
            tbody.innerHTML = '';
            
            degrees.forEach(degree => {
                const row = createDegreeRow(degree);
                tbody.appendChild(row);
            });
        })
        .catch(error => {
            console.error('Error loading degrees:', error);
            alert('Failed to load degrees from database');
        });
}

function createDegreeRow(degree) {
    const tr = document.createElement('tr');
    tr.innerHTML = `
        <td>${degree.id}</td>
        <td>${degree.name}</td>
        <td>${degree.deptId}</td>
        <td>
            <button class="btn btn-edit" onclick="editDegree('${degree.id}')">Edit</button>
            <button class="btn btn-delete" onclick="deleteDegree('${degree.id}')">Delete</button>
        </td>
    `;
    return tr;
}

function handleSearch(e) {
    const searchTerm = e.target.value.toLowerCase();
    const filteredDegrees = degrees.filter(degree => 
        degree.id.toLowerCase().includes(searchTerm) ||
        degree.name.toLowerCase().includes(searchTerm) ||
        degree.deptId.toLowerCase().includes(searchTerm)
    );
    
    const tbody = document.getElementById('degreeTableBody');
    tbody.innerHTML = '';
    
    filteredDegrees.forEach(degree => {
        const row = createDegreeRow(degree);
        tbody.appendChild(row);
    });
}

function resetForm() {
    document.getElementById('degreeForm').reset();
    document.getElementById('editMode').value = 'false';
    document.getElementById('form-title').textContent = 'Add New Degree';
    editingDegreeId = null;
}
