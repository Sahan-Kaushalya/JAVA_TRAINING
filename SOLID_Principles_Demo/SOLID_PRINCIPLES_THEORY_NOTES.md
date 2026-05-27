# SOLID Principles in Java - Complete Theory Notes

---

## 1. Introduction

SOLID is an acronym for five fundamental object-oriented design principles.
They were introduced by Robert C. Martin (Uncle Bob) and are widely used in
professional Java development to produce software that is:

- easy to maintain
- easy to extend
- resilient to change
- testable and loosely coupled

Every letter in SOLID stands for one principle:

```text
S – Single Responsibility Principle  (SRP)
O – Open / Closed Principle           (OCP)
L – Liskov Substitution Principle     (LSP)
I – Interface Segregation Principle   (ISP)
D – Dependency Inversion Principle    (DIP)
```

---

## 2. Why SOLID Matters

Without SOLID principles, code quickly becomes:

- hard to read and understand
- fragile — changing one thing breaks another
- rigid — hard to add new features
- tightly coupled — classes depend too much on each other
- difficult to test in isolation

With SOLID, code is:

- modular and focused
- open to extension but closed to breaking changes
- interchangeable through abstraction
- easy to unit-test
- built for long-term maintainability

---

## 3. S — Single Responsibility Principle (SRP)

### 3.1 Definition

> A class should have **only one reason to change**.

Every class should do exactly one thing and do it well.
If a class handles two or more different responsibilities,
changing one responsibility might break the other.

### 3.2 Wrong Approach (violates SRP)

```java
class Employee {
    String name;
    double salary;

    void calculateSalary() { ... }   // HR logic
    void saveToDatabase()  { ... }   // Database logic
    void generateReport()  { ... }   // Reporting logic
}
```

This class has three responsibilities: payroll, persistence, and reporting.
Changing the database layer risks breaking salary logic.

### 3.3 Correct Approach (follows SRP)

```java
class Employee          { String name; double salary; }
class SalaryCalculator  { double calculate(Employee e) { ... } }
class EmployeeRepository{ void save(Employee e) { ... } }
class EmployeeReporter  { void printReport(Employee e) { ... } }
```

Each class now has exactly one reason to change.

### 3.4 Key Benefits
- Smaller, focused classes
- Changes are isolated
- Easier to test each unit independently

---

## 4. O — Open / Closed Principle (OCP)

### 4.1 Definition

> Software entities (classes, modules, functions) should be **open for extension**
> but **closed for modification**.

You should be able to add new behaviour without changing existing, working code.

### 4.2 Wrong Approach (violates OCP)

```java
class DiscountService {
    double applyDiscount(String type, double price) {
        if (type.equals("SEASONAL"))  return price * 0.90;
        if (type.equals("LOYALTY"))   return price * 0.85;
        if (type.equals("EMPLOYEE"))  return price * 0.70;
        return price;
        // Every new discount type MODIFIES this method – violates OCP!
    }
}
```

### 4.3 Correct Approach (follows OCP)

```java
interface DiscountStrategy {
    double apply(double price);
}

class SeasonalDiscount  implements DiscountStrategy { ... }
class LoyaltyDiscount   implements DiscountStrategy { ... }
class EmployeeDiscount  implements DiscountStrategy { ... }
// New discount? Just add a new class — no old code modified.

class DiscountService {
    double applyDiscount(DiscountStrategy strategy, double price) {
        return strategy.apply(price);
    }
}
```

### 4.4 How to Achieve OCP
- Use abstraction (interfaces / abstract classes)
- Use the Strategy, Decorator, or Template Method design pattern

### 4.5 Key Benefits
- Adding features without touching tested code
- Reduces the risk of regression bugs

---

## 5. L — Liskov Substitution Principle (LSP)

### 5.1 Definition

> Objects of a subclass should be **substitutable** for objects of the superclass
> without altering the correctness of the program.

If class B extends class A, everywhere you use A you should be able to use B
and the program should still work correctly.

### 5.2 Wrong Approach (violates LSP)

```java
class Bird {
    void fly() { System.out.println("Flying"); }
}

class Penguin extends Bird {
    @Override
    void fly() {
        throw new UnsupportedOperationException("Penguins cannot fly!");
        // Substituting Bird with Penguin BREAKS the program – violates LSP!
    }
}
```

### 5.3 Correct Approach (follows LSP)

```java
abstract class Bird { void eat() { ... } }

interface Flyable  { void fly(); }
interface Swimmable{ void swim(); }

class Sparrow extends Bird implements Flyable  { ... }
class Penguin extends Bird implements Swimmable { ... }
```

Now there is no false promise. Penguin never claims it can fly.

### 5.4 Signs That LSP Is Violated
- `UnsupportedOperationException` thrown in overridden methods
- `instanceof` checks needed before calling a method
- Subclass completely ignores or no-ops an inherited method

