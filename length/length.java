package length;

import java.io.IOException;
import java.util.logging.FileHandler;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;

class Main {
  static final Logger LOGGER = Logger.getAnonymousLogger();
  static final String PATH = "length.java";

  public static void main(String[] args) {
    System.setProperty(
        "java.util.logging.SimpleFormatter.format",
        "[%1$tF %1$tT] [%4$-7s] [%2$-16s] %5$s%n");

    FileHandler fh;
    try {
      fh = new FileHandler(PATH + ".output.txt");
    } catch (SecurityException | IOException e) {
      Util.exitAfter(e, "Error while creating " + FileHandler.class, LOGGER);
      return;
    }
    fh.setFormatter(new SimpleFormatter());

    final String starting = "Starting program...";
    LOGGER.addHandler(fh);
    LOGGER.setLevel(Level.ALL);
    LOGGER.info(starting);

    final String specialCharacter = "é";
    final int numBytes = specialCharacter.getBytes().length;
    final String hasLength = specialCharacter
        + " has length " + specialCharacter.length()
        + " and " + numBytes + " bytes!";
    LOGGER.info(hasLength);
  }
}

class Util {
  // Add a private constructor to hide the implicit public
  // one.sonarlint(java:S1118)
  private Util() {
  }

  static void exitAfter(Exception e, String msg, Logger log) {
    log.severe(msg);
    e.printStackTrace();
    System.exit(1);
  }
}
