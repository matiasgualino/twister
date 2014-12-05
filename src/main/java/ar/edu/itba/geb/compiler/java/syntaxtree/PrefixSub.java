package ar.edu.itba.geb.compiler.java.syntaxtree;

import ar.edu.itba.geb.compiler.java.visitors.Visitor;
import ar.edu.itba.geb.compiler.java.visitors.TypeVisitor;

public class PrefixSub extends Exp {
  public Exp exp;

  public PrefixSub(Exp e) {
    exp = e;
  }

  public void accept(Visitor v) {
    v.visit(this);
  }

  public Type accept(TypeVisitor v) {
    return v.visit(this);
  }
}
