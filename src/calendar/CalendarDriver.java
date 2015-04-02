package calendar;

import java.io.File;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.InputMismatchException;
import java.util.Scanner;

/**
 * Driver for Calendar class. Provides a UI to create a single .ics file.
 */
public class CalendarDriver {

  /**
   * Main driver.
   */
  public static void main(String[] args) {

    Scanner keybd = new Scanner(System.in);
    Calendar calendar = new Calendar();
    String fileName;

    fileName = getFileName(keybd);

    getClassificationField(keybd, calendar);
    getLocationField(keybd, calendar);
    getPriorityField(keybd, calendar);
    getSummaryField(keybd, calendar);
    getStartDateTime(keybd, calendar);
    getEndDateTime(keybd, calendar);
    getTimeZoneField(keybd, calendar);

    makeCalendar(fileName, calendar);
  }


  /**
   * Prompts the user for a file name.
   * 
   * @param keybd - input object
   * @return the given file name
   */
  public static String getFileName(Scanner keybd) {

    String input, fileName, extension;
    File file;
    boolean gotFile = false;

    do {
      System.out.println("What would you like to name the calendar file?");
      fileName = keybd.nextLine().trim();
      if (fileName.lastIndexOf(".") == -1)
        fileName = fileName + ".ics";

      else {
        extension = fileName.substring(fileName.lastIndexOf(".") + 1, fileName.length());

        if (!extension.equalsIgnoreCase("ics"))
          fileName = fileName + ".ics";
      }

      file = new File(fileName);
      if (file.exists() && !file.isDirectory()) {

        System.out.println("The file already exists.  Would you like to overwrite it? (yes/no)  ");

        do {
          while ((input = keybd.nextLine().trim()).equals("")) {
          }

          if (input.equalsIgnoreCase("yes"))
            gotFile = true;

          else if (!input.equalsIgnoreCase("no"))
            System.out.println("Invalid response.  Please enter 'yes' or 'no.'");

        }
        while (!input.equalsIgnoreCase("yes") && !input.equalsIgnoreCase("no"));
      }

      else
        gotFile = true;

    }
    while (!gotFile);

    return fileName;
  }


  /**
   * Gets the event classification from the user, then sets it.
   * 
   * @param keybd - input object
   * @param calendar - calendar object
   */
  public static void getClassificationField(Scanner keybd, Calendar calendar) {

    String input;

    System.out.println("What is the classification of the event? (public/private/confidential)");
    while ((input = keybd.nextLine().trim()).equals("")) {
    }

    while (!input.equalsIgnoreCase("public") && !input.equalsIgnoreCase("private")
        && !input.equalsIgnoreCase("confidential")) {
      System.out.println("Invalid input.  Please enter 'public, 'private', or 'confidential.'");
      while ((input = keybd.nextLine().trim()).equals("")) {
      }
    }

    calendar.setClassification(input.toUpperCase());
  }


  /**
   * Gets the event location from the user, then sets it.
   * 
   * @param keybd - input object
   * @param calendar - calendar object
   */
  public static void getLocationField(Scanner keybd, Calendar calendar) {

    String input;

    System.out.println("What is the location of the event?");
    input = keybd.nextLine().trim();

    calendar.setLocation(input);
  }


  /**
   * Gets the event priority from the user, then sets it.
   * 
   * @param keybd - input object
   * @param calendar - calendar object
   */
  public static void getPriorityField(Scanner keybd, Calendar calendar) {

    String input;
    String regex = "^[0-9]$";
    boolean valid = true;

    System.out.println("On a scale of 0-9, 0 being the lowest and 9 being the");
    System.out.println("highest, what is the priority of the event?");

    do {
      while ((input = keybd.nextLine().trim()).equals("")) {
      }
      valid = input.matches(regex);

      if (!valid)
        System.out.println("Invalid input.  Please enter a number between 0 and 9.");
    }
    while (!valid);

    calendar.setPriority(Integer.parseInt(input));
  }


