package module;

import java.util.ArrayList;
import java.util.List;

import javax.swing.JLabel;

import view.Line;

public class Block extends Statement {

	private static final List PROPERTY_DESCRIPTORS;

	private int lineSize = 1;

	private String input = "";

	private JLabel label;

	static {
		List properyList = new ArrayList(2);
		createPropertyList(Block.class, properyList);
		PROPERTY_DESCRIPTORS = reapPropertyList(properyList);
	}

	public static List propertyDescriptors(int apiLevel) {
		return PROPERTY_DESCRIPTORS;
	}

	public Block(String input, int x, int y) {
		super();
		this.input = input;
		if (label == null) {
			label = new JLabel(input);
			label.setBounds(x + 5, y + 5, 100 + input.length(),
					10 + input.length());
		} else {
			label.setText(input);
		}
	}

	public Block() {
		super();
	}

	final List internalStructuralPropertiesForType(int apiLevel) {
		return propertyDescriptors(apiLevel);
	}

	public final int getNodeType() {
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

	public JLabel getLabel() {
		return label;
	}

	public void setLabel(JLabel label) {
		this.label = label;
	}

	@Override
	int getWidthLineSize() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	int getHighLineSize() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public boolean addIfChecking(Statement tempStatement, int x, int y,
			boolean createStatus) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean addBlockChecking(Statement ifStatement, int x, int y,
			boolean createStatus) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void setEnd(boolean b) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public int getInitialX() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int getInitialY() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int getTotalSize() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public boolean addWhileLoopChecking(Statement tempStatement, int x, int y,
			boolean createStatus) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	Statement init(int x, int y, boolean status) {
		// TODO Auto-generated method stub
		return null;
	}

}
