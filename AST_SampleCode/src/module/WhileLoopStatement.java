package module;

import java.util.ArrayList;

import javax.swing.JOptionPane;

import view.Line;
import view.bean.DiamondShape;
import view.bean.RectangleShape;
import view.bean.Shape;

public class WhileLoopStatement extends Statement {

	private Statement ifThenStatement = null;
	private Statement loopStatement = null;

	private ArrayList<Line> lineList = new ArrayList();
	private ArrayList<Shape> shapeList = new ArrayList();

	private int initialX = 0;
	private int initialY = 0;
	// Diamond
	private int ifRulex = 0;
	private int ifRuley = 0;
	// Rectangle 30 X 20
	private int ifResultx = 0;
	private int ifResulty = 0;

	private int totalSize = 120;

	private boolean isStart = false;
	private boolean isEnd = false;

	private int defRecX1 = -10;
	private int defRecY1 = 50;
	private int defRecX2 = 35;
	private int defRecY2 = 20;

	private int widthLineSize = 1;
	private int highLineSize = 1;
	private boolean right = true;

	public WhileLoopStatement() {
		super();
	}

	public WhileLoopStatement(int x, int y, boolean status) {
		super();
		buildDiagram(x, y, -1, -1, defRecX1, defRecY1, defRecX2, defRecY2,
				status);
	}

	public Statement init(int x, int y, boolean status) {
		buildDiagramWithoutLine(x, y, -1, -1, defRecX1, defRecY1, defRecX2, defRecY2,
				status);
		return (Statement) this;
	}

	public Statement init(int x, int y) {
		buildDiagram(x, y, -1, -1, defRecX1, defRecY1, defRecX2, defRecY2, true);
		return (Statement) this;
	}

	// public void rebuild(int x, int y) {
	// buildDiagram(x,y);
	// }

	public final int getNodeType() {
		return WHILE_LOOP_STATEMENT;
	}

	int memSize() {
		return super.memSize();
	}

	public Statement getThenStatement() {
		if (this.ifThenStatement == null) {
			// lazy init must be thread-safe for readers
			synchronized (this) {
				if (this.ifThenStatement == null) {
					this.ifThenStatement = new Block();
				}
			}
		}
		return this.ifThenStatement;
	}

	public void setIfThenStatement(Statement statement) {
		this.ifThenStatement = statement;
	}

	// Result
	public Statement getLoopStatement() {
		return this.loopStatement;
	}

