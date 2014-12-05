package ar.edu.itba.geb.compiler.java.syntaxtree;

import ar.edu.itba.geb.compiler.java.visitors.Visitor;
import ar.edu.itba.geb.compiler.java.visitors.TypeVisitor;

public class For implements Statement {
  public Exp boolExpr;
  public Statement body;
  public StatementList init, step;

  public For(StatementList _init, Exp bool, StatementList _step, Statement b) {
    init = _init;
    boolExpr = bool;
    step = _step;
    body = b;
  }

  public void accept(Visitor v) {
    v.visit(this);
  }

  public Type accept(TypeVisitor v) {
    return v.visit(this);
  }
}
