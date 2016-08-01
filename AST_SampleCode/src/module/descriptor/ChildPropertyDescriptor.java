package module.descriptor;
import module.*;

public final class ChildPropertyDescriptor extends StructuralPropertyDescriptor {

	private final Class childClass;

	private final boolean mandatory;

	private final boolean cycleRisk;

	public ChildPropertyDescriptor(Class nodeClass, String propertyId, Class childType, boolean mandatory, boolean cycleRisk) {
		super(nodeClass, propertyId);
		if (childType == null || !Node.class.isAssignableFrom(childType)) {
			throw new IllegalArgumentException();
		}
		this.childClass = childType;
		this.mandatory = mandatory;
		this.cycleRisk = cycleRisk;
	}

	public final Class getChildType() {
		return this.childClass;
	}
	
	public final boolean isMandatory() {
		return this.mandatory;
	}
	
	public final boolean cycleRisk() {
		return this.isCycleRisk();
	}

	public boolean isCycleRisk() {
		return cycleRisk;
	}
}
