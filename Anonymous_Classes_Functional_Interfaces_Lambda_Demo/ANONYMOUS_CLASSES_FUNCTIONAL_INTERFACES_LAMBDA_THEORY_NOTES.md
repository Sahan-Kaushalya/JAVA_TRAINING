# Anonymous Inner Classes, Functional Interfaces, and Lambda Expressions in Java - Complete Theory Notes

---

## 1. Introduction

Modern Java code is built on three closely related ideas that together make it cleaner, shorter, and more expressive:

- **Anonymous Inner Classes** – nameless class implementations created and used in one place
- **Functional Interfaces** – interfaces that define exactly one abstract method (the contract)
- **Lambda Expressions** – short syntax for implementing a functional interface inline

Understanding how these three work together, and how lambdas evolved from anonymous classes, is essential for every Java developer.

---

## 2. Anonymous Inner Classes

### 2.1 What is an Anonymous Inner Class?

An anonymous inner class is a class that:

- has **no name**
- is **declared and instantiated at the same time**
- is written inline wherever an object is needed
- can extend an abstract class OR implement an interface

### 2.2 Syntax

```java
// Extending an abstract class
AbstractClass obj = new AbstractClass() {
    @Override
    void abstractMethod() {
        // implementation here
    }
};

// Implementing an interface
Interface obj = new Interface() {
    @Override
    public void interfaceMethod() {
        // implementation here
    }
};
```

### 2.3 Key Rules

| Rule | Details |
|------|---------|
| No class name | It is compiled internally as `OuterClass$1`, `OuterClass$2`, etc. |
| One-time use | Cannot be referenced by name elsewhere |
| Can read outer variables | But only if they are `final` or effectively final |
| Can override multiple methods | Unlike lambda, not limited to one abstract method |
| Can implement interfaces with multiple abstract methods | Yes, unlike lambda |

### 2.4 When to Use Anonymous Inner Classes

- When you need to override **more than one method**
- When the implementation is **used only once**
- When you need to **extend a class** (lambdas only work with interfaces)
- For **event handling** in GUI frameworks (Swing, JavaFX)
- For **callback patterns**

### 2.5 Disadvantages of Anonymous Inner Classes

- Verbose and repetitive syntax
- Harder to read for simple single-method logic
- Java 8 lambda expressions replaced most anonymous class usage

---

## 3. Functional Interfaces

### 3.1 What is a Functional Interface?

A functional interface is an interface that contains **exactly one abstract method**.

It may also contain:
- any number of `default` methods
- any number of `static` methods
- methods inherited from `java.lang.Object` (such as `equals`, `toString`) are not counted

### 3.2 The @FunctionalInterface Annotation

```java
@FunctionalInterface
interface Printer {
    void print(String message);
}
```

The `@FunctionalInterface` annotation is optional but recommended. It tells the compiler to enforce the single-abstract-method rule and throws a compile error if violated.

### 3.3 Why Functional Interfaces Exist

They are the **target type** for lambda expressions.
Without functional interfaces, lambdas would have no type to attach to.

```
Lambda Expression  →  assigned to  →  Functional Interface variable
```

### 3.4 Built-in Functional Interfaces in java.util.function

Java 8 provides a rich set of ready-made functional interfaces. The most important are:

#### Predicate\<T>
```java
@FunctionalInterface
public interface Predicate<T> {
    boolean test(T t);
}
```
- **Purpose:** Test a condition, return true or false
- **Use case:** filtering, validation

#### Consumer\<T>
```java
@FunctionalInterface
public interface Consumer<T> {
    void accept(T t);
}
```
- **Purpose:** Accept a value and perform an action, return nothing
- **Use case:** printing, saving, sending

#### Supplier\<T>
```java
@FunctionalInterface
public interface Supplier<T> {
    T get();
}
```
- **Purpose:** Return a value without accepting input
- **Use case:** factories, lazy initialization

