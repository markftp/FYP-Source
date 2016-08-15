package view;

import java.awt.Container;
import java.awt.Cursor;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.ArrayList;

import javax.swing.JFrame;
import javax.swing.JPanel;

import module.Statement;

public class DrawPanel extends JPanel implements MouseMotionListener {

	private static final int recW = 14;
	private ArrayList<Rectangle> rect = new ArrayList();
	private ArrayList<Statement> statement = new ArrayList();
	// private int numOfRecs = 0;
	private int currentSquareIndex = -1;

	private Point startPoint;
	private Point endPoint;
	private ArrayList<Line> lineList = new ArrayList();

	public DrawPanel() {
		// start: set start & end point
		startPoint = new Point(290, 22);
		endPoint = new Point(290, 550);
		// end: set start & end point
		addMouseListener(new MouseAdapter() {

			@Override
			public void mousePressed(MouseEvent evt) {

				int x = evt.getX();

				int y = evt.getY();

				currentSquareIndex = getRec(x, y);

				if (currentSquareIndex < 0) // not inside a square

				{

					addIf(290, y);

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
		g.fillOval((int) startPoint.getX(), (int) startPoint.getY(), 10, 10);
		g.fillOval((int) endPoint.getX(), (int) endPoint.getY(), 10, 10);
		// end: set start & end point
		for (int i = 0; i < rect.size(); i++) {
			Rectangle rectangle = (Rectangle) rect.get(i);

			((Graphics2D) g).draw(rectangle);
			// start: set start & end point
			if (i == 0) {
				lineList.add(new Line((int) startPoint.getX() + 5,
						(int) startPoint.getY() + 5,
						(int) rectangle.getX() + 5, (int) rectangle.getY()));
				buildArrow((int) rectangle.getX() + 6, (int) rectangle.getY(),
						3);
			}
			if (i + 1 == rect.size()) {
				lineList.add(new Line((int) endPoint.getX() + 5, (int) endPoint
						.getY() + 5, (int) rectangle.getX() + 10,
						(int) rectangle.getY() + 14));
				buildArrow((int) endPoint.getX() + 5, (int) endPoint.getY(), 3);
			}
			// end: set start & end point
		}
		// build line
		for (int i = 0; i < lineList.size(); i++) {
			Line line = (Line) lineList.get(i);
			g.drawLine((int) line.getX1(), (int) line.getY1(),
					(int) line.getX2(), (int) line.getY2());
		}
	}

	public int getRec(int x, int y) {

		for (int i = 0; i < rect.size(); i++) {

			if (rect.get(i).contains(x, y)) {

				return i;

			}

		}

		return -1;
	}

	public void addPoint(Point point) {

		rect.add(new Rectangle((int) point.getX(), (int) point.getY(), recW,
				recW));

		repaint();

	}

	public void addIf(int x, int y) {

		rect.add(new Rectangle(x, y, recW, recW));
		lineList.add(new Line(x + 8, y + 15, x + 8, y + 50));
		buildArrow(x + 8, y + 50, 3);
		lineList.add(new Line(x + 15, y + 8, x + 50, y + 8));
		lineList.add(new Line(x + 50, y + 8, x + 50, y + 80));
		lineList.add(new Line(x + 50, y + 80, x + 9, y + 80 ));
		buildArrow(x + 9, y + 80, 2);
		rect.add(new Rectangle(x, y + 50, recW, recW));

		repaint();

	}

	@Override
	public void remove(int n) {

		rect.remove(n);
		if (currentSquareIndex == n) {

			currentSquareIndex = -1;

		}

		repaint();
	}

	@Override
	public void mouseMoved(MouseEvent event) {

		int x = event.getX();

		int y = event.getY();

		if (getRec(x, y) >= 0) {

			setCursor(Cursor.getPredefinedCursor(Cursor.CROSSHAIR_CURSOR));

		} else {

			setCursor(Cursor.getDefaultCursor());

		}
	}

	@Override
	public void mouseDragged(MouseEvent event) {

		int x = event.getX();

		int y = event.getY();

		if (currentSquareIndex >= 0) {

			Graphics graphics = getGraphics();

			graphics.setXORMode(getBackground());

			((Graphics2D) graphics).draw(rect.get(currentSquareIndex));

			rect.get(currentSquareIndex).x = x;

			rect.get(currentSquareIndex).y = y;

			((Graphics2D) graphics).draw(rect.get(currentSquareIndex));

			graphics.dispose();

		}
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

	public static void main(String[] args) {

		JFrame jFrame = new JFrame();

		jFrame.setTitle("");

		jFrame.setSize(600, 600);

		jFrame.addWindowListener(new WindowAdapter() {

			@Override
			public void windowClosing(WindowEvent e) {

				System.exit(0);

			}

		});

		Container cPane = jFrame.getContentPane();

		cPane.add(new DrawPanel());

		jFrame.setVisible(true);
	}
}