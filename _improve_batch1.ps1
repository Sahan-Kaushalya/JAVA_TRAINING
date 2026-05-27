
$root = "E:\Java training\JAVA_TRAINING"
function WJ($path,$content){ [System.IO.File]::WriteAllText($path,$content,[System.Text.Encoding]::UTF8); Write-Host "  Written: $(Split-Path $path -Leaf)" }

# ============================================================
# MODULE: Conditional_Statements_Demo
# ============================================================
$d = "$root\Conditional_Statements_Demo\src"

WJ "$d\IfStatementDemo.java" @'
public class IfStatementDemo {
    // The 'if' statement executes a block ONLY when the condition is true.
    // Syntax:  if (condition) { ... }
    // If condition is false, the block is skipped entirely.

    public static void main(String[] args) {

        // --- Example 1: Basic if with integer comparison ---
        int temperature = 35;
        if (temperature > 30) {
            System.out.println("It is hot outside. Stay hydrated!");
        }

        // --- Example 2: if with boolean expression ---
        boolean isLoggedIn = true;
        if (isLoggedIn) {
            System.out.println("Welcome back, user!");
        }

        // --- Example 3: if with String comparison (use .equals(), never ==) ---
        String role = "ADMIN";
        if (role.equals("ADMIN")) {
            System.out.println("Admin dashboard access granted.");
        }

        // --- Example 4: if with compound condition (&&) ---
        int age  = 22;
        boolean hasLicense = true;
        if (age >= 18 && hasLicense) {
            System.out.println("You are allowed to drive.");
        }

        // --- Example 5: if with compound condition (||) ---
        boolean isMember   = false;
        boolean hasVoucher = true;
        if (isMember || hasVoucher) {
            System.out.println("You are eligible for a discount.");
        }

        // --- Example 6: if with negation (!) ---
        boolean isWeekend = false;
        if (!isWeekend) {
            System.out.println("Today is a working day.");
        }

        // --- Example 7: if with relational operators ---
        double accountBalance = 5000.00;
        double withdrawAmount  = 3000.00;
        if (withdrawAmount <= accountBalance) {
            accountBalance -= withdrawAmount;
            System.out.println("Withdrawal successful. New balance: " + accountBalance);
        }

        // --- Example 8: Single-line if (valid but avoid without braces for safety) ---
        int stock = 0;
        if (stock == 0)
            System.out.println("Out of stock! No braces on single-line if.");

        // Note: Always use braces {} to avoid logic bugs when adding more lines.
    }
}
'@

WJ "$d\IfElseDemo.java" @'
public class IfElseDemo {
    // if-else: executes one of two blocks depending on whether condition is true/false.
    // Syntax:  if (condition) { ... } else { ... }
    // The ternary operator is a compact one-liner for simple if-else assignments.

    public static void main(String[] args) {

        // --- Example 1: Even or Odd ---
        int number = 17;
        if (number % 2 == 0) {
            System.out.println(number + " is even.");
        } else {
            System.out.println(number + " is odd.");
        }

        // --- Example 2: Pass or Fail ---
        int examScore = 55;
        if (examScore >= 50) {
            System.out.println("Exam result: PASSED  (score=" + examScore + ")");
        } else {
            System.out.println("Exam result: FAILED  (score=" + examScore + ")");
        }

        // --- Example 3: Positive, negative, zero (using if-else) ---
        int value = -8;
        if (value > 0) {
            System.out.println(value + " is positive.");
        } else {
            System.out.println(value + " is zero or negative.");
        }

        // --- Example 4: Ternary operator (shorthand for simple if-else) ---
        // Syntax: condition ? valueIfTrue : valueIfFalse
        int a = 15, b = 20;
        int max = (a > b) ? a : b;
        System.out.println("Maximum of " + a + " and " + b + " = " + max);

        // --- Example 5: Ternary for String assignment ---
        boolean isRaining = true;
        String advice = isRaining ? "Take an umbrella." : "No umbrella needed.";
        System.out.println(advice);

        // --- Example 6: Nested ternary (use sparingly -- can hurt readability) ---
        int marks = 72;
        String result = (marks >= 75) ? "Distinction" : (marks >= 50) ? "Pass" : "Fail";
        System.out.println("Result: " + result);

        // --- Example 7: Real-world -- ATM withdrawal check ---
        double balance     = 10_000.00;
        double withdrawal  = 12_500.00;
        if (withdrawal <= balance) {
            balance -= withdrawal;
            System.out.println("Withdrawal approved. Remaining: " + balance);
        } else {
            System.out.println("Withdrawal denied. Insufficient balance.");
        }

        // --- Example 8: String comparison with if-else ---
        String userInput = "yes";
        if (userInput.equalsIgnoreCase("yes")) {
            System.out.println("User confirmed the action.");
        } else {
            System.out.println("User declined the action.");
        }
    }
}
'@

WJ "$d\ElseIfLadderDemo.java" @'
public class ElseIfLadderDemo {
    // else-if ladder: checks multiple exclusive conditions in sequence.
    // Only the FIRST matching block executes; the rest are skipped.
    // Always ends with a final 'else' to handle all remaining cases.

