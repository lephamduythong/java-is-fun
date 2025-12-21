@echo off
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
