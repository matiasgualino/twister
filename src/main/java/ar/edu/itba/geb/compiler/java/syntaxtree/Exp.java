package ar.edu.itba.geb.compiler.java.syntaxtree;import ar.edu.itba.geb.compiler.java.visitors.TypeVisitor;import ar.edu.itba.geb.compiler.java.visitors.Visitor;public abstract class Exp {  public abstract void accept(Visitor v);  public abstract Type accept(TypeVisitor v);  public void setType(Type t) {    type = t;  }  public Type getType() {    return type;  }  private Type type;}