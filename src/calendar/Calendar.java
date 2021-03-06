package calendar;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.TimeZone;

/**
 * Calendar class that generates a .ics file
 * based off of the given parameters.
 */
public class Calendar {

  private String version;               //version field
  private String classification;        //classification field
  private String location;              //location field
  private int priority;                 //priority field
  private String summary;               //summary field
  private String DTSTART;               //date time start field
  private String DTEND;                 //date time end field
  private String timeZone;              //time zone ID field
  private final static String DATE_FORMAT = "yyyyMMdd'T'HHmmss";        //date time format
  private final static String[] validID = TimeZone.getAvailableIDs();   //list of valid time zone IDs


  /**
   * Calendar constructor.
   */
  public Calendar() {
    version = "2.0";
  }


  /**
   * @return version field
   */
  public String getVersion() {
    return version;
  }


  /**
   * @return classification field
   */
  public String getClassification() {
    return classification;
  }


  /**
   * @param classi - the classification to use
   * @throws IllegalArgumentException - if the given classification is invalid
   */
  public void setClassification(String classi) throws IllegalArgumentException {

    if (!classi.equals("PUBLIC") && !classi.equals("PRIVATE") && !classi.equals("CONFIDENTIAL"))
      throw new IllegalArgumentException("Invalid classification.");

    classification = classi;
  }


  /**
   * @return location field
   */
  public String getLocation() {
    return location;
  }


  /**
   * @param loc - the location to use
   */
  public void setLocation(String loc) {
    location = loc;
  }


  /**
   * @return the priority field
   */
  public int getPriority() {
    return priority;
  }


  /**
   * @param pri - the priority to use
   * @throws IllegalArgumentException - if the given priority is invalid
   */
  public void setPriority(int pri) throws IllegalArgumentException {

    if (pri < 0 || pri > 9)
      throw new IllegalArgumentException("Invalid priority level.");

    priority = pri;
  }


  /**
   * @return summary field
   */
  public String getSummary() {
    return summary;
  }


  /**
   * @param summ - the summary to use
   */
  public void setSummary(String summ) {
    summary = summ;
  }


  /**
   * @return the date time start field
   */
  public String getDTSTART() {
    return DTSTART;
  }


  /**
   * @param dtSTART - the date time start to use
   * @throws IllegalArgumentException - if the given date time is invalid
   */
  public void setDTSTART(String dtSTART) throws IllegalArgumentException {

    try {
      DateFormat df = new SimpleDateFormat(DATE_FORMAT);
      df.setLenient(false);
      df.parse(dtSTART);
    }

    catch (ParseException e) {
      throw new IllegalArgumentException("Invalid date time format.");
    }

    DTSTART = dtSTART;
  }


  /**
   * @return the date time end field
   */
  public String getDTEND() {
    return DTEND;
  }


  /**
   * @param dtEND - the date time end to use
   * @throws IllegalArgumentException - if the given date time is invalid
   */
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

    //verify that the end date time comes after the start date time
    if (y1 > y2 || (y1 == y2 && M1 > M2) || (y1 == y2 && M1 == M2 && d1 > d2))
      throw new IllegalArgumentException("End date cannot come before start date.");

    else if (y1 == y2 && M1 == M2 && d1 == d2)
      if (h1 > h2 || (h1 == h2 && m1 >= m2))
        throw new IllegalArgumentException("End time cannot come before start time.");

    DTEND = dtEND;
  }


  /**
   * @return the time zone ID field
   */
  public String getTimeZone() {
    return timeZone;
  }


  /** 
   * @param tzid - the time zone ID to use
   * @throws IllegalArgumentException - if the given time zone ID is invalid
   */
  public void setTimeZone(String tzid) throws IllegalArgumentException {

    boolean valid = false;

    for (int i = 0; i < validID.length; i++)
      if (tzid.equals(validID[i]))
        valid = true;

    if (!valid)
      throw new IllegalArgumentException("Invalid timezone id.");

    timeZone = tzid;
  }


  /**
   * Attempts to create a .ics field with the given name.
   * 
   * @param fName - the filename to use
   * @throws IllegalArgumentException - if there was an error opening the file
   */
  public void makeICS(String fName) throws IllegalArgumentException {

    String extension = fName.substring(fName.lastIndexOf(".") + 1, fName.length());

    if (!extension.equalsIgnoreCase("ics"))
      throw new IllegalArgumentException("Invalid file extension.");

    try {
      PrintWriter writer = new PrintWriter(new FileWriter(fName, false));

      writer.println("BEGIN:VCALENDAR");
      writer.println("VERSION:" + version);
      writer.println("BEGIN:VEVENT");
      writer.println("CLASS:" + classification);

      if (location != null && !location.equals(""))
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

    catch (IOException ioe) {
      System.out.println("Could not open file: " + ioe.getMessage());
    }
  }
}