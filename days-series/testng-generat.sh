#!/bin/bash

# Output file
OUTPUT_FILE="testng.xml"
SUITE_NAME="MyTestSuite"
TEST_NAME="MyTest"

# Find all Java files containing '@Test'
TEST_CLASSES=$(grep -rl "@Test" src/test/java | grep ".java" | sed 's/src\/test\/java\///;s/\.java$//' | tr '/' '.')

# Start writing testng.xml
echo '<!DOCTYPE suite SYSTEM "https://testng.org/testng-1.0.dtd" >' > $OUTPUT_FILE
echo "<suite name=\"$SUITE_NAME\">" >> $OUTPUT_FILE
echo "  <test name=\"$TEST_NAME\">" >> $OUTPUT_FILE
echo "    <classes>" >> $OUTPUT_FILE

# Add each test class
for CLASS in $TEST_CLASSES; do
    echo "      <class name=\"$CLASS\"/>" >> $OUTPUT_FILE
done

echo "    </classes>" >> $OUTPUT_FILE
echo "  </test>" >> $OUTPUT_FILE
echo "</suite>" >> $OUTPUT_FILE

echo "✅ testng.xml generated with the following classes:"
echo "$TEST_CLASSES"
