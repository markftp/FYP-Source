package module;
import module.descriptor.*;

public class IfStatement extends Statement{
	
	private Statement thenStatement = null;
	private Statement optionalElseStatement = null;
	// private Expression expression = null;
	// ChildPropertyDescriptor
	public static final ChildPropertyDescriptor THEN_STATEMENT_PROPERTY =
			new ChildPropertyDescriptor(IfStatement.class, "thenStatement", Statement.class, MANDATORY, CYCLE_RISK);
	public static final ChildPropertyDescriptor ELSE_STATEMENT_PROPERTY =
			new ChildPropertyDescriptor(IfStatement.class, "elseStatement", Statement.class, OPTIONAL, CYCLE_RISK);
	// then
	
	IfStatement(Root ast) {
		super(ast);
	}
	

	final int getNodeType0() {
		return IF_STATEMENT;
	}

	int memSize() {
		return super.memSize() + 3 * 4;
	}

	int treeSize() {
		return
			memSize()
//			+ (this.expression == null ? 0 : getExpression().treeSize())
			+ (this.thenStatement == null ? 0 : getThenStatement().treeSize())
			+ (this.optionalElseStatement == null ? 0 : getElseStatement().treeSize());
	}
	
	public Statement getThenStatement() {
		if (this.thenStatement == null) {
			// lazy init must be thread-safe for readers
			synchronized (this) {
				if (this.thenStatement == null) {
					preLazyInit();
					this.thenStatement = new Block(this.ast);
					postLazyInit(this.thenStatement, THEN_STATEMENT_PROPERTY);
				}
			}
		}
		return this.thenStatement;
	}

	public void setThenStatement(Statement statement) {
		if (statement == null) {
			throw new IllegalArgumentException();
		}
		Node oldChild = this.thenStatement;
		preReplaceChild(oldChild, statement, THEN_STATEMENT_PROPERTY);
		this.thenStatement = statement;
		postReplaceChild(oldChild, statement, THEN_STATEMENT_PROPERTY);
	}

	// else
	public Statement getElseStatement() {
		return this.optionalElseStatement;
	}
	
	public void setElseStatement(Statement statement) {
		Node oldChild = this.optionalElseStatement;
		preReplaceChild(oldChild, statement, ELSE_STATEMENT_PROPERTY);
		this.optionalElseStatement = statement;
		postReplaceChild(oldChild, statement, ELSE_STATEMENT_PROPERTY);
	}
}