### 5.5 Key Benefits
- Reliable polymorphism
- Subclasses can be used interchangeably
- Cleaner inheritance hierarchies

---

## 6. I — Interface Segregation Principle (ISP)

### 6.1 Definition

> A class should not be **forced to implement interfaces it does not use**.

Instead of one large "fat" interface, prefer many small, focused interfaces.

### 6.2 Wrong Approach (violates ISP)

```java
interface Worker {
    void work();
    void eat();
    void sleep();
}

class Robot implements Worker {
    void work()  { ... }
    void eat()   { /* robots don't eat – forced to implement! */ }
    void sleep() { /* robots don't sleep – forced to implement! */ }
}
```

Robot is forced to implement `eat()` and `sleep()` which make no sense for it.

### 6.3 Correct Approach (follows ISP)

```java
interface Workable  { void work();  }
interface Eatable   { void eat();   }
interface Sleepable { void sleep(); }

class HumanWorker implements Workable, Eatable, Sleepable { ... }
class Robot       implements Workable                      { ... }
```

Each class implements only what it needs.

### 6.4 Key Benefits
- No "dummy" or empty method implementations
- Interfaces stay small and meaningful
- Easier to compose behaviour

---

## 7. D — Dependency Inversion Principle (DIP)

### 7.1 Definition

> High-level modules should not depend on low-level modules.
> **Both should depend on abstractions.**
> Abstractions should not depend on details.
> **Details should depend on abstractions.**

### 7.2 Wrong Approach (violates DIP)

```java
class EmailSender {
    void send(String message) { ... }
}

class NotificationService {
    private EmailSender emailSender = new EmailSender(); // tight coupling!

    void notify(String msg) {
        emailSender.send(msg);   // directly depends on concrete class
    }
}
```

If you want to switch to SMS, you must modify `NotificationService`.

### 7.3 Correct Approach (follows DIP)

```java
interface MessageSender {
    void send(String message);
}

class EmailSender implements MessageSender { ... }
class SmsSender   implements MessageSender { ... }
class PushSender  implements MessageSender { ... }

class NotificationService {
    private MessageSender sender; // depends on ABSTRACTION

    NotificationService(MessageSender sender) {
        this.sender = sender;     // injected from outside
    }

    void notify(String msg) {
        sender.send(msg);
    }
}
```

You can now switch the sender without touching `NotificationService` at all.

### 7.4 How to Achieve DIP
- Depend on interfaces, not concrete classes
- Use Dependency Injection (constructor, setter, or field injection)
- Use factories or IoC containers (like Spring)

### 7.5 Key Benefits
- Loose coupling
- Easy to swap implementations
- Highly testable (mock the interface in unit tests)

---

## 8. SOLID Principles — Quick Reference

```text
S – Single Responsibility  → One class, one job
O – Open / Closed          → Open to extend, closed to modify
L – Liskov Substitution    → Subclass must be a valid replacement for superclass
I – Interface Segregation  → Many small interfaces, not one fat interface
D – Dependency Inversion   → Depend on abstractions, not concretions
```

---

## 9. How SOLID Principles Relate to Design Patterns

| Principle | Supported By Pattern |
|-----------|---------------------|
| SRP | Command, Service Layer |
| OCP | Strategy, Decorator, Template Method |
| LSP | Proper inheritance hierarchies |
| ISP | Role interfaces, Adapter |
| DIP | Dependency Injection, Factory, Abstract Factory |

---

## 10. SOLID Violations vs Fixes Summary Table

| Principle | Violation Sign | Fix |
|-----------|---------------|-----|
| SRP | Class does many unrelated things | Split into focused classes |
| OCP | Adding a feature means editing existing code | Use abstraction + new subclass |
| LSP | Subclass throws UnsupportedOperationException | Redesign hierarchy with interfaces |
| ISP | Class forced to implement unused methods | Split fat interface into smaller ones |
| DIP | High-level class creates low-level objects with `new` | Inject abstraction from outside |

---

## 11. Real-World Domain Examples

### SRP — Bank Application
- `Account` manages balance
- `TransactionLogger` logs transactions
- `AccountRepository` handles database operations
- `StatementGenerator` generates PDF statements

### OCP — Payment System
- `PaymentProcessor` works with any `PaymentStrategy`
- Adding `WalletPayment` requires only a new class, no edits to processor

### LSP — Shape Library
- `Square` and `Circle` both extend `Shape` and can be substituted anywhere `Shape` is expected

### ISP — Printer System
- `BasicPrinter` implements only `Printable`
- `AdvancedPrinter` implements `Printable`, `Scannable`, and `Faxable`

### DIP — Notification System
- `OrderService` depends on `NotificationSender` interface
- Can use `EmailSender`, `SmsSender`, or `PushSender` at runtime

---

## 12. Common Mistakes Students Make

