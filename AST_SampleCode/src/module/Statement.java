package module;

import java.util.AbstractList;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;


public abstract class Statement {

	private String optionalLeadingComment = null;
	
	Statement() {
		
	}
		
	int memSize() {
		int size = BASE_NODE_SIZE + 1 * 4 + stringSize(getLeadingComment());
		return size;
	}
	
	
	public static final int BLOCK = 8;
	public static final int IF_STATEMENT = 25;
	public static final int PROTECT = 4;
	
	//
//	private StructuralPropertyDescriptor location = null;
	// field setting
	static final boolean CYCLE_RISK = true;
	static final boolean NO_CYCLE_RISK = false;
	static final boolean MANDATORY = true;
	static final boolean OPTIONAL = false;
	// flag
	int typeAndFlags = 0;
	private int startPosition = -1;
	private int length = 0;
	
	static final int HEADERS = 12;

	static final int BASE_NODE_SIZE = HEADERS + 7 * 4;
	
	public String getLeadingComment() {
		return this.optionalLeadingComment;
	}
	
	private void setNodeType(int nodeType) {
		int old = this.typeAndFlags & 0xFFFF0000;
		this.typeAndFlags = old | (nodeType << 16);
	}
	
	abstract int getNodeType0();
	
//	final void preLazyInit() {
//		this.ast.disableEvents();
//	}
//	
//	final void postLazyInit(Node newChild, ChildPropertyDescriptor property) {
//		newChild.setParent(this, property);
//		this.ast.reenableEvents();
//	}
//
//	final void setParent(Node parent, StructuralPropertyDescriptor property) {
//		this.ast.modifying();
//		this.parent = parent;
//		this.location = property;
//	}

	static void createPropertyList(Class nodeClass, List propertyList) {
		// stuff nodeClass at head of list for future ref
		propertyList.add(nodeClass);
	}
	
//	static void addProperty(StructuralPropertyDescriptor property, List propertyList) {
//		Class nodeClass = (Class) propertyList.get(0);
//		if (property.getNodeClass() != nodeClass) {
//			// easily made cut-and-paste mistake
//			throw new RuntimeException("Structural property descriptor has wrong node class!");  //$NON-NLS-1$
//		}
//		propertyList.add(property);
//	}
	
	static List reapPropertyList(List propertyList) {
		propertyList.remove(0); // remove nodeClass
		// compact
		ArrayList a = new ArrayList(propertyList.size());
		a.addAll(propertyList);
		return Collections.unmodifiableList(a);
	}
	
//	final void preReplaceChild(Node oldChild, Node newChild, ChildPropertyDescriptor property) {
//		if ((this.typeAndFlags & PROTECT) != 0) {
//			// this node is protected => cannot gain or lose children
//			throw new IllegalArgumentException("AST node cannot be modified"); //$NON-NLS-1$
//		}
//		if (newChild != null) {
//			checkNewChild(this, newChild, property.isCycleRisk(), null);
//		}
//		// delink old child from parent
//		if (oldChild != null) {
//			if ((oldChild.typeAndFlags & PROTECT) != 0) {
//				// old child node is protected => cannot be unparented
//				throw new IllegalArgumentException("AST node cannot be modified"); //$NON-NLS-1$
//			}
//			if (newChild != null) {
//				this.ast.preReplaceChildEvent(this, oldChild, newChild, property);
//			} else {
//				this.ast.preRemoveChildEvent(this, oldChild, property);
//			}
//			oldChild.setParent(null, null);
//		} else {
//			if(newChild != null) {
//				this.ast.preAddChildEvent(this, newChild, property);
//			}
//		}
//		// link new child to parent
//		if (newChild != null) {
//			newChild.setParent(this, property);
//			// cannot notify postAddChildEvent until parent is linked to child too
//		}
//	}
//	
//	final void postReplaceChild(Node oldChild, Node newChild, ChildPropertyDescriptor property) {
//		// link new child to parent
//		if (newChild != null) {
//			if (oldChild != null) {
//				this.ast.postReplaceChildEvent(this, oldChild, newChild, property);
//			} else {
//				this.ast.postAddChildEvent(this, newChild, property);
//			}
//		} else {
//			this.ast.postRemoveChildEvent(this, oldChild, property);
//		}
//	}
//	
//	public final Node getRoot() {
//		Node candidate = this;
//		while (true) {
//			Node p = candidate.getParent();
//			if (p == null) {
//				// candidate has no parent - that's the guy
//				return candidate;
//			}
//			candidate = p;
//		}
//	}
//	
//	public final Node getParent() {
//		return this.parent;
//	}
//	
//	static void checkNewChild(Node node, Node newChild,
//			boolean cycleCheck, Class nodeType) {
//		if (newChild.ast != node.ast) {
//			// new child is from a different AST
//			throw new IllegalArgumentException();
//		}
//		if (newChild.getParent() != null) {
//			// new child currently has a different parent
//			throw new IllegalArgumentException();
//		}
//		if (cycleCheck && newChild == node.getRoot()) {
//			// inserting new child would create a cycle
//			throw new IllegalArgumentException();
//		}
//		Class childClass = newChild.getClass();
//		if (nodeType != null && !nodeType.isAssignableFrom(childClass)) {
//			// new child is not of the right type
//			throw new ClassCastException();
//		}
//		if ((newChild.typeAndFlags & PROTECT) != 0) {
//			// new child node is protected => cannot be parented
//			throw new IllegalArgumentException("AST node cannot be modified"); //$NON-NLS-1$
//		}
//	}
//	
//	List internalGetChildListProperty(ChildListPropertyDescriptor property) {
//		throw new RuntimeException("Node does not have this property");  //$NON-NLS-1$
//	}
	
	public final int getStartPosition() {
		return this.startPosition;
	}
	
//	public final void setFlags(int flags) {
//		this.ast.modifying();
//		int old = this.typeAndFlags & 0xFFFF0000;
//		this.typeAndFlags = old | (flags & 0xFFFF);
//	}

	public final int getLength() {
		return this.length;
	}
	
	static int stringSize(String string) {
		int size = 0;
		if (string != null) {
			// Strings usually have 4 instance fields, one of which is a char[]
			size += HEADERS + 4 * 4;
			// char[] has 2 bytes per character
			size += HEADERS + 2 * string.length();
		}
		return size;
	}
	
}
