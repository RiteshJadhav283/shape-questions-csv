# Shape Question Generator

This program creates a CSV file with 200 multiple-choice questions about shapes (like circles, triangles, rectangles, etc.).

## What You Need

- **Java 17 or newer** - Check by running: `java -version`
- **Maven** - Check by running: `mvn -v`

## How to Use

### Option 1: Build and Run JAR File (Recommended)

#### Step 1: Build the Program

Open your terminal in this folder and run:

```bash
mvn clean package
```

This creates a file called `excel-generator-1.0.0.jar` in the `target` folder.

#### Step 2: Generate the CSV File

Run this command:

```bash
java -jar target/excel-generator-1.0.0.jar
```

Done! You'll get a file called `shape_questions.csv` in the same folder.

**Alternative:** You can also double-click the `excel-generator-1.0.0.jar` file in the `target` folder to run it (works best on Windows). The CSV will be generated in your current directory.

### Option 2: Run Java File Directly (Easiest)

If you're using **VSCode** or another IDE:

1. Open `src/main/java/com/example/excel/QuestionGenerator.java`
2. Click the **Run** button above the `main` method
3. Done! The `shape_questions.csv` file will be generated

Or use this Maven command:

```bash
mvn compile exec:java -Dexec.mainClass="com.example.excel.QuestionGenerator"
```

### Want a Different File Name or Location?

Add the file path after the command:

```bash
java -jar target/excel-generator-1.0.0.jar "my_questions.csv"
```

Or save it in a different folder:

```bash
java -jar target/excel-generator-1.0.0.jar "output/questions.csv"
```

## What's in the CSV File?

The CSV file contains:
- 200 questions about shapes
- Multiple correct and wrong answers for each question
- Difficulty levels (Easy, Medium, Hard)
- Time suggestions for answering
- Explanations for the correct answers

You can open this file in Excel, Google Sheets, or any spreadsheet program.

## Need Help?

- Make sure Java 17+ is installed
- Make sure you're in the correct folder when running commands
- If you see errors, read the error message - it usually tells you what's wrong

