package module;

import java.util.AbstractList;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

import module.descriptor.*;

public abstract class Node {
	// statement
	public static final int BLOCK = 8;
	public static final int IF_STATEMENT = 25;
	public static final int PROTECT = 4;
	
	//
	final Root ast;
	private Node parent = null;
	private StructuralPropertyDescriptor location = null;
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
	
	Node(Root ast) {
		if (ast == null) {
			throw new IllegalArgumentException();
		}

		this.ast = ast;
		setNodeType(getNodeType0());
		setFlags(ast.getDefaultNodeFlag());
		// setFlags calls modifying();
	}
	
	private void setNodeType(int nodeType) {
		int old = this.typeAndFlags & 0xFFFF0000;
		this.typeAndFlags = old | (nodeType << 16);
	}
	
	abstract int getNodeType0();
	
	final void preLazyInit() {
		this.ast.disableEvents();
	}
	
	final void postLazyInit(Node newChild, ChildPropertyDescriptor property) {
		newChild.setParent(this, property);
		this.ast.reenableEvents();
	}

	final void setParent(Node parent, StructuralPropertyDescriptor property) {
		this.ast.modifying();
		this.parent = parent;
		this.location = property;
	}

	static void createPropertyList(Class nodeClass, List propertyList) {
		// stuff nodeClass at head of list for future ref
		propertyList.add(nodeClass);
	}
	
	static void addProperty(StructuralPropertyDescriptor property, List propertyList) {
		Class nodeClass = (Class) propertyList.get(0);
		if (property.getNodeClass() != nodeClass) {
			// easily made cut-and-paste mistake
			throw new RuntimeException("Structural property descriptor has wrong node class!");  //$NON-NLS-1$
		}
		propertyList.add(property);
	}
	
	static List reapPropertyList(List propertyList) {
		propertyList.remove(0); // remove nodeClass
		// compact
		ArrayList a = new ArrayList(propertyList.size());
		a.addAll(propertyList);
		return Collections.unmodifiableList(a);
	}
	
	final void preReplaceChild(Node oldChild, Node newChild, ChildPropertyDescriptor property) {
		if ((this.typeAndFlags & PROTECT) != 0) {
			// this node is protected => cannot gain or lose children
			throw new IllegalArgumentException("AST node cannot be modified"); //$NON-NLS-1$
		}
		if (newChild != null) {
			checkNewChild(this, newChild, property.isCycleRisk(), null);
		}
		// delink old child from parent
		if (oldChild != null) {
			if ((oldChild.typeAndFlags & PROTECT) != 0) {
				// old child node is protected => cannot be unparented
				throw new IllegalArgumentException("AST node cannot be modified"); //$NON-NLS-1$
			}
			if (newChild != null) {
				this.ast.preReplaceChildEvent(this, oldChild, newChild, property);
			} else {
				this.ast.preRemoveChildEvent(this, oldChild, property);
			}
			oldChild.setParent(null, null);
		} else {
			if(newChild != null) {
				this.ast.preAddChildEvent(this, newChild, property);
			}
		}
		// link new child to parent
		if (newChild != null) {
			newChild.setParent(this, property);
			// cannot notify postAddChildEvent until parent is linked to child too
		}
	}
	
	final void postReplaceChild(Node oldChild, Node newChild, ChildPropertyDescriptor property) {
		// link new child to parent
		if (newChild != null) {
			if (oldChild != null) {
				this.ast.postReplaceChildEvent(this, oldChild, newChild, property);
			} else {
				this.ast.postAddChildEvent(this, newChild, property);
			}
		} else {
			this.ast.postRemoveChildEvent(this, oldChild, property);
		}
	}
	
	public final Node getRoot() {
		Node candidate = this;
		while (true) {
			Node p = candidate.getParent();
			if (p == null) {
				// candidate has no parent - that's the guy
				return candidate;
			}
			candidate = p;
		}
	}
	
	public final Node getParent() {
		return this.parent;
	}
	
	static void checkNewChild(Node node, Node newChild,
			boolean cycleCheck, Class nodeType) {
		if (newChild.ast != node.ast) {
			// new child is from a different AST
			throw new IllegalArgumentException();
		}
		if (newChild.getParent() != null) {
			// new child currently has a different parent
			throw new IllegalArgumentException();
		}
		if (cycleCheck && newChild == node.getRoot()) {
			// inserting new child would create a cycle
			throw new IllegalArgumentException();
		}
		Class childClass = newChild.getClass();
		if (nodeType != null && !nodeType.isAssignableFrom(childClass)) {
			// new child is not of the right type
			throw new ClassCastException();
		}
		if ((newChild.typeAndFlags & PROTECT) != 0) {
			// new child node is protected => cannot be parented
			throw new IllegalArgumentException("AST node cannot be modified"); //$NON-NLS-1$
		}
	}
	
	List internalGetChildListProperty(ChildListPropertyDescriptor property) {
		throw new RuntimeException("Node does not have this property");  //$NON-NLS-1$
	}
	
