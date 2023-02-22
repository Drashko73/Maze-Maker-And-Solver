import javax.swing.JButton;

public class MyButton extends JButton{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private int i;
	private int j;
	
	public MyButton(int i, int j) {
		super();
		this.i = i;
		this.j = j;
	}

	public int getI() {
		return i;
	}

	public int getJ() {
		return j;
	}
}