    public static void main(String[] args) {

        // --- Example 1: Grade calculator ---
        int marks = 82;
        if (marks >= 90) {
            System.out.println("Grade: A+  (Excellent)");
        } else if (marks >= 80) {
            System.out.println("Grade: A   (Very Good)");
        } else if (marks >= 70) {
            System.out.println("Grade: B   (Good)");
        } else if (marks >= 60) {
            System.out.println("Grade: C   (Average)");
        } else if (marks >= 50) {
            System.out.println("Grade: D   (Pass)");
        } else {
            System.out.println("Grade: F   (Fail)");
        }

        // --- Example 2: BMI category ---
        double bmi = 23.5;
        if (bmi < 18.5) {
            System.out.println("BMI " + bmi + " -> Underweight");
        } else if (bmi < 25.0) {
            System.out.println("BMI " + bmi + " -> Normal weight");
        } else if (bmi < 30.0) {
            System.out.println("BMI " + bmi + " -> Overweight");
        } else {
            System.out.println("BMI " + bmi + " -> Obese");
        }

        // --- Example 3: Internet speed quality ---
        int speedMbps = 45;
        String quality;
        if (speedMbps >= 100) {
            quality = "Ultra-fast";
        } else if (speedMbps >= 50) {
            quality = "Fast";
        } else if (speedMbps >= 20) {
            quality = "Moderate";
        } else if (speedMbps >= 5) {
            quality = "Slow";
        } else {
            quality = "Very slow";
        }
        System.out.println("Speed " + speedMbps + " Mbps -> " + quality);

        // --- Example 4: Tax bracket calculation ---
        double income = 75_000;
        double tax;
        if (income <= 10_000) {
            tax = 0;
        } else if (income <= 30_000) {
            tax = income * 0.10;
        } else if (income <= 60_000) {
            tax = income * 0.20;
        } else if (income <= 100_000) {
            tax = income * 0.30;
        } else {
            tax = income * 0.40;
        }
        System.out.printf("Income: %.0f  |  Tax (30%%): %.2f%n", income, tax);

        // --- Note: if-else ladder vs switch ---
        // Use else-if when: conditions involve ranges, different variables, or complex logic.
        // Use switch when: one variable is compared to specific constant values.
    }
}
'@

WJ "$d\NestedIfDemo.java" @'
public class NestedIfDemo {
    // Nested if: an if (or if-else) placed inside another if block.
    // Use when a secondary condition only makes sense after a primary condition is met.
    // Caution: deep nesting hurts readability. Prefer early returns or extracted methods.

    public static void main(String[] args) {

        // --- Example 1: Login authentication checks ---
        String username = "admin";
        String password = "java@123";
        boolean accountActive = true;

        if (username.equals("admin")) {
            if (accountActive) {
                if (password.equals("java@123")) {
                    System.out.println("Login successful! Welcome, Admin.");
                } else {
                    System.out.println("Login failed: incorrect password.");
                }
            } else {
                System.out.println("Login failed: account is inactive.");
            }
        } else {
            System.out.println("Login failed: unknown username.");
        }

        // --- Example 2: Loan eligibility check ---
        int    creditScore  = 720;
        double annualIncome = 55_000;
        int    employedYears = 3;

        if (creditScore >= 700) {
            System.out.println("Credit score OK.");
            if (annualIncome >= 40_000) {
                System.out.println("Income OK.");
                if (employedYears >= 2) {
                    System.out.println("Loan APPROVED: all criteria met.");
                } else {
                    System.out.println("Loan DENIED: not enough employment years.");
                }
            } else {
                System.out.println("Loan DENIED: insufficient income.");
            }
        } else {
            System.out.println("Loan DENIED: low credit score.");
        }

        // --- Example 3: E-commerce discount logic ---
        boolean isPremiumMember = true;
        double  cartTotal       = 1500.0;
        boolean hasCoupon       = true;
        String  couponCode      = "SAVE10";

        double discount = 0.0;
        if (isPremiumMember) {
            discount += 0.10; // 10% base discount
            if (cartTotal > 1000) {
                discount += 0.05; // extra 5% for large cart
                if (hasCoupon && couponCode.equals("SAVE10")) {
                    discount += 0.10; // extra 10% with valid coupon
                }
            }
        }
        System.out.printf("Cart total: %.2f  |  Discount: %.0f%%  |  Final: %.2f%n",
                cartTotal, discount * 100, cartTotal * (1 - discount));

        // --- Best practice: avoid deep nesting by inverting guards ---
        // Instead of nesting, use early returns (guard clauses):
        // if (!username.equals("admin")) { System.out.println("Unknown user"); return; }
        // if (!accountActive) { System.out.println("Inactive"); return; }
        // ...rest of logic here (one level deep instead of three)
    }
}
'@

WJ "$d\SwitchStatementDemo.java" @'
public class SwitchStatementDemo {
    // switch: compare one variable against a list of constant values.
    // In Java, switch works with: int, char, String, byte, short, Enum.
    // 'break' exits the switch; without it, execution FALLS THROUGH to the next case.
    // Java 14+ introduces Switch Expressions with -> syntax (no fall-through, returns value).

