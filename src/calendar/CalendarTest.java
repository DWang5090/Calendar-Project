package calendar;

import static org.junit.Assert.*;

import org.junit.Test;

public class CalendarTest {

	@Test 
	public final void testCalendar() {
		Calendar cal1 = new Calendar();
		assertNotNull(cal1);
	}

	@Test
	public final void testGetVersion() {
		Calendar cal2 = new Calendar();
		assertEquals("2.0", cal2.getVersion());
		assertNotEquals("3.1", cal2.getVersion());
	}

	@Test 
	public final void testGetClassification() {
		Calendar cal3 = new Calendar();
		assertEquals(null, cal3.getClassification());
		
		assertNotEquals("confidential", cal3.getClassification());
	}
	
	@Test
	public final void testGetAndSetClassification(){
		Calendar cal4 = new Calendar();
		cal4.setClassification("PUBLIC");
		assertEquals("PUBLIC", cal4.getClassification());
		assertNotEquals(null, cal4.getClassification());
		
		cal4.setClassification("PRIVATE");
		assertEquals("PRIVATE", cal4.getClassification());
		assertNotEquals("PUBLIC", cal4.getClassification());
		
		cal4.setClassification("CONFIDENTIAL");
		assertEquals("CONFIDENTIAL", cal4.getClassification());
		assertNotEquals("PRIVATE", cal4.getClassification());
		
	}
	
	@Test (expected = IllegalArgumentException.class)
	public final void testSetClassificationCaseSensitive(){
		Calendar cal5 = new Calendar();
		cal5.setClassification("public");
	}

	@Test (expected = IllegalArgumentException.class)
	public final void testSetClassificationOtherChar(){
		Calendar cal5 = new Calendar();
		cal5.setClassification("dog");
	}
	
	@Test
	public final void testGetLocation() {
		Calendar cal6 = new Calendar();
		assertEquals(null,cal6.getLocation());
	
		assertNotEquals("Sinclair",cal6.getLocation());
	}
	
	@Test
	public final void testGetandSetLocation(){
		Calendar cal7 = new Calendar();
		cal7.setLocation("Hamilton Library");
		assertEquals("Hamilton Library", cal7.getLocation());
		
		assertNotEquals("Sinclair",cal7.getLocation());
	}

	@Test
	public final void testGetPriority() {
		Calendar cal8 = new Calendar();
		assertEquals(0, cal8.getPriority());
		
		assertNotEquals(9, cal8.getPriority());
	}

	@Test
	public final void testGetAndSetPriority() {
		Calendar cal9 = new Calendar();
		
		cal9.setPriority(8);
		assertEquals(8,cal9.getPriority());
		assertNotEquals(7,cal9.getPriority());
		
		cal9.setPriority(6);
		assertEquals(6,cal9.getPriority());
		assertNotEquals(8,cal9.getPriority());
	}

	@Test (expected = IllegalArgumentException.class)
	public final void testSetPriorityNegativeNumber(){
		Calendar cal10 = new Calendar();
		cal10.setPriority(-1);
	}
	
	@Test (expected = IllegalArgumentException.class)
	public final void testSetPriorityGreaterThanNine(){
		Calendar cal11 = new Calendar();
		cal11.setPriority(12);
	}
	
	@Test
	public final void testGetSummary() {
		Calendar cal12 = new Calendar();
		assertEquals(null, cal12.getSummary());
	}

	@Test
	public final void testGetandSetSummary() {
		Calendar cal13 = new Calendar();
		
		cal13.setSummary("Studying finals");
		assertEquals("Studying finals", cal13.getSummary());
		
		assertNotEquals("Review", cal13.getSummary());
	}

	@Test
	public final void testGetDTSTART() {
		Calendar cal14 = new Calendar();
		assertEquals(null,cal14.getDTSTART());
	}

	@Test
	public final void testGetandSetDTSTART() {
		Calendar cal15 = new Calendar();
		cal15.setDTSTART("20150515T061200");
		assertEquals("20150515T061200", cal15.getDTSTART());
		assertNotEquals("20140617T092300", cal15.getDTSTART());
	}

	@Test (expected = IllegalArgumentException.class)
	public final void testSetDTSTARTError(){
		Calendar cal16 = new Calendar();
		cal16.setDTSTART("05/15/2015");
	}
	
	@Test
	public final void testGetDTEND() {
		Calendar cal17 = new Calendar();
		assertEquals(null,cal17.getDTSTART());
	}

	@Test
	public final void testGetAndSetDTEND() {
		Calendar cal18 = new Calendar();
		cal18.setDTSTART("20150515T061200");
		assertEquals("20150515T061200", cal18.getDTSTART());
		assertNotEquals("20140617T092300", cal18.getDTSTART());
	}

	@Test (expected = IllegalArgumentException.class)
	public final void testInvalidDTEND(){
		Calendar cal19 = new Calendar();
		cal19.setDTEND("05/15/2014");
	}
	
	@Test (expected = IllegalArgumentException.class)
	public final void testEndYearBeforeStartYear(){
		Calendar cal19 = new Calendar();
		cal19.setDTSTART("20150515T061200");
		cal19.setDTEND("19900515T061200");
	}
	
	@Test (expected = IllegalArgumentException.class)
	public final void testEndMonthBeforeStartMonth(){
		Calendar cal20 = new Calendar();
		cal20.setDTSTART("20150515T061200");
		cal20.setDTEND("20150415T061200");
	}
	
	@Test (expected = IllegalArgumentException.class)
	public final void testEndDayBeforeStartDay(){
		Calendar cal21 = new Calendar();
		cal21.setDTSTART("20150515T061200");
		cal21.setDTEND("20150503T061200");
	}
	
	@Test (expected = IllegalArgumentException.class)
	public final void testEndHourBeforeStartHour(){
		Calendar cal22 = new Calendar();
		cal22.setDTSTART("20150515T061200");
		cal22.setDTEND("20150515T011200");
	}
	
	@Test (expected = IllegalArgumentException.class)
	public final void testEndMinuteBeforeStartMinute(){
		Calendar cal22 = new Calendar();
		cal22.setDTSTART("20150515T061200");
		cal22.setDTEND("20150515T060000");
	}
	
	@Test
	public final void testGetTimeZone() {
		Calendar cal23 = new Calendar();
		assertNull(cal23.getTimeZone());
	}

	@Test
	public final void testGetAndSetTimeZone() {
		Calendar cal24 = new Calendar();
		cal24.setTimeZone("Etc/GMT-10");
		assertEquals("Etc/GMT-10",cal24.getTimeZone());
	}

	@Test (expected = IllegalArgumentException.class)
	public final void testInvalidTimeZone() {
		Calendar cal25 = new Calendar();
		cal25.setTimeZone("Etc/GMT+25");
	}
	
	@Test (expected = IllegalArgumentException.class)
	public final void testInvalidExtension() {
		Calendar cal26 = new Calendar();
		cal26.makeICS("test.txt");
		
	}

}
