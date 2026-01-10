// API Configuration
const API_URL = 'http://localhost:8080/faculty-management-system/api/courses';

let courses = [];
let editingCourseId = null;

// Initialize
document.addEventListener('DOMContentLoaded', function() {
    loadCourses();
    setupEventListeners();
});

function setupEventListeners() {
    document.getElementById('courseForm').addEventListener('submit', handleFormSubmit);
    document.getElementById('searchInput').addEventListener('input', handleSearch);
}

function handleFormSubmit(e) {
    e.preventDefault();
    
    const courseId = document.getElementById('courseId').value;
    const courseName = document.getElementById('courseName').value;
    const credits = parseInt(document.getElementById('credits').value);
    const lecturerId = document.getElementById('lecturerId').value;
    
    const editMode = document.getElementById('editMode').value === 'true';
    
    if (editMode) {
        updateCourse(editingCourseId, { courseId, courseName, credits, lecturerId });
    } else {
        addCourse({ courseId, courseName, credits, lecturerId });
    }
    
    resetForm();
}

function addCourse(course) {
    fetch(API_URL, {
        method: 'POST',
        headers: { 'Content-Type': 'application/json' },
        body: JSON.stringify(course)
    })
    .then(response => response.json())
    .then(data => {
        if (data.success) {
            alert(data.message);
            loadCourses();
        } else {
            alert(data.message);
        }
    })
    .catch(error => {
        console.error('Error:', error);
        alert('Failed to add course');
    });
}

function updateCourse(oldId, course) {
    fetch(API_URL, {
        method: 'PUT',
        headers: { 'Content-Type': 'application/json' },
        body: JSON.stringify(course)
    })
    .then(response => response.json())
    .then(data => {
        if (data.success) {
            alert(data.message);
            loadCourses();
        } else {
            alert(data.message);
        }
    })
    .catch(error => {
        console.error('Error:', error);
        alert('Failed to update course');
    });
}

function deleteCourse(courseId) {
    if (confirm('Are you sure you want to delete this course?')) {
        fetch(`${API_URL}?id=${courseId}`, {
            method: 'DELETE'
        })
        .then(response => response.json())
        .then(data => {
            if (data.success) {
                alert(data.message);
                loadCourses();
            } else {
                alert(data.message);
            }
        })
        .catch(error => {
            console.error('Error:', error);
            alert('Failed to delete course');
        });
    }
}

function editCourse(courseId) {
    const course = courses.find(c => c.courseId === courseId);
    if (course) {
        document.getElementById('courseId').value = course.courseId;
        document.getElementById('courseName').value = course.courseName;
        document.getElementById('credits').value = course.credits;
        document.getElementById('lecturerId').value = course.lecturerId;
        
        document.getElementById('editMode').value = 'true';
        document.getElementById('form-title').textContent = 'Edit Course';
        editingCourseId = courseId;
        
        // Scroll to form
        document.querySelector('.form-section').scrollIntoView({ behavior: 'smooth' });
    }
}

function loadCourses() {
    fetch(API_URL)
        .then(response => response.json())
        .then(data => {
            courses = data;
            const tbody = document.getElementById('courseTableBody');
            tbody.innerHTML = '';
            
            courses.forEach(course => {
                const row = createCourseRow(course);
                tbody.appendChild(row);
            });
        })
        .catch(error => {
            console.error('Error loading courses:', error);
            alert('Failed to load courses from database');
        });
}

function createCourseRow(course) {
    const tr = document.createElement('tr');
    tr.innerHTML = `
        <td>${course.courseId}</td>
        <td>${course.courseName}</td>
        <td>${course.credits}</td>
        <td>${course.lecturerId}</td>
        <td>
            <button class="btn btn-edit" onclick="editCourse('${course.courseId}')">Edit</button>
            <button class="btn btn-delete" onclick="deleteCourse('${course.courseId}')">Delete</button>
        </td>
    `;
    return tr;
}

function handleSearch(e) {
    const searchTerm = e.target.value.toLowerCase();
    const filteredCourses = courses.filter(course => 
        course.courseId.toLowerCase().includes(searchTerm) ||
        course.courseName.toLowerCase().includes(searchTerm) ||
        course.lecturerId.toLowerCase().includes(searchTerm)
    );
    
    const tbody = document.getElementById('courseTableBody');
    tbody.innerHTML = '';
    
    filteredCourses.forEach(course => {
        const row = createCourseRow(course);
        tbody.appendChild(row);
    });
}

function resetForm() {
    document.getElementById('courseForm').reset();
    document.getElementById('editMode').value = 'false';
    document.getElementById('form-title').textContent = 'Add New Course';
    editingCourseId = null;
}
