import java.util.Arrays;
import java.util.Comparator;

// Interface used in the demos below.
interface Sorter {
    int compare(int a, int b);
}

public class AnonymousClassAdvancedDemo {

    // Instance variable – anonymous class can access this.
    private String ownerName = "Admin";

    public void runDemos() {

        System.out.println("=== Advanced Anonymous Inner Class Demo ===\n");

        // --- Example 1: capturing an effectively-final outer local variable ---
        // The variable 'taxRate' is effectively final because its value never changes.
        final double taxRate = 0.15;

        interface TaxCalculator {
            double calculate(double amount);
        }

        // The anonymous class reads 'taxRate' from the enclosing method.
        TaxCalculator vatCalc = new TaxCalculator() {
            @Override
            public double calculate(double amount) {
                // taxRate is captured from the outer scope (effectively final).
                return amount + (amount * taxRate);
            }
        };

        System.out.println("Amount after 15% tax on 3000 = " + vatCalc.calculate(3000));

        System.out.println();

        // --- Example 2: anonymous class accessing outer instance field ---
        Runnable task = new Runnable() {
            @Override
            public void run() {
                // 'this' here refers to the anonymous Runnable instance, NOT AnonymousClassAdvancedDemo.
                // To access the outer class field, we have to use the outer class name.
                System.out.println("Task run by: " + AnonymousClassAdvancedDemo.this.ownerName);
            }
        };

        task.run();

        System.out.println();

        // --- Example 3: sorting with anonymous Comparator (classic Java pre-8 style) ---
        String[] fruits = {"Banana", "Apple", "Mango", "Cherry", "Avocado"};

        System.out.println("Before sort: " + Arrays.toString(fruits));

        Arrays.sort(fruits, new Comparator<String>() {
            @Override
            public int compare(String a, String b) {
                // Sort alphabetically by the second character of the name.
                return Character.compare(a.charAt(1), b.charAt(1));
            }
        });

        System.out.println("After sort by 2nd char: " + Arrays.toString(fruits));

        System.out.println();

        // --- Example 4: anonymous class used as a method argument (callback pattern) ---
        System.out.println("--- Callback via anonymous class ---");
        processNumbers(10, 20, new Sorter() {
            @Override
            public int compare(int a, int b) {
                // Returns positive if a > b, negative if a < b, zero if equal.
                return Integer.compare(a, b);
            }
        });

        System.out.println();

        // --- Example 5: anonymous Runnable (used before lambdas in multi-threading) ---
        System.out.println("--- Anonymous Runnable thread example ---");
        Thread workerThread = new Thread(new Runnable() {
            @Override
            public void run() {
                System.out.println("Worker thread running: " + Thread.currentThread().getName());
            }
        });

        workerThread.start();

        try {
            workerThread.join(); // wait for thread to finish before continuing
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }

        System.out.println();

        // --- Example 6: anonymous class with state (its own fields) ---
        System.out.println("--- Anonymous class with internal state ---");
        interface Counter {
            void increment();
            int getCount();
        }

        // Anonymous class has its own internal field 'count'.
        Counter counter = new Counter() {
            private int count = 0; // anonymous class can declare its own fields

            @Override
            public void increment() {
                count++;
            }

            @Override
            public int getCount() {
                return count;
            }
        };

        counter.increment();
        counter.increment();
        counter.increment();
        System.out.println("Counter value after 3 increments: " + counter.getCount());
    }

    // Helper method that accepts a Sorter anonymous class.
    static void processNumbers(int x, int y, Sorter sorter) {
        int result = sorter.compare(x, y);
        if (result < 0) {
            System.out.println(x + " is less than " + y);
        } else if (result > 0) {
            System.out.println(x + " is greater than " + y);
        } else {
            System.out.println(x + " equals " + y);
        }
    }

    public static void main(String[] args) {
        new AnonymousClassAdvancedDemo().runDemos();
    }
}

