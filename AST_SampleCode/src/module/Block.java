package module;

import java.util.ArrayList;
import java.util.List;

public class Block extends Statement {

	private static final List PROPERTY_DESCRIPTORS;

	private int lineSize = 1;
	
	static {
		List properyList = new ArrayList(2);
		createPropertyList(Block.class, properyList);
		PROPERTY_DESCRIPTORS = reapPropertyList(properyList);
	}

	public static List propertyDescriptors(int apiLevel) {
		return PROPERTY_DESCRIPTORS;
	}

	Block() {
		super();
	}

	final List internalStructuralPropertiesForType(int apiLevel) {
		return propertyDescriptors(apiLevel);
	}

	final int getNodeType() {
		return BLOCK;
	}

	@Override
	Statement init(int x, int y) {
		return this;
	}

	public int getLineSize() {
		return lineSize;
	}

	public void setLineSize(int lineSize) {
		this.lineSize = lineSize;
	}

}
