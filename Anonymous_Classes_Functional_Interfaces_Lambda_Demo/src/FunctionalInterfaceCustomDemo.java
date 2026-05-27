// -----------------------------------------------------------------------
// Custom functional interfaces – each has exactly ONE abstract method.
// The @FunctionalInterface annotation makes the compiler enforce this rule.
// -----------------------------------------------------------------------

@FunctionalInterface
interface MathOperation {
    int operate(int a, int b); // single abstract method

    // Default methods are allowed and do NOT break the functional interface rule.
    default void printOperation(int a, int b) {
        System.out.println("Result of operating on " + a + " and " + b + " = " + operate(a, b));
    }

    // Static methods are also allowed.
    static MathOperation addition() {
        return (a, b) -> a + b;
    }
}

@FunctionalInterface
interface StringTransformer {
    String transform(String input);
}

@FunctionalInterface
interface Validator<T> {
    boolean validate(T value);

    // Predicate-style default combinators
    default Validator<T> and(Validator<T> other) {
        return value -> this.validate(value) && other.validate(value);
    }

    default Validator<T> or(Validator<T> other) {
        return value -> this.validate(value) || other.validate(value);
    }

    default Validator<T> negate() {
        return value -> !this.validate(value);
    }
}

@FunctionalInterface
interface TriFunction<A, B, C, R> {
    // A functional interface can be generic and take multiple parameters.
    R apply(A a, B b, C c);
}

public class FunctionalInterfaceCustomDemo {

    public static void main(String[] args) {

        System.out.println("=== Custom Functional Interface Demo ===\n");

        // --- Example 1: MathOperation with different lambda implementations ---
        MathOperation add      = (a, b) -> a + b;
        MathOperation subtract = (a, b) -> a - b;
        MathOperation multiply = (a, b) -> a * b;
        MathOperation divide   = (a, b) -> (b != 0) ? a / b : 0;
        MathOperation modulo   = (a, b) -> a % b;
        MathOperation power    = (a, b) -> (int) Math.pow(a, b);

        System.out.println("10 + 5  = " + add.operate(10, 5));
        System.out.println("10 - 5  = " + subtract.operate(10, 5));
        System.out.println("10 * 5  = " + multiply.operate(10, 5));
        System.out.println("10 / 5  = " + divide.operate(10, 5));
        System.out.println("10 % 3  = " + modulo.operate(10, 3));
        System.out.println("2 ^ 8   = " + power.operate(2, 8));

        System.out.println();

        // Using the default method from the functional interface.
        add.printOperation(100, 200);

        // Using the static factory method.
        MathOperation factoryAdd = MathOperation.addition();
        System.out.println("Factory addition 7 + 3 = " + factoryAdd.operate(7, 3));

        System.out.println();

        // --- Example 2: StringTransformer variations ---
        StringTransformer toUpper  = s -> s.toUpperCase();
        StringTransformer toLower  = s -> s.toLowerCase();
        StringTransformer trim     = s -> s.trim();
        StringTransformer reverse  = s -> new StringBuilder(s).reverse().toString();
        StringTransformer addStars = s -> "*** " + s + " ***";

        String test = "  Hello World  ";
        System.out.println("Original  : '" + test + "'");
        System.out.println("toUpper   : " + toUpper.transform(test));
        System.out.println("toLower   : " + toLower.transform(test));
        System.out.println("trim      : '" + trim.transform(test) + "'");
        System.out.println("reverse   : " + reverse.transform(trim.transform(test)));
        System.out.println("addStars  : " + addStars.transform(trim.transform(test)));

        System.out.println();

        // --- Example 3: Generic Validator with logical chaining ---
        Validator<Integer> isPositive  = n -> n > 0;
        Validator<Integer> isEven      = n -> n % 2 == 0;
        Validator<Integer> lessThan100 = n -> n < 100;

        // Combine validators using default and / or / negate methods.
        Validator<Integer> isPositiveAndEven       = isPositive.and(isEven);
        Validator<Integer> isPositiveAndEvenAndSmall = isPositive.and(isEven).and(lessThan100);
        Validator<Integer> isOdd                   = isEven.negate();

        int[] testValues = {-4, 0, 7, 12, 50, 200};

        System.out.println("--- Validator results ---");
        for (int val : testValues) {
            System.out.printf(
                "Value=%4d | positive=%b | positiveEven=%b | positiveEvenSmall=%b | odd=%b%n",
                val,
                isPositive.validate(val),
                isPositiveAndEven.validate(val),
                isPositiveAndEvenAndSmall.validate(val),
                isOdd.validate(val)
            );
        }

        System.out.println();

        // --- Example 4: Tri-function (custom functional interface with 3 params) ---
        TriFunction<String, Integer, Double, String> buildLabel =
            (name, quantity, price) ->
                name + " x" + quantity + " @ Rs." + String.format("%.2f", price)
                + " = Rs." + String.format("%.2f", quantity * price);

        System.out.println("--- Invoice lines ---");
        System.out.println(buildLabel.apply("Apple",  5, 150.00));
        System.out.println(buildLabel.apply("Mango",  3, 250.00));
        System.out.println(buildLabel.apply("Banana", 12, 40.00));
    }
}

