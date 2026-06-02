#!/bin/bash

# Script to uninstall all variants of the application
# Usage: ./uninstall_all_variants.sh

echo "========================================="
echo "Uninstalling All Variants"
echo "========================================="

# Check if device is connected
if ! adb devices | grep -q "device$"; then
    echo "❌ No Android device connected!"
    echo "Please connect a device or start an emulator."
    exit 1
fi

echo "✓ Device connected"
echo ""

echo "Uninstalling all variants..."
echo "----------------------------"

success_count=0
not_installed_count=0

uninstall_package() {
    local package=$1
    local name=$2
    echo -n "Uninstalling $name ($package)... "
    if adb uninstall "$package" > /dev/null 2>&1; then
        echo "✓ Done"
        ((success_count++))
    else
        echo "⚠ Not installed"
        ((not_installed_count++))
    fi
}

# The package name is defined in app/build.gradle.kts
# Since there are no applicationIdSuffixes, all variants share the same package name.
# Note: You can only have one of these installed at a time unless you add suffixes.
PACKAGE_NAME="com.amos_tech_code.knowledgebase"

uninstall_package "$PACKAGE_NAME" "Knowledge Base (All variants)"

echo ""
echo "========================================="
echo "Summary:"
echo "  ✓ Successfully uninstalled: $success_count"
if [ $not_installed_count -gt 0 ]; then
    echo "  ⚠ Not installed: $not_installed_count"
fi
echo "========================================="

# Verify removal
echo ""
echo "Verifying removal..."
echo "--------------------"
if adb shell pm list packages | grep -q "$PACKAGE_NAME"; then
    echo "  ⚠ $PACKAGE_NAME - Still installed!"
else
    echo "  ✓ All variants successfully removed!"
fi

echo ""
echo "Done! 🎉"
