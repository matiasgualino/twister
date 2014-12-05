package ar.edu.itba.geb.compiler.java.visitors;

import ar.edu.itba.geb.compiler.java.syntaxtree.*;

public class DepthFirstVisitor implements Visitor {
	public void visit(Program n) {
		n.mainC.accept(this);
		for (ClassDecl cd : n.classList.getList()) {
			cd.accept(this);
		}
	}

	public void visit(MainClass n) {
		n.classNameId.accept(this);
		n.argId.accept(this);

		for (VarDecl vd : n.localVars.getList()) {
			vd.accept(this);
		}

		for (Statement st : n.statements.getList()) {
			st.accept(this);
		}
		
		for (int i = n.classVars.size() - 1; i >= 0; i--) {
			n.classVars.elementAt(i).accept(this);
		}
		
		for (int i = n.classMethods.size() - 1; i >= 0; i--) {
			n.classMethods.elementAt(i).accept(this);
		}
	}

	public void visit(ClassDeclSimple n) {
		n.classId.accept(this);

		for (VarDecl vd : n.fieldVarList.getList()) {
			vd.accept(this);
		}

		for (MethodDecl md : n.methodList.getList()) {
			md.accept(this);
		}
	}

	public void visit(ClassDeclExtends n) {
		n.classId.accept(this);
		n.baseClassId.accept(this);
		for (VarDecl vd : n.fieldVarList.getList()) {
			vd.accept(this);
		}

		for (MethodDecl md : n.methodList.getList()) {
			md.accept(this);
		}
	}

	public void visit(VarDecl n) {
		n.varType.accept(this);
		for (int i = n.varL.size() - 1; i >= 0; i--) {
			n.varL.get(i).accept(this);
		}
	}

	public void visit(MethodDecl n) {
		n.methodReturnT.accept(this);
		n.methodNameId.accept(this);
		for (Formal f : n.formalParamList.getList()) {
			f.accept(this);
		}

		for (VarDecl vd : n.localVarList.getList()) {
			vd.accept(this);
		}

		for (Statement st : n.statementList.getList()) {
			st.accept(this);
		}
		if (n.returnExpr != null) {
			n.returnExpr.accept(this);
		}
	}

	public void visit(Formal n) {
		n.paramType.accept(this);
		n.paramId.accept(this);
	}

	public void visit(IntArrayType n) {
	}

	public void visit(BooleanType n) {
	}

	public void visit(IntegerType n) {
	}

	public void visit(VoidType n) {
	}

	public void visit(IdentifierType n) {
	}

	public void visit(Block n) {
		for (Statement st : n.stmtList.getList()) {
			st.accept(this);
		}
	}

	public void visit(If n) {
		n.boolExpr.accept(this);
		for (int i = n.trueStmt.size() - 1; i >= 0; i--) {
			n.trueStmt.elementAt(i).accept(this);
		}
		
		if (n.falseStmt != null) {
			for (int i = n.falseStmt.size() - 1; i >= 0; i--) {
				n.falseStmt.elementAt(i).accept(this);
			}
		}
	}

	public void visit(While n) {
		n.boolExpr.accept(this);
		for (int i = n.stmt.size() - 1; i >= 0; i--) {
			n.stmt.elementAt(i).accept(this);
		}
	}

	public void visit(Print n) {
		n.intExpr.accept(this);
	}

	public void visit(Assign n) {
		n.varId.accept(this);
		n.valueExpr.accept(this);
	}

	public void visit(ArrayAssign n) {
		n.arrayId.accept(this);
		n.indexExpr.accept(this);
		n.valueExpr.accept(this);
	}

	public void visit(And n) {
		n.e1.accept(this);
		n.e2.accept(this);
	}

	public void visit(Or n) {
		n.e1.accept(this);
		n.e2.accept(this);
	}

	public void visit(LessThan n) {
		n.e1.accept(this);
		n.e2.accept(this);
	}

	public void visit(Plus n) {
		n.e1.accept(this);
		n.e2.accept(this);
	}

	public void visit(Minus n) {
		n.e1.accept(this);
		n.e2.accept(this);
	}

	public void visit(Times n) {
		n.e1.accept(this);
		n.e2.accept(this);
	}

	public void visit(ArrayLookup n) {
		n.indexExpr.accept(this);
		n.arrayExpr.accept(this);
	}

	public void visit(ArrayLength n) {
		n.arrayExpr.accept(this);
	}

	public void visit(Call n) {
		n.objectExpr.accept(this);
		n.methodId.accept(this);
		for (Exp e : n.paramExprList.getList()) {
			e.accept(this);
		}
	}

	public void visit(IntegerLiteral n) {
	}

	public void visit(StringLiteral n) {
	}

	public void visit(True n) {
	}

	public void visit(False n) {
	}

	public void visit(This n) {
	}

	public void visit(NewArray n) {
		n.sizeExpr.accept(this);
	}

	public void visit(NewObject n) {
	}

	public void visit(Not n) {
		n.boolExpr.accept(this);
	}

	public void visit(Identifier n) {
	}

	public void visit(Div n) {
		n.e1.accept(this);
		n.e2.accept(this);
	}

	public void visit(For n) {

		for (Statement st : n.init.getList()) {
			st.accept(this);
		}
		n.boolExpr.accept(this);
		n.body.accept(this);
		for (Statement st : n.step.getList()) {
			st.accept(this);
		}
	}

	public void visit(PrintString n) {
	}

	public void visit(Equal n) {
		n.e1.accept(this);
		n.e2.accept(this);
	}

	public void visit(NotEqual n) {
		n.e1.accept(this);
		n.e2.accept(this);
	}

	public void visit(Greater n) {
		n.e1.accept(this);
		n.e2.accept(this);
	}

	public void visit(GreaterOrEqual n) {
		n.e1.accept(this);
		n.e2.accept(this);
	}

	public void visit(LessOrEqual n) {
		n.e1.accept(this);
		n.e2.accept(this);
	}

	public void visit(PrefixAdd n) {
		n.exp.accept(this);
	}

	public void visit(PrefixSub n) {
		n.exp.accept(this);
	}

	public void visit(PostfixAdd n) {
		n.exp.accept(this);
	}

	public void visit(PostfixSub n) {
		n.exp.accept(this);
	}

	@Override
	public void visit(StringArrayType n) {
		// TODO Auto-generated method stub

	}

	@Override
	public void visit(StringType n) {
		// TODO Auto-generated method stub

	}

	public void visit(VariableOrMethod n) {
		for (VarDecl vd : n.varList.getList()) {
			vd.accept(this);
		}

		for (MethodDecl md : n.metList.getList()) {
			md.accept(this);
		}
	}

	public void visit(Return n) {
		n.e1.accept(this);
	}
}