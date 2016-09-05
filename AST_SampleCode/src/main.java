import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Dimension;
import java.awt.GridBagLayout;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.BoxLayout;
import javax.swing.ButtonGroup;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;

import view.DrawPanel;
import view.StatementPanel;

public class main {

	public static void main(String[] args) {

		JFrame jFrame = new JFrame();

		jFrame.setTitle("AST");

		jFrame.setSize(800, 800);

		jFrame.addWindowListener(new WindowAdapter() {

			@Override
			public void windowClosing(WindowEvent e) {

				System.exit(0);

			}

		});

		StatementPanel statementPanel = new StatementPanel();
		statementPanel.setBounds(0, 300, 500, 200);
		statementPanel.setPreferredSize(new Dimension(100, 200));
		// end
		
		JScrollPane scrollPane = new JScrollPane(new DrawPanel(statementPanel));
		scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_NEVER);
		scrollPane.setBounds(250, 100, 500, 400);
		JPanel contentPane = new JPanel(null);
		contentPane.setPreferredSize(new Dimension(800, 600));
		contentPane.add(scrollPane);

		JPanel parent = new JPanel();
		parent.setLayout(new BoxLayout(parent, BoxLayout.LINE_AXIS));
		jFrame.add(parent);
		parent.add(statementPanel);
		parent.add(contentPane);

		final CardLayout layout = new CardLayout();
		jFrame.setLayout(layout);
		// jFrame.setContentPane(contentPane);
		jFrame.pack();

		// jFrame.setLayout(new BorderLayout());
		jFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		jFrame.setLocationRelativeTo(null);
		jFrame.setVisible(true);

	}
}
