package module;

public abstract class Statement extends Node {

	private String optionalLeadingComment = null;
	
	Statement(Root ast) {
		super(ast);
	}
	
	int memSize() {
		int size = BASE_NODE_SIZE + 1 * 4 + stringSize(getLeadingComment());
		return size;
	}
	
	public String getLeadingComment() {
		return this.optionalLeadingComment;
	}
}
