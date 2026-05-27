package basics;
// ============================================================
// TOPIC: Creating a Thread by Implementing Runnable
// ============================================================
// The recommended way to create a thread is to implement the Runnable interface.
// Runnable is a @FunctionalInterface with one method: run()
// We pass the Runnable to a Thread object, then call start().
//
// WHY is Runnable preferred over extending Thread?
//  - Your class is free to extend another class (no multiple-inheritance conflict)
//  - Separates the TASK (Runnable) from the THREAD (execution unit)
//  - Supports lambda expressions (Java 8+)
// ============================================================
public class _02_ThreadByRunnable {
    // Step 1: Implement Runnable — define the task
    static class CountdownTask implements Runnable {
        private String taskName;
        private int start;
        public CountdownTask(String taskName, int start) {
            this.taskName = taskName;
            this.start    = start;
        }
        // Step 2: Override run() — the thread's work goes here
        @Override
        public void run() {
            System.out.println(taskName + " countdown started.");
            for (int i = start; i >= 0; i--) {
                System.out.println(taskName + " -> " + i);
                try {
                    Thread.sleep(400);
                } catch (InterruptedException e) {
                    System.out.println(taskName + " interrupted!");
                    return;
                }
            }
            System.out.println(taskName + " -> LAUNCH!");
        }
    }
    public static void main(String[] args) throws InterruptedException {
        System.out.println("=== Thread by Implementing Runnable ===\n");
        // --- Style 1: Classic anonymous class (pre-Java-8) ---
        Runnable anonymousTask = new Runnable() {
            @Override
            public void run() {
                System.out.println("Anonymous Runnable running in: "
                        + Thread.currentThread().getName());
            }
        };
        Thread anonymousThread = new Thread(anonymousTask, "AnonymousThread");
        anonymousThread.start();
        anonymousThread.join();
        System.out.println();
        // --- Style 2: Lambda expression (Java 8+) ---
        // Because Runnable is a @FunctionalInterface, we can use a lambda
        Runnable lambdaTask = () ->
                System.out.println("Lambda Runnable running in: "
                        + Thread.currentThread().getName());
        Thread lambdaThread = new Thread(lambdaTask, "LambdaThread");
        lambdaThread.start();
        lambdaThread.join();
        System.out.println();
        // --- Style 3: Named class (complex, reusable task) ---
        Thread rocketA = new Thread(new CountdownTask("Rocket-A", 5), "RocketA-Thread");
        Thread rocketB = new Thread(new CountdownTask("Rocket-B", 3), "RocketB-Thread");
        rocketA.start();
        rocketB.start();
        rocketA.join();
        rocketB.join();
        System.out.println("\nAll threads finished.");
        // -------------------------------------------------------
        // KEY POINTS:
        // - Thread.currentThread().getName()  -> returns the current thread's name
        // - new Thread(runnable, "name")       -> creates named thread with a task
        // - Lambda shortcut works because Runnable has exactly one abstract method
        // -------------------------------------------------------
    }
}