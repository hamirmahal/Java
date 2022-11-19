package increment;

import java.util.concurrent.atomic.AtomicInteger;
import java.util.logging.Level;
import java.util.logging.Logger;

/*
 * This program is basically the Java version of code from page 7 of the below link.
 * https://pages.cs.wisc.edu/~remzi/OSTEP/threads-intro.pdf
 */
class Main {
  private static final Logger LOGGER = Logger.getAnonymousLogger();
  private static AtomicInteger threadSafeCounter = new AtomicInteger();
  private static int counter = 0;

  // https://www.logicbig.com/tutorials/core-java-tutorial/logging/customizing-default-format.html
  public static void main(String[] args) {
    System.setProperty(
        "java.util.logging.SimpleFormatter.format",
        "[%1$tF %1$tT] [%4$-7s] [%2$-25s] %5$s%n");
    final String initialCounterInfo = "counter is " + counter;

    LOGGER.setLevel(Level.INFO);
    LOGGER.info("Program started!");
    LOGGER.info(initialCounterInfo);

    CounterThread thread1 = new CounterThread();
    CounterThread thread2 = new CounterThread();
    thread1.start();
    thread2.start();

    // https://stackoverflow.com/questions/4691533/java-wait-for-thread-to-finish/4691544#4691544
    try {
      thread1.join();
      thread2.join();
    } catch (InterruptedException e) {
      LOGGER.severe("Encountered an InterruptedException while trying to join threads!");
      Thread.currentThread().interrupt();
      e.printStackTrace();
      System.exit(1);
    }

    LOGGER.info("Finished with counter!");
    final String counterInfo = "counter is " + counter;
    LOGGER.info(counterInfo);

    ThreadSafeCounter thread3 = new ThreadSafeCounter();
    ThreadSafeCounter thread4 = new ThreadSafeCounter();
    thread3.start();
    thread4.start();

    // https://stackoverflow.com/questions/4691533/java-wait-for-thread-to-finish/4691544#4691544
    try {
      thread3.join();
      thread4.join();
    } catch (InterruptedException e) {
      LOGGER.severe("Encountered an InterruptedException while trying to join threads!");
      Thread.currentThread().interrupt();
      e.printStackTrace();
      System.exit(1);
    }

    LOGGER.info("Finished with thread-safe counter!");
    final String threadSafeCounterInfo = "threadSafeCounter is " + threadSafeCounter;
    LOGGER.info(threadSafeCounterInfo);
  }

  static class CounterThread extends Thread {
    @Override
    public void run() {
      for (int i = 1; i <= 10_000; i++) {
        counter++;
      }
    }
  }

  static class ThreadSafeCounter extends Thread {
    @Override
    public void run() {
      for (int i = 1; i <= 10_000; i++) {
        threadSafeCounter.incrementAndGet();
      }
    }
  }
}