#### Function\<T, R>
```java
@FunctionalInterface
public interface Function<T, R> {
    R apply(T t);
}
```
- **Purpose:** Transform one type to another
- **Use case:** mapping, conversion

#### BiFunction\<T, U, R>
```java
@FunctionalInterface
public interface BiFunction<T, U, R> {
    R apply(T t, U u);
}
```
- **Purpose:** Accept two inputs, return one output
- **Use case:** combining values, arithmetic

#### UnaryOperator\<T>
- Extends `Function<T, T>` — input and output are the same type

#### BinaryOperator\<T>
- Extends `BiFunction<T, T, T>` — two same-type inputs, one same-type output

### 3.5 Summary Table of Built-in Functional Interfaces

| Interface | Input | Output | Method | Use |
|-----------|-------|--------|--------|-----|
| `Predicate<T>` | T | boolean | `test(T)` | condition check |
| `Consumer<T>` | T | void | `accept(T)` | action on value |
| `Supplier<T>` | none | T | `get()` | produce a value |
| `Function<T,R>` | T | R | `apply(T)` | transform value |
| `BiFunction<T,U,R>` | T, U | R | `apply(T,U)` | combine two values |
| `UnaryOperator<T>` | T | T | `apply(T)` | transform same type |
| `BinaryOperator<T>` | T, T | T | `apply(T,T)` | combine same type |

---

## 4. Lambda Expressions

### 4.1 What is a Lambda Expression?

A lambda expression is an **anonymous function** — a short block of code that:

- has no name
- can take parameters
- can return a value
- can be passed around like a variable

It is used to implement the **single abstract method** of a functional interface.

### 4.2 Lambda Syntax

```java
(parameters) -> expression
(parameters) -> { statements; }
```

### 4.3 Syntax Variations

```java
// No parameters
() -> System.out.println("Hello")

// One parameter (parentheses optional)
name -> System.out.println(name)
(name) -> System.out.println(name)

// Multiple parameters
(a, b) -> a + b

// Block body with return statement
(a, b) -> {
    int result = a + b;
    return result;
}

// With explicit types (optional, compiler can infer)
(int a, int b) -> a + b
```

### 4.4 Lambda vs Anonymous Inner Class Comparison

| Feature | Anonymous Inner Class | Lambda Expression |
|---------|----------------------|-------------------|
| Class name | none (autogenerated) | none |
| Syntax verbosity | verbose | concise |
| Can extend abstract class | yes | no |
| Works with functional interfaces | yes | yes |
| Can have multiple methods | yes | no (one only) |
| `this` keyword inside | refers to anonymous class instance | refers to enclosing class instance |
| Performance | creates a separate class file at compile time | uses invokedynamic bytecode instruction |
| Introduced in Java | early versions | Java 8 |

### 4.5 Type Inference in Lambdas

The compiler automatically detects the type of lambda parameters from the functional interface:

```java
// The compiler knows operate(int a, int b) from the Calculator interface
Calculator add = (a, b) -> a + b;   // compiler infers a and b are int
```

### 4.6 Effectively Final Variables

A lambda can capture variables from its surrounding scope, but those variables must be **final or effectively final** (their value never changes after assignment).

```java
String prefix = "Hello";       // effectively final
Printer p = msg -> System.out.println(prefix + " " + msg);
```

### 4.7 Method References (Related Concept)

A method reference is an even shorter lambda when the lambda only calls one existing method.

```java
// Lambda form
names.forEach(name -> System.out.println(name));

// Method reference form
names.forEach(System.out::println);
```

Types of method references:

| Type | Syntax | Example |
|------|--------|---------|
| Static method | `ClassName::staticMethod` | `Math::abs` |
| Instance method of object | `object::method` | `str::toUpperCase` |
| Instance method of type | `ClassName::method` | `String::toUpperCase` |
| Constructor | `ClassName::new` | `ArrayList::new` |

---

