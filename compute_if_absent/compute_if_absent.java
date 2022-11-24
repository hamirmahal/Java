package compute_if_absent;

import java.util.concurrent.ConcurrentHashMap;
import java.util.logging.Level;
import java.util.logging.Logger;

class Main {
  private static final ConcurrentHashMap<String, Integer> STRING_TO_INT = new ConcurrentHashMap<>();
  private static final Logger LOGGER = Logger.getAnonymousLogger();

  // https://www.logicbig.com/tutorials/core-java-tutorial/logging/customizing-default-format.html
  public static void main(String[] args) {
    System.setProperty("java.util.logging.SimpleFormatter.format", "[%1$tF %1$tT] [%4$-7s] [%2$-30s] %5$s%n");

    LOGGER.setLevel(Level.INFO);
    LOGGER.info("Program started!");

    final String initial = STRING_TO_INT.toString();
    LOGGER.info(initial);
    STRING_TO_INT.computeIfAbsent("abc", Main::getFive);
    final String terminal = STRING_TO_INT.toString();
    LOGGER.info(terminal);

    LOGGER.info("Finished!");
  }

  private static int getFive(String t) {
    LOGGER.info(t);
    return 5;
  }
}
