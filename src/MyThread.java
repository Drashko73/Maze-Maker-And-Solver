import javax.swing.ImageIcon;
import javax.swing.JButton;

public class MyThread extends Thread
{
	private JButton[][] buttons;
	private int numberOfColumns;
	private MyType[][] matrix;
	private int myStart;
	private int myEnd;
	
	
	public MyThread(JButton[][] buttons, int numberOfColumns, MyType[][] matrix, int myStart, int myEnd) {
		super();
		this.buttons = buttons;
		this.numberOfColumns = numberOfColumns;
		this.matrix = matrix;
		this.myStart = myStart;
		this.myEnd = myEnd;
	}

	@Override
	public void run()
	{
		for(int i = myStart; i < myEnd; i++)
		{
			for(int j = 0; j < numberOfColumns; j++)
				buttons[i][j].setIcon(new ImageIcon("Images//" + matrix[i][j].name() + ".png"));
		}
	}
}