### Mistake 1
Thinking SRP means one method per class. It means one **responsibility** per class.
A class can have many methods as long as they all serve the same responsibility.

### Mistake 2
Misreading OCP as "never modify code". It means don't modify **existing working behaviour**.
Bug fixes and refactoring are still fine.

### Mistake 3
Forcing LSP by adding a flag like `canFly = false` instead of redesigning the hierarchy.

### Mistake 4
Creating one interface per method (over-segregating). ISP means don't force **unrelated** methods — related methods can stay together.

### Mistake 5
Confusing DIP with Dependency Injection.
DIP is the **principle** (depend on abstractions).
Dependency Injection is one **technique** to implement DIP.

---

## 13. Examples Included in This Module

1. `SRPDemo.java` — Employee management system split into focused classes
2. `OCPDemo.java` — Payment and discount systems extended by abstraction
3. `LSPDemo.java` — Shape area calculator and bird hierarchy following LSP correctly
4. `ISPDemo.java` — Smart device and printer system using segregated interfaces
5. `DIPDemo.java` — Notification service and report exporter using interface injection
6. `SOLIDCombinedRealWorldDemo.java` — E-commerce order processing system that follows all five principles together

---

## 14. Interview Questions and Answers

### Question 1
What does SOLID stand for?

**Answer:** Single Responsibility, Open/Closed, Liskov Substitution, Interface Segregation, Dependency Inversion.

### Question 2
What is the Single Responsibility Principle?

**Answer:** A class should have only one reason to change — it should do only one job.

### Question 3
What does "open for extension, closed for modification" mean?

**Answer:** You should be able to add new behaviour by writing new code, not by modifying existing tested code.

### Question 4
How do you detect an LSP violation?

**Answer:** When a subclass overrides a method and throws `UnsupportedOperationException`, or when you need `instanceof` checks before calling a method.

### Question 5
What is a "fat" interface and why is it a problem?

**Answer:** A fat interface has too many unrelated methods. It forces implementing classes to provide empty or dummy implementations for methods they do not need.

### Question 6
What is the difference between DIP and Dependency Injection?

**Answer:** DIP is the design principle that says depend on abstractions. Dependency Injection is a programming technique (passing dependencies from outside) that helps implement DIP.

### Question 7
Which design pattern best supports OCP?

**Answer:** The Strategy pattern, because it allows new behaviour to be added by writing new strategy classes without modifying the context class.

### Question 8
How does DIP help with testing?

**Answer:** Because high-level classes depend on interfaces, you can substitute a real implementation with a mock in unit tests without changing the class under test.

### Question 9
Can you follow all five SOLID principles at the same time?

**Answer:** Yes. They work together and reinforce each other. In fact, following one often naturally leads to following others.

### Question 10
What are the benefits of SRP in a real project?

**Answer:** Each class is small and focused, making it easy to understand, test, modify, and reuse. Changes in one area do not ripple into unrelated areas.

### Question 11
Give a real-world example of ISP.

**Answer:** A `Robot` class should not implement an interface that includes `eat()` and `sleep()`. Instead, separate `Workable`, `Eatable`, and `Sleepable` interfaces let Robot implement only `Workable`.

### Question 12
What happens when LSP is violated?

**Answer:** Polymorphism breaks. Code that works correctly with a base type fails or throws exceptions when a subtype is substituted.

### Question 13
How does OCP reduce bugs?

**Answer:** Because existing tested code is never changed, there is no risk of accidentally introducing regressions when adding new features.

### Question 14
What is the relationship between DIP and the Repository pattern?

**Answer:** The Repository pattern uses DIP — the service layer depends on a repository interface, not a concrete database class. You can swap SQL, NoSQL, or in-memory implementations freely.

### Question 15
Why are SOLID principles important in Java interviews?

**Answer:** They demonstrate professional software design knowledge, ability to write maintainable code, and understanding of clean architecture — all of which are expected from mid-to-senior Java developers.

---

## 15. Quick Revision Cheat Sheet

```text
S – SRP  →  One class = one job
            Employee ≠ EmailSender ≠ ReportGenerator

O – OCP  →  Add new code, don't change old code
            Use interfaces + new subclasses

L – LSP  →  Every subclass must fully honour the base class contract
            Penguin should NOT extend FlyingBird

I – ISP  →  Small, focused interfaces
            Robot implements Workable, not Worker(eat,sleep,work)

D – DIP  →  Depend on interface, not on concrete class
            NotificationService(MessageSender sender)   ← inject abstraction
```

---

## 16. Final Conclusion

SOLID principles are the backbone of professional, maintainable Java development.

They guide developers to write code that is easy to change, easy to test, and easy to extend — even when requirements evolve over time.

If you remember one key idea, let it be this:

> SOLID principles do not add complexity — they remove it. Each principle solves a specific source of fragility common in real-world software.

Mastering SOLID will elevate your Java skills from writing code that works to writing code that lasts.

