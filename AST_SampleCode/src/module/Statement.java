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
	public static final int IF_STATEMENT = 1;
	public static final int PROTECT = 4;
	
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
	
	abstract int getNodeType();

	abstract Statement init(int x, int y);
	
	abstract int getLineSize();

	static void createPropertyList(Class nodeClass, List propertyList) {
		// stuff nodeClass at head of list for future ref
		propertyList.add(nodeClass);
	}
	
	
	static List reapPropertyList(List propertyList) {
		propertyList.remove(0); // remove nodeClass
		// compact
		ArrayList a = new ArrayList(propertyList.size());
		a.addAll(propertyList);
		return Collections.unmodifiableList(a);
	}
	
	
	public final int getStartPosition() {
		return this.startPosition;
	}
	
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