    public static void main(String[] args) {

        // --- Example 1: Day of week (classic switch with int) ---
        int dayNumber = 3;
        switch (dayNumber) {
            case 1: System.out.println("Monday");    break;
            case 2: System.out.println("Tuesday");   break;
            case 3: System.out.println("Wednesday"); break;
            case 4: System.out.println("Thursday");  break;
            case 5: System.out.println("Friday");    break;
            case 6: System.out.println("Saturday");  break;
            case 7: System.out.println("Sunday");    break;
            default: System.out.println("Invalid day number.");
        }

        // --- Example 2: Switch with String (Java 7+) ---
        String season = "SUMMER";
        switch (season) {
            case "SPRING": System.out.println("Season: Spring - Flowers bloom."); break;
            case "SUMMER": System.out.println("Season: Summer - Hot and sunny."); break;
            case "AUTUMN": System.out.println("Season: Autumn - Leaves fall.");   break;
            case "WINTER": System.out.println("Season: Winter - Cold and snowy."); break;
            default:       System.out.println("Unknown season.");
        }

        // --- Example 3: Fall-through (intentional - multiple cases same block) ---
        int month = 11; // November
        int daysInMonth;
        switch (month) {
            case 1: case 3: case 5: case 7:
            case 8: case 10: case 12:
                daysInMonth = 31; break;
            case 4: case 6: case 9: case 11:
                daysInMonth = 30; break;
            case 2:
                daysInMonth = 28; break; // ignoring leap year for simplicity
            default:
                daysInMonth = -1;
        }
        System.out.println("Month " + month + " has " + daysInMonth + " days.");

        // --- Example 4: Calculator using switch ---
        double x = 20, y = 4;
        String operator = "/";
        double calcResult;
        switch (operator) {
            case "+": calcResult = x + y; break;
            case "-": calcResult = x - y; break;
            case "*": calcResult = x * y; break;
            case "/":
                if (y == 0) { System.out.println("Division by zero!"); return; }
                calcResult = x / y; break;
            default: System.out.println("Unknown operator."); return;
        }
        System.out.println(x + " " + operator + " " + y + " = " + calcResult);

        // --- Example 5: Switch Expression (Java 14+ arrow syntax) ---
        // Arrow switch: no fall-through, can return a value directly
        String grade = "B";
        String description = switch (grade) {
            case "A+" -> "Excellent";
            case "A"  -> "Very Good";
            case "B"  -> "Good";
            case "C"  -> "Average";
            default   -> "Below Average";
        };
        System.out.println("Grade " + grade + " = " + description);

        // --- Example 6: Switch expression with yield (multi-line block) ---
        int statusCode = 404;
        String message = switch (statusCode) {
            case 200 -> "OK";
            case 301 -> "Moved Permanently";
            case 404 -> {
                System.out.println("  (Logging 404 error...)");
                yield "Not Found"; // yield returns value from a block
            }
            case 500 -> "Internal Server Error";
            default  -> "Unknown Status";
        };
        System.out.println("HTTP " + statusCode + " -> " + message);
    }
}
'@

# ============================================================
# MODULE: Java_Loops_Demo
# ============================================================
$d = "$root\Java_Loops_Demo\src"

WJ "$d\ForLoopDemo.java" @'
public class ForLoopDemo {
    // The 'for' loop is best when the number of iterations is KNOWN upfront.
    // Syntax:  for (initialization; condition; update) { body }
    // Execution order: init -> check condition -> body -> update -> check condition ...

    public static void main(String[] args) {

        // --- Example 1: Basic counting loop ---
        System.out.println("Counting 1 to 5:");
        for (int i = 1; i <= 5; i++) {
            System.out.print(i + " ");
        }
        System.out.println();

        // --- Example 2: Counting backwards ---
        System.out.print("Countdown: ");
        for (int i = 10; i >= 1; i--) {
            System.out.print(i + " ");
        }
        System.out.println("LAUNCH!");

        // --- Example 3: Step by 2 (even numbers) ---
        System.out.print("Even numbers 0-20: ");
        for (int i = 0; i <= 20; i += 2) {
            System.out.print(i + " ");
        }
        System.out.println();

        // --- Example 4: Sum of first N natural numbers ---
        int n = 10, sum = 0;
        for (int i = 1; i <= n; i++) {
            sum += i;
        }
        System.out.println("Sum of 1 to " + n + " = " + sum);

        // --- Example 5: Multiplication table ---
        int base = 7;
        System.out.println("Multiplication table of " + base + ":");
        for (int i = 1; i <= 10; i++) {
            System.out.printf("  %d x %d = %d%n", base, i, base * i);
        }

        // --- Example 6: Iterate over a char array (for array see Arrays module) ---
        char[] vowels = {'a', 'e', 'i', 'o', 'u'};
        System.out.print("Vowels: ");
        for (int i = 0; i < vowels.length; i++) {
            System.out.print(vowels[i] + " ");
        }
        System.out.println();

        // --- Example 7: Enhanced for-each loop (for arrays and collections) ---
        String[] days = {"Mon", "Tue", "Wed", "Thu", "Fri"};
        System.out.print("Working days: ");
        for (String day : days) {
            System.out.print(day + " ");
        }
        System.out.println();

        // --- Example 8: Factorial using for loop ---
        int num = 6, factorial = 1;
        for (int i = 1; i <= num; i++) {
            factorial *= i;
        }
        System.out.println(num + "! = " + factorial);

        // --- Example 9: Loop with declared but not initialised counter ---
        int outer;
        for (outer = 1; outer <= 3; outer++) {
            System.out.println("outer = " + outer);
        }
        // 'outer' is still accessible here (declared outside the for)
        System.out.println("After loop, outer = " + outer);
    }
}
'@

WJ "$d\WhileLoopDemo.java" @'
public class WhileLoopDemo {
    // The 'while' loop is best when the number of iterations is NOT known upfront.
    // Syntax:  while (condition) { body }
    // The condition is checked BEFORE each iteration.
    // If the condition is initially false, the body never executes.

