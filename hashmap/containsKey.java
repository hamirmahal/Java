import java.util.HashMap; // import the HashMap class

class Main {
  public static void main(String args[]) {
    System.out.println("Hello, world!");
    HashMap<String, String> a = new HashMap<>();
    a.put("abc", "a");
    a.put("def", "a");
    a.put("172.27.17.7:3001", "a");
    boolean result0 = a.containsKey("abc");
    boolean result1 = a.containsKey("def");
    boolean result2 = a.containsKey("172.27.17.7:3001");
    System.out.println(result0);
    System.out.println(result1);
    System.out.println(result2);
  }
}
