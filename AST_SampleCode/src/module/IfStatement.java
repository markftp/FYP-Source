package module;

import java.util.ArrayList;

import view.Line;
import view.bean.DiamondShape;
import view.bean.RectangleShape;
import view.bean.Shape;

public class IfStatement extends Statement{
	
	private Statement ifThenStatement = null;
	private Statement resultStatement = null;
	
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
	
	private int lineSize = 1;
	
	public IfStatement() {
		super();
	}
	
	public IfStatement(int x, int y) {
		super();
		buildDiagram(x,y, -1);
	}
	
	public Statement init(int x, int y) {
		buildDiagram(x,y, -1);
		return (Statement)this;
	}
	
//	public void rebuild(int x, int y) {
//		buildDiagram(x,y);
//	}

	final int getNodeType() {
		return IF_STATEMENT;
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
	public Statement getResultStatement() {
		return this.resultStatement;
	}
	
	public void setResultStatement(Statement resultStatement) {
		if(resultStatement.getNodeType() == resultStatement.IF_STATEMENT)
		{
			totalSize += ((IfStatement)resultStatement).getTotalSize();
			lineList = new ArrayList();
			shapeList = new ArrayList();
			shapeList.add(new DiamondShape(initialX + 30, initialY + 30, initialX - 10, initialY));
			ifRulex = initialX + 30;
			ifRuley = initialY + 30;
			lineList.add(new Line(initialX + 10, initialY + 30, initialX + 10, initialY + 50));
			buildArrow(initialX + 10, initialY + 50, 3);
			
			lineSize = resultStatement.getLineSize()+lineSize;
			
			lineList.add(new Line(initialX + 30, initialY + 15, initialX + 50 * lineSize, initialY + 15));
			lineList.add(new Line(initialX + 50 * lineSize, initialY + 15, initialX + 50 * lineSize , initialY + 100 * lineSize));
			lineList.add(new Line(initialX + 50 * lineSize , initialY + 100 * lineSize, initialX + 10, initialY + 100 * lineSize));
			buildArrow(initialX + 10, initialY + 100 * lineSize, 2);
			lineList.add(new Line(initialX + 10, initialY + 80 * lineSize, initialX + 10, initialY + 120 * lineSize));
			//shapeList.add(new RectangleShape(initialX - 10, initialY + 50, initialX + 30, initialY + 20));
			ifResulty = initialY + 50;
			this.resultStatement = resultStatement.init(initialX,ifResulty);
			
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
			lineList.add(new Line(x, y, x - 6, y - 6));
			lineList.add(new Line(x, y, x + 6, y - 6));
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
			lineList.add(new Line(x, y, x - 6, y - 6));
			lineList.add(new Line(x, y, x + 6, y - 6));
		}
	}
	
	public int getTotalSize(){
		return totalSize;
	}

	public boolean getEnd() {
		return isEnd;
	}

	public void setEnd(boolean isEnd) {
		this.isEnd = isEnd;
	}

	public boolean getStart() {
		return isStart;
	}

	public void setStart(boolean isStart) {
		this.isStart = isStart;
	}
	
	public void buildDiagram(int x, int y, int lineSize2){
		shapeList = new ArrayList();
		lineList = new ArrayList();
		initialX = x;
		initialY = y;
		shapeList.add(new DiamondShape(x + 30, y + 30, x - 10, y));
		ifRulex = x + 30;
		ifRuley = y + 30;
		lineList.add(new Line(x + 10, y + 30, x + 10, y + 50));
		buildArrow(x + 10, y + 50, 3);
//		lineList.add(new Line(x + 30, y + 15, x + 50, y + 15));
//		lineList.add(new Line(x + 50, y + 15, x + 50, y + 100));
//		lineList.add(new Line(x + 50, y + 100, x + 10, y + 100));
//		buildArrow(x + 10, y + 100, 2);
//		lineSize = resultStatement.getLineSize()+lineSize;
		if(lineSize2 == -1)
			lineSize2 = this.lineSize;
		lineList.add(new Line(initialX + 30, initialY + 15, initialX + 50 * lineSize2, initialY + 15));
		lineList.add(new Line(initialX + 50 * lineSize2, initialY + 15, initialX + 50 * lineSize2 , initialY + 100 * lineSize2));
		lineList.add(new Line(initialX + 50 * lineSize2 , initialY + 100 * lineSize2, initialX + 10, initialY + 100 * lineSize2));
		buildArrow(initialX + 10, initialY + 100 * lineSize2, 2);
		shapeList.add(new RectangleShape(x - 10, y + 50, x + 30, y + 20));
		ifResultx = x - 10;
		ifResulty = y + 50;
		lineList.add(new Line(x + 10, y + 80 , x + 10, y + 120 ));
	}
	
	public void buildDiagramWithoutShape(int x, int y, int lineSize2, int size){
		shapeList = new ArrayList();
		lineList = new ArrayList();
		initialX = x;
		initialY = y;
		shapeList.add(new DiamondShape(x + 30, y + 30, x - 10, y));
		ifRulex = x + 30;
		ifRuley = y + 30;
		lineList.add(new Line(x + 10, y + 30, x + 10, y + 50));
		buildArrow(x + 10, y + 50, 3);
//		lineList.add(new Line(x + 30, y + 15, x + 50, y + 15));
//		lineList.add(new Line(x + 50, y + 15, x + 50, y + 100));
//		lineList.add(new Line(x + 50, y + 100, x + 10, y + 100));
//		buildArrow(x + 10, y + 100, 2);
//		lineSize = resultStatement.getLineSize()+lineSize;
		if(lineSize2 == -1)
			lineSize2 = this.lineSize;
		lineList.add(new Line(initialX + 30, initialY + 15, initialX + 50 * lineSize2, initialY + 15));
		lineList.add(new Line(initialX + 50 * lineSize2, initialY + 15, initialX + 50 * lineSize2 , initialY + 100 * lineSize2));
		lineList.add(new Line(initialX + 50 * lineSize2 , initialY + 100 * lineSize2, initialX + 10, initialY + 100 * lineSize2));
		buildArrow(initialX + 10, initialY + 100 * lineSize2, 2);
		//shapeList.add(new RectangleShape(x - 10, y + 50, x + 30, y + 20));
		//ifResultx = x - 10;
		//ifResulty = y + 50;
		//lineList.add(new Line(x + 10, y + 80, x + 10, y + 120));
		totalSize +=size;
	}	

	public int getLineSize() {
		return lineSize;
	}

	public void setLineSize(int lineSize) {
		this.lineSize = lineSize;
	}
	
	public int getInitialX() {
		return initialX;
	}
	
	public int getInitialY() {
		return initialY;
	}
	
}
