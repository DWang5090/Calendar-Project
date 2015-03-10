import java.util.Scanner;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.io.IOException;

public class CalendarMain {

	public static void main(String[] args) throws IOException {
		Scanner input = new Scanner(System.in);
		String response = "";
		boolean correctResponse = true;
		//System.out.println("VERSION: 2.0");
		//System.out.println("CLASS: " + response.toUpperCase());
		
		while(correctResponse){
			if(response.toUpperCase().equals("PUBLIC") || response.toUpperCase().equals("PRIVATE") || response.toUpperCase().equals("CONFIDENTIAL")){
				correctResponse  = false;
			}else{
				System.out.println("How would you like to set this event as?");
				response = input.nextLine();
			}
		}
		
		FileWriter file = new FileWriter("event.ics");
		file.write("VERSION: 2.0" + "\n" + "CLASS: " + response.toUpperCase());
		
		file.close();
	}

}
