import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.LineBorder;

public class GUI_Class extends JFrame
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JButton addStartButton = new JButton("Add START");
	private JButton addEndButton = new JButton("Add END");
	private JButton addWallButton = new JButton("Add WALL");
	private JButton removeElementButton = new JButton("Remove");
	private JButton startAlg = new JButton("Start !");
	private JButton restartButton = new JButton("Restart");
	private JLabel labelAdd = new JLabel("");
	private JLabel blocksPassed = new JLabel("");
	private JButton buttons[][];
	private Engine engine;
	private boolean removePressed = false;
	private int exitI;
	private int exitJ;
	private int ROWS;
	private int COLUMNS;
	private int pathShowed;
	private int[][] p;
	
	public GUI_Class(int r, int c)
	{
		engine = new Engine(r, c);
		buttons = new JButton[r][c];
		ROWS = r;
		COLUMNS = c;
		
		setTitle("Maze Generator");
		setBounds(500,100,100,100);
		
		JPanel buttons = new JPanel(new GridLayout(6,1));
		buttons.setBackground(Color.YELLOW);
		
		addStartButton.setBackground(Color.GREEN);
		addEndButton.setBackground(Color.RED);
		addWallButton.setBackground(Color.ORANGE);
		startAlg.setBackground(Color.CYAN);
		removeElementButton.setBackground(Color.GRAY);
		restartButton.setBackground(Color.YELLOW);
		
		buttons.add(addStartButton);
		buttons.add(addEndButton);
		buttons.add(addWallButton);
		buttons.add(removeElementButton);
		buttons.add(startAlg);
		
		addStartButton.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				
				engine.switchComponentToAdd(MyType.START);
				labelAdd.setText("Currently adding: START");
			}
		});
		
		addEndButton.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				
				engine.switchComponentToAdd(MyType.END);
				labelAdd.setText("Currently adding: END");
			}
		});
		
		removeElementButton.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				
				if(removePressed == false)
				{
					labelAdd.setText("Currently removing... Press Cancel to STOP!");
					removePressed = true;
					removeElementButton.setText("Cancel...!");
				}
				else
				{
					labelAdd.setText("Removing FINISHED !");
					removePressed = false;
					removeElementButton.setText("Remove");
				}
				engine.removeElement();
				
			}
		});
		
		addWallButton.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				
				labelAdd.setText("Currently adding: WALL");
				engine.switchComponentToAdd(MyType.WALL);
				
			}
		});
		
		startAlg.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				
				int [][] parents = engine.findExit();
				if(parents != null && parents[engine.getEndPosition() / c][engine.getEndPosition() % c] != -1)
				{
					int i = engine.getEndPosition() / COLUMNS;
					int j = engine.getEndPosition() % COLUMNS;
					exitI = i;
					exitJ = j;
					
					int t = showPath(engine.getStartPosition(), parents[i][j], parents, 1);
					blocksPassed.setText("Distance: " + t + " blocks");
					
					pathShowed = 1;
					p = parents;
				}
			}
		});
		
		JPanel gridPanel = new JPanel(new GridLayout(r, c));
		
		for(int i = 0; i < r; i++) {
			for(int j = 0; j < c; j++) {
				
				JButton button = new MyButton(i, j);
				button.setPreferredSize(new Dimension(20,20));
				
				button.addActionListener(new ActionListener() {
					
					@Override
					public void actionPerformed(ActionEvent e) {
						
						MyButton b = (MyButton)e.getSource();
						engine.addComponent(b.getI(), b.getJ());
						
						b.setIcon(new ImageIcon("Images//" + engine.getGrid()[b.getI()][b.getJ()].name() + ".png"));
						
						if(pathShowed == 1)
						{
							pathShowed = 0;
							blocksPassed.setText("");
							showPath(engine.getStartPosition(), p[exitI][exitJ], p, 0);
						}
					}
				});
				
				gridPanel.add(button);
				this.buttons[i][j] = button;
				
			}
		}
		
		gridPanel.setBorder(new LineBorder(Color.BLACK, 5));
		
		restartButton.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				blocksPassed.setText("");
				pathShowed = 0;
				engine.initialize();
				refresh();
				
			}
		});
		
		buttons.add(restartButton, BorderLayout.CENTER);
		
		refresh();
		
		JPanel helperPanel = new JPanel(new FlowLayout());
		helperPanel.add(buttons);
		helperPanel.setBackground(Color.WHITE);
		
		JPanel labelsPanel = new JPanel(new GridLayout(1,2));
		labelsPanel.add(labelAdd);
		labelsPanel.add(blocksPassed);
		
		getContentPane().add(helperPanel, BorderLayout.EAST);
		getContentPane().add(gridPanel, BorderLayout.CENTER);
		getContentPane().add(labelsPanel, BorderLayout.SOUTH);
		
		pack();
		setVisible(true);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
	}
	
	private int showPath(int s, int v, int[][] p, int flag)
	{
		if(s == v)
			return 0;
		
		int i = v / COLUMNS;
		int j = v % COLUMNS;
		
		if(flag == 1)
			buttons[i][j].setIcon(new ImageIcon("Images//PATH.png"));
		else
			buttons[i][j].setIcon(new ImageIcon("Images//" + engine.getGrid()[i][j].name() + ".png"));
		return 1 + showPath(s, p[i][j], p, flag);
	}
	
	private void refresh() {
		labelAdd.setText("Currently adding: " + engine.getWhatToAdd().name());
		MyType[][] p = engine.getGrid();
		
		(new MyThread(buttons, COLUMNS, p, 0, ROWS/4)).start();
		(new MyThread(buttons, COLUMNS, p, ROWS/4, 2*ROWS/4)).start();
		(new MyThread(buttons, COLUMNS, p, 2*ROWS/4, 3*ROWS/4)).start();
		(new MyThread(buttons, COLUMNS, p, 3*ROWS/4, ROWS)).start();
	}
}
