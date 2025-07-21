#!/bin/bash
echo "========================================"
echo "MotorPH Payroll System - Build and Run"
echo "========================================"

echo
echo "1. Cleaning previous build..."
rm -rf build
mkdir -p build/classes

echo
echo "2. Compiling Java sources..."
javac -cp "lib/*" -d build/classes src/**/*.java

if [ $? -ne 0 ]; then
    echo "❌ Compilation failed!"
    exit 1
fi

echo "✅ Compilation successful!"

echo
echo "3. Running test suite..."
java -cp "build/classes:lib/*" Test.SimpleTestRunner

echo
echo "4. Starting MotorPH Payroll System..."
java -cp "build/classes:lib/*" ui.MainApplication