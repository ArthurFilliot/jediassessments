package org.jediassessments.galacticstandardcalendar.calendar;

import java.time.Instant;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;

import org.jboss.logging.Logger;
import org.jediassessments.galacticstandardcalendar.Service;
import org.jediassessments.galacticstandardcalendar.date.GalacticDate;
import org.jediassessments.galacticstandardcalendar.window.GalacticWindow;
import org.jediassessments.galacticstandardcalendar.window.GalacticWindowService;

import io.smallrye.mutiny.Multi;

@ApplicationScoped
public class GalacticStandardCalendarService implements Service {
	
	private static final Logger LOG = Logger.getLogger(GalacticStandardCalendarService.class);
	
	// Business Delegate
	@Inject
	private GalacticWindowService windowService;
	
	public Multi<GalacticWindow> now(int count, int interval) {
		GalacticCalendarSavePoint savepoint = new GalacticCalendarSavePoint();
		return windowService.now(savepoint.userInstant(), savepoint.galacticCalendarDate(), savepoint.speed(), count, interval);
	}
	
	public Multi<GalacticWindow> now(GalacticCalendarSavePoint savepoint, int count, int interval) {
		return windowService.now(savepoint.userInstant(), savepoint.galacticCalendarDate(), savepoint.speed(), count, interval);
	}

}
