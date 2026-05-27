# Java Threads — Complete Theory Notes

---

## Table of Contents

1. [Introduction to Threads](#1-introduction-to-threads)
2. [Creating Threads](#2-creating-threads)
3. [Thread Lifecycle (States)](#3-thread-lifecycle-states)
4. [Thread Methods Cheat Sheet](#4-thread-methods-cheat-sheet)
5. [Synchronization](#5-synchronization)
6. [Thread Communication — wait / notify / notifyAll](#6-thread-communication--wait--notify--notifyall)
7. [Deadlock](#7-deadlock)
8. [ExecutorService and Thread Pools](#8-executorservice-and-thread-pools)
9. [Callable and Future](#9-callable-and-future)
10. [volatile Keyword](#10-volatile-keyword)
11. [ThreadLocal](#11-threadlocal)
12. [Thread Priority and Daemon Threads](#12-thread-priority-and-daemon-threads)
13. [java.util.concurrent Locks](#13-javautilconcurrent-locks)
14. [Concurrent Collections](#14-concurrent-collections)
15. [Synchronization Utilities](#15-synchronization-utilities)
16. [Atomic Classes](#16-atomic-classes)
17. [Best Practices](#17-best-practices)
18. [Quick Reference Table](#18-quick-reference-table)

---

## 1. Introduction to Threads

### What is a Thread?
A **thread** is the smallest unit of execution within a program.  
A **process** (a running program) can contain multiple threads — they share the same memory (heap) but each has its own stack and program counter.

### Why use Threads?
| Reason | Example |
|--------|---------|
| **Concurrency** | Handle multiple user requests simultaneously |
| **Parallelism** | Use multiple CPU cores for faster computation |
| **Responsiveness** | Keep UI responsive while doing background work |
| **Resource sharing** | Share data efficiently between tasks |

### Process vs Thread
| Feature | Process | Thread |
|---------|---------|--------|
| Memory | Separate memory space | Shared heap; own stack |
| Communication | IPC (sockets, pipes) | Direct shared variables |
| Creation cost | High | Low |
| Context switch | Expensive | Cheaper |

---

## 2. Creating Threads

There are **three main ways** to create threads in Java.

### 2.1 Extend `Thread` Class
```java
class MyThread extends Thread {
    @Override
    public void run() {
        System.out.println("Thread running: " + getName());
    }
}
// Usage:
MyThread t = new MyThread();
t.start(); // creates a new OS thread and calls run() on it
```

**Limitation:** Your class cannot extend any other class (Java has single inheritance).

---

### 2.2 Implement `Runnable` Interface ✅ (Recommended)
```java
class MyTask implements Runnable {
    @Override
    public void run() {
        System.out.println("Task running in: " + Thread.currentThread().getName());
    }
}
// Usage:
Thread t = new Thread(new MyTask(), "TaskThread");
t.start();
```

**Advantages:**
- Free to extend another class
- Separates the *task* from the *thread* (better design)
- Supports lambda expressions

---

### 2.3 Lambda (Java 8+)
```java
// Runnable is a @FunctionalInterface → can use lambda
Thread t = new Thread(() -> System.out.println("Lambda thread!"), "LambdaThread");
t.start();
```

---

### ⚠️ `start()` vs `run()`
| Method | What happens |
|--------|-------------|
| `start()` | Creates a **new OS thread**; JVM calls `run()` on it |
| `run()` | Executes `run()` in the **current thread** (no new thread!) |

> **Always call `start()`** — calling `run()` directly is a common mistake.

---

## 3. Thread Lifecycle (States)

A thread passes through the following states (defined in `Thread.State` enum):

```
                ┌─────────────────────────────────────────────────────────┐
  new Thread()  │                                                         │
      │         ▼                                                         │
   [NEW] ──start()──► [RUNNABLE] ──────────────────────────► [TERMINATED]│
                           │  ▲                                           │
                    sleep/ │  │ time expires /                            │
                    join/  │  │ notify / lock                             │
                    wait() │  │ acquired                                  │
                           ▼  │                                           │
                   [TIMED_WAITING] / [WAITING] / [BLOCKED]                │
                └─────────────────────────────────────────────────────────┘
```

| State | When |
|-------|------|
| `NEW` | Thread created, `start()` not yet called |
| `RUNNABLE` | After `start()`; actively running or ready to run |
| `BLOCKED` | Waiting to acquire a monitor lock (synchronized block) |
| `WAITING` | `wait()`, `join()` with no timeout — waiting indefinitely |
| `TIMED_WAITING` | `sleep(ms)`, `wait(ms)`, `join(ms)` — waiting with timeout |
| `TERMINATED` | `run()` method returned or exception thrown |

```java
Thread t = new Thread(() -> { /* ... */ });
System.out.println(t.getState()); // NEW
t.start();
System.out.println(t.getState()); // RUNNABLE
t.join();
System.out.println(t.getState()); // TERMINATED
```

---

## 4. Thread Methods Cheat Sheet

| Method | Description |
|--------|-------------|
| `start()` | Starts new thread; calls `run()` |
| `run()` | Contains thread's task (do NOT call directly) |
| `sleep(ms)` | Pauses current thread for ms milliseconds |
| `join()` | Calling thread waits for THIS thread to finish |
| `join(ms)` | Wait at most ms milliseconds |
| `interrupt()` | Sets the interrupt flag; wakes sleeping/waiting thread |
| `isInterrupted()` | Checks interrupt flag (does NOT clear it) |
| `interrupted()` | Static; checks AND clears interrupt flag |
| `getName()` | Returns thread name |
| `setName(s)` | Sets thread name |
| `getId()` | Returns unique thread ID |
| `getState()` | Returns `Thread.State` enum |
| `getPriority()` | Returns priority (1–10) |
| `setPriority(n)` | Sets priority (1–10) |
| `isDaemon()` | Returns true if daemon thread |
| `setDaemon(b)` | Set daemon status (before `start()`) |
| `yield()` | Hints to yield CPU to other threads (not guaranteed) |
| `currentThread()` | Static; returns reference to currently running thread |

---

## 5. Synchronization

### The Problem: Race Condition
When multiple threads read and write a shared variable concurrently, results become unpredictable.

```
Thread-1: reads count=5
Thread-2: reads count=5     ← both read the SAME old value
Thread-1: writes count=6
Thread-2: writes count=6    ← Thread-1's increment is LOST!
```

This is a **race condition** — the result depends on the timing of thread execution.

---

### The Solution: `synchronized`

The `synchronized` keyword ensures **mutual exclusion** — only one thread can execute the synchronized code at a time.

#### Form 1: Synchronized Method
```java
public synchronized void increment() {
    count++; // only one thread at a time
}
```
Lock is the **current object** (`this`).

#### Form 2: Synchronized Block (finer-grained)
```java
private final Object lock = new Object();

public void increment() {
    synchronized (lock) {
        count++; // only the critical section is locked
    }
}
```

#### Static Synchronized Method
```java
public static synchronized void staticMethod() {
    // lock is the CLASS object (MyClass.class)
}
```

---

### How Monitors Work
Every Java object has an internal **monitor lock** (mutex).  
`synchronized` acquires the monitor before entering and releases it on exit.

- **Re-entrant:** A thread can re-acquire its own lock without deadlocking.
- **Visibility:** The JVM flushes thread-local caches when a lock is released/acquired.

---

## 6. Thread Communication — wait / notify / notifyAll

Used to coordinate threads that depend on each other (e.g., producer-consumer).

| Method | Description |
|--------|-------------|
| `wait()` | Releases lock; suspends thread until `notify()`/`notifyAll()` |
| `wait(ms)` | Like `wait()` but wakes up after ms milliseconds at the latest |
| `notify()` | Wakes up ONE random thread waiting on this object's monitor |
| `notifyAll()` | Wakes up ALL threads waiting on this object's monitor |

> **Rule:** Must be called inside a `synchronized` block/method **on the same object**.

### Producer-Consumer Pattern
```java
synchronized void produce(int item) throws InterruptedException {
    while (buffer.size() == CAPACITY) {
        wait();           // release lock; wait for consumer to take items
    }
    buffer.add(item);
    notifyAll();          // wake up consumer
}

synchronized int consume() throws InterruptedException {
    while (buffer.isEmpty()) {
        wait();           // release lock; wait for producer to add items
    }
    int item = buffer.poll();
    notifyAll();          // wake up producer
    return item;
}
```

### ⚠️ Always use `while`, not `if`
```java
// WRONG — if() can miss spurious wakeups
if (buffer.isEmpty()) wait();

// CORRECT — re-check condition after waking up
while (buffer.isEmpty()) wait();
```

A **spurious wakeup** is when a thread wakes up even though `notify()` was not called — the JVM/OS is allowed to do this. The `while` loop re-checks and goes back to sleep if needed.

---

## 7. Deadlock

### What is a Deadlock?
A deadlock occurs when two or more threads permanently block each other, each waiting for a lock held by the other.

```
Thread-1  holds  Lock-A, waiting for Lock-B
Thread-2  holds  Lock-B, waiting for Lock-A
→ Neither can proceed — DEADLOCK
```

### Four Necessary Conditions (Coffman Conditions)
All four must hold simultaneously:
1. **Mutual Exclusion** — A resource (lock) can only be held by one thread
2. **Hold and Wait** — A thread holds a lock while waiting for another
3. **No Preemption** — Locks cannot be forcibly taken from a thread
4. **Circular Wait** — A circular chain of threads, each waiting for the next

### Prevention Strategies

#### 1. Lock Ordering (Most common)
Always acquire locks in the **same global order** across all threads.
```java
// Both threads acquire Lock-A first, then Lock-B — no circular wait
synchronized (lockA) {
    synchronized (lockB) { /* ... */ }
}
```

#### 2. `tryLock()` with timeout
```java
if (lockA.tryLock(1, TimeUnit.SECONDS)) {
    try {
        if (lockB.tryLock(1, TimeUnit.SECONDS)) {
            try { /* work */ }
            finally { lockB.unlock(); }
        }
    } finally { lockA.unlock(); }
}
```

#### 3. Minimize lock scope
Avoid holding locks while calling external methods you don't control.

---

## 8. ExecutorService and Thread Pools

### Why Thread Pools?
Creating a new `Thread` for each task is expensive (OS resource allocation, setup overhead).  
A **thread pool** pre-creates a fixed set of threads and reuses them for many tasks.

### Factory Methods (`Executors` class)

| Factory Method | Description |
|----------------|-------------|
| `newFixedThreadPool(n)` | Exactly `n` threads; excess tasks queue up |
| `newCachedThreadPool()` | Dynamically grows/shrinks; idle threads expire after 60s |
| `newSingleThreadExecutor()` | One thread; tasks execute sequentially |
| `newScheduledThreadPool(n)` | Supports delay and periodic execution |

### Usage Pattern
```java
ExecutorService executor = Executors.newFixedThreadPool(4);

// Submit tasks
executor.execute(() -> System.out.println("Task 1")); // fire-and-forget
Future<Integer> future = executor.submit(() -> 42);   // get result later

// Shutdown gracefully
executor.shutdown();                          // stop accepting new tasks
executor.awaitTermination(10, TimeUnit.SECONDS); // wait for running tasks
```

### `shutdown()` vs `shutdownNow()`
| Method | Behavior |
|--------|----------|
| `shutdown()` | Graceful; queued tasks still run; no new tasks accepted |
| `shutdownNow()` | Forceful; interrupts running tasks; returns pending tasks |

### Scheduled Execution
```java
ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(2);

// Run once after 2-second delay
scheduler.schedule(task, 2, TimeUnit.SECONDS);

// Run every 1 second (fixed rate — timing based on start time)
scheduler.scheduleAtFixedRate(task, 0, 1, TimeUnit.SECONDS);

// Run 1 second after each completion (fixed delay — timing based on end time)
scheduler.scheduleWithFixedDelay(task, 0, 1, TimeUnit.SECONDS);
```

---

## 9. Callable and Future

### Limitations of `Runnable`
- `run()` cannot return a value
- `run()` cannot throw checked exceptions

### `Callable<V>` solves both
```java
Callable<Integer> task = () -> {
    Thread.sleep(1000);
    return 42; // returns a value!
};
```

### `Future<V>` — represents the pending result
```java
ExecutorService executor = Executors.newFixedThreadPool(2);
Future<Integer> future = executor.submit(task);

// ... do other work ...

int result = future.get(); // blocks until result is ready
```

### `Future` Methods
| Method | Description |
|--------|-------------|
| `get()` | Blocks until result is ready; returns result |
| `get(timeout, unit)` | Blocks up to timeout; throws `TimeoutException` |
| `isDone()` | Non-blocking; true if completed (success, failure, or cancel) |
| `cancel(mayInterrupt)` | Attempts cancellation; `true` if successful |
| `isCancelled()` | `true` if task was cancelled |

### Exception Handling
```java
try {
    Integer result = future.get();
} catch (ExecutionException e) {
    // The exception thrown inside call() is wrapped in ExecutionException
    Throwable cause = e.getCause();
    System.out.println("Task failed: " + cause.getMessage());
} catch (InterruptedException e) {
    Thread.currentThread().interrupt(); // restore interrupt flag
}
```

---

## 10. `volatile` Keyword

### The Visibility Problem
The JVM allows threads to cache variables in CPU registers/cache.  
A write in one thread may **not be visible** to another thread reading from its cache.

```
Thread-1 writes: stopFlag = true  → written to Thread-1's cache
Thread-2 reads:  stopFlag         → reads stale false from Thread-2's cache
```

### What `volatile` Guarantees
1. **Visibility** — Every read goes to **main memory**; every write is flushed to main memory immediately.
2. **Ordering** — Prevents instruction reordering around a volatile read/write.

### Usage
```java
volatile boolean stopFlag = false; // thread-safe boolean flag

// Thread-1 (writer)
stopFlag = true;

// Thread-2 (reader) — sees the update immediately
while (!stopFlag) { /* work */ }
```

### `volatile` vs `synchronized`
| Feature | `volatile` | `synchronized` |
|---------|-----------|----------------|
| Visibility | ✅ Yes | ✅ Yes |
| Atomicity | ❌ No (compound ops) | ✅ Yes |
| Mutual exclusion | ❌ No | ✅ Yes |
| Performance | Faster | Slightly slower |

### When to use `volatile`
✅ Boolean flag written by one thread, read by others  
✅ Single reference assignment (e.g., singleton initialization)  
❌ **Not** for `count++`, `count += n` — these are compound (not atomic)  

### Use `AtomicInteger` for compound operations
```java
AtomicInteger counter = new AtomicInteger(0);
counter.incrementAndGet(); // atomic; thread-safe
```

---

## 11. `ThreadLocal`

### What is `ThreadLocal`?
`ThreadLocal<T>` provides each thread with its **own independent copy** of a variable.  
No sharing → no _04_synchronization needed.

```
Thread-A → has its own value of threadLocal
Thread-B → has its own value of threadLocal  (completely separate)
Main     → has its own value of threadLocal
```

### API
```java
ThreadLocal<Integer> tl = ThreadLocal.withInitial(() -> 0); // lazy initial value

tl.get();       // get this thread's value
tl.set(value);  // set this thread's value
tl.remove();    // REMOVE — prevents memory leaks in thread pools
```

### Common Use Cases
| Use Case | Description |
|----------|-------------|
| `SimpleDateFormat` | Not thread-safe; give each thread its own instance |
| Database connections | Per-thread transaction context |
| User/request context | Web server — store logged-in user for current request |
| Spring `@Transactional` | Uses `ThreadLocal` internally to bind connections |

### ⚠️ Memory Leak Warning
In thread **pools**, threads are reused. If you `set()` a value but do NOT `remove()` it,  
the value remains even after the task finishes and may leak to the next task using that thread.

```java
// ALWAYS clean up in a finally block when using thread pools
try {
    tl.set(value);
    doWork();
} finally {
    tl.remove(); // CRITICAL
}
```

---

## 12. Thread Priority and Daemon Threads

### Thread Priority
Every thread has a priority between **1** (lowest) and **10** (highest). Default is **5**.

```java
thread.setPriority(Thread.MIN_PRIORITY);  // 1
thread.setPriority(Thread.NORM_PRIORITY); // 5 (default)
thread.setPriority(Thread.MAX_PRIORITY);  // 10
```

> **Note:** Priority is a **hint** to the OS scheduler — not a guarantee. Behavior varies by OS and JVM.

### Daemon Threads
| Feature | User Thread | Daemon Thread |
|---------|-------------|---------------|
| JVM exit | JVM waits for it to finish | JVM exits when only daemons remain |
| Default | Yes | No (must call `setDaemon(true)`) |
| Use for | Application logic | Background services, GC, logging |

```java
Thread daemon = new Thread(() -> { /* background work */ });
daemon.setDaemon(true); // must set BEFORE start()
daemon.start();
```

---

## 13. `java.util.concurrent` Locks

### `ReentrantLock`
More powerful than `synchronized` — supports:
- `tryLock()` — non-blocking attempt
- `tryLock(timeout, unit)` — timed attempt
- `lockInterruptibly()` — can be interrupted while waiting
- **Fairness mode** — longest-waiting thread gets the lock first

```java
ReentrantLock lock = new ReentrantLock();

lock.lock(); // acquire
try {
    // critical section
} finally {
    lock.unlock(); // ALWAYS in finally
}
```

### `tryLock()` — non-blocking
```java
if (lock.tryLock()) {
    try { /* work */ }
    finally { lock.unlock(); }
} else {
    System.out.println("Lock busy, skipping...");
}
```

### `ReentrantReadWriteLock`
Allows **multiple concurrent readers** or **one exclusive writer** — not both.

```java
ReadWriteLock rwLock  = new ReentrantReadWriteLock();
Lock          readLock  = rwLock.readLock();
Lock          writeLock = rwLock.writeLock();

// Multiple threads can hold readLock simultaneously
readLock.lock();
try { /* read shared data */ }
finally { readLock.unlock(); }

// Only ONE thread can hold writeLock; all readers blocked
writeLock.lock();
try { /* modify shared data */ }
finally { writeLock.unlock(); }
```

### `synchronized` vs `ReentrantLock`
| Feature | `synchronized` | `ReentrantLock` |
|---------|---------------|-----------------|
| Syntax | Simple | Verbose (manual unlock) |
| Fairness | No control | Optional fair mode |
| `tryLock` | ❌ | ✅ |
| Interruptible wait | ❌ | ✅ `lockInterruptibly()` |
| Multiple conditions | ❌ | ✅ `newCondition()` |

---

## 14. Concurrent Collections

Regular collections (`ArrayList`, `HashMap`, etc.) are **not thread-safe**.  
The `java.util.concurrent` package provides thread-safe alternatives.

### `ConcurrentHashMap`
- Thread-safe `HashMap`; uses internal segmentation for high concurrency
- No full-map lock — only the accessed bucket is locked

```java
ConcurrentHashMap<String, Integer> map = new ConcurrentHashMap<>();
map.put("a", 1);
map.computeIfAbsent("b", k -> 0);
```

### `CopyOnWriteArrayList`
- Thread-safe `ArrayList`
- Writes create a **new copy** of the array — reads are never blocked
- Best when reads >> writes (e.g., listener/subscriber lists)

```java
CopyOnWriteArrayList<String> list = new CopyOnWriteArrayList<>();
list.add("item"); // creates a new internal copy
```

### `BlockingQueue`
| Type | Bound | Description |
|------|-------|-------------|
| `ArrayBlockingQueue` | Bounded | Fixed-size circular array |
| `LinkedBlockingQueue` | Optional bound | Linked node queue |
| `PriorityBlockingQueue` | Unbounded | Ordered by natural/comparator order |
| `SynchronousQueue` | 0 | Each put must wait for a take |

```java
BlockingQueue<Integer> queue = new ArrayBlockingQueue<>(10);
queue.put(item);    // BLOCKS if full
queue.take();       // BLOCKS if empty
queue.offer(item);  // returns false if full (non-blocking)
queue.poll();       // returns null if empty (non-blocking)
```

---

## 15. Synchronization Utilities

### `CountDownLatch`
One or more threads wait until a counter reaches zero.  
**One-shot** — cannot be reset.

```java
CountDownLatch latch = new CountDownLatch(3);

// Worker threads call countDown()
new Thread(() -> { doWork(); latch.countDown(); }).start();

// Main thread waits
latch.await(); // blocks until count == 0
```

**Use cases:** Wait for multiple services to initialize; wait for N events.

---

### `CyclicBarrier`
N threads synchronize at a common point. When all arrive, they all proceed.  
**Re-usable** (can be used multiple times for multiple rounds/phases).

```java
CyclicBarrier barrier = new CyclicBarrier(3, () -> System.out.println("All arrived!"));

// Each thread calls:
barrier.await(); // blocks until all 3 threads have called await()
```

**Use cases:** Parallel computation with _04_synchronization phases (e.g., parallel matrix multiplication).

---

### `Semaphore`
Controls access to a limited pool of resources.

```java
Semaphore semaphore = new Semaphore(3); // 3 permits = 3 concurrent threads allowed

semaphore.acquire(); // take a permit (blocks if none available)
try {
    // use the resource
} finally {
    semaphore.release(); // return the permit
}
```

**Use cases:** Connection pool, rate limiter, parking lot (n spots).

---

### `Exchanger`
Two threads exchange data at a _04_synchronization point.

```java
Exchanger<String> exchanger = new Exchanger<>();

// Thread-1                          Thread-2
String result = exchanger.exchange("data-from-T1");  // exchange("data-from-T2")
// result = "data-from-T2"          // result = "data-from-T1"
```

---

### `Phaser` (Java 7+)
A more flexible alternative to `CyclicBarrier` and `CountDownLatch`.  
Dynamic — threads can register and deregister at any point.

---

## 16. Atomic Classes

`java.util.concurrent.atomic` provides **lock-free** thread-safe operations  
using CPU-level atomic instructions (CAS — Compare-And-Swap).

| Class | Type | Key Methods |
|-------|------|-------------|
| `AtomicBoolean` | boolean | `get()`, `set()`, `compareAndSet()` |
| `AtomicInteger` | int | `get()`, `incrementAndGet()`, `addAndGet()` |
| `AtomicLong` | long | `get()`, `incrementAndGet()`, `addAndGet()` |
| `AtomicReference<V>` | object reference | `get()`, `set()`, `compareAndSet()` |

```java
AtomicInteger counter = new AtomicInteger(0);

counter.incrementAndGet();       // ++counter (atomic)
counter.getAndIncrement();       // counter++ (atomic)
counter.addAndGet(5);            // counter += 5 (atomic)
counter.compareAndSet(10, 20);   // if(counter==10) counter=20 (atomic)
```

### `AtomicInteger` vs `volatile int` vs `synchronized`
| | `volatile int` | `AtomicInteger` | `synchronized` |
|--|---------------|-----------------|----------------|
| Visibility | ✅ | ✅ | ✅ |
| Atomic `++` | ❌ | ✅ | ✅ |
| Lock-free | ✅ | ✅ | ❌ |
| Multi-step ops | ❌ | ❌ | ✅ |

---

## 17. Best Practices

| # | Best Practice | Why |
|---|--------------|-----|
| 1 | Prefer `ExecutorService` over raw `Thread` | Reusable, manageable, efficient |
| 2 | Always call `shutdown()` on executors | Prevents resource leaks |
| 3 | Always `unlock()` in a `finally` block | Prevents lock never being released |
| 4 | Always use `while` (not `if`) with `wait()` | Guards against spurious wakeups |
| 5 | Prefer `notifyAll()` over `notify()` | Multiple waiters may need to be woken |
| 6 | Use `ThreadLocal.remove()` in thread pools | Prevents memory leaks |
| 7 | Use `volatile` only for simple flags/references | Compound ops need `Atomic*` or `synchronized` |
| 8 | Acquire multiple locks in a consistent order | Prevents deadlocks |
| 9 | Keep synchronized sections short | Reduces contention and improves throughput |
| 10 | Prefer concurrent collections over manual sync | Less error-prone; better performance |
| 11 | Use `Atomic*` classes for simple counters | Lock-free; faster than `synchronized` |
| 12 | Name your threads | Makes debugging and profiling much easier |

---

## 18. Quick Reference Table

| Concept | Class / Keyword | Key Points |
|---------|-----------------|------------|
| Create thread | `Thread`, `Runnable` | Call `start()`, not `run()` |
| Thread state | `Thread.State` | NEW, RUNNABLE, BLOCKED, WAITING, TIMED_WAITING, TERMINATED |
| Mutual exclusion | `synchronized` | Method or block; lock is object/class |
| Communication | `wait()`, `notify()`, `notifyAll()` | Inside synchronized; always use while loop |
| Deadlock | N/A | Consistent lock ordering; `tryLock()` |
| Thread pool | `ExecutorService`, `Executors` | `execute()`, `submit()`, `shutdown()` |
| Return value | `Callable<V>`, `Future<V>` | `submit()` → `Future`; `future.get()` blocks |
| Visibility | `volatile` | Flush/read main memory; not atomic for compound ops |
| Per-thread data | `ThreadLocal<T>` | Always `remove()` in thread pools |
| Background thread | `setDaemon(true)` | Dies when all user threads finish |
| Priority | `setPriority(n)` | 1–10; OS hint only |
| Advanced lock | `ReentrantLock` | `tryLock()`, fairness, interruptible |
| Read/write lock | `ReentrantReadWriteLock` | Multiple concurrent readers OR one writer |
| Atomic ops | `AtomicInteger`, `AtomicBoolean` | Lock-free; CAS; good for counters |
| Safe map | `ConcurrentHashMap` | Segmented; high concurrency |
| Safe list | `CopyOnWriteArrayList` | Write copies array; reads never block |
| Blocking queue | `ArrayBlockingQueue`, `LinkedBlockingQueue` | `put()` blocks when full; `take()` blocks when empty |
| Wait for N events | `CountDownLatch` | `countDown()` / `await()` |
| Barrier | `CyclicBarrier` | All threads wait; all proceed together |
| Resource limit | `Semaphore` | `acquire()` / `release()` |

---

## Code Files in This Demo

| File | Concept Demonstrated |
|------|--------------------|
| `_01_ThreadByExtendingThread.java` | Creating thread by extending Thread |
| `_02_ThreadByRunnable.java` | Creating thread by implementing Runnable (lambda too) |
| `_03_ThreadLifecycleDemo.java` | Thread states: NEW, RUNNABLE, BLOCKED, WAITING, TIMED_WAITING, TERMINATED |
| `_04_SynchronizationDemo.java` | Race condition, synchronized method, synchronized block |
| `_05_ThreadCommunicationDemo.java` | wait(), notify(), notifyAll() — Producer-Consumer |
| `_06_DeadlockDemo.java` | Deadlock, lock ordering prevention, tryLock() prevention |
| `_07_ExecutorServiceDemo.java` | Fixed/Cached/Single/Scheduled thread pools |
| `_08_CallableAndFutureDemo.java` | Callable, Future, get(), isDone(), timeout, exceptions |
| `_09_VolatileDemo.java` | volatile flag, visibility vs atomicity, AtomicInteger |
| `_10_ThreadLocalDemo.java` | Per-thread counter, per-thread SimpleDateFormat, request context |
| `_11_PriorityAndDaemonDemo.java` | Thread priority, Thread.yield(), daemon thread lifecycle |
| `_12_LocksDemo.java` | ReentrantLock, tryLock(), ReadWriteLock |
| `_13_ConcurrentCollectionsDemo.java` | ConcurrentHashMap, CopyOnWriteArrayList, BlockingQueue |
| `_14_SyncUtilitiesDemo.java` | CountDownLatch, CyclicBarrier, Semaphore |

