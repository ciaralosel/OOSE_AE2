package detectors;

//Written by Ciara Losel 2438870L

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import com.github.javaparser.JavaParser;
import com.github.javaparser.ParseProblemException;
import com.github.javaparser.ast.CompilationUnit;

public class Driver {
	
	private static Scanner userInput = new Scanner(System.in);
	
	public static void main(String[] args) {
		System.out.println("Enter file path or enter ! to exit.");
		String FILE_PATH = userInput.nextLine();
		
		FileInputStream inputStream = null;
		
		try {
			
			if (!FILE_PATH.equals("!")) {
				//Provide default program code to operate on
				if(FILE_PATH.equals("")) {
					FILE_PATH = "src/main/java/detectors/Calculator.java";
				}
			
				inputStream = new FileInputStream(FILE_PATH);
				CompilationUnit cu = JavaParser.parse(inputStream);
				UselessControlFlowDetector controlFlowVisitor = new UselessControlFlowDetector();
				RecursionDetector recursionDetectorVisitor = new RecursionDetector();
				List<Breakpoints> uselessControlFlowCollector = new ArrayList<>();
				List<Breakpoints> recursionCollector = new ArrayList<>();
				controlFlowVisitor.visit(cu,  uselessControlFlowCollector);
				recursionDetectorVisitor.visit(cu, recursionCollector);
			
				System.out.println("Useless Control Flows:");
				for(Breakpoints brkPnt : uselessControlFlowCollector)
					System.out.println(brkPnt);
				System.out.println("Recursions:");
				for (Breakpoints brkPnt : recursionCollector) {
					System.out.println(brkPnt);
				}
			}
			
			
		} catch (ParseProblemException e) {
			System.out.println("Error occurred when parsing.");
			e.printStackTrace();
		} catch (FileNotFoundException e) {
			System.out.println("File path provided wasn't found.");
			e.printStackTrace();
		} finally {
			try {
				inputStream.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

}
