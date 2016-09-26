package view;

import java.awt.Adjustable;
import java.awt.BorderLayout;
import java.awt.Color;
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
import module.WhileLoopStatement;
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

	private JLabel startPointLabel;
	private JLabel endPointLabel;
	private JLabel clearLabel;
	// private JLabel startPointLabel = new JLabel("Start Point");
	// private JLabel endPointLabel = new JLabel("End Point");

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

				if (x > 450 && y > 350)
					reset();
				else {
					if (statementPanel.getStatementSelected().equals(
							"If Statement")) {
						System.out.println("Add If!");
						addIfStatement(x, y);
					} else if (statementPanel.getStatementSelected().equals(
							"While Loop Statement")) {
						System.out.println("Add While Loop Statement!");
						addWhileLoopStatement(x, y);
					} else if (statementPanel.getStatementSelected().equals(
							"Block")) {
						System.out.println("Add Block!");
						addBlock(x, y);
					}
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
		startPointLabel = new JLabel("Start Point");
		endPointLabel = new JLabel("End Point");
		clearLabel = new JLabel("Clear");
	}

	@Override
	public void paintComponent(Graphics g) {

		super.paintComponent(g);

		startPointLabel.setBounds((int) startPoint.getX() - 20,
				(int) startPoint.getY() - 30, 100, 40);
		this.add(startPointLabel);
		g.fillOval((int) startPoint.getX() + 5, (int) startPoint.getY(), 10, 10);
		g.fillOval((int) endPoint.getX() + 5, (int) endPoint.getY(), 10, 10);
		// build shape
		System.out.println("statementList.size(): " + statementList.size());
		for (int x = 0; x < statementList.size(); x++) {
			Statement tempStatement = statementList.get(x);
			if (tempStatement.getNodeType() == (Statement.IF_STATEMENT)) {
				generateIfStatement((IfStatement) tempStatement, g);
			} else if (tempStatement.getNodeType() == (Statement.WHILE_LOOP_STATEMENT)) {
				generateWhileLoopStatement((WhileLoopStatement) tempStatement,
						g);
			}
		}

		endPointLabel.setBounds((int) endPoint.getX() - 20,
				(int) endPoint.getY(), 100, 40);
		this.add(endPointLabel);
		clearLabel.setBounds((int) endPoint.getX() + 200,
				(int) endPoint.getY(), 100, 40);
		clearLabel.setForeground(Color.red);
		this.add(clearLabel);

	}

	public void addBlock(int x, int y) {
		if (statementList.size() == 0) {
			System.out.println("No statement!");
		} else {
			boolean createStatus = false;
			// check include any statement
			for (int i = 0; i < statementList.size(); i++) {
				Statement tempStatement = statementList.get(i);
				createStatus = tempStatement.addBlockChecking(tempStatement, x,
						y, createStatus);
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
				Statement tempStatement = statementList.get(i);
				createStatus = tempStatement.addIfChecking(tempStatement, x, y,
						createStatus);
			}

			// not include any statement
			if (!createStatus) {
				// reset end point to new
				for (int i = 0; i < statementList.size(); i++) {
					resetEndPoint(statementList.get(i));
				}
				// create new statement
				Statement tempStatement = statementList.get(statementList
						.size() - 1);
				tempStatement.setEnd(false);
				IfStatement ifStatement2 = new IfStatement();
				ifStatement2.setEnd(true);
				ifStatement2.init(
						tempStatement.getInitialX(),
						tempStatement.getInitialY()
								+ tempStatement.getTotalSize());
				statementList.add(ifStatement2);
			}
		}
		repaint();
	}

	public void addWhileLoopStatement(int x, int y) {
		if (statementList.size() == 0) {
			WhileLoopStatement whileLoopstat = new WhileLoopStatement(startXNo,
					startYNo, true);
			whileLoopstat.setStart(true);
			whileLoopstat.setEnd(true);
			statementList.add(whileLoopstat);
		} else {
			boolean createStatus = false;
			// System.out.println("x: "+x);
			// System.out.println("y: "+y);

			// check include any statement
			for (int i = 0; i < statementList.size(); i++) {
				Statement tempStatement = statementList.get(i);
				createStatus = tempStatement.addWhileLoopChecking(
						tempStatement, x, y, createStatus);
			}

			if (!createStatus) {
				// reset end point to new
				for (int i = 0; i < statementList.size(); i++) {
					resetEndPoint(statementList.get(i));
				}
				// create new statement
				Statement tempStatement = statementList.get(statementList
						.size() - 1);
				tempStatement.setEnd(false);
				WhileLoopStatement whileLoopStatement = new WhileLoopStatement();
				whileLoopStatement.setEnd(true);
				whileLoopStatement.init(
						tempStatement.getInitialX(),
						tempStatement.getInitialY()
								+ tempStatement.getTotalSize());
				statementList.add(whileLoopStatement);
			}
		}
		repaint();
	}

	private void resetEndPoint(Statement tempStatement) {
		tempStatement.setEnd(false);
		// if (tempStatement.getResultStatement() != null) {
		// if (tempStatement.getNodeType() == (Statement.IF_STATEMENT)) {
		// resetEndPoint((IfStatement) tempStatement.getResultStatement());
		// } else if (tempStatement.getNodeType() == (Statement.BLOCK)) {
		// resetEndPoint((IfStatement) tempStatement.getResultStatement());
		// }
		// }
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

		g.drawLine((int) line.getX1(), (int) line.getY1(), (int) line.getX2(),
				(int) line.getY2());
	}

	private void drawRectangle(Graphics g, RectangleShape diamondShape) {
		g.drawRect(diamondShape.getX1(), diamondShape.getY1(),
				diamondShape.getWidth(), diamondShape.getHeight());
	}

	private boolean IsinsideStatement(int x, int y) {
		if (statementList.size() == 0) {
			return false;
		}
		return false;
	}

	private void generateWhileLoopStatement(
			WhileLoopStatement whileLoopStatement, Graphics g) {
		// System.out.println("ifStatement.getEnd(): " + ifStatement.getEnd());
		if (whileLoopStatement.getLoopStatement() != null) {
			Statement tempStatement = whileLoopStatement.getLoopStatement();
			System.out.println(tempStatement.getNodeType());

			if (tempStatement.getNodeType() == tempStatement.IF_STATEMENT) {
				IfStatement ifstatement2 = (IfStatement) whileLoopStatement
						.getLoopStatement();
				generateIfStatement(ifstatement2, g);
			} else if (tempStatement.getNodeType() == tempStatement.WHILE_LOOP_STATEMENT) {
				WhileLoopStatement ifstatement2 = (WhileLoopStatement) whileLoopStatement
						.getLoopStatement();
				generateWhileLoopStatement(ifstatement2, g);
			} else if (tempStatement.getNodeType() == tempStatement.BLOCK) {
				//
				JLabel tempLabel = ((Block) tempStatement).getLabel();
				// System.out.println("test");
				// for (int i = 0; i < labelList.size(); i++) {
				this.add(tempLabel);
				// }
			}
		}
		ArrayList<Shape> shapeList = whileLoopStatement.getShapeList();
		ArrayList<Line> lineList = whileLoopStatement.getLineList();
		// build line
		for (int i = 0; i < shapeList.size(); i++) {
			Shape shape = shapeList.get(i);
			switch (shape.getShapeType()) {
			case 1:
				drawRectangle(g, (RectangleShape) shape);
				// add start point line
				if (i == 0 && whileLoopStatement.getStart()) {
					drawLine(g, new Line((int) startPoint.getX() + 10,
							(int) startPoint.getY() + 5,
							((RectangleShape) shape).getX1() + 5,
							((RectangleShape) shape).getY1()));
					buildArrow(((RectangleShape) shape).getX1() + 6,
							((RectangleShape) shape).getY1(), 3, lineList);
				}
				// add end point line
				if (i + 1 == shapeList.size() && whileLoopStatement.getEnd()) {
					drawLine(g, new Line((int) endPoint.getX() + 10,
							(int) endPoint.getY() + 5, shape.getX1() + 20,
							shape.getY1() + 50));
					buildArrow((int) endPoint.getX() + 10,
							(int) endPoint.getY(), 3, lineList);
				}
				break;
			case 2:
				drawDiamond(g, (DiamondShape) shape);
				// add start point line
				if (i == 0 && whileLoopStatement.getStart()) {
					int x = (((DiamondShape) shape).getX1() + ((DiamondShape) shape)
							.getX2()) / 2;
					int y = ((((DiamondShape) shape).getY1() + ((DiamondShape) shape)
							.getY2()) / 2);
					drawLine(g, new Line((int) startPoint.getX() + 10,
							(int) startPoint.getY() + 5, x, y - 15));
					buildArrow(x, y - 15, 3, lineList);
				}
				// add end point line
				if (i + 1 == shapeList.size() && whileLoopStatement.getEnd()) {
					drawLine(g, new Line((int) endPoint.getX() + 5,
							(int) endPoint.getY() + 5,
							(int) ((RectangleShape) shape).getX1() + 20,
							((RectangleShape) shape).getY1() + 30));
					buildArrow((int) endPoint.getX() + 5,
							(int) endPoint.getY(), 3, lineList);
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

	private void generateIfStatement(IfStatement ifStatement, Graphics g) {
		// System.out.println("ifStatement.getEnd(): " + ifStatement.getEnd());
		if (ifStatement.getResultStatement() != null) {
			Statement tempStatement = ifStatement.getResultStatement();
			System.out.println(tempStatement.getNodeType());

			if (tempStatement.getNodeType() == tempStatement.IF_STATEMENT) {
				IfStatement ifstatement2 = (IfStatement) ifStatement
						.getResultStatement();
				generateIfStatement(ifstatement2, g);
			} else if (tempStatement.getNodeType() == tempStatement.WHILE_LOOP_STATEMENT) {
				WhileLoopStatement ifstatement2 = (WhileLoopStatement) ifStatement
						.getResultStatement();
				generateWhileLoopStatement(ifstatement2, g);
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
					drawLine(g, new Line((int) startPoint.getX() + 10,
							(int) startPoint.getY() + 5,
							((RectangleShape) shape).getX1() + 5,
							((RectangleShape) shape).getY1()));
					buildArrow(((RectangleShape) shape).getX1() + 6,
							((RectangleShape) shape).getY1(), 3, lineList);
				}
				// add end point line
				if (i + 1 == shapeList.size() && ifStatement.getEnd()) {
					drawLine(g, new Line((int) endPoint.getX() + 10,
							(int) endPoint.getY() + 5, shape.getX1() + 20,
							shape.getY1() + 30));
					buildArrow((int) endPoint.getX() + 10,
							(int) endPoint.getY(), 3, lineList);
				}
				break;
			case 2:
				drawDiamond(g, (DiamondShape) shape);
				// add start point line
				if (i == 0 && ifStatement.getStart()) {
					int x = (((DiamondShape) shape).getX1() + ((DiamondShape) shape)
							.getX2()) / 2;
					int y = ((((DiamondShape) shape).getY1() + ((DiamondShape) shape)
							.getY2()) / 2);
					drawLine(g, new Line((int) startPoint.getX() + 10,
							(int) startPoint.getY() + 5, x, y - 15));
					buildArrow(x, y - 15, 3, lineList);
				}
				// add end point line
				if (i + 1 == shapeList.size() && ifStatement.getEnd()) {
					drawLine(g, new Line((int) endPoint.getX() + 5,
							(int) endPoint.getY() + 5,
							(int) ((RectangleShape) shape).getX1() + 20,
							((RectangleShape) shape).getY1() + 30));
					buildArrow((int) endPoint.getX() + 5,
							(int) endPoint.getY(), 3, lineList);
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

	public void reset() {
		statementList = new ArrayList();
		repaint();
	}

}