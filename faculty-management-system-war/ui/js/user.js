// API Configuration
const API_URL = 'http://localhost:8080/faculty-management-system/api/users';

let users = [];
let editingUsername = null;

// Initialize
document.addEventListener('DOMContentLoaded', function() {
    loadUsers();
    setupEventListeners();
});

function setupEventListeners() {
    document.getElementById('userForm').addEventListener('submit', handleFormSubmit);
    document.getElementById('searchInput').addEventListener('input', handleSearch);
}

function handleFormSubmit(e) {
    e.preventDefault();
    
    const username = document.getElementById('username').value;
    const password = document.getElementById('password').value;
    const role = document.getElementById('role').value;
    
    const editMode = document.getElementById('editMode').value === 'true';
    
    if (editMode) {
        updateUser(editingUsername, { username, password, role });
    } else {
        addUser({ username, password, role });
    }
    
    resetForm();
}

function addUser(user) {
    fetch(API_URL, {
        method: 'POST',
        headers: { 'Content-Type': 'application/json' },
        body: JSON.stringify(user)
    })
    .then(response => response.json())
    .then(data => {
        if (data.success) {
            alert(data.message);
            loadUsers();
        } else {
            alert(data.message);
        }
    })
    .catch(error => {
        console.error('Error:', error);
        alert('Failed to add user');
    });
}

function updateUser(oldUsername, user) {
    fetch(API_URL, {
        method: 'PUT',
        headers: { 'Content-Type': 'application/json' },
        body: JSON.stringify(user)
    })
    .then(response => response.json())
    .then(data => {
        if (data.success) {
            alert(data.message);
            loadUsers();
        } else {
            alert(data.message);
        }
    })
    .catch(error => {
        console.error('Error:', error);
        alert('Failed to update user');
    });
}

function deleteUser(username) {
    if (confirm('Are you sure you want to delete this user?')) {
        fetch(`${API_URL}?id=${username}`, {
            method: 'DELETE'
        })
        .then(response => response.json())
        .then(data => {
            if (data.success) {
                alert(data.message);
                loadUsers();
            } else {
                alert(data.message);
            }
        })
        .catch(error => {
            console.error('Error:', error);
            alert('Failed to delete user');
        });
    }
}

function editUser(username) {
    const user = users.find(u => u.username === username);
    if (user) {
        document.getElementById('username').value = user.username;
        document.getElementById('password').value = user.password;
        document.getElementById('role').value = user.role;
        
        document.getElementById('editMode').value = 'true';
        document.getElementById('form-title').textContent = 'Edit User';
        editingUsername = username;
        
        // Scroll to form
        document.querySelector('.form-section').scrollIntoView({ behavior: 'smooth' });
    }
}

function loadUsers() {
    fetch(API_URL)
        .then(response => response.json())
        .then(data => {
            users = data;
            const tbody = document.getElementById('userTableBody');
            tbody.innerHTML = '';
            
            users.forEach(user => {
                const row = createUserRow(user);
                tbody.appendChild(row);
            });
        })
        .catch(error => {
            console.error('Error loading users:', error);
            alert('Failed to load users from database');
        });
}

function createUserRow(user) {
    const tr = document.createElement('tr');
    tr.innerHTML = `
        <td>${user.username}</td>
        <td><span class="role-badge ${user.role}">${user.role}</span></td>
        <td>
            <button class="btn btn-edit" onclick="editUser('${user.username}')">Edit</button>
            <button class="btn btn-delete" onclick="deleteUser('${user.username}')">Delete</button>
        </td>
    `;
    return tr;
}

function handleSearch(e) {
    const searchTerm = e.target.value.toLowerCase();
    const filteredUsers = users.filter(user => 
        user.username.toLowerCase().includes(searchTerm) ||
        user.role.toLowerCase().includes(searchTerm)
    );
    
    const tbody = document.getElementById('userTableBody');
    tbody.innerHTML = '';
    
    filteredUsers.forEach(user => {
        const row = createUserRow(user);
        tbody.appendChild(row);
    });
}

function resetForm() {
    document.getElementById('userForm').reset();
    document.getElementById('editMode').value = 'false';
    document.getElementById('form-title').textContent = 'Add New User';
    editingUsername = null;
}