## 5. How All Three Work Together

```text
[Functional Interface]        defines the contract (one abstract method)
         |
         |  implemented by
         v
[Anonymous Inner Class]       verbose, multi-method friendly
         |
         |  modernized by
         v
[Lambda Expression]           concise, single-method shorthand
```

### Example – Same task, three styles

```java
// Style 1 – with an object from a regular named class
class AddCalculator implements Calculator {
    public int operate(int a, int b) { return a + b; }
}
Calculator c1 = new AddCalculator();

// Style 2 – with anonymous inner class
Calculator c2 = new Calculator() {
    public int operate(int a, int b) { return a + b; }
};

// Style 3 – with lambda expression
Calculator c3 = (a, b) -> a + b;
```

All three produce the same result. Lambdas are just the neatest way to do it.

---

## 6. Chaining Functional Interfaces

Functional interfaces like `Predicate` and `Function` support chaining.

### Predicate chaining
```java
Predicate<Integer> isPositive = n -> n > 0;
Predicate<Integer> isEven     = n -> n % 2 == 0;

Predicate<Integer> isPositiveAndEven = isPositive.and(isEven);
Predicate<Integer> isPositiveOrEven  = isPositive.or(isEven);
Predicate<Integer> isNotPositive     = isPositive.negate();
```

### Function chaining
```java
Function<Integer, Integer> doubleIt = n -> n * 2;
Function<Integer, Integer> addTen   = n -> n + 10;

Function<Integer, Integer> doubleThenAddTen = doubleIt.andThen(addTen);
```

---

## 7. Real-World Use Cases

| Use Case | Which to use |
|----------|-------------|
| Button click handler | Anonymous class (multi-method) OR lambda (single Action) |
| Filter a list of users | `Predicate` + lambda |
| Print all items | `Consumer` + lambda |
| Create a new object | `Supplier` + lambda |
| Convert data type | `Function` + lambda |
| Sort a collection | `Comparator` + lambda |
| Validate input | `Predicate` + lambda |
| Process stream of data | Lambda + Stream API |

---

## 8. Common Mistakes Students Make

### Mistake 1
Assuming any interface can be used as a lambda target.
A lambda only works with interfaces that have **exactly one abstract method**.

### Mistake 2
Trying to modify an outer variable inside a lambda.
Variables captured by lambda must be **final or effectively final**.

### Mistake 3
Confusing `this` inside a lambda vs inside an anonymous class.
- Inside lambda: `this` refers to the **enclosing class**
- Inside anonymous class: `this` refers to the **anonymous class itself**

### Mistake 4
Forgetting the semicolon after an anonymous class instantiation.
```java
Runnable r = new Runnable() {
    public void run() { ... }
};    // <-- this semicolon is required
```

### Mistake 5
Using an anonymous class where a lambda would be cleaner (for single-method interfaces).

### Mistake 6
Trying to use a lambda to implement an abstract class instead of an interface.
Lambdas only work with **interfaces**, not abstract classes.

---

## 9. Examples Included in This Module

This module provides practical programs for:

1. `AnonymousClassWithAbstractClassDemo.java` – anonymous class extending an abstract class with output examples
2. `AnonymousClassWithInterfaceDemo.java` – anonymous class implementing a single and multi-method interface
3. `AnonymousClassAdvancedDemo.java` – anonymous classes capturing outer variables, used in arrays and as method arguments
4. `FunctionalInterfaceCustomDemo.java` – defining custom functional interfaces with default and static methods
5. `BuiltInFunctionalInterfacesDemo.java` – Predicate, Consumer, Supplier, Function, BiFunction with real examples
6. `LambdaSyntaxVariationsDemo.java` – all syntax forms of lambda expressions with comments
7. `LambdaWithCollectionsDemo.java` – lambda expressions used with List sorting, filtering, mapping
8. `AnonymousVsLambdaComparisonDemo.java` – side-by-side comparison of anonymous class vs lambda for the same task

