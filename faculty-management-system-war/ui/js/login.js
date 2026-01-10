// API Configuration
const API_URL = 'http://localhost:8080/faculty-management-system/api/login';

document.getElementById('loginForm').addEventListener('submit', handleLogin);

function handleLogin(e) {
    e.preventDefault();
    
    const username = document.getElementById('username').value;
    const password = document.getElementById('password').value;
    const role = document.getElementById('role').value;
    
    // Hide any previous error
    hideError();
    
    // Show loading state
    const submitBtn = document.querySelector('.btn-login');
    const originalText = submitBtn.textContent;
    submitBtn.textContent = 'Logging in...';
    submitBtn.disabled = true;
    
    // Send login request
    fetch(API_URL, {
        method: 'POST',
        headers: { 'Content-Type': 'application/json' },
        body: JSON.stringify({ username, password, role })
    })
    .then(response => response.json())
    .then(data => {
        submitBtn.textContent = originalText;
        submitBtn.disabled = false;
        
        if (data.success) {
            // Store user info in session storage
            sessionStorage.setItem('user', JSON.stringify({
                username: username,
                role: data.role
            }));
            
            // Redirect based on role
            redirectToDashboard(data.role);
        } else {
            showError(data.message || 'Invalid username, password, or role');
        }
    })
    .catch(error => {
        submitBtn.textContent = originalText;
        submitBtn.disabled = false;
        console.error('Login error:', error);
        showError('Login failed. Please make sure the server is running.');
    });
}

function redirectToDashboard(role) {
    // For now, redirect to index page
    // Later you can create role-specific dashboards
    window.location.href = 'index.html';
}

function showError(message) {
    const errorDiv = document.getElementById('errorMessage');
    errorDiv.textContent = message;
    errorDiv.classList.add('show');
}

function hideError() {
    const errorDiv = document.getElementById('errorMessage');
    errorDiv.classList.remove('show');
}

// Check if user is already logged in
window.addEventListener('DOMContentLoaded', function() {
    const user = sessionStorage.getItem('user');
    if (user) {
        const userData = JSON.parse(user);
        window.location.href = 'index.html';
    }
});
