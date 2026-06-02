#!/bin/bash

# Script to build and install variants of the application
# Usage: ./install_all_variants.sh

echo "========================================="
echo "Building and Installing Project Variants"
echo "========================================="

# Check if device is connected
if ! adb devices | grep -q "device$"; then
    echo "❌ No Android device connected!"
    echo "Please connect a device or start an emulator."
    exit 1
fi

echo "✓ Device connected"
echo ""

# Step 1: Clean and Build
echo "Step 1: Building all variants..."
echo "--------------------------------"
echo "Running: ./gradlew assembleDebug assembleRelease assembleDebugMinified"
./gradlew assembleDebug assembleRelease assembleDebugMinified

if [ $? -ne 0 ]; then
    echo "❌ Build failed! Please fix the errors and try again."
    exit 1
fi

echo ""
echo "✓ Build completed successfully"
echo ""

# Debug: Show what APKs were actually created
echo "APKs found in build directory:"
find app/build/outputs/apk -name "*.apk" -type f | while read apk; do
    echo "  - $apk"
done
echo ""

echo "Step 2: Installing variants..."
echo "------------------------------"
echo "NOTE: Since all variants currently share the same applicationId,"
echo "installing one will overwrite the previous one on the device."
echo ""

success_count=0
failed_count=0

install_variant() {
    local name=$2
    local apk_path=$3

    echo "Processing $name..."
    echo "  Expected APK: $apk_path"

    if [ ! -f "$apk_path" ]; then
        echo "  ❌ APK not found!"
        return 1
    fi

    echo -n "  Installing... "
    local install_output=$(adb install -r "$apk_path" 2>&1)
    local install_result=$?

    if [ $install_result -eq 0 ]; then
        echo "✓ Success"
        echo ""
        return 0
    else
        echo "❌ Failed"
        echo "  Error: $install_output"
        echo ""
        return 1
    fi
}

# Install variants found in build.gradle.kts
# Note: In a real world scenario, you'd usually only install one at a time if they share ID
install_variant "com.amos_tech_code.knowledgebase" "Debug" "app/build/outputs/apk/debug/app-debug.apk"
[ $? -eq 0 ] && ((success_count++)) || ((failed_count++))

install_variant "com.amos_tech_code.knowledgebase" "Debug Minified" "app/build/outputs/apk/debugMinified/app-debugMinified.apk"
[ $? -eq 0 ] && ((success_count++)) || ((failed_count++))

install_variant "com.amos_tech_code.knowledgebase" "Release" "app/build/outputs/apk/release/app-release.apk"
[ $? -eq 0 ] && ((success_count++)) || ((failed_count++))

echo ""
echo "========================================="
echo "Summary:"
echo "  ✓ Successfully installed: $success_count"
if [ $failed_count -gt 0 ]; then
    echo "  ❌ Failed: $failed_count"
fi
echo "========================================="

echo ""
echo "Done! 🎉"