	public void setLoopStatement(Statement loopStatement) {
		if (loopStatement.getNodeType() == loopStatement.IF_STATEMENT) {
			totalSize += ((WhileLoopStatement) loopStatement).getTotalSize();
			lineList = new ArrayList();
			shapeList = new ArrayList();
			shapeList.add(new DiamondShape(initialX + 30, initialY + 30,
					initialX - 10, initialY));
			ifRulex = initialX + 30;
			ifRuley = initialY + 30;
			lineList.add(new Line(initialX + 10, initialY + 30, initialX + 10,
					initialY + 50));
			buildArrow(initialX + 10, initialY + 50, 3);

			widthLineSize = loopStatement.getWidthLineSize() + widthLineSize;
			highLineSize = loopStatement.getHighLineSize() + highLineSize;
			lineList.add(new Line(initialX + 30, initialY + 15, initialX + 50
					* widthLineSize, initialY + 15));
			lineList.add(new Line(initialX + 50 * widthLineSize, initialY + 15,
					initialX + 50 * widthLineSize, initialY + 100
							* highLineSize));
			lineList.add(new Line(initialX + 50 * widthLineSize, initialY + 100
					* highLineSize, initialX + 10, initialY + 100
					* highLineSize));
			buildArrow(initialX + 10, initialY + 100 * highLineSize, 2);
			lineList.add(new Line(initialX + 10, initialY + 80 * highLineSize,
					initialX + 10, initialY + 120 * widthLineSize));
			// shapeList.add(new RectangleShape(initialX - 10, initialY + 50,
			// initialX + 30, initialY + 20));
			ifResulty = initialY + 50;
			this.loopStatement = (WhileLoopStatement) loopStatement.init(
					initialX, ifResulty);

		} else if (loopStatement.getNodeType() == loopStatement.WHILE_LOOP_STATEMENT) {
			totalSize += ((WhileLoopStatement) loopStatement).getTotalSize() - 70;
			lineList = new ArrayList();
			shapeList = new ArrayList();
			shapeList.add(new DiamondShape(initialX + 30, initialY + 30,
					initialX - 10, initialY));
			ifRulex = initialX + 30;
			ifRuley = initialY + 30;
			lineList.add(new Line(initialX + 10, initialY + 30, initialX + 10,
					initialY + 50));
			System.out.println("---------");
			buildArrow(initialX + 10, initialY + 30, 1);

			widthLineSize = loopStatement.getWidthLineSize() + widthLineSize;
			highLineSize = loopStatement.getHighLineSize() + highLineSize;

			lineList.add(new Line(initialX + 30, initialY + 15, initialX + 50,
					initialY + 15));
			lineList.add(new Line(initialX + 50, initialY + 15, initialX + 50,
					initialY + 65));
			lineList.add(new Line(initialX + 50, initialY + 65, initialX + 30,
					initialY + 65, true));
			buildArrow(initialX + 30, initialY + 65, 2);
			
			lineList.add(new Line(initialX - 10, initialY + 15, initialX - 30 * widthLineSize,
					initialY + 15));
			lineList.add(new Line(initialX - 30  * widthLineSize, initialY + 15, initialX - 30  * widthLineSize,
					initialY + 75 * highLineSize));
			lineList.add(new Line(initialX - 30   * widthLineSize, initialY + 75 * highLineSize,
					initialX + 10, initialY + 75 * highLineSize));

//			ifResultx = x - 10;
//			ifResulty = y + 50;
//			lineList.add(new Line(initialX + 10, initialY + 100, x + 10, initialY + 120));
			
//			lineList.add(new Line(initialX + 10, initialY + 80 * highLineSize,
//					initialX + 10, initialY + 120 * widthLineSize));
			// shapeList.add(new RectangleShape(initialX - 10, initialY + 50,
			// initialX + 30, initialY + 20));
			ifResulty = initialY + 50;
			if (right)
				this.loopStatement = (WhileLoopStatement) loopStatement.init(
						initialX, ifResulty, false);
			else
				this.loopStatement = (WhileLoopStatement) loopStatement.init(
						initialX, ifResulty, true);

		} else if (loopStatement.getNodeType() == loopStatement.BLOCK) {
			this.loopStatement = loopStatement;
			int LabelSize = ((Block) loopStatement).getLabel().getText()
					.length();
			int result = LabelSize / 4;
			widthLineSize = result;
			buildDiagram(initialX, initialY, widthLineSize, highLineSize,
					defRecX1, defRecY1, defRecX2 * result, defRecY2, right);

		}
	}

	public ArrayList<Shape> getShapeList() {
		return shapeList;
	}

	public void setShapeList(ArrayList<Shape> shapeList) {
		this.shapeList = shapeList;
	}

	public ArrayList<Line> getLineList() {
		return lineList;
	}

	public void setLineList(ArrayList<Line> lineList) {
		this.lineList = lineList;
	}

	private void buildArrow(int x, int y, int status) {
		if (status == 1) {
			lineList.add(new Line(x, y, x - 6, y + 6));
			lineList.add(new Line(x, y, x + 6, y + 6));
		}
		if (status == 2) {
			lineList.add(new Line(x, y, x + 6, y - 6));
			lineList.add(new Line(x, y, x + 6, y + 6));
		}
		if (status == 3) {
			lineList.add(new Line(x, y, x - 6, y - 6));
			lineList.add(new Line(x, y, x + 6, y - 6));
		}
		if (status == 4) {
			lineList.add(new Line(x, y, x - 6, y + 6));
			lineList.add(new Line(x, y, x - 6, y - 6));
		}
	}

	public int getTotalSize() {
		return totalSize;
	}

	public boolean getEnd() {
		return isEnd;
	}

