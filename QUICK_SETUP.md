# Quick Setup Guide - Faculty Management System Web UI

## What You Need

1. Download **GSON library** (for JSON handling):
   - Download link: https://repo1.maven.org/maven2/com/google/code/gson/gson/2.10.1/gson-2.10.1.jar
   - Save it to: `C:\xampp\htdocs\faculty-management-system\lib\gson-2.10.1.jar`

## Setup Steps

### 1. Download GSON
```powershell
# Create lib folder
cd C:\xampp\htdocs\faculty-management-system
mkdir lib

# Download GSON manually from the link above and place in lib folder
# Or use PowerShell:
Invoke-WebRequest -Uri "https://repo1.maven.org/maven2/com/google/code/gson/gson/2.10.1/gson-2.10.1.jar" -OutFile "lib\gson-2.10.1.jar"
```

### 2. Run the Deployment Script
```powershell
cd C:\xampp\htdocs\faculty-management-system
.\deploy.ps1
```

### 3. Start Tomcat
1. Open **XAMPP Control Panel**
2. Click **Start** next to **Tomcat**
3. Wait 10-15 seconds for the application to deploy

### 4. Access the Web UI
Open your browser and go to:
```
http://localhost:8080/faculty-management-system/ui/index.html
```

## That's It!

Your web UI is now connected to the MySQL database. Any changes you make in the UI will be saved to the database and will be visible across all pages.

## Troubleshooting

**Problem: "Command not found" when running deploy.ps1**
```powershell
Set-ExecutionPolicy -ExecutionPolicy RemoteSigned -Scope CurrentUser
```

**Problem: Tomcat won't start**
- Make sure no other program is using port 8080
- Check XAMPP logs for errors

**Problem: Can't see data in UI**
- Press F12 in browser to open Developer Tools
- Check Console tab for errors
- Make sure MySQL is running in XAMPP
- Verify Tomcat started successfully

**Problem: "404 Not Found"**
- Wait a bit longer (deployment can take 15-20 seconds)
- Check if the folder exists: `C:\xampp\tomcat\webapps\faculty-management-system`
- Restart Tomcat

## What Changed?

The JavaScript files now connect to your Java servlets, which talk to your MySQL database. This means:

✅ Real data from your database displays in the UI
✅ Add/Edit/Delete operations save to the database
✅ Changes are persistent and work across all pages
✅ Multiple users can access the same data
