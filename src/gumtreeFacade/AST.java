package gumtreeFacade;

import java.io.IOException;

import fr.labri.gumtree.client.TreeGeneratorRegistry;
import fr.labri.gumtree.io.TreeIoUtils;
import fr.labri.gumtree.tree.Tree;

public class AST {
	
	
	public static String getASTinJSON(String filePath) throws IOException {
		String json = null;

		Tree t = TreeGeneratorRegistry.getInstance().getTree(filePath);
		if (t != null) {
			json = TreeIoUtils.toJSON(t);
		}
		
		return json;
	}
	
	public static void main(String[] args) throws IOException {
		System.out.println(getASTinJSON("/Users/mihai/work/gumtree/gumtreeFacade/src/gumtreeFacade/AST.java")); 
	}
}
