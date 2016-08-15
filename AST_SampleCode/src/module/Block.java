package module;


import java.util.ArrayList;
import java.util.List;
//import module.descriptor.*;

public class Block extends Statement {

//	public static final ChildListPropertyDescriptor STATEMENTS_PROPERTY =
//		new ChildListPropertyDescriptor(Block.class, "statements", Statement.class, CYCLE_RISK); //$NON-NLS-1$

	private static final List PROPERTY_DESCRIPTORS;

	
	
	static {
		List properyList = new ArrayList(2);
		createPropertyList(Block.class, properyList);
//		addProperty(STATEMENTS_PROPERTY, properyList);
		PROPERTY_DESCRIPTORS = reapPropertyList(properyList);
	}

	public static List propertyDescriptors(int apiLevel) {
		return PROPERTY_DESCRIPTORS;
	}

//	private Node.NodeList statements =
//		new Node.NodeList(STATEMENTS_PROPERTY);

	Block() {
		super();
	}

	final List internalStructuralPropertiesForType(int apiLevel) {
		return propertyDescriptors(apiLevel);
	}

//	final List internalGetChildListProperty(ChildListPropertyDescriptor property) {
//		if (property == STATEMENTS_PROPERTY) {
//			return statements();
//		}
//		// allow default implementation to flag the error
//		return super.internalGetChildListProperty(property);
//	}

	final int getNodeType0() {
		return BLOCK;
	}

//	Node clone0(Root target) {
//		Block result = new Block(target);
//		result.setSourceRange(getStartPosition(), getLength());
//		result.copyLeadingComment(this);
//		result.statements().addAll(
//			Node.copySubtrees(target, statements()));
//		return result;
//	}

//	final boolean subtreeMatch0(ASTMatcher matcher, Object other) {
//		// dispatch to correct overloaded match method
//		return matcher.match(this, other);
//	}

//	void accept0(ASTVisitor visitor) {
//		boolean visitChildren = visitor.visit(this);
//		if (visitChildren) {
//			acceptChildren(visitor, this.statements);
//		}
//		visitor.endVisit(this);
//	}

//	public List statements() {
//		return this.statements;
//	}

	int memSize() {
		return super.memSize() + 1 * 4;
	}

//	int treeSize() {
//		return memSize() + this.statements.listSize();
//	}
}

