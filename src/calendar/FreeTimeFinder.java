package calendar;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Scanner;

/**
 * Class used to find the free time on a given day.
 */
public class FreeTimeFinder {

  private String[] fileNames; // array of file names to sort through
  private ArrayList<TimeNode> busy; // array of busy times, sort by start time
  private ArrayList<Calendar> freeTimes; // array of free time events
  private int size; // size of array of free time
  private String date; // the date of free times
  private String tzid; // the time zone id of free times


  /**
   * Constructor for free time finder.
   * 
   * @param fNames
   */
  FreeTimeFinder(String[] fNames) {

    this.fileNames = fNames;
    this.busy = new ArrayList<TimeNode>();
    this.freeTimes = new ArrayList<Calendar>();
    this.size = 0;
    this.date = null;
    this.tzid = null;
  }


  /**
   * @return size of list
   */
  public int getSize() {
    return this.size;
  }


  /**
   * Returns the free time event of a
   * 
   * @param i - index of free time event
   * @return free time event
   */
  public Calendar getFreeEvent(int i) {
    return this.freeTimes.get(i);
  }


  /**
   * Parse files for start and end times, then add them to a list sorted by start times.
   * 
   * @param fileNames - array of file names to sort through
   * @throws IOException - if a file could not be open, or if there were mismatching dates
   */
  public void calculateBusyTimes() throws IOException {

    File file;
    Scanner input;
    String nextLine;
    String[] tokens;
    TimeNode temp;
    String startDate, endDate;

    try {

      // cycle through the list of files
      for (int i = 0; i < this.fileNames.length; i++) {

        file = new File(this.fileNames[i]);
        input = new Scanner(file);

        this.busy.add(new TimeNode());

        // search for the event's start and end times
        while (input.hasNext()) {

          nextLine = input.nextLine();
          tokens = nextLine.split(":");

          // save the start time, end time, and tzid of the event
          if (tokens[0].equals("DTSTART"))
            this.busy.get(i).setStart(tokens[1]);

          if (tokens[0].equals("DTEND"))
            this.busy.get(i).setEnd(tokens[1]);

          if (tokens[0].equals("TZID"))
            this.busy.get(i).setTZID(tokens[1]);
        }

        input.close();
      }

      // check if the start date, end date, and tzid of each event matches
      temp = this.busy.get(0);
      startDate = temp.startDate;
      endDate = temp.endDate;

      // also update class variables for date and time zone
      this.date = startDate;
      this.tzid = temp.tzid;

      for (int i = 1; i < busy.size(); i++) {

        temp = this.busy.get(i);

        if (!startDate.equals(endDate))
          throw new IOException("The start date and end date must be the same.");

        if (!temp.startDate.equals(startDate) || !temp.endDate.equals(endDate) || !temp.tzid.equals(this.tzid))
          throw new IOException("The start dates, end dates, or time zones did not match.");
      }

      // sort the list by start times
      Collections.sort(this.busy);
    }

    catch (IOException ioe) {
      throw new IOException("Could not open file: " + ioe.getMessage());
    }
  }


  /**
   * Finds the free time between events and stores that info in an array.
   */
  public void calculateFreeTimes() {

    int i = 0;
    TimeNode temp, current;

    current = this.busy.get(i);

    // compare first event to the start of the day
    if (Integer.parseInt(current.startTime) > 0) {
      createEvent("000000", current.startTime);
    }

    // cycle through the list of events and compare two at a time
    while (i < this.busy.size() - 1) {

      temp = this.busy.get(i + 1);

      // disregard an event if the current event ends after it does
      if (Integer.parseInt(current.endTime) > Integer.parseInt(temp.endTime)) {
        i++;
        continue;
      }

      // create free time event if there is free time between
      // the two events being compared, then update the current event
      else if (Integer.parseInt(current.endTime) < Integer.parseInt(temp.startTime)) {
        createEvent(current.endTime, temp.startTime);
        current = temp;
      }

      // update the current event if it ends before the next event ends
      else if (Integer.parseInt(current.endTime) < Integer.parseInt(temp.endTime))
        current = temp;

      i++;
    }

    // compare last event to the end of the day
    if (Integer.parseInt(current.endTime) < 235900) {
      createEvent(current.endTime, "235900");
    }

    // update size of free time array
    this.size = this.freeTimes.size();
  }


  /**
   * Adds a free time event to the list of free times.
   */
  public void createEvent(String startTime, String endTime) {

    Calendar event = new Calendar();

    event.setClassification("PUBLIC");
    event.setPriority(1);
    event.setSummary("POSSIBLE MEETING TIME");
    event.setTimeZone(this.tzid);
    event.setDTSTART(this.date + "T" + startTime);
    event.setDTEND(this.date + "T" + endTime);

    this.freeTimes.add(event);
  }

  /**
   * Node class which saves the start and end date times of an event.
   */
  public class TimeNode implements Comparable<TimeNode> {

    private String startDate; // start date
    private String endDate; // end date
    private String startTime; // start time, saved as a String to preserve leading zeroes
    private String endTime; // end time, saved as a String to preserve leading zeroes
    private String tzid; // time zone id


    /**
     * Default constructor of the TimeNode class.
     */
    TimeNode() {
      this.startDate = null;
      this.endDate = null;
      this.startTime = null;
      this.endTime = null;
      this.tzid = null;
    }


    /**
     * Sets the start time and date variables.
     * 
     * @param dtstart - starting date time
     */
    private void setStart(String dtstart) {
      this.startDate = dtstart.substring(0, 8);
      this.startTime = dtstart.substring(9, 15);
    }


    /**
     * Sets the end time and date variables.
     * 
     * @param dtend - ending date time
     */
    private void setEnd(String dtend) {
      this.endDate = dtend.substring(0, 8);
      this.endTime = dtend.substring(9, 15);
    }


    /**
     * Sets the time zone id variable.
     * 
     * @param tzid - time zone id
     */
    private void setTZID(String tzid) {
      this.tzid = tzid;
    }


    @Override
    public int compareTo(TimeNode node) {
      return Integer.parseInt(this.startTime) - Integer.parseInt(node.startTime);
    }
  }

}
