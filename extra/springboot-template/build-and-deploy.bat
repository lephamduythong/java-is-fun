@echo off

echo ======================================
echo Building Frontend Application...
echo ======================================

cd FE

echo [1/3] Building...
call npm run build
if %errorlevel% neq 0 (
    echo Build failed!
    pause
    exit /b 1
)

echo [2/3] Clearing target directory...
set TARGET=..\src\main\resources\static\app
if exist "%TARGET%" (
    rd /s /q "%TARGET%"
)
mkdir "%TARGET%"

echo [3/3] Copying dist to %TARGET%...
xcopy /e /i /y "dist\*" "%TARGET%\"

echo Done building frontend!

cd ..

echo ======================================
echo Building Spring Boot Application...
echo ======================================

call mvn clean package

if %ERRORLEVEL% NEQ 0 (
    echo.
    echo ======================================
    echo Build FAILED!
    echo ======================================
    pause
    exit /b %ERRORLEVEL%
)

echo.
echo ======================================
echo Build SUCCESS!
echo ======================================
echo.
echo Copying WAR file to WildFly...

copy /Y "target\test-api.war" "E:\CODING\wildfly-26.1.3.Final\standalone\deployments\test-api.war"

if %ERRORLEVEL% EQU 0 (
    echo.
    echo ======================================
    echo Deployment SUCCESS!
    echo WAR file copied to WildFly deployments
    echo ======================================
) else (
    echo.
    echo ======================================
    echo Copy FAILED!
    echo ======================================
)

echo.
pause