    public static void main(String[] args) {

        // --- Example 1: Basic while ---
        int count = 1;
        System.out.print("While 1-5: ");
        while (count <= 5) {
            System.out.print(count + " ");
            count++;
        }
        System.out.println();

        // --- Example 2: Sum until value exceeds limit ---
        int total = 0, n = 1;
        while (total + n <= 100) {
            total += n;
            n++;
        }
        System.out.println("Sum of consecutive numbers <= 100: " + total + " (stopped at n=" + n + ")");

        // --- Example 3: Reverse digits of a number ---
        int num     = 98_765;
        int original = num;
        int reversed = 0;
        while (num != 0) {
            int digit = num % 10;   // extract last digit
            reversed  = reversed * 10 + digit;
            num       /= 10;         // remove last digit
        }
        System.out.println("Reverse of " + original + " = " + reversed);

        // --- Example 4: Count digits in a number ---
        int number   = 1_000_000;
        int digitCount = 0;
        int temp     = number;
        while (temp != 0) {
            digitCount++;
            temp /= 10;
        }
        System.out.println(number + " has " + digitCount + " digits.");

        // --- Example 5: GCD (Greatest Common Divisor) using Euclidean algorithm ---
        int a = 56, b = 98;
        int x = a, y = b;
        while (y != 0) {
            int remainder = x % y;
            x = y;
            y = remainder;
        }
        System.out.println("GCD of " + a + " and " + b + " = " + x);

        // --- Example 6: Power calculation (base^exp without Math.pow) ---
        int base = 3, exp = 5;
        long power = 1;
        int e = exp;
        while (e > 0) {
            power *= base;
            e--;
        }
        System.out.println(base + "^" + exp + " = " + power);

        // --- Example 7: Infinite loop guarded by a condition ---
        // In real applications, while(true) is used with break for menu systems, servers, etc.
        int attempts = 0;
        String correctPin = "1234";
        String[] userPins = {"0000", "1111", "1234"}; // simulates user input attempts
        int idx = 0;
        while (true) {
            String entered = userPins[idx++];
            attempts++;
            if (entered.equals(correctPin)) {
                System.out.println("PIN accepted after " + attempts + " attempt(s).");
                break;
            }
            System.out.println("Wrong PIN: " + entered + ". Try again.");
            if (attempts >= 3) {
                System.out.println("Account locked after 3 failed attempts.");
                break;
            }
        }
    }
}
'@

WJ "$d\DoWhileLoopDemo.java" @'
public class DoWhileLoopDemo {
    // The 'do-while' loop executes the body AT LEAST ONCE, then checks the condition.
    // Syntax:  do { body } while (condition);
    // Key difference from 'while': condition is checked AFTER the first execution.

    public static void main(String[] args) {

        // --- Example 1: Basic do-while ---
        int i = 1;
        System.out.print("do-while 1-5: ");
        do {
            System.out.print(i + " ");
            i++;
        } while (i <= 5);
        System.out.println();

        // --- Example 2: Runs at least once even when condition is initially false ---
        int x = 10;
        System.out.println("\nDemonstrating at-least-once execution:");
        do {
            System.out.println("  This body executes once even though x=" + x + " violates x < 5.");
            x++;
        } while (x < 5);
        System.out.println("After loop, x = " + x);

        // --- Example 3: Menu-driven simulation (classic do-while use case) ---
        System.out.println("\nMenu simulation:");
        int[] menuChoices = {1, 2, 3, 4}; // simulates user input
        int choiceIdx = 0;
        int choice;
        do {
            choice = menuChoices[choiceIdx++];
            switch (choice) {
                case 1 -> System.out.println("  [1] View profile");
                case 2 -> System.out.println("  [2] Edit profile");
                case 3 -> System.out.println("  [3] View reports");
                case 4 -> System.out.println("  [4] Exit selected.");
                default -> System.out.println("  Invalid option.");
            }
        } while (choice != 4);

        // --- Example 4: Input validation loop ---
        // Simulated: keep asking until a valid positive number is entered
        int[] inputs = {-5, 0, -1, 7}; // simulates user entries
        int inputIdx = 0;
        int validInput;
        do {
            validInput = inputs[inputIdx++];
            System.out.println("User entered: " + validInput +
                    (validInput > 0 ? " -> accepted" : " -> invalid, try again"));
        } while (validInput <= 0);

        // --- Example 5: Sum of digits until a specific sum threshold ---
        int num  = 54_321;
        int digitSum = 0;
        int copyNum = num;
        do {
            digitSum += copyNum % 10;
            copyNum   /= 10;
        } while (copyNum > 0);
        System.out.println("\nSum of digits of " + num + " = " + digitSum);

        // --- Example 6: Comparison -- while vs do-while ---
        // while: condition checked first  -> may never run
        // do-while: body runs first       -> always runs at least once
        System.out.println("\nwhile vs do-while with false condition:");
        int val = 100;

        System.out.print("while loop: ");
        while (val < 5) {
            System.out.print("executed ");  // never printed
        }
        System.out.println("(never ran)");

        System.out.print("do-while loop: ");
        do {
            System.out.print("executed once");  // prints exactly once
            val++;
        } while (val < 5);
        System.out.println(" (ran once even though condition was false initially)");
    }
}
'@

WJ "$d\NestedLoopDemo.java" @'
public class NestedLoopDemo {
    // A nested loop is a loop placed inside another loop.
    // The inner loop completes ALL its iterations for EACH iteration of the outer loop.
    // Time complexity of nested loops is typically O(n^2) for equal ranges.

