import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.Supplier;

// Interfaces used to show all lambda syntax forms.
@FunctionalInterface
interface NoParamNoReturn {
    void execute();
}

@FunctionalInterface
interface OneParamNoReturn {
    void run(String message);
}

@FunctionalInterface
interface TwoParamWithReturn {
    int compute(int a, int b);
}

@FunctionalInterface
interface OneParamWithReturn {
    String process(String input);
}

public class LambdaSyntaxVariationsDemo {

    public static void main(String[] args) {

        System.out.println("=== Lambda Expression Syntax Variations Demo ===\n");

        // ---------------------------------------------------------------
        // FORM 1: No parameters, no return value
        //   () -> statement
        // ---------------------------------------------------------------
        System.out.println("--- Form 1: No params, no return ---");

        NoParamNoReturn sayHello  = () -> System.out.println("Hello from lambda!");
        NoParamNoReturn printLine = () -> System.out.println("-------------------");

        sayHello.execute();
        printLine.execute();

        System.out.println();

        // ---------------------------------------------------------------
        // FORM 2: One parameter, no return value
        //   param -> statement            (parentheses optional for one param)
        //   (param) -> statement
        // ---------------------------------------------------------------
        System.out.println("--- Form 2: One param, no return ---");

        OneParamNoReturn printMsg1 = message -> System.out.println("Without parens: " + message);
        OneParamNoReturn printMsg2 = (message) -> System.out.println("With parens:    " + message);

        printMsg1.run("Lambda is clean");
        printMsg2.run("Lambda is clean");

        System.out.println();

        // ---------------------------------------------------------------
        // FORM 3: Multiple parameters, expression body (implicit return)
        //   (a, b) -> expression
        // ---------------------------------------------------------------
        System.out.println("--- Form 3: Multiple params, expression body ---");

        TwoParamWithReturn add      = (a, b) -> a + b;
        TwoParamWithReturn subtract = (a, b) -> a - b;
        TwoParamWithReturn multiply = (a, b) -> a * b;
        TwoParamWithReturn max      = (a, b) -> a > b ? a : b;

        System.out.println("6 + 4  = " + add.compute(6, 4));
        System.out.println("6 - 4  = " + subtract.compute(6, 4));
        System.out.println("6 * 4  = " + multiply.compute(6, 4));
        System.out.println("max(6,4) = " + max.compute(6, 4));

        System.out.println();

        // ---------------------------------------------------------------
        // FORM 4: Block body with explicit return
        //   (params) -> {
        //       // multiple statements
        //       return value;
        //   }
        // ---------------------------------------------------------------
        System.out.println("--- Form 4: Block body with explicit return ---");

        TwoParamWithReturn divide = (a, b) -> {
            if (b == 0) {
                System.out.println("Cannot divide by zero!");
                return 0;
            }
            return a / b;
        };

        System.out.println("10 / 2 = " + divide.compute(10, 2));
        System.out.println("10 / 0 = " + divide.compute(10, 0));
        System.out.println("Grade for 85: " + getGrade(85));
        System.out.println("Grade for 72: " + getGrade(72));
        System.out.println("Grade for 55: " + getGrade(55));

        System.out.println();

        // ---------------------------------------------------------------
        // FORM 5: Explicit parameter types (optional – compiler infers them)
        //   (TypeA a, TypeB b) -> expression
        // ---------------------------------------------------------------
        System.out.println("--- Form 5: Explicit parameter types ---");

        TwoParamWithReturn power = (int base, int exp) -> (int) Math.pow(base, exp);
        System.out.println("2^10 = " + power.compute(2, 10));
        System.out.println("3^5  = " + power.compute(3, 5));

        System.out.println();

        // ---------------------------------------------------------------
        // FORM 6: Capturing an effectively-final variable from outer scope
        // ---------------------------------------------------------------
        System.out.println("--- Form 6: Variable capture ---");

        String prefix = "Mr.";           // effectively final (never reassigned)
        int    bonus  = 500;             // effectively final

        OneParamWithReturn addTitle = name -> prefix + " " + name;
        TwoParamWithReturn addBonus = (salary, months) -> salary * months + bonus;

        System.out.println(addTitle.process("Perera"));
        System.out.println("Salary for 3 months + bonus = " + addBonus.compute(50000, 3));

        System.out.println();

        // ---------------------------------------------------------------
        // FORM 7: Lambda assigned to built-in functional interface types
        // ---------------------------------------------------------------
        System.out.println("--- Form 7: Assigned to java.util.function types ---");

        Predicate<String>  isNotBlank   = s -> !s.isBlank();
        Consumer<String>   printWrapped = s -> System.out.println(">> " + s + " <<");
        Supplier<String>   getTimestamp = () -> "Timestamp: " + System.currentTimeMillis();
        Function<String, Integer> toLen = String::length;      // method reference (shortest form)

        System.out.println("'  ' not blank?  " + isNotBlank.test("  "));
        System.out.println("'Hi' not blank?  " + isNotBlank.test("Hi"));
        printWrapped.accept("Important message");
        System.out.println(getTimestamp.get());
        System.out.println("Length of 'Lambda' = " + toLen.apply("Lambda"));

        System.out.println();

        // ---------------------------------------------------------------
        // FORM 8: Storing lambdas in an array and invoking dynamically
        // ---------------------------------------------------------------
        System.out.println("--- Form 8: Lambda array ---");

        TwoParamWithReturn[] operations = {
            (a, b) -> a + b,
            (a, b) -> a - b,
            (a, b) -> a * b,
            (a, b) -> (b != 0) ? a / b : -1,
            (a, b) -> a % b
        };

        String[] opNames = { "add", "subtract", "multiply", "divide", "modulo" };

        int x = 20, y = 4;
        for (int i = 0; i < operations.length; i++) {
            System.out.println(opNames[i] + "(" + x + ", " + y + ") = " + operations[i].compute(x, y));
        }
    }

    // Helper method using a lambda internally.
    static String getGrade(int score) {
        Function<Integer, String> grader = s -> {
            if (s >= 80) return "A - Distinction";
            if (s >= 70) return "B - Merit";
            if (s >= 60) return "C - Pass";
            return "F - Fail";
        };
        return grader.apply(score);
    }
}

