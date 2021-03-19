package org.jediassessments.galacticstandardcalendar;

@SuppressWarnings("preview")
public record OneDay (String dt) {
	public static OneDay of(Object... args) {
		return OwnFactory.newInstance(OneDay.class, args);
	}
}
