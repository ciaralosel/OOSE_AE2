package detectors;

//Written by Ciara Losel 2438870L

public class Breakpoints {
	
	private String className;
	private String methodName;
	private int startIndex;
	private int endIndex;
	
	public Breakpoints(String className, String methodName, int startIndex, int endIndex) {
		this.className = className;
		this.methodName = methodName;
		this.startIndex = startIndex;
		this.endIndex = endIndex;
	}
	
	@Override
	public String toString() {
		return "className = " + className + ",methodName =" + methodName + ",startIndex=" + startIndex + ",endIndex =" + endIndex;
	}

}