    public static void main(String[] args) {

        // --- Example 1: Simple nested loop (multiplication table grid) ---
        System.out.println("--- Multiplication Table (1x1 to 5x5) ---");
        System.out.printf("%-4s", " ");
        for (int col = 1; col <= 5; col++) System.out.printf("%-6d", col);
        System.out.println();
        for (int row = 1; row <= 5; row++) {
            System.out.printf("%-4d", row);
            for (int col = 1; col <= 5; col++) {
                System.out.printf("%-6d", row * col);
            }
            System.out.println();
        }

        // --- Example 2: Right-angled triangle of stars ---
        System.out.println("\n--- Right Triangle ---");
        for (int row = 1; row <= 5; row++) {
            for (int col = 1; col <= row; col++) {
                System.out.print("* ");
            }
            System.out.println();
        }

        // --- Example 3: Inverted triangle ---
        System.out.println("\n--- Inverted Triangle ---");
        for (int row = 5; row >= 1; row--) {
            for (int col = 1; col <= row; col++) {
                System.out.print("* ");
            }
            System.out.println();
        }

        // --- Example 4: Number pyramid ---
        System.out.println("\n--- Number Pyramid ---");
        for (int row = 1; row <= 5; row++) {
            for (int space = row; space <= 5; space++) System.out.print(" ");
            for (int col = 1; col <= row; col++) System.out.print(col + " ");
            System.out.println();
        }

        // --- Example 5: Check all prime numbers in range 2-30 ---
        System.out.println("\n--- Prime Numbers from 2 to 30 ---");
        System.out.print("Primes: ");
        for (int candidate = 2; candidate <= 30; candidate++) {
            boolean isPrime = true;
            for (int divisor = 2; divisor <= Math.sqrt(candidate); divisor++) {
                if (candidate % divisor == 0) {
                    isPrime = false;
                    break; // inner loop optimisation -- stop checking once factor found
                }
            }
            if (isPrime) System.out.print(candidate + " ");
        }
        System.out.println();

        // --- Example 6: Print all pairs that sum to a target ---
        int target = 10;
        System.out.println("\n--- Pairs summing to " + target + " (from 1-9) ---");
        for (int p = 1; p <= 9; p++) {
            for (int q = p + 1; q <= 9; q++) { // q starts at p+1 to avoid duplicates
                if (p + q == target) {
                    System.out.println("  (" + p + ", " + q + ")");
                }
            }
        }

        // --- Example 7: 2D matrix traversal ---
        System.out.println("\n--- 2D Matrix Traversal ---");
        int[][] matrix = {
            {1, 2, 3},
            {4, 5, 6},
            {7, 8, 9}
        };
        System.out.println("Matrix:");
        for (int row = 0; row < matrix.length; row++) {
            for (int col = 0; col < matrix[row].length; col++) {
                System.out.printf("%-4d", matrix[row][col]);
            }
            System.out.println();
        }
    }
}
'@

WJ "$d\BreakAndContinueDemo.java" @'
public class BreakAndContinueDemo {
    // break:    immediately exits the NEAREST enclosing loop or switch.
    // continue: skips the REST of the current iteration and jumps to the next.
    // Labelled break/continue: exit or skip an OUTER loop from within a nested loop.

    public static void main(String[] args) {

        // --- Example 1: break in a for loop ---
        System.out.print("break at 5: ");
        for (int i = 1; i <= 10; i++) {
            if (i == 5) break;        // exits the loop immediately
            System.out.print(i + " ");
        }
        System.out.println("(loop exited at 5)");

        // --- Example 2: break in a while loop -- linear search ---
        int[] numbers = {3, 7, 1, 9, 4, 2, 8};
        int   target   = 9;
        int   foundAt  = -1;
        for (int i = 0; i < numbers.length; i++) {
            if (numbers[i] == target) {
                foundAt = i;
                break;  // stop searching once found
            }
        }
        System.out.println("Searched for " + target + " -> found at index " + foundAt);

        // --- Example 3: continue -- skip even numbers ---
        System.out.print("Odd numbers 1-10: ");
        for (int i = 1; i <= 10; i++) {
            if (i % 2 == 0) continue;  // skip even; jump to next iteration
            System.out.print(i + " ");
        }
        System.out.println();

        // --- Example 4: continue -- skip negative values ---
        int[] data   = {5, -3, 8, -1, 0, 12, -7, 6};
        int positiveSum = 0;
        for (int val : data) {
            if (val <= 0) continue;   // skip zeros and negatives
            positiveSum += val;
        }
        System.out.println("Sum of positives only: " + positiveSum);

        // --- Example 5: Labelled break -- exit outer loop from inner loop ---
        System.out.println("Labelled break (stop outer when inner finds 5):");
        outerLoop:                          // label placed on the outer loop
        for (int row = 1; row <= 3; row++) {
            for (int col = 1; col <= 5; col++) {
                System.out.print("[" + row + "," + col + "] ");
                if (col == 3) {
                    System.out.println("<-- labelled break here");
                    break outerLoop;        // jumps OUT of the outer 'for row' loop
                }
            }
        }
        System.out.println("Both loops exited.");

        // --- Example 6: Labelled continue -- skip to next outer iteration ---
        System.out.println("Labelled continue (skip rest of inner when col==2):");
        outer:
        for (int row = 1; row <= 3; row++) {
            for (int col = 1; col <= 4; col++) {
                if (col == 2) continue outer;  // skip rest of inner, advance outer
                System.out.print("[" + row + "," + col + "] ");
            }
        }
        System.out.println();

        // --- Example 7: Real-world -- break from infinite loop when done ---
        System.out.println("Processing orders:");
        String[] orders = {"ORD001", "ORD002", "DONE", "ORD003"};
        for (String order : orders) {
            if (order.equals("DONE")) {
                System.out.println("Sentinel 'DONE' found. Stopping processing.");
                break;
            }
            System.out.println("  Processing: " + order);
        }
    }
}
'@

