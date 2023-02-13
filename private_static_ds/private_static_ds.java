package private_static_ds;

import java.io.IOException;
import java.util.ArrayList;
import java.util.logging.FileHandler;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;

class Main {
  static final Logger LOGGER = Logger.getAnonymousLogger();
  static final String PATH = "private_static_ds/private_static_ds.java";

  public static void main(String[] args) {
    System.setProperty(
        "java.util.logging.SimpleFormatter.format",
        "[%1$tF %1$tT] [%4$-7s] [%2$-27s] %5$s%n");

    FileHandler fh;
    try {
      fh = new FileHandler(PATH + ".output.txt");
    } catch (SecurityException | IOException e) {
      Test.exitAfter(e, "Error while creating " + FileHandler.class, LOGGER);
      return;
    }
    fh.setFormatter(new SimpleFormatter());

    final String starting = "Starting program...";
    LOGGER.addHandler(fh);
    LOGGER.setLevel(Level.ALL);
    LOGGER.info(starting);

    Test.init();
    Test.log(LOGGER);
  }
}

class Test {
  private static final ArrayList<String> list = new ArrayList<>();

  // Add a private constructor to hide the implicit public
  // one.sonarlint(java:S1118)
  private Test() {
  }

  static void exitAfter(Exception e, String msg, Logger log) {
    log.severe(msg);
    e.printStackTrace();
    System.exit(1);
  }

  static void init() {
    list.add("there");
    list.add("and");
    list.add("back");
    list.add("again!");
  }

  static void log(Logger logger) {
    final String listString = "list: " + list;
    logger.info(listString);
  }
}
