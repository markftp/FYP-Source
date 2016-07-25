package module;
import java.io.IOException;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
 
import org.eclipse.jdt.core.dom.AST;
import org.eclipse.jdt.core.dom.ASTNode;
import org.eclipse.jdt.core.dom.ASTParser;
import org.eclipse.jdt.core.dom.ASTVisitor;
import org.eclipse.jdt.core.dom.ChildListPropertyDescriptor;
import org.eclipse.jdt.core.dom.ChildPropertyDescriptor;
import org.eclipse.jdt.core.dom.CompilationUnit;
import org.eclipse.jdt.core.dom.SimpleName;
import org.eclipse.jdt.core.dom.SimplePropertyDescriptor;
import org.eclipse.jdt.core.dom.StructuralPropertyDescriptor;
import org.eclipse.jdt.core.dom.VariableDeclarationFragment;

import bean.Statement;
 
public class StatementTreeView {
	
	// create AST use AST parser
	public CompilationUnit parse(String str) {
		ASTParser parser = ASTParser.newParser(AST.JLS3);
		parser.setKind(ASTParser.K_COMPILATION_UNIT);
		parser.setSource(str.toCharArray()); // set source
		parser.setResolveBindings(true); // we need bindings later on
		
		final CompilationUnit cu = (CompilationUnit) parser.createAST(null);
		 
		cu.accept(new ASTVisitor() {
			Set names = new HashSet();
			public boolean visit(VariableDeclarationFragment node) {
				SimpleName name = node.getName();
				this.names.add(name.getIdentifier());
			//	System.out.println("Declaration of '"+name+"' at line"+cu.getLineNumber(name.getStartPosition()));
				return false; // do not continue to avoid usage info
			}
 
			public boolean visit(SimpleName node) {
				if (this.names.contains(node.getIdentifier())) {
			//	System.out.println("Usage of '" + node + "' at line " +	cu.getLineNumber(node.getStartPosition()));
				}
				return true;
			}
		});
		return cu;
	}
 
	
	private void toString(ASTNode node) {
	    List properties = node.structuralPropertiesForType();
	    for (Iterator iterator = properties.iterator(); iterator.hasNext();) {
	        Object descriptor = iterator.next();
	        if (descriptor instanceof SimplePropertyDescriptor) {
	            SimplePropertyDescriptor simple = (SimplePropertyDescriptor) descriptor;
	            Object value = node.getStructuralProperty(simple);
	            System.out.println(simple.getId() + " (" + value.toString() + ")");
	        } else if (descriptor instanceof ChildPropertyDescriptor) {
	        	 ChildPropertyDescriptor child = (ChildPropertyDescriptor) descriptor;
		            ASTNode childNode = (ASTNode) node.getStructuralProperty(child);
		            if (childNode != null) {
		                System.out.println("Child (" + child.getId() + ") {");
		                toString(childNode);
		                System.out.println("}");
		            }
	        } else {
	            ChildListPropertyDescriptor list = (ChildListPropertyDescriptor) descriptor;
	            System.out.println("List (" + list.getId() + "){");
	            	print((List) node.getStructuralProperty(list));
	            System.out.println("}");
	        }
	    }
	}

	private void print(List nodes) {
		for (Iterator iterator = nodes.iterator(); iterator.hasNext();) {
	    	toString((ASTNode) iterator.next());
	    }
	}
	
	public static void main(String[] args) throws IOException {
		StatementTreeView stv = new StatementTreeView();
		// sample for set if statement to AST
		String ifMethod = "public class If { String header = \"if\"; String desc = \"If to specify a block of code to be executed, if a specified condition is true\";  desc= \"If\"; " +
				"	public String getHeader() { return header;}"+
				"public void setHeader(String header) { this.header = header;}}";
		// create if statement AST
		ASTNode astNode = stv.parse(ifMethod);
		AST ast = astNode.getAST();
		System.out.println("AST API Level: "+ ast.apiLevel());
		System.out.println("Print AST: ");
		// print AST
		stv.toString(astNode);
		// update desc
		System.out.println();
		astNode.setStructuralProperty((StructuralPropertyDescriptor) astNode.getProperty("desc"), "test");
		astNode.properties();
		
		
	}
}