# ============================================================
# MODULE: Java_Arrays_Demo
# ============================================================
$d = "$root\Java_Arrays_Demo\src"

WJ "$d\ArrayBasicsDemo.java" @'
import java.util.Arrays;

public class ArrayBasicsDemo {
    // An array is a fixed-size, ordered collection of elements of the SAME type.
    // Arrays are stored as contiguous memory on the heap.
    // Index starts at 0 and ends at length-1.
    // Accessing index out of range throws ArrayIndexOutOfBoundsException.

    public static void main(String[] args) {

        // --- Declaring and initialising ---
        int[] ages = new int[5];          // default values: 0
        ages[0] = 25; ages[1] = 30; ages[2] = 22; ages[3] = 28; ages[4] = 35;

        // Array literal syntax (declare + initialise together)
        double[] prices = {9.99, 19.99, 5.49, 14.99};

        String[] names  = {"Alice", "Bob", "Charlie", "Diana"};

        // --- Accessing elements ---
        System.out.println("First age  : " + ages[0]);
        System.out.println("Last price : " + prices[prices.length - 1]);
        System.out.println("Name at 2  : " + names[2]);

        // --- Array length property ---
        System.out.println("Length of ages[] : " + ages.length);

        // --- Iterating with for loop ---
        System.out.print("All ages   : ");
        for (int i = 0; i < ages.length; i++) {
            System.out.print(ages[i] + " ");
        }
        System.out.println();

        // --- Iterating with enhanced for-each ---
        System.out.print("All names  : ");
        for (String name : names) {
            System.out.print(name + " ");
        }
        System.out.println();

        // --- Modifying an element ---
        prices[2] = 6.99;
        System.out.println("Updated price[2] = " + prices[2]);

        // --- Default values when not explicitly initialised ---
        int[]     intArr  = new int[3];     // {0, 0, 0}
        double[]  dblArr  = new double[3];  // {0.0, 0.0, 0.0}
        boolean[] boolArr = new boolean[3]; // {false, false, false}
        String[]  strArr  = new String[3];  // {null, null, null}
        System.out.println("Default int    : " + intArr[0]);
        System.out.println("Default double : " + dblArr[0]);
        System.out.println("Default boolean: " + boolArr[0]);
        System.out.println("Default String : " + strArr[0]);

        // --- Arrays.toString() for printing ---
        System.out.println("ages as String : " + Arrays.toString(ages));
        System.out.println("prices as String: " + Arrays.toString(prices));

        // --- Copying arrays ---
        int[] copy = Arrays.copyOf(ages, ages.length);
        copy[0] = 999;
        System.out.println("Original ages[0]: " + ages[0] + " | Copy ages[0]: " + copy[0]);

        // Partial copy
        int[] partial = Arrays.copyOfRange(ages, 1, 4); // indices 1, 2, 3
        System.out.println("Partial copy: " + Arrays.toString(partial));
    }
}
'@

WJ "$d\ArrayTraversalDemo.java" @'
import java.util.Arrays;

public class ArrayTraversalDemo {
    // Traversal means visiting every element in an array.
    // Main approaches: for loop, enhanced for-each, Arrays.stream().

    public static void main(String[] args) {

        int[] scores = {88, 72, 95, 61, 83, 77, 90, 55, 68, 84};

        // --- 1. Classic for loop (access index) ---
        System.out.println("--- 1. Classic for loop with index ---");
        for (int i = 0; i < scores.length; i++) {
            System.out.printf("  scores[%d] = %d%n", i, scores[i]);
        }

        // --- 2. Enhanced for-each (clean, no index needed) ---
        System.out.println("\n--- 2. Enhanced for-each ---");
        int total = 0;
        for (int score : scores) {
            total += score;
        }
        System.out.println("Sum = " + total + "  | Average = " + (double)total / scores.length);

        // --- 3. Find max and min in one pass ---
        System.out.println("\n--- 3. Min and Max ---");
        int max = scores[0], min = scores[0];
        int maxIdx = 0, minIdx = 0;
        for (int i = 1; i < scores.length; i++) {
            if (scores[i] > max) { max = scores[i]; maxIdx = i; }
            if (scores[i] < min) { min = scores[i]; minIdx = i; }
        }
        System.out.println("Max = " + max + " at index " + maxIdx);
        System.out.println("Min = " + min + " at index " + minIdx);

        // --- 4. Count elements satisfying condition ---
        System.out.println("\n--- 4. Count scores >= 80 ---");
        int highScoreCount = 0;
        for (int score : scores) {
            if (score >= 80) highScoreCount++;
        }
        System.out.println("Scores >= 80: " + highScoreCount + " out of " + scores.length);

        // --- 5. Reverse traversal ---
        System.out.print("\n--- 5. Reverse order ---\n");
        for (int i = scores.length - 1; i >= 0; i--) {
            System.out.print(scores[i] + " ");
        }
        System.out.println();

        // --- 6. Print even-indexed elements ---
        System.out.print("\n--- 6. Even-indexed elements ---\n");
        for (int i = 0; i < scores.length; i += 2) {
            System.out.print(scores[i] + " ");
        }
        System.out.println();

        // --- 7. String array traversal with transformation ---
        String[] words = {"hello", "java", "arrays", "demo"};
        System.out.println("\n--- 7. Uppercase transformation ---");
        for (int i = 0; i < words.length; i++) {
            words[i] = words[i].toUpperCase();
        }
        System.out.println(Arrays.toString(words));

        // --- 8. Two-pointer technique (sum pair check) ---
        System.out.println("\n--- 8. Two-pointer sum search in sorted array ---");
        int[] sorted = {1, 4, 6, 8, 11, 14, 17, 20};
        int   targetSum = 22;
        int   left = 0, right = sorted.length - 1;
        boolean pairFound = false;
        while (left < right) {
            int s = sorted[left] + sorted[right];
            if      (s == targetSum) { System.out.println("Pair found: " + sorted[left] + " + " + sorted[right]); pairFound = true; break; }
            else if (s < targetSum)  { left++; }
            else                     { right--; }
        }
        if (!pairFound) System.out.println("No pair sums to " + targetSum);
    }
}
'@