	public final int getStartPosition() {
		return this.startPosition;
	}
	
	public final void setFlags(int flags) {
		this.ast.modifying();
		int old = this.typeAndFlags & 0xFFFF0000;
		this.typeAndFlags = old | (flags & 0xFFFF);
	}

	public final int getLength() {
		return this.length;
	}
	
	abstract int treeSize();
	
	abstract int memSize();
	
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
	
	class NodeList extends AbstractList {

		/**
		 * The underlying list in which the nodes of this list are
		 * stored (element type: <code>Node</code>).
		 * <p>
		 * Be stingy on storage - assume that list will be empty.
		 * </p>
		 * <p>
		 * This field declared default visibility (rather than private)
		 * so that accesses from <code>NodeList.Cursor</code> do not require
		 * a synthetic accessor method.
		 * </p>
		 */
		ArrayList store = new ArrayList(0);

		/**
		 * The property descriptor for this list.
		 */
		ChildListPropertyDescriptor propertyDescriptor;

		/**
		 * A cursor for iterating over the elements of the list.
		 * Does not lose its position if the list is changed during
		 * the iteration.
		 */
		class Cursor implements Iterator {
			/**
			 * The position of the cursor between elements. If the value
			 * is N, then the cursor sits between the element at positions
			 * N-1 and N. Initially just before the first element of the
			 * list.
			 */
			private int position = 0;

			/* (non-Javadoc)
			 * Method declared on <code>Iterator</code>.
			 */
			public boolean hasNext() {
				return this.position < NodeList.this.store.size();
			}

			/* (non-Javadoc)
			 * Method declared on <code>Iterator</code>.
			 */
			public Object next() {
				Object result = NodeList.this.store.get(this.position);
				this.position++;
				return result;
		    }

			/* (non-Javadoc)
			 * Method declared on <code>Iterator</code>.
			 */
			public void remove() {
				throw new UnsupportedOperationException();
			}

			/**
			 * Adjusts this cursor to accomodate an add/remove at the given
			 * index.
			 *
			 * @param index the position at which the element was added
			 *    or removed
			 * @param delta +1 for add, and -1 for remove
			 */
			void update(int index, int delta) {
				if (this.position > index) {
					// the cursor has passed the added or removed element
					this.position += delta;
				}
			}
		}

		/**
		 * A list of currently active cursors (element type:
		 * <code>Cursor</code>), or <code>null</code> if there are no
		 * active cursors.
		 * <p>
		 * It is important for storage considerations to maintain the
		 * null-means-empty invariant; otherwise, every NodeList instance
		 * will waste a lot of space. A cursor is needed only for the duration
		 * of a visit to the child nodes. Under normal circumstances, only a
		 * single cursor is needed; multiple cursors are only required if there
		 * are multiple visits going on at the same time.
		 * </p>
		 */
		private List cursors = null;

		/**
		 * Creates a new empty list of nodes owned by this node.
		 * This node will be the common parent of all nodes added to
		 * this list.
		 *
		 * @param property the property descriptor
		 * @since 3.0
		 */
		NodeList(ChildListPropertyDescriptor property) {
			super();
			this.propertyDescriptor = property;
		}

		/* (non-javadoc)
		 * @see java.util.AbstractCollection#size()
		 */
		public int size() {
			return this.store.size();
		}

		/* (non-javadoc)
		 * @see AbstractList#get(int)
		 */
		public Object get(int index) {
			return this.store.get(index);
		}

		/* (non-javadoc)
		 * @see List#set(int, java.lang.Object)
		 */
		public Object set(int index, Object element) {
		    if (element == null) {
		        throw new IllegalArgumentException();
		    }
			if ((Node.this.typeAndFlags & PROTECT) != 0) {
				// this node is protected => cannot gain or lose children
				throw new IllegalArgumentException("AST node cannot be modified"); //$NON-NLS-1$
			}
			// delink old child from parent, and link new child to parent
			Node newChild = (Node) element;
			Node oldChild = (Node) this.store.get(index);
			if (oldChild == newChild) {
				return oldChild;
			}
			if ((oldChild.typeAndFlags & PROTECT) != 0) {
				// old child is protected => cannot be unparented
				throw new IllegalArgumentException("AST node cannot be modified"); //$NON-NLS-1$
			}
			Node.checkNewChild(Node.this, newChild, this.propertyDescriptor.getCycleRisk(), this.propertyDescriptor.getElementType());
			Node.this.ast.preReplaceChildEvent(Node.this, oldChild, newChild, this.propertyDescriptor);

			Object result = this.store.set(index, newChild);
			// n.b. setParent will call ast.modifying()
			oldChild.setParent(null, null);
			newChild.setParent(Node.this, this.propertyDescriptor);
			Node.this.ast.postReplaceChildEvent(Node.this, oldChild, newChild, this.propertyDescriptor);
			return result;
		}

