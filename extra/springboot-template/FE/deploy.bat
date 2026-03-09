@echo off
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

echo Done!
pause
