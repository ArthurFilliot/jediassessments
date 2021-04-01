package org.jediassessments.galacticstandardcalendar;

import org.junit.jupiter.api.Test;

public class GalacticDateFormatterTest {

	@Test
	public void testFormats() {
		// <yy>, <pp>, <dd> 	: -35, 06, 25
		// <y>, <p>, <W>, <EE> 	: -35, 06, 4, Atunda
		// <yy>, <ppp>, <dd> 	: -35, Telona, 25
		// <yy>, <DD> 			: -35, 131
		// <yy>, <w> 			: -35, 26
	}
}
