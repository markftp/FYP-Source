package view;

import java.awt.Adjustable;
import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.geom.AffineTransform;
import java.util.ArrayList;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

import module.Block;
import module.IfStatement;
import module.Statement;
import view.bean.DiamondShape;
import view.bean.RectangleShape;
import view.bean.Shape;

public class DrawPanel extends JPanel implements MouseMotionListener {

	private static final int recW = 20;
	// private ArrayList<Rectangle> rect = new ArrayList();
	// private ArrayList<Line> lineList = new ArrayList();
	// private ArrayList<Shape> shapeList = new ArrayList();
	private ArrayList<Statement> statementList = new ArrayList();
	// private int numOfRecs = 0;
	private int currentSquareIndex = -1;

	private int startXNo = 250;
	private int startYNo = 50;

	private Point startPoint;
	private Point endPoint;

	// panel
	private StatementPanel statementPanel;

	public DrawPanel(final StatementPanel statementPanel) {
		// start: set start & end point
		this.statementPanel = statementPanel;
		startPoint = new Point(startXNo, 22);
		endPoint = new Point(startXNo, 350);
		// end: set start & end point
		addMouseListener(new MouseAdapter() {

			@Override
			public void mousePressed(MouseEvent evt) {

				int x = evt.getX();

				int y = evt.getY();

				if (statementPanel.getStatementSelected().equals("If Statement")) {
					System.out.println("Add If!");
					addIfStatement(x, y);
				} else if (statementPanel.getStatementSelected().equals("Block")) {
					System.out.println("Add Block!");
					addBlock(x, y);
				}

			}

			@Override
			public void mouseClicked(MouseEvent evt) {
				int x = evt.getX();

				int y = evt.getY();
				if (evt.getClickCount() == 1) {
					System.out.println("x:" + x);
					System.out.println("y:" + y);
				}

				if (evt.getClickCount() >= 2) {

					remove(currentSquareIndex);

				}

			}

		});

		addMouseMotionListener(this);
	}

	@Override
	public void paintComponent(Graphics g) {

		super.paintComponent(g);
		// start: set start & end point
		JLabel startPointLabel = new JLabel("Start Point");
		startPointLabel.setBounds((int) startPoint.getX() - 20, (int) startPoint.getY() - 30, 100, 40);
		this.add(startPointLabel);
		g.fillOval((int) startPoint.getX() + 5, (int) startPoint.getY(), 10, 10);
		g.fillOval((int) endPoint.getX() + 5, (int) endPoint.getY(), 10, 10);
		// build shape
		System.out.println("statementList.size(): " + statementList.size());
		for (int x = 0; x < statementList.size(); x++) {
			IfStatement ifstatement = (IfStatement) statementList.get(x);
			generateIfStatement(ifstatement, g);
		}
		JLabel endPointLabel = new JLabel("End Point");
		endPointLabel.setBounds((int) endPoint.getX() - 20, (int) endPoint.getY(), 100, 40);
		this.add(endPointLabel);

	}

	public void addBlock(int x, int y) {
		if (statementList.size() == 0) {
			System.out.println("No statement!");
		} else {
			boolean createStatus = false;
			// check include any statement
			for (int i = 0; i < statementList.size(); i++) {
				createStatus = addBlockChecking((IfStatement) statementList.get(i), x, y, createStatus);
			}

		}
		repaint();
	}

	public void addIfStatement(int x, int y) {
		if (statementList.size() == 0) {
			IfStatement ifstat = new IfStatement(startXNo, startYNo);
			ifstat.setStart(true);
			ifstat.setEnd(true);
			statementList.add(ifstat);
		} else {
			boolean createStatus = false;
			// System.out.println("x: "+x);
			// System.out.println("y: "+y);

			// check include any statement
			for (int i = 0; i < statementList.size(); i++) {
				createStatus = addIfChecking((IfStatement) statementList.get(i), x, y, createStatus);
			}

			// not include any statement
			if (!createStatus) {
				// reset end point to new
				for (int i = 0; i < statementList.size(); i++) {
					resetEndPoint((IfStatement) statementList.get(i));
				}
				// create new statement
				IfStatement ifStatement = (IfStatement) statementList.get(statementList.size() - 1);
				ifStatement.setEnd(false);
				IfStatement ifStatement2 = new IfStatement();
				ifStatement2.setEnd(true);
				ifStatement2.init(ifStatement.getInitialX(), ifStatement.getInitialY() + ifStatement.getTotalSize());
				statementList.add(ifStatement2);
			}
		}
		repaint();
	}

