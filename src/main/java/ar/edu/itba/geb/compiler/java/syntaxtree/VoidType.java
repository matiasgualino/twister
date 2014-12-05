package ar.edu.itba.geb.compiler.java.syntaxtree;

import ar.edu.itba.geb.compiler.java.visitors.Visitor;
import ar.edu.itba.geb.compiler.java.visitors.TypeVisitor;

public class VoidType implements Type {
  private static VoidType instance = new VoidType();

  public static VoidType instance() {
    return instance;
  }

  private VoidType() { }

  public void accept(Visitor v) {
    v.visit(this);
  }

  public Type accept(TypeVisitor v) {
    return v.visit(this);
  }

  @Override
  public String toString() {
    return "void";
  }
}
