import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.function.Consumer;
import java.util.function.Predicate;

// -----------------------------------------------------------------------
// This demo shows the SAME task solved three ways:
//   1. Named class (old style)
//   2. Anonymous inner class (pre-Java 8)
//   3. Lambda expression (Java 8+ modern style)
//
// The goal is to clearly see how each approach differs.
// -----------------------------------------------------------------------

// Functional interface used across all comparison examples.
@FunctionalInterface
interface Discount {
    double apply(double price);
}

// Named class approach (for comparison only).
class TenPercentDiscount implements Discount {
    @Override
    public double apply(double price) {
        return price * 0.90;
    }
}

public class AnonymousVsLambdaComparisonDemo {

    public static void main(String[] args) {

        System.out.println("=== Anonymous Inner Class vs Lambda – Side-by-Side Comparison ===\n");

        double originalPrice = 5000.0;

        // ---------------------------------------------------------------
        // COMPARISON 1: Implementing a simple functional interface
        // ---------------------------------------------------------------
        System.out.println("--- Comparison 1: Implementing Discount interface ---\n");

        // Style A – named class
        Discount namedDiscount = new TenPercentDiscount();
        System.out.println("[Named Class]     10% off Rs." + originalPrice + " = Rs." + namedDiscount.apply(originalPrice));

        // Style B – anonymous inner class
        Discount anonymousDiscount = new Discount() {
            @Override
            public double apply(double price) {
                return price * 0.85;    // 15% discount
            }
        };
        System.out.println("[Anonymous Class] 15% off Rs." + originalPrice + " = Rs." + anonymousDiscount.apply(originalPrice));

        // Style C – lambda expression (cleanest)
        Discount lambdaDiscount = price -> price * 0.80;    // 20% discount
        System.out.println("[Lambda]          20% off Rs." + originalPrice + " = Rs." + lambdaDiscount.apply(originalPrice));

        System.out.println();

        // ---------------------------------------------------------------
        // COMPARISON 2: Runnable for threading
        // ---------------------------------------------------------------
        System.out.println("--- Comparison 2: Runnable for Thread ---\n");

        // Style B – anonymous Runnable
        Thread t1 = new Thread(new Runnable() {
            @Override
            public void run() {
                System.out.println("[Anonymous Runnable] Thread t1 running");
            }
        });

        // Style C – lambda Runnable
        Thread t2 = new Thread(() -> System.out.println("[Lambda Runnable]    Thread t2 running"));

        t1.start();
        t2.start();
        try { t1.join(); t2.join(); } catch (InterruptedException e) { Thread.currentThread().interrupt(); }

        System.out.println();

        // ---------------------------------------------------------------
        // COMPARISON 3: Comparator for sorting
        // ---------------------------------------------------------------
        System.out.println("--- Comparison 3: Sorting with Comparator ---\n");

        List<String> list1 = Arrays.asList("Banana", "Apple", "Cherry", "Mango");
        List<String> list2 = Arrays.asList("Banana", "Apple", "Cherry", "Mango");
        List<String> list3 = Arrays.asList("Banana", "Apple", "Cherry", "Mango");

        // Style B – anonymous Comparator
        list1.sort(new Comparator<String>() {
            @Override
            public int compare(String a, String b) {
                return a.compareTo(b);
            }
        });
        System.out.println("[Anonymous Comparator] " + list1);

        // Style C – lambda Comparator
        list2.sort((a, b) -> a.compareTo(b));
        System.out.println("[Lambda Comparator]    " + list2);

        // Style D – method reference (even shorter lambda)
        list3.sort(String::compareTo);
        System.out.println("[Method Reference]     " + list3);

        System.out.println();

        // ---------------------------------------------------------------
        // COMPARISON 4: Predicate for filtering
        // ---------------------------------------------------------------
        System.out.println("--- Comparison 4: Filtering with Predicate ---\n");

        List<Integer> numbers = Arrays.asList(1, 2, 3, 4, 5, 6, 7, 8, 9, 10);

        // Style B – anonymous Predicate
        Predicate<Integer> anonEven = new Predicate<Integer>() {
            @Override
            public boolean test(Integer n) {
                return n % 2 == 0;
            }
        };

        // Style C – lambda Predicate
        Predicate<Integer> lambdaEven = n -> n % 2 == 0;

        System.out.print("[Anonymous Predicate] Even numbers: ");
        numbers.stream().filter(anonEven).forEach(n -> System.out.print(n + " "));
        System.out.println();

        System.out.print("[Lambda Predicate]    Even numbers: ");
        numbers.stream().filter(lambdaEven).forEach(n -> System.out.print(n + " "));
        System.out.println();

        System.out.println();

        // ---------------------------------------------------------------
        // COMPARISON 5: Consumer for printing
        // ---------------------------------------------------------------
        System.out.println("--- Comparison 5: Consumer for printing ---\n");

        // Style B – anonymous Consumer
        Consumer<String> anonPrint = new Consumer<String>() {
            @Override
            public void accept(String s) {
                System.out.println("[Anon] " + s.toUpperCase());
            }
        };

        // Style C – lambda Consumer
        Consumer<String> lambdaPrint = s -> System.out.println("[Lambda] " + s.toUpperCase());

        // Style D – method reference Consumer
        Consumer<String> methodRefPrint = System.out::println;

        anonPrint.accept("hello from consumer");
        lambdaPrint.accept("hello from consumer");
        methodRefPrint.accept("[MethodRef] hello from consumer");

        System.out.println();

        // ---------------------------------------------------------------
        // KEY DIFFERENCE: `this` keyword
        // ---------------------------------------------------------------
        System.out.println("--- Key Difference: 'this' keyword ---\n");
        new AnonymousVsLambdaComparisonDemo().demonstrateThis();
    }

