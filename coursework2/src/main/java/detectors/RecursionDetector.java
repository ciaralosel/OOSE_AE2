package detectors;

//Written by Ciara Losel 2438870L

import java.util.List;
import java.util.Stack;

import com.github.javaparser.ast.Node;
import com.github.javaparser.ast.body.ClassOrInterfaceDeclaration;
import com.github.javaparser.ast.body.MethodDeclaration;
import com.github.javaparser.ast.expr.MethodCallExpr;
import com.github.javaparser.ast.visitor.VoidVisitorAdapter;

public class RecursionDetector extends VoidVisitorAdapter<List<Breakpoints>> {
	
	private String className;
	private String methodName;
	//Stack is appropriate structure to detect recursion as can pop and push methods onto the stack when they're called
	private Stack<MethodDeclaration> methodDeclarationStack = new Stack<>();
	private List<Breakpoints> breakpointsCollector;
	
	@Override
	public void visit(ClassOrInterfaceDeclaration classDeclaration, List<Breakpoints> breakpointsCollector) {
		this.className = classDeclaration.getName().asString();
		this.breakpointsCollector = breakpointsCollector;
		super.visit(classDeclaration,  breakpointsCollector);
	}
	
	@Override
	public void visit(MethodCallExpr methodCall, List<Breakpoints> breakpointsCollector) {
		//This method checks if methodDeclaration and name of method being called are the same, which would indicate recursion. If so, it creates a new breakpoint.
		String methodName = methodDeclarationStack.peek().getName().asString();
		if (methodCall.getName().asString().equals(methodName)) {
			newBreakpoint(methodCall);
		}
	}
	
	public void visit(MethodDeclaration methodDeclaration, List<Breakpoints> breakpointsCollector) {
		//This method adds a new method declaration to the stack, checks its contents and then removes it from the stack.
		methodDeclarationStack.push(methodDeclaration);
		this.methodName = methodDeclaration.getNameAsString();
		super.visit(methodDeclaration, breakpointsCollector);
		methodDeclarationStack.pop();
	}
	
	public void newBreakpoint(Node n) {
		int startIndex = n.getRange().get().begin.line;
		int endIndex = n.getRange().get().begin.line;
		Breakpoints breakpoints = new Breakpoints(className, methodName, startIndex, endIndex);
		this.breakpointsCollector.add(breakpoints);
	}

}