		/* (non-javadoc)
		 * @see List#add(int, java.lang.Object)
		 */
		public void add(int index, Object element) {
		    if (element == null) {
		        throw new IllegalArgumentException();
		    }
			if ((Node.this.typeAndFlags & PROTECT) != 0) {
				// this node is protected => cannot gain or lose children
				throw new IllegalArgumentException("AST node cannot be modified"); //$NON-NLS-1$
			}
			// link new child to parent
			Node newChild = (Node) element;
			Node.checkNewChild(Node.this, newChild, this.propertyDescriptor.getCycleRisk(), this.propertyDescriptor.getElementType());
			Node.this.ast.preAddChildEvent(Node.this, newChild, this.propertyDescriptor);


			this.store.add(index, element);
			updateCursors(index, +1);
			// n.b. setParent will call ast.modifying()
			newChild.setParent(Node.this, this.propertyDescriptor);
			Node.this.ast.postAddChildEvent(Node.this, newChild, this.propertyDescriptor);
		}

		/* (non-javadoc)
		 * @see List#remove(int)
		 */
		public Object remove(int index) {
			if ((Node.this.typeAndFlags & PROTECT) != 0) {
				// this node is protected => cannot gain or lose children
				throw new IllegalArgumentException("AST node cannot be modified"); //$NON-NLS-1$
			}
			// delink old child from parent
			Node oldChild = (Node) this.store.get(index);
			if ((oldChild.typeAndFlags & PROTECT) != 0) {
				// old child is protected => cannot be unparented
				throw new IllegalArgumentException("AST node cannot be modified"); //$NON-NLS-1$
			}

			Node.this.ast.preRemoveChildEvent(Node.this, oldChild, this.propertyDescriptor);
			// n.b. setParent will call ast.modifying()
			oldChild.setParent(null, null);
			Object result = this.store.remove(index);
			updateCursors(index, -1);
			Node.this.ast.postRemoveChildEvent(Node.this, oldChild, this.propertyDescriptor);
			return result;

		}

		/**
		 * Allocate a cursor to use for a visit. The client must call
		 * <code>releaseCursor</code> when done.
		 * <p>
		 * This method is internally synchronized on this NodeList.
		 * It is thread-safe to create a cursor.
		 * </p>
		 *
		 * @return a new cursor positioned before the first element
		 *    of the list
		 */
		Cursor newCursor() {
			synchronized (this) {
				// serialize cursor management on this NodeList
				if (this.cursors == null) {
					// convert null to empty list
					this.cursors = new ArrayList(1);
				}
				Cursor result = new Cursor();
				this.cursors.add(result);
				return result;
			}
		}

		/**
		 * Releases the given cursor at the end of a visit.
		 * <p>
		 * This method is internally synchronized on this NodeList.
		 * It is thread-safe to release a cursor.
		 * </p>
		 *
		 * @param cursor the cursor
		 */
		void releaseCursor(Cursor cursor) {
			synchronized (this) {
				// serialize cursor management on this NodeList
				this.cursors.remove(cursor);
				if (this.cursors.isEmpty()) {
					// important: convert empty list back to null
					// otherwise the node will hang on to needless junk
					this.cursors = null;
				}
			}
		}

		/**
		 * Adjusts all cursors to accomodate an add/remove at the given
		 * index.
		 * <p>
		 * This method is only used when the list is being modified.
		 * The AST is not thread-safe if any of the clients are modifying it.
		 * </p>
		 *
		 * @param index the position at which the element was added
		 *    or removed
		 * @param delta +1 for add, and -1 for remove
		 */
		private synchronized void updateCursors(int index, int delta) {
			if (this.cursors == null) {
				// there are no cursors to worry about
				return;
			}
			for (Iterator it = this.cursors.iterator(); it.hasNext(); ) {
				Cursor c = (Cursor) it.next();
				c.update(index, delta);
			}
		}

		/**
		 * Returns an estimate of the memory footprint of this node list
		 * instance in bytes.
	     * <ul>
	     * <li>1 object header for the NodeList instance</li>
	     * <li>5 4-byte fields of the NodeList instance</li>
	     * <li>0 for cursors since null unless walk in progress</li>
	     * <li>1 object header for the ArrayList instance</li>
	     * <li>2 4-byte fields of the ArrayList instance</li>
	     * <li>1 object header for an Object[] instance</li>
	     * <li>4 bytes in array for each element</li>
	     * </ul>
	 	 *
		 * @return the size of this node list in bytes
		 */
		int memSize() {
			int result = HEADERS + 5 * 4;
			result += HEADERS + 2 * 4;
			result += HEADERS + 4 * size();
			return result;
		}

		/**
		 * Returns an estimate of the memory footprint in bytes of this node
		 * list and all its subtrees.
		 *
		 * @return the size of this list of subtrees in bytes
		 */
		int listSize() {
			int result = memSize();
			for (Iterator it = iterator(); it.hasNext(); ) {
				Node child = (Node) it.next();
				result += child.treeSize();
			}
			return result;
		}
	}

}