	public void setEnd(boolean isEnd) {
		this.isEnd = isEnd;
		if (loopStatement != null && !isEnd)
			loopStatement.setEnd(isEnd);
	}

	public boolean getStart() {
		return isStart;
	}

	public void setStart(boolean isStart) {
		this.isStart = isStart;
	}

	public void buildDiagram(int x, int y, int widthLineSize, int highLineSize,
			int RecX1, int RecY1, int RecX2, int RecY2, boolean right) {
		this.right = right;
		System.out.println("~buildDiagram~");
		shapeList = new ArrayList();
		lineList = new ArrayList();
		initialX = x;
		initialY = y;
		shapeList.add(new DiamondShape(x + 30, y + 30, x - 10, y));
		ifRulex = x + 30;
		ifRuley = y + 30;
		lineList.add(new Line(x + 10, y + 30, x + 10, y + 50));
		buildArrow(x + 10, y + 30, 1);
		// lineList.add(new Line(x + 30, y + 15, x + 50, y + 15));
		// lineList.add(new Line(x + 50, y + 15, x + 50, y + 100));
		// lineList.add(new Line(x + 50, y + 100, x + 10, y + 100));
		// buildArrow(x + 10, y + 100, 2);
		// lineSize = loopStatement.getLineSize()+lineSize;
		if (highLineSize == -1)
			highLineSize = this.highLineSize;
		if (widthLineSize == -1)
			widthLineSize = this.widthLineSize;
		//

		if (!right) {
			lineList.add(new Line(initialX + 30, initialY + 15, initialX + 50
					* widthLineSize, initialY + 15));
			lineList.add(new Line(initialX + 50 * widthLineSize, initialY + 15,
					initialX + 50 * widthLineSize, initialY + 65 * highLineSize));
			lineList.add(new Line(initialX + 50 * widthLineSize, initialY + 65,
					initialX + 35 * widthLineSize, initialY + 65, true));
			buildArrow(initialX + 35 * widthLineSize, initialY + 65, 2);
		} else {
			lineList.add(new Line(initialX + 30, initialY + 15, initialX + 50
					* widthLineSize, initialY + 15));
			lineList.add(new Line(initialX + 50 * widthLineSize, initialY + 15,
					initialX + 50 * widthLineSize, initialY + 65 * highLineSize));
			lineList.add(new Line(initialX + 50 * widthLineSize, initialY + 65,
					initialX + 35 * widthLineSize, initialY + 65, true));
			buildArrow(initialX + 35 * widthLineSize, initialY + 65, 2);
		}

		//
		shapeList.add(new RectangleShape(x + RecX1, y + RecY1, x + RecX2, y
				+ RecY2));

		lineList.add(new Line(initialX - 10, initialY + 15, initialX - 30,
				initialY + 15));
		lineList.add(new Line(initialX - 30, initialY + 15, initialX - 30,
				initialY + 100 * highLineSize));
		lineList.add(new Line(initialX - 30, initialY + 100 * highLineSize,
				initialX + 10, initialY + 100 * highLineSize));

		ifResultx = x - 10;
		ifResulty = y + 50;
		lineList.add(new Line(x + 10, y + 100, x + 10, y + 120));
	}

