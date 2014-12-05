package ar.edu.itba.geb.compiler.java.symboltable;

public class Util {
  public static String getDefinedName(String label) {
    return label.split("@")[1].trim();
  }
}
