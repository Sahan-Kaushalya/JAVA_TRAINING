import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;

// Simple data class for demonstration.
class Student {
    String name;
    int    age;
    String major;
    double gpa;

    Student(String name, int age, String major, double gpa) {
        this.name  = name;
        this.age   = age;
        this.major = major;
        this.gpa   = gpa;
    }

    @Override
    public String toString() {
        return String.format("%-12s | age=%-2d | %-10s | gpa=%.1f", name, age, major, gpa);
    }
}

public class LambdaWithCollectionsDemo {

    public static void main(String[] args) {

        System.out.println("=== Lambda Expressions with Collections Demo ===\n");

        // ---------------------------------------------------------------
        // 1. List.forEach with lambda
        // ---------------------------------------------------------------
        System.out.println("--- 1. forEach with lambda ---");

        List<String> cities = Arrays.asList("Colombo", "Kandy", "Galle", "Jaffna", "Matara");

        // Classic loop style
        System.out.print("Classic loop:  ");
        for (String city : cities) {
            System.out.print(city + " ");
        }
        System.out.println();

        // Lambda forEach
        System.out.print("Lambda forEach:");
        cities.forEach(city -> System.out.print(" " + city));
        System.out.println();

        System.out.println();

        // ---------------------------------------------------------------
        // 2. Sorting a List with Comparator lambda
        // ---------------------------------------------------------------
        System.out.println("--- 2. Sorting with lambda Comparator ---");

        List<String> fruits = new ArrayList<>(Arrays.asList("Mango", "Apple", "Banana", "Cherry", "Fig"));

        // Sort alphabetically ascending
        Collections.sort(fruits, (a, b) -> a.compareTo(b));
        System.out.println("Alphabetical ASC:  " + fruits);

        // Sort alphabetically descending
        fruits.sort((a, b) -> b.compareTo(a));
        System.out.println("Alphabetical DESC: " + fruits);

        // Sort by string length, then alphabetically
        fruits.sort(Comparator.comparingInt(String::length).thenComparing(Comparator.naturalOrder()));
        System.out.println("By length then alpha: " + fruits);

        System.out.println();

        // ---------------------------------------------------------------
        // 3. Working with a List of objects (Student)
        // ---------------------------------------------------------------
        List<Student> students = Arrays.asList(
            new Student("Amal",    20, "IT",       3.8),
            new Student("Kumari",  22, "Medicine", 3.5),
            new Student("Nimal",   21, "IT",       2.9),
            new Student("Supun",   23, "Law",      3.2),
            new Student("Dilsha",  20, "Medicine", 3.9),
            new Student("Kasun",   22, "IT",       3.7),
            new Student("Nishani", 21, "Law",      2.7)
        );

        System.out.println("--- 3. All students ---");
        students.forEach(System.out::println);  // method reference

        System.out.println();

        // ---------------------------------------------------------------
        // 4. Filtering with Predicate lambda
        // ---------------------------------------------------------------
        System.out.println("--- 4. Filtering (IT students) ---");

        Predicate<Student> isIT         = s -> s.major.equals("IT");
        Predicate<Student> highGpa      = s -> s.gpa >= 3.5;
        Predicate<Student> isITHighGpa  = isIT.and(highGpa);

        List<Student> itStudents = students.stream()
            .filter(isIT)
            .collect(Collectors.toList());
        itStudents.forEach(System.out::println);

        System.out.println();
        System.out.println("--- IT students with GPA >= 3.5 ---");
        students.stream()
            .filter(isITHighGpa)
            .forEach(System.out::println);

        System.out.println();

        // ---------------------------------------------------------------
        // 5. Mapping / transforming with Function lambda
        // ---------------------------------------------------------------
        System.out.println("--- 5. Mapping (extract names) ---");

        Function<Student, String> getName = s -> s.name;
        Function<Student, String> getSummary = s ->
            s.name + " (" + s.major + " | GPA: " + s.gpa + ")";

        List<String> nameList = students.stream()
            .map(getName)
            .collect(Collectors.toList());
        System.out.println("Names: " + nameList);

        System.out.println();
        System.out.println("Summaries:");
        students.stream()
            .map(getSummary)
            .forEach(System.out::println);

        System.out.println();

        // ---------------------------------------------------------------
        // 6. Sorting list of objects
        // ---------------------------------------------------------------
        System.out.println("--- 6. Sorting students by GPA descending ---");

        students.stream()
            .sorted((a, b) -> Double.compare(b.gpa, a.gpa))      // lambda comparator
            .forEach(System.out::println);

        System.out.println();

        System.out.println("--- 6b. Using Comparator.comparing ---");
        students.stream()
            .sorted(Comparator.comparing((Student s) -> s.gpa).reversed())
            .forEach(System.out::println);

        System.out.println();

        // ---------------------------------------------------------------
        // 7. Removing items from a list with lambda (removeIf)
        // ---------------------------------------------------------------
        System.out.println("--- 7. removeIf with lambda ---");

        List<Integer> numbers = new ArrayList<>(Arrays.asList(1, 2, 3, 4, 5, 6, 7, 8, 9, 10));
        System.out.println("Before removeIf: " + numbers);

        numbers.removeIf(n -> n % 2 == 0);   // remove all even numbers
        System.out.println("After removing evens: " + numbers);

        System.out.println();

        // ---------------------------------------------------------------
        // 8. replaceAll – transform every element in place
        // ---------------------------------------------------------------
        System.out.println("--- 8. replaceAll with lambda ---");

        List<String> words = new ArrayList<>(Arrays.asList("hello", "world", "java", "lambda"));
        System.out.println("Before replaceAll: " + words);

        words.replaceAll(w -> w.substring(0, 1).toUpperCase() + w.substring(1));  // capitalize
        System.out.println("After capitalize:  " + words);

        System.out.println();

        // ---------------------------------------------------------------
        // 9. Counting and aggregation
        // ---------------------------------------------------------------
        System.out.println("--- 9. Counting and aggregation ---");

        long countIT = students.stream().filter(isIT).count();
        System.out.println("Number of IT students: " + countIT);

        double avgGpa = students.stream()
            .mapToDouble(s -> s.gpa)
            .average()
            .orElse(0.0);
        System.out.printf("Average GPA: %.2f%n", avgGpa);

        double maxGpa = students.stream()
            .mapToDouble(s -> s.gpa)
            .max()
            .orElse(0.0);
        System.out.printf("Highest GPA: %.1f%n", maxGpa);
    }
}