	public void buildDiagramWithoutLine(int x, int y, int widthLineSize, int highLineSize,
			int RecX1, int RecY1, int RecX2, int RecY2, boolean right) {
		this.right = right;
		System.out.println("~buildDiagram~");
		shapeList = new ArrayList();
		lineList = new ArrayList();
		initialX = x;
		initialY = y;
		shapeList.add(new DiamondShape(x + 30, y + 30, x - 10, y));
		ifRulex = x + 30;
		ifRuley = y + 30;
		lineList.add(new Line(x + 10, y + 30, x + 10, y + 50));
		buildArrow(x + 10, y + 30, 1);
		// lineList.add(new Line(x + 30, y + 15, x + 50, y + 15));
		// lineList.add(new Line(x + 50, y + 15, x + 50, y + 100));
		// lineList.add(new Line(x + 50, y + 100, x + 10, y + 100));
		// buildArrow(x + 10, y + 100, 2);
		// lineSize = loopStatement.getLineSize()+lineSize;
		if (highLineSize == -1)
			highLineSize = this.highLineSize;
		if (widthLineSize == -1)
			widthLineSize = this.widthLineSize;
		//

		if (!right) {
			lineList.add(new Line(initialX - 10, initialY + 15, initialX - 30
					, initialY + 15));
			lineList.add(new Line(initialX - 30, initialY + 15,
					initialX -30 , initialY + 65 * highLineSize));
			lineList.add(new Line(initialX -30 , initialY + 65 * highLineSize,
					initialX - 10 , initialY + 65, true));
			buildArrow(initialX - 10 , initialY + 65, 4);
		} else {
			lineList.add(new Line(initialX + 30, initialY + 15, initialX + 50
					* widthLineSize, initialY + 15));
			lineList.add(new Line(initialX + 50 * widthLineSize, initialY + 15,
					initialX + 50 * widthLineSize, initialY + 65 * highLineSize));
			lineList.add(new Line(initialX + 50 * widthLineSize, initialY + 65,
					initialX + 35 * widthLineSize, initialY + 65, true));
			buildArrow(initialX + 35 * widthLineSize, initialY + 65, 2);
		}

		//
		shapeList.add(new RectangleShape(x + RecX1, y + RecY1, x + RecX2, y
				+ RecY2));

//		lineList.add(new Line(initialX - 10, initialY + 15, initialX - 30,
//				initialY + 15));
//		lineList.add(new Line(initialX - 30, initialY + 15, initialX - 30,
//				initialY + 100 * highLineSize));
//		lineList.add(new Line(initialX - 30, initialY + 100 * highLineSize,
//				initialX + 10, initialY + 100 * highLineSize));

		ifResultx = x - 10;
		ifResulty = y + 50;
		lineList.add(new Line(x + 10, y + 100, x + 10, y + 120));
	}
	
	public void buildDiagramWithoutShape(int x, int y, int widthLineSize,
			int highLineSize, int size) {
		System.out.println("~buildDiagramWithoutShape~");
		shapeList = new ArrayList();
		lineList = new ArrayList();
		initialX = x;
		initialY = y;
		shapeList.add(new DiamondShape(x + 30, y + 30, x - 10, y));
		ifRulex = x + 30;
		ifRuley = y + 30;
		lineList.add(new Line(x + 10, y + 30, x + 10, y + 50));
		buildArrow(x + 10, y + 50, 3);
		// lineList.add(new Line(x + 30, y + 15, x + 50, y + 15));
		// lineList.add(new Line(x + 50, y + 15, x + 50, y + 100));
		// lineList.add(new Line(x + 50, y + 100, x + 10, y + 100));
		// buildArrow(x + 10, y + 100, 2);
		// lineSize = loopStatement.getLineSize()+lineSize;
		if (highLineSize == -1)
			highLineSize = this.highLineSize;
		if (widthLineSize == -1)
			widthLineSize = this.widthLineSize;
		
		if (!right) {
			lineList.add(new Line(initialX - 10, initialY + 15, initialX - 30
					, initialY + 15));
			lineList.add(new Line(initialX - 30, initialY + 15,
					initialX -30 , initialY + 65 * highLineSize));
			lineList.add(new Line(initialX -30 , initialY + 65 * highLineSize,
					initialX - 10 , initialY + 65, true));
			buildArrow(initialX - 10 , initialY + 65, 4);
		} else {
			lineList.add(new Line(initialX + 30, initialY + 15, initialX + 50
					* widthLineSize, initialY + 15));
			lineList.add(new Line(initialX + 50 * widthLineSize, initialY + 15,
					initialX + 50 * widthLineSize, initialY + 65 * highLineSize));
			lineList.add(new Line(initialX + 50 * widthLineSize, initialY + 65,
					initialX + 35 * widthLineSize, initialY + 65, true));
			buildArrow(initialX + 35 * widthLineSize, initialY + 65, 2);
		}
		// lineList.add(new Line(initialX + 50 * widthLineSize, initialY + 65
		// * highLineSize, initialX + 35, initialY + 65 * highLineSize));
		buildArrow(initialX + 35, initialY + 65 * highLineSize, 2);
		// shapeList.add(new RectangleShape(x + RecX1, y + RecY1, x + RecX2, y
		// + RecY2));

		lineList.add(new Line(initialX - 10, initialY + 15, initialX - 30,
				initialY + 15));
		lineList.add(new Line(initialX - 30, initialY + 15, initialX - 30,
				initialY + 100 * highLineSize));
		lineList.add(new Line(initialX - 30, initialY + 100 * highLineSize,
				initialX + 10, initialY + 100 * highLineSize));

		totalSize += size;
	}

