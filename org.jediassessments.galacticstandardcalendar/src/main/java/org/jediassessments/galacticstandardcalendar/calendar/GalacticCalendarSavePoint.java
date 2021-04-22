package org.jediassessments.galacticstandardcalendar.calendar;

import java.time.Instant;

import org.jediassessments.galacticstandardcalendar.date.GalacticDate;

@SuppressWarnings("preview")
public record GalacticCalendarSavePoint(Instant userInstant, GalacticDate galacticCalendarDate, Speed speed) {
	public GalacticCalendarSavePoint() {
	      this(Instant.now(),GalacticDate.BATTLEOFNABOO, Speed.ONEDAY_PER_SEC); 
	}
	public static GalacticCalendarSavePoint of(Instant userInstant, GalacticDate galacticCalendarDate, Speed speed) {
		return new GalacticCalendarSavePoint(userInstant, galacticCalendarDate, speed);
	}
}