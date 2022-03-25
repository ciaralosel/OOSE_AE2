package detectors;

//Written by Ciara Losel 2438870L

import java.util.List;

import com.github.javaparser.ast.visitor.VoidVisitorAdapter;
import com.github.javaparser.ast.Node;
import com.github.javaparser.ast.NodeList;
import com.github.javaparser.ast.stmt.DoStmt;
import com.github.javaparser.ast.stmt.ForStmt;
import com.github.javaparser.ast.stmt.IfStmt;
import com.github.javaparser.ast.stmt.SwitchEntry;
import com.github.javaparser.ast.stmt.SwitchStmt;
import com.github.javaparser.ast.stmt.WhileStmt;
import com.github.javaparser.ast.stmt.ForEachStmt;
import com.github.javaparser.ast.body.ClassOrInterfaceDeclaration;
import com.github.javaparser.ast.body.ConstructorDeclaration;
import com.github.javaparser.ast.body.MethodDeclaration;


public class UselessControlFlowDetector extends VoidVisitorAdapter<List<Breakpoints>> {
	
	private List<Breakpoints> breakpointsCollector;
	private String className;
	private String methodName;
	
	@Override
	public void visit(ClassOrInterfaceDeclaration classDeclaration, List<Breakpoints> breakpointsCollector) {
		this.className = classDeclaration.getName().asString();
		this.breakpointsCollector = breakpointsCollector;
		super.visit(classDeclaration,  breakpointsCollector);
	}
	
	@Override
	public void visit(MethodDeclaration methodDeclaration, List<Breakpoints> breakpointsCollector) {
		//This method checks if the method is empty or only contains comments. If so, it creates a new breakpoint.
		this.methodName = methodDeclaration.getName().asString();
		String body = methodDeclaration.getBody().toString();
		String[] bodyLines = body.split("\n");
		int commentCount = 0;
		int operationCount = 0;
		for (String line : bodyLines) {
			if (line.startsWith("//")) {
				commentCount = commentCount + 1;
			} else {
				operationCount = operationCount + 1;
			}
		}
		boolean onlyContainsComments = false;
		if ((commentCount > 0) && (operationCount == 0)) {
			onlyContainsComments = true;
		}
		
		if ((methodDeclaration.getBody().isEmpty()) || (onlyContainsComments)) {
			newBreakpoint(methodDeclaration);
			return;
		} else {
			super.visit(methodDeclaration,  breakpointsCollector);
		}	
	}
	
	
	@Override
	public void visit(DoStmt doStatement, List<Breakpoints> breakpointsCollector) {
		//This method checks if a do statement is empty or only contains comments. If so, it creates a new breakpoint.
		String body = doStatement.getBody().toString();
		String[] bodyLines = body.split("\n");
		int commentCount = 0;
		int operationCount = 0;
		for (String line : bodyLines) {
			if (line.startsWith("//")) {
				commentCount = commentCount + 1;
			} else {
				operationCount = operationCount + 1;
			}
		}
		boolean onlyContainsComments = false;
		if ((commentCount > 0) && (operationCount == 0)) {
			onlyContainsComments = true;
		}
		
		if ((doStatement.getBody().toString().equals("{\r\n"
				+ "}")) || (doStatement.getBody().toString().startsWith("{\r\n" + "//"))) {
			newBreakpoint(doStatement);
			return;
		} else if (onlyContainsComments) {
			newBreakpoint(doStatement);
			return;
		} else {
			super.visit(doStatement,  breakpointsCollector);
		}
	}
	
	@Override
	public void visit(ForStmt forStatement, List<Breakpoints> breakpointsCollector) {
		//This method checks if a for statement is empty or only contains comments. If so, it creates a new breakpoint.
		String body = forStatement.getBody().toString();
		String[] bodyLines = body.split("\n");
		int commentCount = 0;
		int operationCount = 0;
		for (String line : bodyLines) {
			if (line.startsWith("//")) {
				commentCount = commentCount + 1;
			} else {
				operationCount = operationCount + 1;
			}
		}
		boolean onlyContainsComments = false;
		if ((commentCount > 0) && (operationCount == 0)) {
			onlyContainsComments = true;
		}
		
		if ((forStatement.getBody().toString().equals("{\r\n"
				+ "}")) || (forStatement.getBody().toString().startsWith("{\r\n" + "//"))) {
			newBreakpoint(forStatement);
			return;
		} else if (onlyContainsComments) {
			newBreakpoint(forStatement);
			return;
		} else {
			super.visit(forStatement,  breakpointsCollector);
		}
	}
	