	public int getWidthLineSize() {
		return widthLineSize;
	}

	public void setWidthLineSize(int widthLineSize) {
		this.widthLineSize = widthLineSize;
	}

	public int getInitialX() {
		return initialX;
	}

	public int getInitialY() {
		return initialY;
	}

	public int getDefRecX1() {
		return defRecX1;
	}

	public void setDefRecX1(int defRecX1) {
		this.defRecX1 = defRecX1;
	}

	public int getDefRecX2() {
		return defRecX2;
	}

	public void setDefRecX2(int defRecX2) {
		this.defRecX2 = defRecX2;
	}

	public int getDefRecY1() {
		return defRecY1;
	}

	public void setDefRecY1(int defRecY1) {
		this.defRecY1 = defRecY1;
	}

	public int getDefRecY2() {
		return defRecY2;
	}

	public void setDefRecY2(int defRecY2) {
		this.defRecY2 = defRecY2;
	}

	public int getHighLineSize() {
		return highLineSize;
	}

	public void setHighLineSize(int highLineSize) {
		this.highLineSize = highLineSize;
	}

	public boolean addWhileLoopChecking(Statement tempStatement, int x, int y,
			boolean createStatus) {
		WhileLoopStatement ifStatement = (WhileLoopStatement) tempStatement;
		ArrayList<Shape> shapeList = ifStatement.getShapeList();
		// refersh diagram
		if (createStatus) {
			ifStatement
					.buildDiagram(
							ifStatement.getInitialX(),
							ifStatement.getInitialY()
									+ ifStatement.getTotalSize(), -1, -1,
							ifStatement.getDefRecX1(),
							ifStatement.getDefRecY1(),
							ifStatement.getDefRecX2(),
							ifStatement.getDefRecY2(), right);
			if (ifStatement.getLoopStatement() != null) {
				if ((ifStatement.getLoopStatement()).getNodeType() == (Statement.IF_STATEMENT)) {
					IfStatement resultIfStatement = (IfStatement) ifStatement
							.getLoopStatement();
					addIfChecking(resultIfStatement, x, y, createStatus);
				} else if ((ifStatement.getLoopStatement()).getNodeType() == (Statement.WHILE_LOOP_STATEMENT)) {
					WhileLoopStatement resultIfStatement = (WhileLoopStatement) ifStatement
							.getLoopStatement();
					addWhileLoopChecking(resultIfStatement, x, y, createStatus);
				}
			}
		}
		if (!createStatus) {
			if (ifStatement.getLoopStatement() != null) {
				// if ((ifStatement.getLoopStatement()).getNodeType() ==
				// (Statement.IF_STATEMENT)) {
				WhileLoopStatement resultIfStatement = (WhileLoopStatement) ifStatement
						.getLoopStatement();
				createStatus = addWhileLoopChecking(resultIfStatement, x, y,
						createStatus);
				if (createStatus) {
					ifStatement
							.setWidthLineSize(ifStatement.getWidthLineSize() + 1);
					ifStatement
							.setHighLineSize(ifStatement.getHighLineSize() + 1);
					ifStatement.buildDiagramWithoutShape(
							ifStatement.getInitialX(),
							ifStatement.getInitialY(),
							ifStatement.getWidthLineSize(),
							ifStatement.getHighLineSize(),
							ifStatement.getTotalSize());

				}
				// }
			}

			for (int z = 0; z < shapeList.size(); z++) {
				Shape shape = shapeList.get(z);

				switch (shape.getShapeType()) {
				case 1:
					// Rectangle
					// System.out.println("x: "+shape.getX1());
					// System.out.println("x2: "+shape.getX2());
					// System.out.println("Y1: "+shape.getY1());
					// System.out.println("y2: "+shape.getY2());
					// System.out.println("w: "+shape.getWidth());
					// System.out.println("h: "+shape.getHeight());
					// check have inside the other statement?
					// true get statement and set inside
					if (x >= shape.getX1()
							&& x <= (shape.getX1() + shape.getWidth())
							&& y >= shape.getY1()
							&& y <= (shape.getY1() + shape.getHeight())) {
						createStatus = true;
						System.out.println("include");
						// IfStatement ifStatement = (IfStatement)
						// statementList.get(0);
						// ifStatement.setStart(true);
						ifStatement.setEnd(false);
						WhileLoopStatement whileLoopStatement2 = new WhileLoopStatement();
						whileLoopStatement2.setStart(false);
						whileLoopStatement2.setEnd(true);
						ifStatement.setLoopStatement(whileLoopStatement2);
						// statementList = new ArrayList();
						// statementList.add(ifStatement);
					}
					break;
				case 2:
					// Diamond
					// shape.getX1();
					// shape.getY1();
					break;
				}
			}
		}
		return createStatus;
	}

