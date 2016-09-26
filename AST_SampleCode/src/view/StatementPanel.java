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
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.geom.AffineTransform;
import java.util.ArrayList;

import javax.swing.AbstractButton;
import javax.swing.ButtonGroup;
import javax.swing.ButtonModel;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import module.IfStatement;
import module.Statement;
import view.bean.DiamondShape;
import view.bean.RectangleShape;
import view.bean.Shape;

public class StatementPanel extends JPanel implements MouseMotionListener {

	private String statementSelected = "If Statement";
	private JRadioButton ifstatement = new JRadioButton("If Statement");
	private JRadioButton block = new JRadioButton("Block");
	private JRadioButton whileLoopStatement = new JRadioButton("While Loop Statement");
	
	public StatementPanel() {
		// start: set start & end point
		ButtonGroup bG = new ButtonGroup();
		bG.add(ifstatement);
		bG.add(whileLoopStatement);
		bG.add(block);
		this.add(ifstatement);
		this.add(whileLoopStatement);
		this.add(block);
		ifstatement.setSelected(true);
		ActionListener actionListener = new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent actionEvent) {
				AbstractButton aButton = (AbstractButton) actionEvent.getSource();
				setStatementSelected(aButton.getText());
			}
		};
		ifstatement.addActionListener(actionListener);
		whileLoopStatement.addActionListener(actionListener);
		block.addActionListener(actionListener);
	}

	@Override
	public void paintComponent(Graphics g) {

	}

	@Override
	public void mouseDragged(MouseEvent arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseMoved(MouseEvent arg0) {
		// TODO Auto-generated method stub

	}

	public String getStatementSelected() {
		return statementSelected;
	}

	public void setStatementSelected(String statementSelected) {
		this.statementSelected = statementSelected;
	}

}