WJ "$d\ArrayUpdateAndSumDemo.java" @'
import java.util.Arrays;

public class ArrayUpdateAndSumDemo {
    // Core array operations: update elements, compute aggregates (sum, average, product),
    // apply bulk transformations, rotate, shift, and use Arrays utility methods.

    public static void main(String[] args) {

        // --- 1. Update single and multiple elements ---
        int[] temps = {22, 25, 19, 30, 27};
        System.out.println("Original temps: " + Arrays.toString(temps));
        temps[2] = 23;          // single update
        for (int i = 0; i < temps.length; i++) temps[i] += 1; // bulk: add 1 to all
        System.out.println("After updates : " + Arrays.toString(temps));

        // --- 2. Sum, average, product ---
        int[] nums = {4, 7, 2, 9, 5, 6, 3, 8};
        int sum = 0; long product = 1;
        for (int n : nums) { sum += n; product *= n; }
        double avg = (double) sum / nums.length;
        System.out.printf("Sum=%d  Average=%.2f  Product=%d%n", sum, avg, product);

        // --- 3. Increment all elements (e.g., apply discount to all prices) ---
        double[] prices = {100.0, 250.0, 75.0, 400.0, 180.0};
        double discountRate = 0.10;
        System.out.println("\nBefore discount: " + Arrays.toString(prices));
        for (int i = 0; i < prices.length; i++) {
            prices[i] = prices[i] * (1 - discountRate);
        }
        System.out.println("After 10% off : " + Arrays.toString(prices));

        // --- 4. Fill array with a value (Arrays.fill) ---
        int[] filled = new int[6];
        Arrays.fill(filled, 99);
        System.out.println("\nAfter Arrays.fill(99): " + Arrays.toString(filled));

        // --- 5. Sort ascending and descending ---
        int[] data = {5, 2, 8, 1, 9, 3, 7, 4, 6};
        System.out.println("\nUnsorted : " + Arrays.toString(data));
        Arrays.sort(data);
        System.out.println("Sorted   : " + Arrays.toString(data));
        // Reverse manually (Arrays.sort doesn't directly sort primitives descending)
        for (int left = 0, right = data.length - 1; left < right; left++, right--) {
            int tmp = data[left]; data[left] = data[right]; data[right] = tmp;
        }
        System.out.println("Reversed : " + Arrays.toString(data));

        // --- 6. Left rotation by k positions ---
        int[] arr = {1, 2, 3, 4, 5};
        int k = 2;
        System.out.println("\nOriginal:      " + Arrays.toString(arr));
        int[] rotated = new int[arr.length];
        for (int i = 0; i < arr.length; i++) {
            rotated[(i - k + arr.length) % arr.length] = arr[i];
        }
        System.out.println("Left rotate " + k + ": " + Arrays.toString(rotated));

        // --- 7. Frequency count ---
        int[] votes = {3, 1, 2, 3, 1, 3, 2, 1, 3, 2};
        int[] freq  = new int[4]; // candidates 1, 2, 3
        for (int v : votes) freq[v]++;
        System.out.println("\nVote counts: Candidate1=" + freq[1] +
                           " Candidate2=" + freq[2] + " Candidate3=" + freq[3]);

        // --- 8. Arrays.equals and Arrays.compare ---
        int[] a1 = {1, 2, 3}, a2 = {1, 2, 3}, a3 = {1, 2, 4};
        System.out.println("\na1 == a2 (reference): " + (a1 == a2));
        System.out.println("Arrays.equals(a1,a2): " + Arrays.equals(a1, a2)); // true
        System.out.println("Arrays.equals(a1,a3): " + Arrays.equals(a1, a3)); // false
    }
}
'@

WJ "$d\ArraySearchDemo.java" @'
import java.util.Arrays;

public class ArraySearchDemo {
    // Linear Search: check each element one by one. O(n). Works on unsorted arrays.
    // Binary Search: divide-and-conquer on SORTED array. O(log n). Much faster for large n.
    // Arrays.binarySearch(): built-in binary search. Array MUST be sorted.

    // --- Linear Search helper ---
    static int linearSearch(int[] arr, int target) {
        for (int i = 0; i < arr.length; i++) {
            if (arr[i] == target) return i;
        }
        return -1; // not found
    }

    // --- Binary Search helper (manual implementation) ---
    static int binarySearch(int[] arr, int target) {
        int low = 0, high = arr.length - 1;
        while (low <= high) {
            int mid = low + (high - low) / 2; // avoids integer overflow vs (low+high)/2
            if      (arr[mid] == target) return mid;
            else if (arr[mid] <  target) low  = mid + 1;
            else                         high = mid - 1;
        }
        return -1;
    }