---

## 10. Interview Questions and Answers

### Question 1
What is an anonymous inner class?

**Answer:** A class without a name that is declared and instantiated at the same time, used to provide a one-time implementation of an abstract class or interface.

### Question 2
Can an anonymous inner class implement an interface with multiple abstract methods?

**Answer:** Yes. Unlike lambda expressions, anonymous inner classes can implement any number of abstract methods.

### Question 3
What is a functional interface?

**Answer:** An interface that contains exactly one abstract method. It may also have default and static methods.

### Question 4
What is the purpose of the `@FunctionalInterface` annotation?

**Answer:** It tells the compiler to enforce the single-abstract-method rule and produces an error if more than one abstract method is added.

### Question 5
What is a lambda expression?

**Answer:** A short, unnamed function that implements the single abstract method of a functional interface.

### Question 6
What is the difference between a lambda and an anonymous inner class?

**Answer:** A lambda is shorter and only works with functional interfaces (one abstract method). An anonymous class is more verbose but can handle multiple methods and can extend abstract classes.

### Question 7
What does `this` refer to inside a lambda expression?

**Answer:** It refers to the enclosing class instance, not the lambda itself.

### Question 8
What are effectively final variables in lambda context?

**Answer:** Local variables whose value never changes after they are assigned, even without the `final` keyword. Lambdas can only capture such variables.

### Question 9
Name four built-in functional interfaces from `java.util.function`.

**Answer:** `Predicate<T>`, `Consumer<T>`, `Supplier<T>`, `Function<T,R>`.

### Question 10
What does `Predicate.and()` do?

**Answer:** It combines two predicates with a logical AND operation — both conditions must be true.

### Question 11
What is a method reference?

**Answer:** A shorthand form of lambda that directly refers to an existing method using the `::` operator.

### Question 12
When should you prefer an anonymous inner class over a lambda?

**Answer:** When implementing an interface with more than one abstract method, or when extending an abstract class, since lambdas cannot be used in those cases.

### Question 13
Can a lambda expression modify a local variable from the outer scope?

**Answer:** No. It can only read variables that are final or effectively final.

### Question 14
What is `BinaryOperator<T>`?

**Answer:** A functional interface that takes two arguments of the same type and returns a result of the same type. It extends `BiFunction<T,T,T>`.

### Question 15
How did lambda expressions change Java development?

**Answer:** They made code shorter, enabled functional-style programming, reduced boilerplate anonymous class code, and are the foundation for the Stream API.

---

## 11. Quick Revision Summary

```text
Anonymous Inner Class
  -> nameless class, declared and instantiated inline
  -> can extend abstract class or implement interface
  -> supports multiple abstract methods
  -> verbose syntax
  -> this = anonymous class instance

Functional Interface
  -> exactly ONE abstract method
  -> @FunctionalInterface annotation enforces the rule
  -> target type for lambda expressions
  -> Built-in: Predicate, Consumer, Supplier, Function, BiFunction

Lambda Expression
  -> short inline implementation of a functional interface
  -> syntax: (params) -> expression  OR  (params) -> { body }
  -> can capture effectively final outer variables
  -> this = enclosing class
  -> cleaner alternative to anonymous inner class

Method Reference
  -> even shorter lambda when calling an existing method
  -> syntax: ClassName::method  or  object::method
```

---

## 12. Final Conclusion

Anonymous inner classes, functional interfaces, and lambda expressions are tightly connected.
Anonymous classes came first and solved the problem of one-time implementations.
Functional interfaces defined the contract that lambdas needed to work with.
Lambda expressions then made code dramatically cleaner by removing the verbosity.

If you remember one key idea, let it be this:

> Anonymous classes provide the full picture; lambdas provide the shortcut. The functional interface is the bridge that connects them.

Mastering all three gives you full control over modern Java design, clean API usage, and confident interview performance.

