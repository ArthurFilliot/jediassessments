package org.jediassessments.galacticstandardcalendar;

import java.time.Instant;

@SuppressWarnings("preview")
public record GalacticCalendarSavePoint(Instant userInstant, GalacticDate galacticCalendarDate) {
	public GalacticCalendarSavePoint() {
	      this(Instant.now(),GalacticDate.BATTLEOFNABOO); 
	}
	public static GalacticCalendarSavePoint of(Instant userInstant, GalacticDate galacticCalendarDate) {
		return new GalacticCalendarSavePoint(userInstant,galacticCalendarDate);
	}
}