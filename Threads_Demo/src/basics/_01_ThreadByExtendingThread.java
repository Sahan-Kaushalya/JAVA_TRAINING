package basics;

// ============================================================
// TOPIC: Creating a Thread by Extending the Thread Class
// ============================================================
// The simplest way to create a thread is to extend java.lang.Thread
// and override its run() method.
// The run() method defines what the thread will DO when it executes.
// We start the thread by calling start() (NOT run() directly).
// Calling start() tells the JVM to create a new OS thread and then invoke run().
// ============================================================

public class _01_ThreadByExtendingThread {

    // Step 1: Create a class that extends Thread
    static class PrintNumbersThread extends Thread {

        private String threadName;

        public PrintNumbersThread(String threadName) {
            this.threadName = threadName;
        }

        // Step 2: Override the run() method — this is the thread's task
        @Override
        public void run() {
            System.out.println(threadName + " started.");

            for (int i = 1; i <= 5; i++) {
                System.out.println(threadName + " -> Number: " + i);

                try {
                    // Thread.sleep() pauses this thread for the given milliseconds
                    // It does NOT release any locks held by this thread
                    Thread.sleep(500); // sleep 500ms between prints
                } catch (InterruptedException e) {
                    // InterruptedException is thrown if another thread interrupts this thread while sleeping
                    System.out.println(threadName + " was interrupted!");
                    return; // exit cleanly
                }
            }

            System.out.println(threadName + " finished.");
        }
    }

    public static void main(String[] args) {

        System.out.println("=== Thread by Extending Thread ===\n");

        // Step 3: Create thread instances
        PrintNumbersThread thread1 = new PrintNumbersThread("Thread-A");
        PrintNumbersThread thread2 = new PrintNumbersThread("Thread-B");

        // Step 4: Call start() to launch both threads concurrently
        // Both threads will run INDEPENDENTLY — their output may interleave
        thread1.start();
        thread2.start();

        // The main thread continues here while thread1 and thread2 run in parallel
        System.out.println("Main thread: both threads have been started.");

        try {
            // join() makes the current (main) thread WAIT until the target thread finishes
            thread1.join();
            thread2.join();
        } catch (InterruptedException e) {
            System.out.println("Main thread was interrupted!");
        }

        System.out.println("\nMain thread: both threads have finished.");

        // -------------------------------------------------------
        // KEY POINTS:
        // - Extending Thread is easy but restricts you from extending another class
        //   (Java does not support multiple class inheritance).
        // - start()  → creates a new thread and calls run() on it
        // - run()    → just executes in the current thread (no new thread created)
        // - sleep()  → pauses current thread; throws InterruptedException
        // - join()   → calling thread waits for the target thread to finish
        // -------------------------------------------------------
    }
}

