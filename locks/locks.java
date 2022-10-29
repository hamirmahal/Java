package locks;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.concurrent.locks.ReentrantLock;

class Main {
  final static ArrayList<String> dataStructureToBeLocked = new ArrayList<>();

  // https://www.javatpoint.com/java-get-current-date
  final static DateTimeFormatter dtf = DateTimeFormatter.ofPattern("HH:mm:ss MM-dd-yyyy");
  final static String getTime() {
    return " " + dtf.format(LocalDateTime.now());
  }

  // https://www.baeldung.com/java-concurrent-locks#1-reentrantlock
  final static ReentrantLock lock = new ReentrantLock();
  final static boolean printingVerbosely = false;

  public static void main(String args[]) {
    if (printingVerbosely) {
      System.out.println("Program started!" + getTime());
    }

    // https://www.javatpoint.com/how-to-create-a-thread-in-java
    SideThread st = new SideThread();
    st.start();

    lock.lock();
    if (printingVerbosely) {
      System.out.println("\nLock acquired in main method" + getTime());
    }
    dataStructureToBeLocked.add("main method!");
    System.out.println("Data structure after lock in main method " + dataStructureToBeLocked);
    if (printingVerbosely) {
      System.out.println("Releasing lock in main method!" + getTime());
    }
    lock.unlock();

    /*
    https://stackoverflow.com/questions/4691533/java-wait-for-thread-to-finish/4691544#4691544
    */
    try {
      st.join();
      if (printingVerbosely) {
        System.out.println("\nFinished!" + getTime());
        System.out.println("Data structure is " + dataStructureToBeLocked);
      }
    } catch (InterruptedException e) {
      System.err.println("\nFailed to wait for side thread!" + getTime());
      e.printStackTrace();
    }
  }

  static class SideThread extends Thread {
    public void run() {
      lock.lock();
      if (printingVerbosely) {
        System.out.println("\nLock acquired in SideThread!" + getTime());
      }
      dataStructureToBeLocked.add("SideThread!");
      if (printingVerbosely) {
        System.out.println("Data structure after lock in SideThread! " + dataStructureToBeLocked);
        System.out.println("Releasing lock in SideThread!" + getTime());
      }
      lock.unlock();
    }
  }
}
