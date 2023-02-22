import java.util.Scanner;

public class Main {
	public static void main(String[] args) {		
		try (Scanner s = new Scanner(System.in)) {
			
			System.out.print("Enter number of rows: ");
			int rows = s.nextInt();
			while(rows < 8 || rows > 30)
			{
				System.out.print("Please enter rows value in range [8,30]: ");
				rows = s.nextInt();
			}
			
			System.out.print("Enter number of columns: ");
			int columns = s.nextInt();
			while(columns < 8 || columns > 30)
			{
				System.out.print("Please enter columns value in range [8,30]: ");
				columns = s.nextInt();
			}
			
			new GUI_Class(rows, columns);
		}
	}
}
