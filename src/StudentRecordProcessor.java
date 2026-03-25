import java.io.*;
import java.util.ArrayList;
import java.util.List;

class InvalidScoreException extends Exception {
    public InvalidScoreException(String message) {
        super(message);
    }
}

public class StudentRecordProcessor {
    // Поля для хранения данных
    private final List<Student> students = new ArrayList<>();

    // _____реализуйте класс Student ниже в этом же файле______

    private double averageScore;
    private Student highestStudent;


    /**
     * Task 1 + Task 2 + Task 5 + Task 6
     */
    public void readFile() {
        // TODO: реализуйте чтение файла здесь
        try (BufferedReader br = new BufferedReader(new FileReader("students.txt"))) {

            String line;
            while((line = br.readLine()) != null){
                String[] parts = line.split(",");
                try {
                    String name =parts[0];
                    int score = Integer.parseInt(parts[1]);

                    if (0> score || score >100) {
                        throw new InvalidScoreException("Score invalid");
                    }
                    students.add(new Student(name, score));
                    System.out.println("valid:" + line);
                }catch (NumberFormatException e) {
                    System.out.println("inv data:" + line);
                }catch (InvalidScoreException e) {
                    System.out.println("inv data:" + line);
                }
            }


        }catch (FileNotFoundException e) {
            System.out.println("file not found");
        }catch (IOException e) {
            System.out.println("error");
        }


    }

    /**
     * Task 3 + Task 8
     */
    public void processData() {
        // TODO: обработка данных и сортировка здесь
        if (students.isEmpty()) {
            System.out.println("no valid");
            return;
        }
        int sum = 0;
        highestStudent = students.get(0);

        for (Student s : students) {
            sum += s.getScore();

            if (s.getScore() > highestStudent.getScore()) {
                highestStudent = s;
            }
        }
        averageScore = (double) sum / students.size();
        students.sort((a, b) -> b.getScore() - a.getScore());
    }

    /**
     * Task 4 + Task 5 + Task 8
     */
    public void writeFile() {
        // TODO: запись результата в файл здесь
        try (BufferedWriter bw = new BufferedWriter(new FileWriter("output/report.txt"))) {
            if (highestStudent == null) {
                bw.write("No valid data");
                return;
            }


                bw.write("Average: " + averageScore);
            bw.newLine();

            bw.write("highest: " + highestStudent.getName() + " : " + highestStudent.getScore());
            bw.newLine();

            bw.write("- Students -");
            bw.newLine();

            for (Student s : students) {
                bw.write(s.getName() + " : " + s.getScore());
                bw.newLine();
            }

        } catch (IOException e) {
            System.out.println("Error writing file");
        }

    }

    public static void main(String[] args) {
        StudentRecordProcessor processor = new StudentRecordProcessor();

        try {
            processor.readFile();
            processor.processData();
            processor.writeFile();
            System.out.println("Processing completed. Check output/report.txt");
        } catch (Exception e) {
            System.err.println("Unexpected error: " + e.getMessage());
        }
    }
}

// class InvalidScoreException реализуйте меня
// class Student (name, score)
class Student {
    private String name;
    private int score;

    public Student(String name, int score) {
        this.name = name;
        this.score = score;
    }

    public String getName() {
        return name;
    }

    public int getScore() {
        return score;
    }
}