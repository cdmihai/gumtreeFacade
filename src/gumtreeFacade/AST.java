package gumtreeFacade;

import java.io.BufferedWriter;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintStream;
import java.util.UUID;

import fr.labri.gumtree.client.DiffClient;
import fr.labri.gumtree.client.TreeGeneratorRegistry;
import fr.labri.gumtree.io.TreeIoUtils;
import fr.labri.gumtree.tree.Tree;

public class AST {
	
	
	public static String getTreeAST(String fileContents, String extension) throws IOException {
		String filePath = getTempFileFromString(fileContents, extension).getAbsolutePath();
		
		return getTreeAST(filePath);
	}

	public static String getTreeAST(String filePath) throws IOException {
		String json = null;

		Tree t = TreeGeneratorRegistry.getInstance().getTree(filePath);
		if (t != null) {
			json = TreeIoUtils.toJSON(t);
		}
		
		return json;
	}
	
	public static String getDiffAST(String srcContents, String destContents, String extension) throws IOException {
		
		String srcPath = getTempFileFromString(srcContents, extension).getAbsolutePath();
		String destPath = getTempFileFromString(destContents, extension).getAbsolutePath();
		
		return getDiffAST(srcPath, destPath);
	}

	public static String getDiffAST(String srcFilePath, String destFilePath) {
		String json = null;

		if (srcFilePath != null && destFilePath != null) {
			json = captureOutput(srcFilePath, destFilePath);
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
	
	private static File getTempFileFromString(String fileContents, String extension){
		File tmpFile = null;
		
		extension = "." + extension;
		
		try {
			tmpFile = File.createTempFile(UUID.randomUUID().toString(), extension);
			BufferedWriter bw = new BufferedWriter(new FileWriter(tmpFile));
	        bw.write(fileContents);
	        bw.close();
	        //return fromFile(tmpFile.getAbsolutePath());
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		return tmpFile;
	}
	
	public static void main(String[] args) throws IOException {
		//System.out.println(getTreeAST("/home/nicozone/workspace/gumtree/samples/java/Example_v1.java"));
		//System.out.println(getDiffAST("/Users/mihai/work/gumtree/gumtree/samples/java/simple/AnnotationsTestv1.java", "/Users/mihai/work/gumtree/gumtree/samples/java/simple/AnnotationsTestv3.java"));
		//System.out.println(getDiffAST("/Users/mihai/work/gumtree/gumtree/samples/java/oldies/Perm_v0.java", "/Users/mihai/work/gumtree/gumtree/samples/java/oldies/Perm_v1.java"));
		
		//System.out.println(getTreeAST("public class A{Integer f; void foo(){Float f;}}", "java"));
		System.out.println(getDiffAST("public class A{Integer f; void foo(){Float f;}}", "public class A{Integer f; Double g; void foo(){Float foo;}}", "java"));
	}
}