    String instanceName = "AnonymousVsLambdaComparisonDemo instance";

    void demonstrateThis() {
        // Anonymous class: 'this' refers to the anonymous class, NOT the outer instance.
        Runnable anonRunnable = new Runnable() {
            @Override
            public void run() {
                // 'this' here refers to the anonymous Runnable object.
                System.out.println("[Anonymous] 'this' class = " + this.getClass().getSimpleName());
                // To access outer class: use OuterClass.this
                System.out.println("[Anonymous] outer field  = " + AnonymousVsLambdaComparisonDemo.this.instanceName);
            }
        };

        // Lambda: 'this' refers to the enclosing class instance directly.
        Runnable lambdaRunnable = () -> {
            // 'this' here refers to AnonymousVsLambdaComparisonDemo directly.
            System.out.println("[Lambda]    'this' class = " + this.getClass().getSimpleName());
            System.out.println("[Lambda]    outer field  = " + this.instanceName);
        };

        anonRunnable.run();
        System.out.println();
        lambdaRunnable.run();

        System.out.println();

        // ---------------------------------------------------------------
        // SUMMARY TABLE (printed to console)
        // ---------------------------------------------------------------
        System.out.println("--- Summary: Anonymous Class vs Lambda ---");
        System.out.println("+---------------------------------------+----------------------+--------------------+");
        System.out.println("| Feature                               | Anonymous Class      | Lambda             |");
        System.out.println("+---------------------------------------+----------------------+--------------------+");
        System.out.println("| Syntax length                         | verbose              | concise            |");
        System.out.println("| Extend abstract class                 | YES                  | NO                 |");
        System.out.println("| Implement multi-method interface      | YES                  | NO (1 method only) |");
        System.out.println("| 'this' refers to                      | anonymous class      | enclosing class    |");
        System.out.println("| Can have own fields                   | YES                  | NO                 |");
        System.out.println("| Compile output                        | separate .class file | invokedynamic      |");
        System.out.println("| Readability for simple logic          | low                  | high               |");
        System.out.println("| Available since Java version          | early versions       | Java 8             |");
        System.out.println("+---------------------------------------+----------------------+--------------------+");
    }
}