	public boolean addIfChecking(Statement tempStatement, int x, int y,
			boolean createStatus) {
		WhileLoopStatement ifStatement = (WhileLoopStatement) tempStatement;
		ArrayList<Shape> shapeList = ifStatement.getShapeList();
		// refersh diagram
		if (createStatus) {
			ifStatement
					.buildDiagram(
							ifStatement.getInitialX(),
							ifStatement.getInitialY()
									+ ifStatement.getTotalSize(), -1, -1,
							ifStatement.getDefRecX1(),
							ifStatement.getDefRecY1(),
							ifStatement.getDefRecX2(),
							ifStatement.getDefRecY2(), right);
			if (ifStatement.getLoopStatement() != null) {
				if ((ifStatement.getLoopStatement()).getNodeType() == (Statement.IF_STATEMENT)) {
					WhileLoopStatement resultIfStatement = (WhileLoopStatement) ifStatement
							.getLoopStatement();
					addIfChecking(resultIfStatement, x, y, createStatus);
				}
			}
		}
		if (!createStatus) {
			if (ifStatement.getLoopStatement() != null) {
				// if ((ifStatement.getLoopStatement()).getNodeType() ==
				// (Statement.IF_STATEMENT)) {
				WhileLoopStatement resultIfStatement = (WhileLoopStatement) ifStatement
						.getLoopStatement();
				createStatus = addIfChecking(resultIfStatement, x, y,
						createStatus);
				if (createStatus) {
					ifStatement
							.setWidthLineSize(ifStatement.getWidthLineSize() + 1);
					ifStatement
							.setHighLineSize(ifStatement.getHighLineSize() + 1);
					ifStatement.buildDiagramWithoutShape(
							ifStatement.getInitialX(),
							ifStatement.getInitialY(),
							ifStatement.getWidthLineSize(),
							ifStatement.getHighLineSize(),
							ifStatement.getTotalSize());

				}
				// }
			}

			for (int z = 0; z < shapeList.size(); z++) {
				Shape shape = shapeList.get(z);

				switch (shape.getShapeType()) {
				case 1:
					// Rectangle
					// System.out.println("x: "+shape.getX1());
					// System.out.println("x2: "+shape.getX2());
					// System.out.println("Y1: "+shape.getY1());
					// System.out.println("y2: "+shape.getY2());
					// System.out.println("w: "+shape.getWidth());
					// System.out.println("h: "+shape.getHeight());
					// check have inside the other statement?
					// true get statement and set inside
					if (x >= shape.getX1()
							&& x <= (shape.getX1() + shape.getWidth())
							&& y >= shape.getY1()
							&& y <= (shape.getY1() + shape.getHeight())) {
						createStatus = true;
						System.out.println("include");
						System.out.println("include2");
						// IfStatement ifStatement = (IfStatement)
						// statementList.get(0);
						// ifStatement.setStart(true);
						ifStatement.setEnd(false);
						WhileLoopStatement whileLoopStatement2 = new WhileLoopStatement();
						whileLoopStatement2.setStart(false);
						whileLoopStatement2.setEnd(true);
						ifStatement.setLoopStatement(whileLoopStatement2);
						// statementList = new ArrayList();
						// statementList.add(ifStatement);
					}
					break;
				case 2:
					// Diamond
					// shape.getX1();
					// shape.getY1();
					break;
				}
			}
		}
		return createStatus;
	}