  /**
   * Gets the summary field from the user, then sets it.
   * 
   * @param keybd - input object
   * @param calendar - calendar object
   */
  public static void getSummaryField(Scanner keybd, Calendar calendar) {

    String input;

    System.out.println("Please enter a brief summary of the event.");
    while ((input = keybd.nextLine().trim()).equals("")) {
    }

    calendar.setSummary(input);
  }


  /**
   * Gets the start date and time from the user, then sets it.
   * 
   * @param keybd - input object
   * @param calendar - calendar object
   */
  public static void getStartDateTime(Scanner keybd, Calendar calendar) {

    String input, dateTime;
    boolean valid;

    System.out.println("Please enter the start date. (MM/dd/yyyy)");

    do {
      input = keybd.nextLine().trim();
      valid = isValidDate(input);

      if (!valid)
        System.out.println("Invalid input.  Please enter a valid date. (MM/dd/yyyy)");
    }
    while (!valid);

    String[] dTokens = input.split("/");
    dateTime = dTokens[2] + dTokens[0] + dTokens[1];

    System.out.println("Please enter the start time. (hh:mm)");

    do {
      input = keybd.nextLine().trim();
      valid = isValidTime(input);

      if (!valid)
        System.out.println("Invalid input.  Please enter a valid time. (hh:mm)");
    }
    while (!valid);

    String[] TTokens = input.split(":");
    dateTime = dateTime + "T" + TTokens[0] + TTokens[1] + "00";

    calendar.setDTSTART(dateTime);
  }


  /**
   * Checks if the given date is valid.
   * 
   * @param date - the date to check
   * @return true if the date is valid
   */
  public static boolean isValidDate(String date) {

    if (date.length() != 10)
      return false;

    try {
      DateFormat df = new SimpleDateFormat("MM/dd/yyyy");
      df.setLenient(false);
      df.parse(date);
      return true;
    }

    catch (ParseException e) {
      return false;
    }
  }


  /**
   * Checks if the given time is valid.
   * 
   * @param time - the time to check
   * @return true if the time is valid
   */
  public static boolean isValidTime(String time) {

    if (time.length() != 5)
      return false;

    try {
      DateFormat df = new SimpleDateFormat("HH:mm");
      df.setLenient(false);
      df.parse(time);
      return true;
    }

    catch (ParseException e) {
      return false;
    }
  }


  /**
   * Gets the end date and time from the user, then sets it.
   * 
   * @param keybd - input object
   * @param calendar - calendar object
   */
  public static void getEndDateTime(Scanner keybd, Calendar calendar) {

    String input, dateTime;
    boolean valid;

    System.out.println("Please enter the end date. (MM/dd/yyyy)");

    do {
      input = keybd.nextLine().trim();
      valid = isValidDate(input);

      if (!valid)
        System.out.println("Invalid input.  Please enter a valid date. (MM/dd/yyyy)");

      else if (!validEndDate(calendar.getDTSTART(), input)) {
        System.out.println("Invalid input.  Please enter a date that procedes the start date.");
        valid = false;
      }
    }
    while (!valid);

    String[] dTokens = input.split("/");
    dateTime = dTokens[2] + dTokens[0] + dTokens[1];

    System.out.println("Please enter the end time. (hh:mm)");

    do {
      input = keybd.nextLine().trim();
      valid = isValidTime(input);

      if (!valid)
        System.out.println("Invalid input.  Please enter a valid time. (hh:mm)");

      else if (!validEndTime(calendar.getDTSTART(), dateTime, input)) {
        System.out.println("Invalid input.  Please enter a time that procedes the start time.");
        valid = false;
      }
    }
    while (!valid);

    String[] TTokens = input.split(":");
    dateTime = dateTime + "T" + TTokens[0] + TTokens[1] + "00";

    calendar.setDTEND(dateTime);
  }


