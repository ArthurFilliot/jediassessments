package org.jediassessments.galacticstandardcalendar.date;

public interface GalacticDateFormatter {
	default String format(GalacticDate dt) {
		int dayOrdinal = (dt.getPeriod().getDayInYear() + dt.getDay() -2) % 5;
		GalacticDateDay day = GalacticDateDay.values()[dayOrdinal];
		return day + " " + dt.getDay() + " " + dt.getPeriod() + " " + dt.getYear();
	}
}