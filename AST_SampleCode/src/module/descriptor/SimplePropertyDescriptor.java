package module.descriptor;

import module.*;

public final class SimplePropertyDescriptor extends StructuralPropertyDescriptor {

	private final Class valueType;
	
	private final boolean mandatory;
	
	SimplePropertyDescriptor(Class nodeClass, String propertyId, Class valueType, boolean mandatory) {
		super(nodeClass, propertyId);
		if (valueType == null || Node.class.isAssignableFrom(valueType)) {
			throw new IllegalArgumentException();
		}
		this.valueType = valueType;
		this.mandatory = mandatory;
	}
	
	public Class getValueType() {
		return this.valueType;
	}

	public boolean isMandatory() {
		return this.mandatory;
	}
}
