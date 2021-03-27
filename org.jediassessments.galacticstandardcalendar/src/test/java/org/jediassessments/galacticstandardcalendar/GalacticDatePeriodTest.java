package org.jediassessments.galacticstandardcalendar;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class GalacticDatePeriodTest {
	
	@Test
	public void getDayInYearTest() {
		Integer dayOfElona = GalacticDatePeriod.Elona.getDayInYear();
		Integer dayOfKelona = GalacticDatePeriod.Kelona.getDayInYear();
		Integer dayOfTapani = GalacticDatePeriod.Tapani.getDayInYear();
		Integer dayOfSelona = GalacticDatePeriod.Selona.getDayInYear();

		Assertions.assertAll(
				() -> assertEquals(1,dayOfElona),
				() -> assertEquals(1+(5*7),dayOfKelona),
				() -> assertEquals(1+(5*7)+(5*7),dayOfTapani),
				() -> assertEquals(1+(5*7)+(5*7)+1,dayOfSelona));
	}
	
	@Test
	public void getTotalNbDaysInYear() {
		Integer lengthOfYear = GalacticDatePeriod.getLenghtOfYear();
		
		assertEquals(369, lengthOfYear);
	}

}
