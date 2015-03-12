import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.io.File;
import java.util.Scanner;
	
public class CalendarProject {
	

	/**
	 * @param args
	 * @throws FileNotFoundException 
	 */
	// Passed args should be in the form
	// Summary, Location, Priority, bleh
	public static void main(String[] args) throws FileNotFoundException {
		updateFile(args);

	}
	
	public static void updateFile(String[] contents) throws FileNotFoundException {
		Scanner input = new Scanner(System.in);
		System.out.println("Name / Summary of event: ");
		String summary = input.nextLine();
		PrintWriter printWriter = new PrintWriter(summary.concat(".ics"));
		System.out.println("Location of event:");
		String location = input.nextLine();
		System.out.println("Priority of event:");
		String priority = input.nextLine();
	    //PrintWriter printWriter = new PrintWriter (contents[0].substring(contents[0].indexOf(':')+1).concat(".ics"));
	    printWriter.println("BEGIN:VCALENDAR");
	    printWriter.println("VERSION:2.0");
	    printWriter.println("CALSCALE:GREGORIAN");
	    printWriter.println("X-WR-TIMEZONE:Pacific/Honolulu");
	    printWriter.println("BEGIN:VEVENT");
	    printWriter.println("DTSTART:20150310T223000Z");
	    printWriter.println("DTEND:20150311T040000Z");
	    printWriter.println("SUMMARY:" + summary);
	    printWriter.println("LOCATION:" + location);
	    printWriter.println("PRIORITY:" + priority);
	  /*  for (String cur : contents) {
	   	printWriter.println(cur);
	    }
	    */
	    printWriter.println("END:VEVENT");
	    printWriter.println("END:VCALENDAR");
	    printWriter.close();
	    
	}

}