	public boolean addBlockChecking(Statement tempStatement, int x, int y,
			boolean createStatus) {
		WhileLoopStatement ifStatement = (WhileLoopStatement) tempStatement;
		ArrayList<Shape> shapeList = ifStatement.getShapeList();
		// refersh diagram
		if (createStatus) {
			ifStatement
					.buildDiagram(
							ifStatement.getInitialX(),
							ifStatement.getInitialY()
									+ ifStatement.getTotalSize(), -1, -1,
							ifStatement.getDefRecX1(),
							ifStatement.getDefRecY1(),
							ifStatement.getDefRecX2(),
							ifStatement.getDefRecY2(), right);
			if (ifStatement.getLoopStatement() != null) {
				// if ((ifStatement.getLoopStatement()).getNodeType() ==
				// (Statement.IF_STATEMENT)) {
				// IfStatement resultIfStatement = (IfStatement) ifStatement
				// .getLoopStatement();
				addBlockChecking(ifStatement.getLoopStatement(), x, y,
						createStatus);
			}
		}
		//
		if (!createStatus) {
			if (ifStatement.getLoopStatement() != null) {
				// if ((ifStatement.getLoopStatement()).getNodeType() ==
				// (Statement.IF_STATEMENT)) {
				Statement resultIfStatement = (Statement) ifStatement
						.getLoopStatement();
				createStatus = addBlockChecking(resultIfStatement, x, y,
						createStatus);
				if (createStatus) {
					ifStatement.setWidthLineSize(resultIfStatement
							.getWidthLineSize() + 1);
					ifStatement.setHighLineSize(resultIfStatement
							.getHighLineSize() + 1);
					ifStatement.buildDiagramWithoutShape(
							ifStatement.getInitialX(),
							ifStatement.getInitialY(),
							ifStatement.getWidthLineSize(),
							ifStatement.getHighLineSize(),
							ifStatement.getTotalSize());

				}
				// }
			}

			for (int z = 0; z < shapeList.size(); z++) {
				Shape shape = shapeList.get(z);

				switch (shape.getShapeType()) {
				case 1:
					// Rectangle
					// System.out.println("x: "+shape.getX1());
					// System.out.println("x2: "+shape.getX2());
					// System.out.println("Y1: "+shape.getY1());
					// System.out.println("y2: "+shape.getY2());
					// System.out.println("w: "+shape.getWidth());
					// System.out.println("h: "+shape.getHeight());
					// check have inside the other statement?
					// true get statement and set inside
					if (x >= shape.getX1()
							&& x <= (shape.getX1() + shape.getWidth())
							&& y >= shape.getY1()
							&& y <= (shape.getY1() + shape.getHeight())) {
						createStatus = true;
						System.out.println("include!");
						String input = JOptionPane.showInputDialog(null,
								"Enter Input:", "Dialog for Input Block",
								JOptionPane.WARNING_MESSAGE);
						// if(label!=null)
						// input = label.getText();
						if (input.length() <= 3 || input == null) {
							System.out.println("no create!");
							return false;
						}
						System.out.println("input value:" + input);
						System.out.println("input length:" + input.length());
						// ifStatement.setEnd(false);
						Block block = new Block(input, shape.getX1(),
								shape.getY1());
						ifStatement.setLoopStatement(block);
					}
					break;
				case 2:
					// Diamond
					// shape.getX1();
					// shape.getY1();
					break;
				}
			}
		}
		return createStatus;
	}

}
