package gumtreeFacade;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.PrintStream;

import fr.labri.gumtree.client.DiffClient;
import fr.labri.gumtree.client.TreeGeneratorRegistry;
import fr.labri.gumtree.io.TreeIoUtils;
import fr.labri.gumtree.tree.Tree;

public class AST {
	
	
	public static String getTreeAST(String filePath) throws IOException {
		String json = null;

		Tree t = TreeGeneratorRegistry.getInstance().getTree(filePath);
		if (t != null) {
			json = TreeIoUtils.toJSON(t);
		}
		
		return json;
	}
	
	public static String getDiffAST(String filePath1, String filePath2) throws IOException {
		String json = null;
		
		if (filePath1 != null && filePath2 != null) {
			json = captureOutput(filePath1, filePath2);
		}
		return json;
	}
	
	private static String captureOutput(String src, String dst) {
		ByteArrayOutputStream baos = new ByteArrayOutputStream(); 
		PrintStream ps = new PrintStream(baos);
		    
		// IMPORTANT: Save the old System.out!
		PrintStream old = System.out;
		System.setOut(ps);
		
		DiffClient.main(new String[] {"--output", "json", src, dst});
		
		// Reset System.out
		System.out.flush();
	    System.setOut(old);
	    
	    return baos.toString();
	}
	
	public static void main(String[] args) throws IOException {
		//System.out.println(getTreeAST("/home/nicozone/workspace/gumtree/samples/java/Example_v1.java"));
		System.out.println(getDiffAST("/home/nicozone/workspace/gumtree/samples/java/Example_v1.java", "/home/nicozone/workspace/gumtree/samples/java/Example_v2.java"));
	}
}
