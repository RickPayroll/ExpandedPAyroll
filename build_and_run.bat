@echo off
echo ========================================
echo MotorPH Payroll System - Build and Run
echo ========================================

echo.
echo 1. Cleaning previous build...
if exist build rmdir /s /q build
mkdir build\classes

echo.
echo 2. Compiling Java sources...
javac -cp "lib\*" -d build\classes src\**\*.java

if %ERRORLEVEL% NEQ 0 (
    echo ❌ Compilation failed!
    pause
    exit /b 1
)

echo ✅ Compilation successful!

echo.
echo 3. Running test suite...
java -cp "build\classes;lib\*" Test.SimpleTestRunner

echo.
echo 4. Starting MotorPH Payroll System...
java -cp "build\classes;lib\*" ui.MainApplication

pause