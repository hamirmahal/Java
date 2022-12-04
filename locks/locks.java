package locks;

import java.io.IOException;
import java.util.ArrayList;
import java.util.concurrent.locks.ReentrantLock;
import java.util.logging.FileHandler;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;

class Main {
  static final ArrayList<String> dataStructureToBeLocked = new ArrayList<>();
  // https://stackoverflow.com/a/27559619
  static final String thisFilesName = new Throwable()
      .getStackTrace()[0]
      .toString()
      .split("\\(")[1]
      .split(":")[0];

  // https://www.baeldung.com/java-concurrent-locks#1-reentrantlock
  static final ReentrantLock lock = new ReentrantLock();
  static final Logger logger = Logger.getLogger("");

  // https://www.logicbig.com/tutorials/core-java-tutorial/logging/customizing-default-format.html
  public static void main(String args[]) {
    System.setProperty(
        "java.util.logging.SimpleFormatter.format",
        "[%1$tF %1$tT] [%4$-7s] [%2$-25s] %5$s%n");

    try {
      FileHandler f = new FileHandler(thisFilesName + ".output.txt", true);
      f.setFormatter(new SimpleFormatter());
      logger.addHandler(f);
    } catch (SecurityException | IOException e1) {
      System.err.println("Failed to create new FileHandler!");
      e1.printStackTrace();
      System.exit(1);
    }
    // Comment out the `setLevel` line to see all logs.
    logger.setLevel(Level.WARNING);
    logger.info("Program started!");

    // https://www.javatpoint.com/how-to-create-a-thread-in-java
    SideThread st = new SideThread();
    st.start();

    lock.lock();
    logger.info("Lock acquired!");
    dataStructureToBeLocked.add("main method!");
    logger.info("Data structure after lock aquisition!");
    logger.warning(dataStructureToBeLocked.toString());
    logger.info("Releasing lock!");
    lock.unlock();

    // https://stackoverflow.com/questions/4691533/java-wait-for-thread-to-finish/4691544#4691544
    try {
      st.join();
      logger.info("Finished!");
      logger.info("Data structure is " + dataStructureToBeLocked);
    } catch (InterruptedException e) {
      // Either re-interrupt this method or rethrow the
      // "InterruptedException" that can be caught here.sonarlint(java:S2142)
      Thread.currentThread().interrupt();
      System.err.println("Failed to wait for side thread!");
      e.printStackTrace();
    }
  }

  static class SideThread extends Thread {
    @Override
    public void run() {
      lock.lock();
      logger.info("Lock acquired!");
      dataStructureToBeLocked.add("SideThread!");
      logger.info("Data structure after lock!");
      logger.info(dataStructureToBeLocked.toString());
      logger.info("Releasing lock!");
      lock.unlock();
    }
  }
}
