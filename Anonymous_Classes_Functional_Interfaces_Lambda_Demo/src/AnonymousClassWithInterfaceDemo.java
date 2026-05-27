// Interface with a single abstract method.
interface Greeting {
    void greet(String name);
}

// Interface with a single abstract method + a default method.
interface Discountable {
    double applyDiscount(double price);

    // default method – does NOT count as the abstract method
    default void showPolicy() {
        System.out.println("Discount policy: fixed rate per implementation");
    }
}

// Interface with MULTIPLE abstract methods.
// An anonymous class can implement all of them. A lambda CANNOT be used here.
interface Report {
    String getTitle();
    String getBody();
    void print();
}

public class AnonymousClassWithInterfaceDemo {

    public static void main(String[] args) {

        System.out.println("=== Anonymous Class Implementing Interface ===\n");

        // --- Example 1: implementing a single-method interface ---
        Greeting formalGreeting = new Greeting() {
            @Override
            public void greet(String name) {
                System.out.println("Good morning, " + name + ". Welcome!");
            }
        };

        Greeting casualGreeting = new Greeting() {
            @Override
            public void greet(String name) {
                System.out.println("Hey " + name + "! What's up?");
            }
        };

        formalGreeting.greet("Mr. Silva");
        casualGreeting.greet("Nimal");

        System.out.println();

        // --- Example 2: implementing interface that has a default method too ---
        Discountable tenPercent = new Discountable() {
            @Override
            public double applyDiscount(double price) {
                return price - (price * 0.10);
            }
        };

        Discountable twentyPercent = new Discountable() {
            @Override
            public double applyDiscount(double price) {
                return price - (price * 0.20);
            }
        };

        double originalPrice = 5000.00;
        tenPercent.showPolicy();
        System.out.printf("10%% discount on %.2f = %.2f%n", originalPrice, tenPercent.applyDiscount(originalPrice));

        twentyPercent.showPolicy();
        System.out.printf("20%% discount on %.2f = %.2f%n", originalPrice, twentyPercent.applyDiscount(originalPrice));

        System.out.println();

        // --- Example 3: implementing a MULTI-method interface (lambda cannot do this) ---
        // The anonymous class provides concrete implementations for all three methods.
        Report salesReport = new Report() {
            @Override
            public String getTitle() {
                return "Monthly Sales Report";
            }

            @Override
            public String getBody() {
                return "Total sales this month: Rs. 1,450,000\nGrowth: 12% from last month";
            }

            @Override
            public void print() {
                System.out.println("=== " + getTitle() + " ===");
                System.out.println(getBody());
            }
        };

        salesReport.print();

        System.out.println();

        // --- Example 4: anonymous class stored in array and iterated ---
        System.out.println("--- Multiple greeting styles in array ---");
        Greeting[] styles = {
            new Greeting() {
                @Override
                public void greet(String name) {
                    System.out.println("[English] Hello, " + name + "!");
                }
            },
            new Greeting() {
                @Override
                public void greet(String name) {
                    System.out.println("[Sinhala] Ayubowan, " + name + "!");
                }
            },
            new Greeting() {
                @Override
                public void greet(String name) {
                    System.out.println("[Tamil]   Vanakkam, " + name + "!");
                }
            }
        };

        for (Greeting g : styles) {
            g.greet("Kamal");
        }
    }
}

