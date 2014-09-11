package gumtreeFacade;

import fr.labri.gumtree.client.batch.JSONProcessor;

public class AST {
	
	
	public static void generateASTs(String inputDirectory) {
		JSONProcessor jsGenerator = new JSONProcessor(inputDirectory);
		jsGenerator.process();
	}
	
	public static void main(String[] args) {
		generateASTs("/Users/mihai/work/gumtree/gumtreeFacade/src/gumtreeFacade");
	}
}
