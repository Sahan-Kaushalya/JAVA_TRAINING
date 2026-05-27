import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.function.BiFunction;
import java.util.function.BinaryOperator;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.Supplier;
import java.util.function.UnaryOperator;

public class BuiltInFunctionalInterfacesDemo {

    public static void main(String[] args) {

        System.out.println("=== Built-In Functional Interfaces Demo ===\n");

        // ---------------------------------------------------------------
        // 1. Predicate<T>  ->  T in, boolean out
        //    Method: boolean test(T t)
        // ---------------------------------------------------------------
        System.out.println("--- Predicate<T> ---");

        Predicate<Integer> isAdult      = age -> age >= 18;
        Predicate<String>  isNotEmpty   = str -> !str.isEmpty();
        Predicate<String>  startsWithA  = str -> str.startsWith("A");
        Predicate<Integer> isEven       = n -> n % 2 == 0;
        Predicate<Integer> isPositive   = n -> n > 0;

        System.out.println("Is 20 adult?           " + isAdult.test(20));
        System.out.println("Is 15 adult?           " + isAdult.test(15));
        System.out.println("Is 'Hello' not empty?  " + isNotEmpty.test("Hello"));
        System.out.println("Is '' not empty?       " + isNotEmpty.test(""));

        // Predicate chaining
        Predicate<Integer> isPositiveEven = isPositive.and(isEven);
        Predicate<Integer> isPositiveOrEven = isPositive.or(isEven);
        Predicate<Integer> isOdd = isEven.negate();

        System.out.println("isPositiveEven(4)?     " + isPositiveEven.test(4));
        System.out.println("isPositiveEven(-4)?    " + isPositiveEven.test(-4));
        System.out.println("isPositiveOrEven(-4)?  " + isPositiveOrEven.test(-4));
        System.out.println("isOdd(7)?              " + isOdd.test(7));

        // Filtering a list using Predicate
        List<String> names = Arrays.asList("Alice", "Bob", "Anna", "Charlie", "Amy", "David");
        System.out.print("Names starting with A: ");
        names.stream()
             .filter(startsWithA)
             .forEach(n -> System.out.print(n + " "));
        System.out.println();

        System.out.println();

        // ---------------------------------------------------------------
        // 2. Consumer<T>  ->  T in, void out
        //    Method: void accept(T t)
        // ---------------------------------------------------------------
        System.out.println("--- Consumer<T> ---");

        Consumer<String>  printUpper      = s -> System.out.println(s.toUpperCase());
        Consumer<Integer> printSquare     = n -> System.out.println("Square of " + n + " = " + (n * n));
        Consumer<String>  printWithBorder = s -> System.out.println("[ " + s + " ]");

        printUpper.accept("hello world");
        printSquare.accept(9);
        printWithBorder.accept("Important Notice");

        // Consumer chaining with andThen
        Consumer<String> printThenBorder = printUpper.andThen(printWithBorder);
        System.out.println("-- Consumer chain (printUpper then printWithBorder) --");
        printThenBorder.accept("chained consumer");

        System.out.println();

        // ---------------------------------------------------------------
        // 3. Supplier<T>  ->  nothing in, T out
        //    Method: T get()
        // ---------------------------------------------------------------
        System.out.println("--- Supplier<T> ---");

        Supplier<String>  greetingSupplier = () -> "Welcome to our system!";
        Supplier<Double>  randomSupplier   = () -> Math.random() * 100;
        Supplier<Integer> fixedIdSupplier  = () -> 1001;

        System.out.println(greetingSupplier.get());
        System.out.printf("Random value: %.2f%n", randomSupplier.get());
        System.out.println("User ID: " + fixedIdSupplier.get());

        // Supplier used in Optional.orElseGet (lazy evaluation)
        Optional<String> maybeName = Optional.empty();
        String result = maybeName.orElseGet(() -> "Default Name");
        System.out.println("Optional result: " + result);

        System.out.println();

        // ---------------------------------------------------------------
        // 4. Function<T, R>  ->  T in, R out
        //    Method: R apply(T t)
        // ---------------------------------------------------------------
        System.out.println("--- Function<T, R> ---");

        Function<String, Integer>  strToLength    = String::length;
        Function<Integer, String>  intToString    = n -> "Number: " + n;
        Function<String, String>   trim           = String::trim;
        Function<Double, String>   formatCurrency = amount -> "Rs. " + String.format("%.2f", amount);

        System.out.println("Length of 'Hello'     = " + strToLength.apply("Hello"));
        System.out.println("Int to string (42)    = " + intToString.apply(42));
        System.out.println("Trim '  hello  '      = '" + trim.apply("  hello  ") + "'");
        System.out.println("Format 4750.5         = " + formatCurrency.apply(4750.5));

        // Function chaining: andThen and compose
        Function<String, Integer> trimThenLength = trim.andThen(strToLength);
        System.out.println("Trim then length '  Hi  ' = " + trimThenLength.apply("  Hi  "));

        Function<Integer, Integer> doubleIt   = n -> n * 2;
        Function<Integer, Integer> addTen     = n -> n + 10;
        Function<Integer, Integer> doubleThenAdd = doubleIt.andThen(addTen);  // (n*2)+10
        Function<Integer, Integer> addThenDouble = doubleIt.compose(addTen);  // (n+10)*2

        System.out.println("doubleThenAdd(5) = " + doubleThenAdd.apply(5));  // (5*2)+10 = 20
        System.out.println("addThenDouble(5) = " + addThenDouble.apply(5));  // (5+10)*2 = 30

        System.out.println();

        // ---------------------------------------------------------------
        // 5. BiFunction<T, U, R>  ->  T and U in, R out
        //    Method: R apply(T t, U u)
        // ---------------------------------------------------------------
        System.out.println("--- BiFunction<T, U, R> ---");

        BiFunction<String, Integer, String> repeat      = (str, times) -> str.repeat(times);
        BiFunction<Integer, Integer, Integer> multiply  = (a, b) -> a * b;
        BiFunction<String, String, String> fullName     = (first, last) -> first + " " + last;

        System.out.println("Repeat 'ab' 3 times       = " + repeat.apply("ab", 3));
        System.out.println("Multiply 6 * 7            = " + multiply.apply(6, 7));
        System.out.println("Full name: " + fullName.apply("Kamal", "Perera"));

        System.out.println();

        // ---------------------------------------------------------------
        // 6. UnaryOperator<T>  ->  T in, T out (same type as Funtion<T,T>)
        //    Method: T apply(T t)
        // ---------------------------------------------------------------
        System.out.println("--- UnaryOperator<T> ---");

        UnaryOperator<Integer> square  = n -> n * n;
        UnaryOperator<String>  shout   = s -> s.toUpperCase() + "!";
        UnaryOperator<Double>  negate  = d -> -d;

        System.out.println("Square of 9    = " + square.apply(9));
        System.out.println("Shout 'hello'  = " + shout.apply("hello"));
        System.out.println("Negate 3.14    = " + negate.apply(3.14));

        System.out.println();

        // ---------------------------------------------------------------
        // 7. BinaryOperator<T>  ->  T, T in, T out (same types)
        //    Method: T apply(T t1, T t2)
        // ---------------------------------------------------------------
        System.out.println("--- BinaryOperator<T> ---");

        BinaryOperator<Integer> sum      = (a, b) -> a + b;
        BinaryOperator<String>  concat   = (a, b) -> a + " " + b;
        BinaryOperator<Integer> maxOf    = (a, b) -> a > b ? a : b;
        BinaryOperator<Integer> minOf    = (a, b) -> a < b ? a : b;

        System.out.println("Sum 10 + 20          = " + sum.apply(10, 20));
        System.out.println("Concat 'Hello' 'Java'= " + concat.apply("Hello", "Java"));
        System.out.println("Max of 45, 72        = " + maxOf.apply(45, 72));
        System.out.println("Min of 45, 72        = " + minOf.apply(45, 72));

        // Using Integer::sum as a BinaryOperator method reference
        BinaryOperator<Integer> intSum = Integer::sum;
        System.out.println("Integer::sum 40+60   = " + intSum.apply(40, 60));
    }
}