    public static void main(String[] args) {

        // --- Linear Search ---
        int[] unsorted = {64, 34, 25, 12, 22, 11, 90, 45, 78};
        System.out.println("Array: " + Arrays.toString(unsorted));

        int target1 = 22;
        int idx1    = linearSearch(unsorted, target1);
        System.out.println("Linear search for " + target1 + " -> index: " + idx1);

        int target2 = 100;
        int idx2    = linearSearch(unsorted, target2);
        System.out.println("Linear search for " + target2 + " -> index: " + idx2 + " (not found)");

        // --- Binary Search on sorted array ---
        int[] sorted = {5, 12, 18, 24, 31, 47, 59, 63, 78, 95};
        System.out.println("\nSorted array: " + Arrays.toString(sorted));

        int target3 = 47;
        int idx3    = binarySearch(sorted, target3);
        System.out.println("Binary search for " + target3 + " -> index: " + idx3);

        int target4 = 100;
        int idx4    = binarySearch(sorted, target4);
        System.out.println("Binary search for " + target4 + " -> index: " + idx4 + " (not found)");

        // --- Arrays.binarySearch() (built-in; array must be sorted) ---
        System.out.println("\nArrays.binarySearch() for " + target3 + ": " +
                Arrays.binarySearch(sorted, target3));

        // --- Count occurrences (linear scan) ---
        int[] data   = {3, 1, 4, 1, 5, 9, 2, 6, 5, 3, 5};
        int   lookFor = 5;
        int   countOf = 0;
        for (int v : data) if (v == lookFor) countOf++;
        System.out.println("\nOccurrences of " + lookFor + " in " + Arrays.toString(data) + " = " + countOf);

        // --- Search in String array ---
        String[] fruits = {"Apple", "Banana", "Cherry", "Date", "Fig", "Grape"};
        String   fruit   = "Cherry";
        int      strIdx  = -1;
        for (int i = 0; i < fruits.length; i++) {
            if (fruits[i].equals(fruit)) { strIdx = i; break; }
        }
        System.out.println("\nSearch for '" + fruit + "' in fruits[] -> index: " + strIdx);

        // --- Performance note ---
        System.out.println("\nTime Complexity:");
        System.out.println("  Linear Search : O(n) - checks every element in worst case");
        System.out.println("  Binary Search : O(log n) - halves search space each step");
        System.out.println("  For n=1000: linear ~1000 steps, binary ~10 steps");
    }
}
'@

WJ "$d\TwoDimensionalArrayDemo.java" @'
import java.util.Arrays;

public class TwoDimensionalArrayDemo {
    // A 2D array is an array of arrays -- think of it as a table with rows and columns.
    // Syntax: int[][] matrix = new int[rows][cols];
    // Access: matrix[rowIndex][colIndex]
    // Java also supports jagged arrays (rows of different lengths).

    public static void main(String[] args) {

        // --- 1. Declare, initialize and print a 2D array ---
        int[][] grid = {
            {1,  2,  3,  4},
            {5,  6,  7,  8},
            {9, 10, 11, 12}
        };
        System.out.println("--- 2D Grid (3 rows x 4 cols) ---");
        for (int r = 0; r < grid.length; r++) {
            System.out.println(Arrays.toString(grid[r]));
        }

        // --- 2. Row and column counts ---
        System.out.println("Rows = " + grid.length + "  Cols = " + grid[0].length);

        // --- 3. Access specific element ---
        System.out.println("grid[1][2] = " + grid[1][2]); // row 1, col 2 -> 7

        // --- 4. Matrix sum ---
        int total = 0;
        for (int[] row : grid) for (int val : row) total += val;
        System.out.println("Sum of all elements: " + total);

        // --- 5. Row sums ---
        System.out.println("Row sums: ");
        for (int r = 0; r < grid.length; r++) {
            int rowSum = 0;
            for (int val : grid[r]) rowSum += val;
            System.out.println("  Row " + r + " sum = " + rowSum);
        }

        // --- 6. Column sums ---
        System.out.println("Column sums: ");
        for (int c = 0; c < grid[0].length; c++) {
            int colSum = 0;
            for (int r = 0; r < grid.length; r++) colSum += grid[r][c];
            System.out.println("  Col " + c + " sum = " + colSum);
        }

        // --- 7. Matrix transpose (swap rows and columns) ---
        int rows = grid.length, cols = grid[0].length;
        int[][] transposed = new int[cols][rows];
        for (int r = 0; r < rows; r++)
            for (int c = 0; c < cols; c++)
                transposed[c][r] = grid[r][c];

        System.out.println("Transposed (4x3):");
        for (int[] row : transposed) System.out.println(Arrays.toString(row));

        // --- 8. Matrix multiplication (2x3 * 3x2) ---
        int[][] A = {{1, 2, 3}, {4, 5, 6}};         // 2x3
        int[][] B = {{7, 8}, {9, 10}, {11, 12}};    // 3x2
        int[][] C = new int[2][2];                   // result 2x2
        for (int r = 0; r < 2; r++)
            for (int c = 0; c < 2; c++)
                for (int k = 0; k < 3; k++)
                    C[r][c] += A[r][k] * B[k][c];
        System.out.println("Matrix A*B:");
        for (int[] row : C) System.out.println(Arrays.toString(row));

        // --- 9. Jagged (ragged) array ---
        System.out.println("Jagged array:");
        int[][] jagged = new int[4][];
        for (int r = 0; r < jagged.length; r++) {
            jagged[r] = new int[r + 1]; // row 0 has 1 col, row 1 has 2, etc.
            for (int c = 0; c <= r; c++) jagged[r][c] = c + 1;
        }
        for (int[] row : jagged) System.out.println(Arrays.toString(row));
    }
}
'@

Write-Host "`n=== Batch 1 complete ==="

