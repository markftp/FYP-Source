package module.descriptor;
import module.*;

public abstract class StructuralPropertyDescriptor {

	/**
	 * Property id.
	 */
	private final String propertyId;

	/**
	 * The concrete AST node type that owns this property.
	 */
	private final Class nodeClass;

	/**
	 * Creates a new property descriptor for the given node type
	 * with the given property id.
	 * Note that this constructor is declared package-private so that
	 * property descriptors can only be created by the AST
	 * implementation.
	 *
	 * @param nodeClass concrete AST node type that owns this property
	 * @param propertyId the property id
	 */
	StructuralPropertyDescriptor(Class nodeClass, String propertyId) {
		if (nodeClass == null || propertyId == null) {
			throw new IllegalArgumentException();
		}
		this.propertyId = propertyId;
		this.nodeClass = nodeClass;
	}

	/**
	 * Returns the id of this property.
	 *
	 * @return the property id
	 */
	public final String getId() {
		return this.propertyId;
	}

	/**
	 * Returns the AST node type that owns this property.
	 * <p>
	 * For example, for all properties of the node type
	 * TypeDeclaration, this method returns <code>TypeDeclaration.class</code>.
	 * </p>
	 *
	 * @return the node type that owns this property
	 */
	public final Class getNodeClass() {
		return this.nodeClass;
	}

	/**
	 * Returns whether this property is a simple property
	 * (instance of {@link SimplePropertyDescriptor}.
	 *
	 * @return <code>true</code> if this is a simple property, and
	 * <code>false</code> otherwise
	 */
	public final boolean isSimpleProperty(){
		return (this instanceof SimplePropertyDescriptor);
	}

	/**
	 * Returns whether this property is a child property
	 * (instance of {@link ChildPropertyDescriptor}.
	 *
	 * @return <code>true</code> if this is a child property, and
	 * <code>false</code> otherwise
	 */
	public final boolean isChildProperty() {
		return (this instanceof ChildPropertyDescriptor);
	}

	/**
	 * Returns whether this property is a child list property
	 * (instance of {@link ChildListPropertyDescriptor}.
	 *
	 * @return <code>true</code> if this is a child list property, and
	 * <code>false</code> otherwise
	 */
	public final boolean isChildListProperty() {
		return (this instanceof ChildListPropertyDescriptor);
	}

	/**
	 * Returns a string suitable for debug purposes.
	 * @return {@inheritDoc}
	 */
	public String toString() {
		StringBuffer b = new StringBuffer();
		if (isChildListProperty()) {
			b.append("ChildList"); //$NON-NLS-1$
		}
		if (isChildProperty()) {
			b.append("Child"); //$NON-NLS-1$
		}
		if (isSimpleProperty()) {
			b.append("Simple"); //$NON-NLS-1$
		}
		b.append("Property["); //$NON-NLS-1$
		if (this.nodeClass != null) {
			b.append(this.nodeClass.getName());
		}
		b.append(","); //$NON-NLS-1$
		if (this.propertyId != null) {
			b.append(this.propertyId);
		}
		b.append("]"); //$NON-NLS-1$
		return b.toString();
	}
}
