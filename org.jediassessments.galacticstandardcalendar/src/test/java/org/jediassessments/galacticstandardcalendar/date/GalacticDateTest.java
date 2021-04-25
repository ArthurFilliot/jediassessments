package org.jediassessments.galacticstandardcalendar.date;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

public class GalacticDateTest {

	@Test
	public void testPlusDaysFromBeginning() {
		GalacticDate start = GalacticDate.BATTLEOFNABOO;
		GalacticDate plusOne = start.plusDays(1);
		GalacticDate plus70 = start.plusDays(70);
		GalacticDate plus71 = start.plusDays(71);
		
		assertEquals(new GalacticDate(-35,1,2), plusOne);
		assertEquals(new GalacticDate(-35,3,1), plus70);
		assertEquals(new GalacticDate(-35,4,1), plus71);
	}
	
	@Test
	public void testPlusDaysFromAnywhere() {
		GalacticDate start = new GalacticDate(-35,3,1);
		GalacticDate plusOne = start.plusDays(1);
		GalacticDate plus70 = start.plusDays(70);
		
		assertEquals(new GalacticDate(-35,4,1), plusOne);
		assertEquals(new GalacticDate(-35,6,30), plus70);
	}
	
	@Test
	public void testPlusDaysBeyondOneYear() {
		GalacticDate start = new GalacticDate(-35,16,1);
		GalacticDate plusFive = start.plusDays(5);
		
		assertEquals(new GalacticDate(-34,1,1), plusFive);
	}
	
	@Test
	public void testPlusDaysExactlyTwoYears() {
		GalacticDate start = GalacticDate.BATTLEOFNABOO;
		GalacticDate plusTwoYears = start.plusYears(2);
		GalacticDate plusFourYears = plusTwoYears.plusYears(2);
		
		assertEquals(new GalacticDate(-33,1,1), plusTwoYears);
		assertEquals(new GalacticDate(-31,1,1), plusFourYears);

	}
}
