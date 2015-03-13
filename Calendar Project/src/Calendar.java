import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.TimeZone;

public class Calendar {
	
	private String version;
	private String classification;
	private String location;
	private int priority;
	private String summary;
	private String DTSTART;
	private String DTEND;
	private String timeZone;
	private final static String DATE_FORMAT = "yyyyMMdd'T'HHmmss";
	private final static String[] validID = TimeZone.getAvailableIDs();
	
	
	public Calendar() {
		version = "2.0";
	}
	
	public String getVersion() {
		return version;
	}
	
	public String getClassification() {
		return classification;
	}
	public void setClassification(String classi) throws IllegalArgumentException {
		
		if ( !classi.equals("PUBLIC") && !classi.equals("PRIVATE") && !classi.equals("CONFIDENTIAL"))
			throw new IllegalArgumentException("Invalid classification.");
		
		classification = classi;
	}
	public String getLocation() {
		return location;
	}
	public void setLocation(String loc) {
		location = loc;
	}
	public int getPriority() {
		return priority;
	}
	public void setPriority(int pri) throws IllegalArgumentException {
		
		if ( pri < 0 || pri > 9 )
			throw new IllegalArgumentException("Invalid priority level.");
		
		priority = pri;
	}
	public String getSummary() {
		return summary;
	}
	public void setSummary(String summ) {
		summary = summ;
	}
	public String getDTSTART() {
		return DTSTART;
	}
	public void setDTSTART(String dtSTART) throws IllegalArgumentException {
		
		try {
			DateFormat df = new SimpleDateFormat(DATE_FORMAT);
			df.setLenient(false);
			df.parse(dtSTART);
		}
        
		catch (ParseException e) {
			throw new IllegalArgumentException("Invalid date tiem format.");
		}
		
		DTSTART = dtSTART;
	}
	public String getDTEND() {
		return DTEND;
	}
	public void setDTEND(String dtEND) throws IllegalArgumentException {
		
		try {
			DateFormat df = new SimpleDateFormat(DATE_FORMAT);
			df.setLenient(false);
			df.parse(dtEND);
		}
        
		catch (ParseException e) {
			throw new IllegalArgumentException("Invalid date time format.");
		}
		
		int y1 = Integer.parseInt(DTSTART.substring(0, 4));
		int M1 = Integer.parseInt(DTSTART.substring(4, 6));
		int d1 = Integer.parseInt(DTSTART.substring(6, 8));
		int h1 = Integer.parseInt(DTSTART.substring(9, 11));
		int m1 = Integer.parseInt(DTSTART.substring(11, 13));
		int y2 = Integer.parseInt(dtEND.substring(0, 4));
		int M2 = Integer.parseInt(dtEND.substring(4, 6));
		int d2 = Integer.parseInt(dtEND.substring(6, 8));
		int h2 = Integer.parseInt(dtEND.substring(9, 11));
		int m2 = Integer.parseInt(dtEND.substring(11, 13));
		
		if ( y1 > y2 || (y1 == y2 && M1 > M2) || (y1 == y2 && M1 == M2 && d1 > d2))
			throw new IllegalArgumentException("End date cannot come before start date.");
		
		else if ( y1 == y2 && M1 == M2 && d1 == d2 )
			if ( h1 > h2 || (h1 == h2 && m1 >= m2) )
				throw new IllegalArgumentException("End time cannot come before start time.");
		
		DTEND = dtEND;
	}
	public String getTimeZone() {
		return timeZone;
	}
	public void setTimeZone(String tzid) throws IllegalArgumentException {
		
		boolean valid = false;
		
		for (int i = 0; i < validID.length; i++)
			if (tzid.equals(validID[i]))
				valid = true;
		
		if (!valid)
			throw new IllegalArgumentException("Invalid timezone id.");
		
		timeZone = tzid;
	}
	
	public void makeICS(String fName) throws IllegalArgumentException {
		
		String extension = fName.substring(fName.lastIndexOf(".") + 1, fName.length());
		
		if (!extension.equalsIgnoreCase("ics") )
			throw new IllegalArgumentException("Invalid file extension.");
		
		try {
			PrintWriter writer = new PrintWriter(new FileWriter(fName, false));
				
			writer.println("BEGIN:VCALENDAR");
			writer.println("VERSION:" + version);
			writer.println("BEGIN:VEVENT");
			writer.println("CLASS:" + classification);
			
			if ( !location.equals("") )
				writer.println("LOCATION:" + location);
			
			writer.println("PRIORITY:" + priority);
			writer.println("SUMMARY:" + summary);
			writer.println("DTSTART:" + DTSTART);
			writer.println("DTEND:" + DTEND);
			writer.println("TZID:" + timeZone);
			writer.println("END:VEVENT");
			writer.println("END:VCALENDAR");
			writer.close();
		}
		
		catch(IOException ioe) {
			System.out.println("Could not open file: " + ioe.getMessage());
		}
	}
}