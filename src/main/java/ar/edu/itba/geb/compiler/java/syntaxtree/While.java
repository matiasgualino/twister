package ar.edu.itba.geb.compiler.java.syntaxtree;import ar.edu.itba.geb.compiler.java.visitors.TypeVisitor;import ar.edu.itba.geb.compiler.java.visitors.Visitor;public class While implements Statement {  public Exp boolExpr;  public StatementList stmt;  public While(Exp ae, StatementList as) {    boolExpr = ae;    stmt = as;  }  public void accept(Visitor v) {    v.visit(this);  }  public Type accept(TypeVisitor v) {    return v.visit(this);  }}