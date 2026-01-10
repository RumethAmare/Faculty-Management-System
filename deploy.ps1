# Faculty Management System - Quick Deploy Script
Write-Host "========================================" -ForegroundColor Cyan
Write-Host "Faculty Management System Deployment" -ForegroundColor Cyan
Write-Host "========================================" -ForegroundColor Cyan
Write-Host ""

# Check if required files exist
if (-not (Test-Path "lib\gson-2.10.1.jar")) {
    Write-Host "ERROR: gson-2.10.1.jar not found in lib folder!" -ForegroundColor Red
    Write-Host "Please download from: https://repo1.maven.org/maven2/com/google/code/gson/gson/2.10.1/" -ForegroundColor Yellow
    exit 1
}

if (-not (Test-Path "C:\xampp\tomcat\lib\servlet-api.jar")) {
    Write-Host "ERROR: servlet-api.jar not found in Tomcat lib folder!" -ForegroundColor Red
    Write-Host "Please make sure XAMPP with Tomcat is installed." -ForegroundColor Yellow
    exit 1
}

Write-Host "Step 1: Compiling servlets..." -ForegroundColor Green
javac -d bin -cp "C:\xampp\tomcat\lib\servlet-api.jar;lib\gson-2.10.1.jar;mysql-connector-j-9.5.0.jar;bin" src\servlet\*.java 2>&1 | Out-Null

if ($LASTEXITCODE -ne 0) {
    Write-Host "ERROR: Compilation failed!" -ForegroundColor Red
    exit 1
}
Write-Host "  ✓ Compilation successful" -ForegroundColor Green

Write-Host "Step 2: Creating WAR structure..." -ForegroundColor Green
Remove-Item -Path "faculty-management-system-war" -Recurse -Force -ErrorAction SilentlyContinue
mkdir faculty-management-system-war\WEB-INF\classes -Force | Out-Null
mkdir faculty-management-system-war\WEB-INF\lib -Force | Out-Null
Write-Host "  ✓ Directory structure created" -ForegroundColor Green

Write-Host "Step 3: Copying files..." -ForegroundColor Green
xcopy /E /I /Y bin\* faculty-management-system-war\WEB-INF\classes\ > $null 2>&1
copy WEB-INF\web.xml faculty-management-system-war\WEB-INF\ > $null 2>&1
copy lib\gson-2.10.1.jar faculty-management-system-war\WEB-INF\lib\ > $null 2>&1
copy mysql-connector-j-9.5.0.jar faculty-management-system-war\WEB-INF\lib\ > $null 2>&1
xcopy /E /I /Y ui\* faculty-management-system-war\ui\ > $null 2>&1
Write-Host "  ✓ Files copied" -ForegroundColor Green

Write-Host "Step 4: Deploying to Tomcat..." -ForegroundColor Green
Remove-Item -Path "C:\xampp\tomcat\webapps\faculty-management-system" -Recurse -Force -ErrorAction SilentlyContinue
xcopy /E /I /Y faculty-management-system-war\* C:\xampp\tomcat\webapps\faculty-management-system\ > $null 2>&1
Write-Host "  ✓ Deployed to Tomcat" -ForegroundColor Green

Write-Host ""
Write-Host "========================================" -ForegroundColor Cyan
Write-Host "Deployment Complete!" -ForegroundColor Green
Write-Host "========================================" -ForegroundColor Cyan
Write-Host ""
Write-Host "Next steps:" -ForegroundColor Yellow
Write-Host "1. Start Tomcat in XAMPP Control Panel" -ForegroundColor White
Write-Host "2. Wait 10-15 seconds for deployment" -ForegroundColor White
Write-Host "3. Access the application at:" -ForegroundColor White
Write-Host "   http://localhost:8080/faculty-management-system/ui/index.html" -ForegroundColor Cyan
Write-Host ""
