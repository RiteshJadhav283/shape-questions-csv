package com.example.excel;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.nio.charset.StandardCharsets;
import java.util.*;

public class QuestionGenerator {

    private static final Random RANDOM = new Random();
    private static final String TOPIC_NUMBER = "040101";
    private static final String CONTRIBUTOR_EMAIL = "2024.riteshs@isu.ac.in";

    public static void main(String[] args) {
        int count = 200; // Number of questions to generate
        String outputFileName = args != null && args.length > 0 ? args[0] : "shape_questions.csv";
        try {
            generateQuestions(count, outputFileName);
            System.out.println("CSV generated at: " + new File(outputFileName).getAbsolutePath());
        } catch (IOException e) {
            System.err.println("Failed to generate CSV: " + e.getMessage());
            System.exit(1);
        }
    }

    public static void generateQuestions(int count, String outputFileName) throws IOException {
        try (BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(outputFileName), StandardCharsets.UTF_8))) {
            // CSV header
            String[] headers = new String[]{
                    "Sr. No",
                    "Question Type",
                    "Answer Type",
                    "Topic Number",
                    "Question (Text Only)",
                    "Correct Answer 1",
                    "Correct Answer 2",
                    "Correct Answer 3",
                    "Correct Answer 4",
                    "Wrong Answer 1",
                    "Wrong Answer 2",
                    "Wrong Answer 3",
                    "Time in seconds",
                    "Difficulty Level",
                    "Question (Image/ Audio/ Video)",
                    "Contributor's Registered mailId",
                    "Solution (Text Only)",
                    "Solution (Image/ Audio/ Video)",
                    "Variation Number"
            };
            writeCsvRow(writer, headers);

            for (int i = 0; i < count; i++) {
                int variationNumber = 128; // All variation numbers set to 128
                ShapeData shape = getShapeData(100 + i); // Still use different shapes
                
                String[] row = new String[]{
                        String.valueOf(i + 1), // Sr. No
                        "Text", // Question Type
                        "1", // Answer Type
                        TOPIC_NUMBER, // Topic Number
                        shape.questionEn + "<br>#" + shape.questionMr, // Question (Text Only)
                        shape.correctAnswer, // Correct Answer 1
                        "", "", "", // Empty correct answers 2-4
                        shape.wrongAnswers[0], // Wrong Answer 1
                        shape.wrongAnswers[1], // Wrong Answer 2
                        shape.wrongAnswers[2], // Wrong Answer 3
                        "45", // Time in seconds
                        shape.difficulty, // Difficulty Level
                        shape.questionImage, // Question (Image)
                        CONTRIBUTOR_EMAIL, // Contributor's Registered mailId
                        shape.solutionText, // Solution (Text Only)
                        shape.solutionImage, // Solution (Image)
                        String.valueOf(variationNumber) // Variation Number
                };

                writeCsvRow(writer, row);
            }
        }
    }

    private static class ShapeData {
        String questionEn;
        String questionMr;
        String correctAnswer;
        String[] wrongAnswers;
        String questionImage;
        String solutionText;
        String solutionImage;
        String difficulty;

        public ShapeData(String questionEn, String questionMr, String correctAnswer, 
                        String[] wrongAnswers, String questionImage, 
                        String solutionText, String solutionImage, String difficulty) {
            this.questionEn = questionEn;
            this.questionMr = questionMr;
            this.correctAnswer = correctAnswer;
            this.wrongAnswers = wrongAnswers;
            this.questionImage = questionImage;
            this.solutionText = solutionText;
            this.solutionImage = solutionImage;
            this.difficulty = difficulty;
        }
    }

    // Only Cylinder shape is used as the correct answer
    private static final String[] ALL_SHAPES = {
        "Cylinder"
    };
    // Other shapes to use as wrong answers
    private static final String[] WRONG_ANSWER_SHAPES = {
        "Circle", "Cone", "Cube", "Parallel piped", "Hexagon", "Octagon", 
        "Oval", "Pentagon", "Prism", "Pyramid", "Rectangle", 
        "Semicircle", "Sphere", "Square", "Triangle"
    };

    private static ShapeData getShapeData(int variationNumber) {
        int shapeIndex = variationNumber % ALL_SHAPES.length;
        String shapeName = ALL_SHAPES[shapeIndex];
        
        // Select one of 5 cylinder variations randomly
        int cylinderVariation = RANDOM.nextInt(5);
        String cylinderSvg = getCylinderSvg(cylinderVariation);
        
        // Build question with embedded SVG at the end
        String questionEn = "What is the name of the geometric shape shown in the image below?<br>";
        String questionMr = "चित्रात दाखविलेल्या भौमितिक आकाराचे नाव काय आहे?<br>" + cylinderSvg + "<br>";
        
        // Use fixed solution text
        String solution = getShapeSolution(shapeName);
        String[] wrongAnswers = getWrongAnswers(shapeName);
        
        // Format correct answer with English and Marathi
        String formattedCorrectAnswer = formatAnswer(shapeName);
        
        // Format wrong answers with English and Marathi
        String[] formattedWrongAnswers = new String[3];
        for (int i = 0; i < wrongAnswers.length; i++) {
            formattedWrongAnswers[i] = formatAnswer(wrongAnswers[i]);
            // Add extra line break after the last wrong answer
            if (i == wrongAnswers.length - 1) {
                formattedWrongAnswers[i] += "<br>";
            }
        }
        
        return new ShapeData(
            questionEn,
            questionMr,
            formattedCorrectAnswer,
            formattedWrongAnswers,
            "",  // No separate SVG for question image column
            solution,
            "",  // No SVG for solution image
            "1"
        );
    }
    
    private static String getCylinderSvg(int variation) {
        switch (variation) {
            case 0:
                // Standard cylinder
                return "<svg width=\"200\" height=\"240\" viewBox=\"0 0 200 240\" xmlns=\"http://www.w3.org/2000/svg\"><ellipse cx=\"100\" cy=\"180\" rx=\"70\" ry=\"20\" fill=\"#a3d5f7\" stroke=\"#225588\" stroke-width=\"2\"/><path d=\"M30 180 V40 C30 30, 170 30, 170 40 V180\" fill=\"#a3d5f7\" stroke=\"#225588\" stroke-width=\"2\"/><ellipse cx=\"100\" cy=\"40\" rx=\"70\" ry=\"20\" fill=\"#a3d5f7\" stroke=\"#225588\" stroke-width=\"2\"/></svg>";
            case 1:
                // Taller cylinder
                return "<svg width=\"200\" height=\"280\" viewBox=\"0 0 200 280\" xmlns=\"http://www.w3.org/2000/svg\"><ellipse cx=\"100\" cy=\"240\" rx=\"60\" ry=\"18\" fill=\"#a3d5f7\" stroke=\"#225588\" stroke-width=\"2\"/><path d=\"M40 240 V30 C40 22, 160 22, 160 30 V240\" fill=\"#a3d5f7\" stroke=\"#225588\" stroke-width=\"2\"/><ellipse cx=\"100\" cy=\"30\" rx=\"60\" ry=\"18\" fill=\"#a3d5f7\" stroke=\"#225588\" stroke-width=\"2\"/></svg>";
            case 2:
                // Wider cylinder
                return "<svg width=\"220\" height=\"200\" viewBox=\"0 0 220 200\" xmlns=\"http://www.w3.org/2000/svg\"><ellipse cx=\"110\" cy=\"160\" rx=\"85\" ry=\"22\" fill=\"#a3d5f7\" stroke=\"#225588\" stroke-width=\"2\"/><path d=\"M25 160 V50 C25 40, 195 40, 195 50 V160\" fill=\"#a3d5f7\" stroke=\"#225588\" stroke-width=\"2\"/><ellipse cx=\"110\" cy=\"50\" rx=\"85\" ry=\"22\" fill=\"#a3d5f7\" stroke=\"#225588\" stroke-width=\"2\"/></svg>";
            case 3:
                // Shorter cylinder
                return "<svg width=\"200\" height=\"180\" viewBox=\"0 0 200 180\" xmlns=\"http://www.w3.org/2000/svg\"><ellipse cx=\"100\" cy=\"140\" rx=\"70\" ry=\"20\" fill=\"#a3d5f7\" stroke=\"#225588\" stroke-width=\"2\"/><path d=\"M30 140 V60 C30 50, 170 50, 170 60 V140\" fill=\"#a3d5f7\" stroke=\"#225588\" stroke-width=\"2\"/><ellipse cx=\"100\" cy=\"60\" rx=\"70\" ry=\"20\" fill=\"#a3d5f7\" stroke=\"#225588\" stroke-width=\"2\"/></svg>";
            case 4:
                // Narrow cylinder
                return "<svg width=\"180\" height=\"240\" viewBox=\"0 0 180 240\" xmlns=\"http://www.w3.org/2000/svg\"><ellipse cx=\"90\" cy=\"200\" rx=\"50\" ry=\"15\" fill=\"#a3d5f7\" stroke=\"#225588\" stroke-width=\"2\"/><path d=\"M40 200 V45 C40 37, 140 37, 140 45 V200\" fill=\"#a3d5f7\" stroke=\"#225588\" stroke-width=\"2\"/><ellipse cx=\"90\" cy=\"45\" rx=\"50\" ry=\"15\" fill=\"#a3d5f7\" stroke=\"#225588\" stroke-width=\"2\"/></svg>";
            default:
                return "<svg width=\"200\" height=\"240\" viewBox=\"0 0 200 240\" xmlns=\"http://www.w3.org/2000/svg\"><ellipse cx=\"100\" cy=\"180\" rx=\"70\" ry=\"20\" fill=\"#a3d5f7\" stroke=\"#225588\" stroke-width=\"2\"/><path d=\"M30 180 V40 C30 30, 170 30, 170 40 V180\" fill=\"#a3d5f7\" stroke=\"#225588\" stroke-width=\"2\"/><ellipse cx=\"100\" cy=\"40\" rx=\"70\" ry=\"20\" fill=\"#a3d5f7\" stroke=\"#225588\" stroke-width=\"2\"/></svg>";
        }
    }
    
    private static String formatAnswer(String shapeName) {
        String marathiName = getMarathiName(shapeName);
        return shapeName + "<br>#" + marathiName + "<br>";
    }
    
    private static String[] getWrongAnswers(String correctAnswer) {
        // Use other shapes as wrong answers
        List<String> wrongShapes = new ArrayList<>(Arrays.asList(WRONG_ANSWER_SHAPES));
        Collections.shuffle(wrongShapes, RANDOM);
        return new String[]{wrongShapes.get(0), wrongShapes.get(1), wrongShapes.get(2)};
    }


    private static String getShapeSolution(String shapeName) {
        // Only Cylinder solution is returned with fixed examples
        return "Ans : Cylinder.<br>" +
               "The shape of the object shown in the picture is called as <b>Cylinder.</b><br>" +
               "One of the surface is curved.<br>" +
               "Top and bottom surfaces are flat, equal and circular.<br>" +
               "It has two edges and both the edges are circular in shape.<br>" +
               "This does not have any corner.<br>" +
               "Steel container, Cold drink Can, Drum are some examples of the cylinder shape objects.<br>" +
               "Hence, shape of the given geometric object is called as <b>Cylinder</b> is the answer.<br>" +
               "#उत्तर : दंडगोल किंवा वृत्तचिती.<br>" +
               "चित्रात दाखविलेल्या वस्तूच्या आकाराला <b>दंडगोल किंवा वृत्तचिती</b> असे म्हणतात.<br>" +
               "याचे, तळाचा आणि वरचा असे दोन्ही पृष्ठभाग वर्तुळाकार, सपाट आणि समान असतात. <br>" +
               "याला दोन कडा असतात आणि दोन्ही वर्तुळाकार असतात.<br>" +
               "अशा आकाराला एकही कोपरा नसतो.<br>" +
               "पिंप, थंड पेयाचा डबा, ढोल इत्यादी वस्तूंचा आकार दंडगोलाकृती असतो.<br>" +
               "म्हणून, या भौमितिक आकाराचे नाव <b>दंडगोल किंवा वृत्तचिती</b> असे आहे.<br>";
    }

    
    private static String getMarathiName(String englishName) {
        Map<String, String> names = new HashMap<>();
        names.put("Triangle", "त्रिकोण");
        // names.put("Equilateral Triangle", "समभुज त्रिकोण");
        // names.put("Isosceles Triangle", "समद्विभुज त्रिकोण");
        // names.put("Scalene Triangle", "विषमभुज त्रिकोण");
        names.put("Square", "चौरस");
        names.put("Rectangle", "आयत");
        // names.put("Parallelogram", "समांतरभुज चौकोन");
        // names.put("Rhombus", "समचतुर्भुज");
        // names.put("Trapezium", "समलंब चौकोन");
        // names.put("Kite", "पतंग");
        names.put("Circle", "वर्तुळ");
        // names.put("Semi-circle", "अर्धवर्तुळ");
        names.put("Semicircle", "अर्धवर्तुळ");
        // names.put("Quadrilateral", "चौकोन");
        names.put("Pentagon", "पंचकोन");
        names.put("Hexagon", "षटकोन");
        // names.put("Heptagon", "सप्तकोन");
        names.put("Octagon", "अष्टकोन");
        // names.put("Nonagon", "नवकोन");
        // names.put("Decagon", "दशकोन");
        names.put("Oval", "अंडाकार");
        names.put("Cube", "घन");
        names.put("Parallel piped", "इष्टिकाचिती");
        names.put("Sphere", "गोल");
        names.put("Cylinder", "दंडगोल किंवा वृत्तचिती");
        names.put("Cone", "शंकू");
        names.put("Prism", "प्रिझम");
        names.put("Pyramid", "पिरॅमिड");
        // names.put("Star", "तारा");
        // names.put("Heart", "हृदय");
        // names.put("Diamond", "हिरा");
        // names.put("Crescent", "चंद्रकोर");
        

        //Commented names are not used in the program need to be removed
        return names.getOrDefault(englishName, englishName);
    }


    private static void writeCsvRow(BufferedWriter writer, String[] columns) throws IOException {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < columns.length; i++) {
            if (i > 0) sb.append(',');
            sb.append(escapeCsv(columns[i] == null ? "" : columns[i]));
        }
        sb.append('\n');
        writer.write(sb.toString());
    }

    private static String escapeCsv(String value) {
        if (value == null) return "";
        boolean needsQuoting = value.contains(",") || value.contains("\n") || value.contains("\r") || value.contains("\"") || value.contains("<");
        String v = value.replace("\"", "\"\"");
        if (needsQuoting) {
            return "\"" + v + "\"";
        }
        return v;
    }
}