	@Override
	public void visit(IfStmt ifStatement, List<Breakpoints> breakpointsCollector) {
		//This method checks if the then body of an if statement is empty or only contains comments. If so, it creates a new breakpoint.
		String thenStatement = ifStatement.getThenStmt().toString();
		String[] thenStatementLines = thenStatement.split("\n");
		int commentCount = 0;
		int operationCount = 0;
		for (String line : thenStatementLines) {
			if (line.startsWith("//")) {
				commentCount = commentCount + 1;
			} else {
				operationCount = operationCount + 1;
			}
		}
		boolean onlyContainsComments = false;
		if ((commentCount > 0) && (operationCount == 0)) {
			onlyContainsComments = true;
		}
		
		if ((ifStatement.getThenStmt().toString().equals("{\r\n"
				+ "}")) || (ifStatement.getThenStmt().toString().startsWith("{\r\n" + "//"))) {
			newBreakpoint(ifStatement);
			return;
		} else if (onlyContainsComments) {
			newBreakpoint(ifStatement);
			return;
		} else {
			super.visit(ifStatement,  breakpointsCollector);
		}
	}
	
	@Override
	public void visit(WhileStmt whileStatement, List<Breakpoints> breakpointsCollector) {
		//This method checks if a while statement is empty of only contains comments. If so, it creates a new breakpoint.
		String body = whileStatement.getBody().toString();
		String[] bodyLines = body.split("\n");
		int commentCount = 0;
		int operationCount = 0;
		for (String line : bodyLines) {
			if (line.startsWith("//")) {
				commentCount = commentCount + 1;
			} else {
				operationCount = operationCount + 1;
			}
		}
		boolean onlyContainsComments = false;
		if ((commentCount > 0) && (operationCount == 0)) {
			onlyContainsComments = true;
		}
		
		if ((whileStatement.getBody().toString().equals("{\r\n"
				+ "}")) || (whileStatement.getBody().toString().startsWith("{\r\n" + "//"))) {
			newBreakpoint(whileStatement);
			return;
		} else if (onlyContainsComments) {
			newBreakpoint(whileStatement);
			return;
		} else {
			super.visit(whileStatement,  breakpointsCollector);
		}
	}
	
	@Override
	public void visit(ForEachStmt forEachStatement, List<Breakpoints> breakpointsCollector) {
		//This method checks if a for each statement is empty or only contains comments. If so, it creates a new breakpoint.
		String body = forEachStatement.getBody().toString();
		String[] bodyLines = body.split("\n");
		int commentCount = 0;
		int operationCount = 0;
		for (String line : bodyLines) {
			if (line.startsWith("//")) {
				commentCount = commentCount + 1;
			} else {
				operationCount = operationCount + 1;
			}
		}
		boolean onlyContainsComments = false;
		if ((commentCount > 0) && (operationCount == 0)) {
			onlyContainsComments = true;
		}
		
		if ((forEachStatement.getBody().toString().equals("{\r\n"
				+ "}")) || (forEachStatement.getBody().toString().startsWith("{\r\n" + "//"))) {
			newBreakpoint(forEachStatement);
			return;
		} else if (onlyContainsComments) {
			newBreakpoint(forEachStatement);
			return;
		} else {
			super.visit(forEachStatement,  breakpointsCollector);
		}
	}
	
	@Override
	public void visit(SwitchStmt switchStatement, List<Breakpoints> breakpointsCollector) {
		//This method checks if a switch statement is empty. If so, it creates a new breakpoint.
		//It also checks the case statements to see if they are empty or only contain comments. If so, it creates a new breakpoint.
		NodeList<SwitchEntry> entries = switchStatement.getEntries();
		int numberOfUselessEntries = 0;
		for (SwitchEntry entry : entries) {
			String body = entry.toString().substring(entry.toString().indexOf(":")+1);
			String[] bodyLines = body.split("\n");
			int commentCount = 0;
			int operationCount = 0;
			for (String line : bodyLines) {
				if (line.startsWith("//")) {
					commentCount = commentCount + 1;
				} else {
					operationCount = operationCount + 1;
				}
			}
			boolean onlyContainsComments = false;
			if ((commentCount > 0) && (operationCount == 0)) {
				onlyContainsComments = true;
			}

			if ((body.equals("\r\n")) || (onlyContainsComments)) {
				numberOfUselessEntries = numberOfUselessEntries + 1;
				newBreakpoint(entry);
			}		
		}
		
		if ((numberOfUselessEntries == entries.size())||(switchStatement.isEmptyStmt())) {
			newBreakpoint(switchStatement);
		}
	}
	
	
	@Override
	public void visit(ConstructorDeclaration constructorDeclaration, List<Breakpoints> breakpointsCollector) {
		//This method checks if the constructor is empty which isn't useless.
		if (!constructorDeclaration.getBody().isEmpty()) {
			super.visit(constructorDeclaration, breakpointsCollector);
		} else {
			return;
		}
	}
	
	public void newBreakpoint(Node n) {
		int startIndex = n.getRange().get().begin.line;
		int endIndex = n.getRange().get().end.line;
		Breakpoints breakpoints = new Breakpoints(className, methodName, startIndex, endIndex);
		this.breakpointsCollector.add(breakpoints);
	}

}
