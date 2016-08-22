import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

import view.DrawPanel;

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

		// Container cPane = jFrame.getContentPane();

		JScrollPane scrollPane = new JScrollPane(new DrawPanel());
		scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_NEVER);
		scrollPane.setBounds(250, 100, 500, 400);
		JPanel contentPane = new JPanel(null);
		contentPane.setPreferredSize(new Dimension(800, 600));
		contentPane.add(scrollPane);
		//jFrame.add(contentPane);
		
		 jFrame.setContentPane(contentPane);
		 jFrame.pack();

		jFrame.setLayout(new BorderLayout());
		jFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		jFrame.setLocationRelativeTo(null);
		jFrame.setVisible(true);

	}
}