  /**
   * Checks if the end date is valid.
   * 
   * @param start - the date to compare to
   * @param end - the date to check
   * @return true if the date is on or after the start date
   */
  public static boolean validEndDate(String start, String end) {

    boolean flag = true;
    int y1 = Integer.parseInt(start.substring(0, 4));
    int M1 = Integer.parseInt(start.substring(4, 6));
    int d1 = Integer.parseInt(start.substring(6, 8));
    int y2 = Integer.parseInt(end.substring(6, 10));
    int M2 = Integer.parseInt(end.substring(0, 2));
    int d2 = Integer.parseInt(end.substring(3, 5));

    if (y1 > y2 || (y1 == y2 && M1 > M2) || (y1 == y2 && M1 == M2 && d1 > d2))
      flag = false;

    return flag;
  }


  /**
   * Check if the end time is valid.
   * 
   * @param start - the date and time to compare to
   * @param endDate - the date to check
   * @param endTime - the time to check
   * @return true if the date and time is after the start date and time
   */
  public static boolean validEndTime(String start, String endDate, String endTime) {

    boolean flag = true;
    int y1 = Integer.parseInt(start.substring(0, 4));
    int M1 = Integer.parseInt(start.substring(4, 6));
    int d1 = Integer.parseInt(start.substring(6, 8));
    int h1 = Integer.parseInt(start.substring(9, 11));
    int m1 = Integer.parseInt(start.substring(11, 13));
    int y2 = Integer.parseInt(endDate.substring(0, 4));
    int M2 = Integer.parseInt(endDate.substring(4, 6));
    int d2 = Integer.parseInt(endDate.substring(6, 8));
    int h2 = Integer.parseInt(endTime.substring(0, 2));
    int m2 = Integer.parseInt(endTime.substring(3, 5));

    if (y1 == y2 && M1 == M2 && d1 == d2)
      if (h1 > h2 || (h1 == h2 && m1 >= m2))
        flag = false;

    return flag;
  }


  /**
   * Gets the time zone field from the user, then sets it.
   * 
   * @param keybd - input object
   * @param calendar - calendar object
   */
  public static void getTimeZoneField(Scanner keybd, Calendar calendar) {

    String input, s1, s2;
    String regex = "^[+-](\\d|1[0-2])$";
    boolean valid = true;
    String[] posGMToffsets = { "(CET)", "(EET)", "(BT)", "", "", "", "", "(CCT)", "(JST)", "(GST)", "", "(NZST)" };
    String[] negGMToffsets =
        { "(WAT)", "(AT)", "", "(AST)", "(EST)", "(CST)", "(MST)", "(PST)", "(YST)", "(HST)", "(NT)", "(IDLW)" };

    System.out.println("Please enter the GMT offset of the timezone for the event (-12 through +14).");

    for (int i = 0; i < 12; i++) {
      s1 = "GMT+" + (i + 1) + " " + posGMToffsets[i];
      s2 = "GMT-" + (i + 1) + " " + negGMToffsets[i];
      System.out.format("%-16s %-16s%n", s1, s2);
    }

    s1 = "GMT+" + 13 + " ";
    s2 = "GMT+" + 14 + " ";

    System.out.format("%-16s%n", s1);
    System.out.format("%-16s%n", s2);

    do {
      while ((input = keybd.nextLine().trim()).equals("")) {
      }
      valid = (input.matches(regex) || input.equals("0") || input.equals("+13") || input.equals("+14"));

      if (!valid)
        System.out.println("Invalid input.  Please enter a signed integer between -12 and +14.");
    }
    while (!valid);

    calendar.setTimeZone("Etc/GMT" + input);
  }


  /**
   * Attempts to create the .ics file.
   * 
   * @param fName - the filename to use
   * @param calendar - calendar object
   */
  public static void makeCalendar(String fName, Calendar calendar) {

    try {
      System.out.println("Generating the calendar file...");
      calendar.makeICS(fName);
    }

    catch (InputMismatchException ime) {
      System.out.println("  Failed to create the file!");
    }

    System.out.println(" the file has been created!");
  }
}
