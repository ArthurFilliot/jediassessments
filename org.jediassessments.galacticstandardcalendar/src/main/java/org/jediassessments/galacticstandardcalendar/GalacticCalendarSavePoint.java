package org.jediassessments.galacticstandardcalendar;

import java.time.Instant;

@SuppressWarnings("preview")
public record GalacticCalendarSavePoint(Instant userInstant, GalacticDate galacticCalendarDate, Speed speed) {
	public GalacticCalendarSavePoint() {
	      this(Instant.now(),GalacticDate.BATTLEOFNABOO, Speed.PAUSE); 
	}
	public static GalacticCalendarSavePoint of(Instant userInstant, GalacticDate galacticCalendarDate, Speed speed) {
		return new GalacticCalendarSavePoint(userInstant, galacticCalendarDate, speed);
	}
}