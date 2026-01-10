# Faculty Management System - Web UI Setup

## Prerequisites
1. XAMPP installed with Apache Tomcat
2. MySQL database running (via XAMPP)
3. Java JDK 8 or higher
4. Servlet API and GSON library

## Step 1: Download Required Libraries

Download these JAR files and place them in a `lib` folder:
1. **servlet-api.jar** (from Tomcat's lib folder: `C:\xampp\tomcat\lib\servlet-api.jar`)
2. **gson-2.10.1.jar** (Download from: https://repo1.maven.org/maven2/com/google/code/gson/gson/2.10.1/gson-2.10.1.jar)

## Step 2: Compile Servlets

Open PowerShell and run:
```powershell
cd C:\xampp\htdocs\faculty-management-system

# Create lib directory if it doesn't exist
mkdir lib -Force

# Copy servlet-api.jar from Tomcat
copy C:\xampp\tomcat\lib\servlet-api.jar lib\

# Compile all servlets
javac -d bin -cp "lib\servlet-api.jar;lib\gson-2.10.1.jar;mysql-connector-j-9.5.0.jar;bin" src\servlet\*.java src\dao\*.java src\model\*.java src\util\*.java
```

## Step 3: Create WAR File Structure

```powershell
# Create WAR directory structure
mkdir faculty-management-system-war\WEB-INF\classes -Force
mkdir faculty-management-system-war\WEB-INF\lib -Force

# Copy compiled classes
xcopy /E /I bin\* faculty-management-system-war\WEB-INF\classes\

# Copy web.xml
copy WEB-INF\web.xml faculty-management-system-war\WEB-INF\

# Copy libraries
copy lib\gson-2.10.1.jar faculty-management-system-war\WEB-INF\lib\
copy mysql-connector-j-9.5.0.jar faculty-management-system-war\WEB-INF\lib\

# Copy UI files
xcopy /E /I ui\* faculty-management-system-war\ui\
```

## Step 4: Deploy to Tomcat

1. **Option A: Copy to Tomcat webapps**
   ```powershell
   xcopy /E /I faculty-management-system-war C:\xampp\tomcat\webapps\faculty-management-system
   ```

2. **Option B: Create and deploy WAR file**
   ```powershell
   cd faculty-management-system-war
   jar -cvf ..\faculty-management-system.war *
   cd ..
   copy faculty-management-system.war C:\xampp\tomcat\webapps\
   ```

## Step 5: Start Tomcat

1. Open XAMPP Control Panel
2. Start Apache (if not running)
3. Start Tomcat
4. Wait for deployment (check `C:\xampp\tomcat\logs\catalina.log`)

## Step 6: Access the Application

Open your browser and navigate to:
```
http://localhost:8080/faculty-management-system/ui/index.html
```

## API Endpoints

The following REST API endpoints are available:

- **Courses**: `http://localhost:8080/faculty-management-system/api/courses`
- **Degrees**: `http://localhost:8080/faculty-management-system/api/degrees`
- **Departments**: `http://localhost:8080/faculty-management-system/api/departments`
- **Lecturers**: `http://localhost:8080/faculty-management-system/api/lecturers`
- **Students**: `http://localhost:8080/faculty-management-system/api/students`
- **Users**: `http://localhost:8080/faculty-management-system/api/users`

## Troubleshooting

### Issue: 404 Not Found
- Check if Tomcat is running
- Verify the application is deployed to `C:\xampp\tomcat\webapps\faculty-management-system`
- Check Tomcat logs at `C:\xampp\tomcat\logs\`

### Issue: 500 Internal Server Error
- Check database connection in DBConnection.java
- Verify MySQL is running in XAMPP
- Check Tomcat logs for detailed error messages

### Issue: CORS Errors
- Servlets already include CORS headers
- Make sure you're accessing via `localhost:8080`, not file://

### Issue: Cannot load data
- Open browser DevTools (F12) → Console tab to see errors
- Verify API endpoints are accessible by visiting them directly
- Check that database tables have data

## Quick Deploy Script

Save this as `deploy.ps1`:

```powershell
# Quick deployment script
Write-Host "Building and deploying Faculty Management System..." -ForegroundColor Green

# Compile
javac -d bin -cp "lib\servlet-api.jar;lib\gson-2.10.1.jar;mysql-connector-j-9.5.0.jar;bin" src\servlet\*.java

# Create structure
mkdir faculty-management-system-war\WEB-INF\classes -Force | Out-Null
mkdir faculty-management-system-war\WEB-INF\lib -Force | Out-Null

# Copy files
xcopy /E /I /Y bin\* faculty-management-system-war\WEB-INF\classes\ > $null
copy WEB-INF\web.xml faculty-management-system-war\WEB-INF\ > $null
copy lib\gson-2.10.1.jar faculty-management-system-war\WEB-INF\lib\ > $null
copy mysql-connector-j-9.5.0.jar faculty-management-system-war\WEB-INF\lib\ > $null
xcopy /E /I /Y ui\* faculty-management-system-war\ui\ > $null

# Deploy
xcopy /E /I /Y faculty-management-system-war\* C:\xampp\tomcat\webapps\faculty-management-system\ > $null

Write-Host "Deployment complete!" -ForegroundColor Green
Write-Host "Access at: http://localhost:8080/faculty-management-system/ui/index.html" -ForegroundColor Cyan
```

Run with:
```powershell
.\deploy.ps1
```
