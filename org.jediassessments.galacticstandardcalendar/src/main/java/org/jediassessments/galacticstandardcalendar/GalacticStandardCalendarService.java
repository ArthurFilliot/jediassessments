package org.jediassessments.galacticstandardcalendar;

import java.time.Instant;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;

import org.jboss.logging.Logger;
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
	
	public Multi<GalacticWindow> now(int count) {
		return windowService.now(count);
	}
	
	public Multi<GalacticWindow> now(Instant instant, GalacticDate savepoint, Speed speed, int count) {
		return windowService.now(instant, savepoint, speed, count);
	}
	
	@SuppressWarnings("preview")
	public Multi<GalacticWindow> now(GalacticCalendarSavePoint savepoint, int count) {
		return windowService.now(savepoint, count);
	}

}
