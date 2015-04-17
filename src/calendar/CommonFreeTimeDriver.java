package calendar;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.Scanner;

/**
 * Takes as input a list of calendar files for two people on a given day,
 * then creates files which contain shared free times on that day.
 */
public class CommonFreeTimeDriver {

  /**
   * Main driver.
   */
  public static void main(String[] args) {

    FreeTimeFinder freeTime = null;
    String[] fileNames = null, fileNames1 = null, fileNames2= null;
    Scanner keybd = new Scanner(System.in);
    
    initiateProgram(freeTime, fileNames, fileNames1, fileNames2, keybd);
    keybd.close();    
  }


  /**
   * Prompts the user for a list of .ics files, then validates that list.
   * 
   * @return array of .ics files
   */
  public static String[] getFiles(Scanner keybd, String person) {

    String input, extension = "";
    String[] fileNames;
    File file;

    System.out.println("Please enter a list of calendar files for the " + person + " person (include the extension).");
    input = keybd.nextLine();
    fileNames = input.split("\\s+");

    for (int i = 0; i < fileNames.length; i++) {

	      file = new File(fileNames[i]);
	      if(!fileExists(file, extension, fileNames, i)){
	    	  return null;
	      };
    }
    return fileNames;
  }
  
  public static boolean fileExists(File file, String extension, String [] fileNames, int index){
	  if (file.exists() && !file.isDirectory()) {
          extension = fileNames[index].substring(fileNames[index].lastIndexOf(".") + 1, fileNames[index].length());
          if(!hasExtension(extension)){
        	  return false;
          };
          return true;
      }
      else
        return false;
  }
  
  public static boolean hasExtension(String extension){
	  if (!extension.equalsIgnoreCase("ics")){
          return false;
	  }else{
		  return true;
	  }
  }
  
  public static boolean initiateProgram(FreeTimeFinder freeTime,  String[] fileNames, String[] fileNames1, String[] fileNames2, Scanner keybd){
	  //collect file names for both people
	    if ( (fileNames1=getFiles(keybd, "first")) != null && (fileNames2=getFiles(keybd, "second")) != null ) {
	      
	      //put both file lists into a single array
	      fileNames = Arrays.copyOf(fileNames1, fileNames1.length + fileNames2.length);
	      System.arraycopy(fileNames2, 0, fileNames, fileNames1.length, fileNames2.length);      
	      freeTime = new FreeTimeFinder(fileNames);

	      try {
	        //parse the files to find busy times, then calculate shared free times
	        freeTime.calculateBusyTimes();
	        freeTime.calculateFreeTimes();
	        
	        //make calendar files for the shared free times
	        for (int i = 0; i < freeTime.getSize(); i++) {
	          freeTime.getFreeEvent(i).makeICS("FreeTime" + i + ".ics");
	        }
	      }
	      
	      catch (IOException ioe) {
	        System.out.println(ioe.getMessage());
	      }
	      
	      System.out.println("Calendar files noting the free time between events have been created.");
	      return true;
	    }else{
	      System.out.println("Error!  Invalid file names!");
	      return false;
	    }
  }
}