	private void resetEndPoint(IfStatement ifStatement) {
		ifStatement.setEnd(false);
		if (ifStatement.getResultStatement() != null) {
			if ((ifStatement.getResultStatement()).getNodeType() == (Statement.IF_STATEMENT)) {
				resetEndPoint((IfStatement) ifStatement.getResultStatement());
			} else if ((ifStatement.getResultStatement()).getNodeType() == (Statement.BLOCK)) {
				resetEndPoint((IfStatement) ifStatement.getResultStatement());
			}
		}
	}

	private boolean addBlockChecking(IfStatement ifStatement, int x, int y, boolean createStatus) {
		ArrayList<Shape> shapeList = ifStatement.getShapeList();
		// refersh diagram
		if (createStatus) {
			ifStatement.buildDiagram(ifStatement.getInitialX(), ifStatement.getInitialY() + ifStatement.getTotalSize(),
					-1, -1, ifStatement.getDefRecX1(), ifStatement.getDefRecY1(), ifStatement.getDefRecX2(),
					ifStatement.getDefRecY2());
			if (ifStatement.getResultStatement() != null) {
				if ((ifStatement.getResultStatement()).getNodeType() == (Statement.IF_STATEMENT)) {
					IfStatement resultIfStatement = (IfStatement) ifStatement.getResultStatement();
					addBlockChecking(resultIfStatement, x, y, createStatus);
				}
			}
		}
		//
		if (!createStatus) {
			if (ifStatement.getResultStatement() != null) {
				if ((ifStatement.getResultStatement()).getNodeType() == (Statement.IF_STATEMENT)) {
					IfStatement resultIfStatement = (IfStatement) ifStatement.getResultStatement();
					createStatus = addBlockChecking(resultIfStatement, x, y, createStatus);
					if (createStatus) {
						ifStatement.setWidthLineSize(resultIfStatement.getWidthLineSize() + 1);
						ifStatement.setHighLineSize(resultIfStatement.getHighLineSize() + 1);
						ifStatement.buildDiagramWithoutShape(ifStatement.getInitialX(), ifStatement.getInitialY(),
								ifStatement.getWidthLineSize(), ifStatement.getHighLineSize(),
								ifStatement.getTotalSize());

					}
				}
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
					if (x >= shape.getX1() && x <= (shape.getX1() + shape.getWidth()) && y >= shape.getY1()
							&& y <= (shape.getY1() + shape.getHeight())) {
						createStatus = true;
						System.out.println("include!");
						String input = JOptionPane.showInputDialog(null, "Enter Input:", "Dialog for Input Block",
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
						Block block = new Block(input, shape.getX1(), shape.getY1());
						ifStatement.setResultStatement(block);
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

	private boolean addIfChecking(IfStatement ifStatement, int x, int y, boolean createStatus) {
		ArrayList<Shape> shapeList = ifStatement.getShapeList();
		// refersh diagram
		if (createStatus) {
			ifStatement.buildDiagram(ifStatement.getInitialX(), ifStatement.getInitialY() + ifStatement.getTotalSize(),
					-1, -1, ifStatement.getDefRecX1(), ifStatement.getDefRecY1(), ifStatement.getDefRecX2(),
					ifStatement.getDefRecY2());
			if (ifStatement.getResultStatement() != null) {
				if ((ifStatement.getResultStatement()).getNodeType() == (Statement.IF_STATEMENT)) {
					IfStatement resultIfStatement = (IfStatement) ifStatement.getResultStatement();
					addIfChecking(resultIfStatement, x, y, createStatus);
				}
			}
		}
		if (!createStatus) {
			if (ifStatement.getResultStatement() != null) {
				if ((ifStatement.getResultStatement()).getNodeType() == (Statement.IF_STATEMENT)) {
					IfStatement resultIfStatement = (IfStatement) ifStatement.getResultStatement();
					createStatus = addIfChecking(resultIfStatement, x, y, createStatus);
					if (createStatus) {
						ifStatement.setWidthLineSize(ifStatement.getWidthLineSize() + 1);
						ifStatement.setHighLineSize(ifStatement.getHighLineSize() + 1);
						ifStatement.buildDiagramWithoutShape(ifStatement.getInitialX(), ifStatement.getInitialY(),
								ifStatement.getWidthLineSize(), ifStatement.getHighLineSize(),
								ifStatement.getTotalSize());

					}
				}
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
					if (x >= shape.getX1() && x <= (shape.getX1() + shape.getWidth()) && y >= shape.getY1()
							&& y <= (shape.getY1() + shape.getHeight())) {
						createStatus = true;
						System.out.println("include");
						// IfStatement ifStatement = (IfStatement)
						// statementList.get(0);
						// ifStatement.setStart(true);
						ifStatement.setEnd(false);
						IfStatement ifStatement2 = new IfStatement();
						ifStatement2.setStart(false);
						ifStatement2.setEnd(true);
						ifStatement.setResultStatement(ifStatement2);
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

	@Override
	public void remove(int n) {

		// rect.remove(n);
		// if (currentSquareIndex == n) {
		//
		// currentSquareIndex = -1;
		//
		// }
		//
		// repaint();
	}

	@Override
	public void mouseDragged(MouseEvent event) {

	}

	private void drawDiamond(Graphics g, DiamondShape diamondShape) {
		int x = (diamondShape.getX1() + diamondShape.getX2()) / 2;
		int y = (diamondShape.getY1() + diamondShape.getY2()) / 2;
		g.drawLine(diamondShape.getX1(), y, x, diamondShape.getY1());
		g.drawLine(x, diamondShape.getY1(), diamondShape.getX2(), y);
		g.drawLine(diamondShape.getX2(), y, x, diamondShape.getY2());
		g.drawLine(x, diamondShape.getY2(), (int) diamondShape.getX1(), y);
	}

	private void drawLine(Graphics g, Line line) {

		g.drawLine((int) line.getX1(), (int) line.getY1(), (int) line.getX2(), (int) line.getY2());
	}

	private void drawRectangle(Graphics g, RectangleShape diamondShape) {
		g.drawRect(diamondShape.getX1(), diamondShape.getY1(), diamondShape.getWidth(), diamondShape.getHeight());
	}

	private boolean IsinsideStatement(int x, int y) {
		if (statementList.size() == 0) {
			return false;
		}
		return false;
	}

	private void generateIfStatement(IfStatement ifStatement, Graphics g) {
		// System.out.println("ifStatement.getEnd(): " + ifStatement.getEnd());
		if (ifStatement.getResultStatement() != null) {
			Statement tempStatement = ifStatement.getResultStatement();
			System.out.println(tempStatement.getNodeType());

			if (tempStatement.getNodeType() == tempStatement.IF_STATEMENT) {
				IfStatement ifstatement2 = (IfStatement) ifStatement.getResultStatement();
				generateIfStatement(ifstatement2, g);
			} else if (tempStatement.getNodeType() == tempStatement.BLOCK) {
				//
				JLabel tempLabel = ((Block) tempStatement).getLabel();
				// System.out.println("test");
				// for (int i = 0; i < labelList.size(); i++) {
				this.add(tempLabel);
				// }
			}
		}
		ArrayList<Shape> shapeList = ifStatement.getShapeList();
		ArrayList<Line> lineList = ifStatement.getLineList();
		// build line
		for (int i = 0; i < shapeList.size(); i++) {
			Shape shape = shapeList.get(i);
			switch (shape.getShapeType()) {
			case 1:
				drawRectangle(g, (RectangleShape) shape);
				// add start point line
				if (i == 0 && ifStatement.getStart()) {
					drawLine(g, new Line((int) startPoint.getX() + 10, (int) startPoint.getY() + 5,
							((RectangleShape) shape).getX1() + 5, ((RectangleShape) shape).getY1()));
					buildArrow(((RectangleShape) shape).getX1() + 6, ((RectangleShape) shape).getY1(), 3, lineList);
				}
				// add end point line
				if (i + 1 == shapeList.size() && ifStatement.getEnd()) {
					drawLine(g, new Line((int) endPoint.getX() + 10, (int) endPoint.getY() + 5, shape.getX1() + 20,
							shape.getY1() + 30));
					buildArrow((int) endPoint.getX() + 10, (int) endPoint.getY(), 3, lineList);
				}
				break;
			case 2:
				drawDiamond(g, (DiamondShape) shape);
				// add start point line
				if (i == 0 && ifStatement.getStart()) {
					int x = (((DiamondShape) shape).getX1() + ((DiamondShape) shape).getX2()) / 2;
					int y = ((((DiamondShape) shape).getY1() + ((DiamondShape) shape).getY2()) / 2);
					drawLine(g, new Line((int) startPoint.getX() + 10, (int) startPoint.getY() + 5, x, y - 15));
					buildArrow(x, y - 15, 3, lineList);
				}
				// add end point line
				if (i + 1 == shapeList.size() && ifStatement.getEnd()) {
					drawLine(g, new Line((int) endPoint.getX() + 5, (int) endPoint.getY() + 5,
							(int) ((RectangleShape) shape).getX1() + 20, ((RectangleShape) shape).getY1() + 30));
					buildArrow((int) endPoint.getX() + 5, (int) endPoint.getY(), 3, lineList);
				}
				break;
			}
		}
		// build line
		for (int i = 0; i < lineList.size(); i++) {
			Line line = (Line) lineList.get(i);
			drawLine(g, line);
		}
	}

	private void buildArrow(int x, int y, int status, ArrayList lineList) {
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

	@Override
	public void mouseMoved(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}

}