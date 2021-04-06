package org.jediassessments.galacticstandardcalendar;

import java.time.Duration;
import java.time.Instant;
import java.util.function.BiFunction;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;

import org.jboss.logging.Logger;
import org.jediassessments.galacticstandardcalendar.date.GalacticDate;
import org.jediassessments.galacticstandardcalendar.date.GalacticDateService;

import io.smallrye.mutiny.Multi;

@ApplicationScoped
public class GalacticStandardCalendarService implements Service {
	
	private static final Logger LOG = Logger.getLogger(GalacticStandardCalendarService.class);
	
	// Business Delegate
	@Inject
	private GalacticDateService dateService;
	
	public Multi<GalacticDate> now(int count) {
		return dateService.now(count);
	}
	
	public Multi<GalacticDate> now(Instant instant, GalacticDate savepoint, Speed speed, int count) {
		return dateService.now(instant, savepoint, speed, count);
	}
	
	@SuppressWarnings("preview")
	public Multi<GalacticDate> now(GalacticCalendarSavePoint savepoint, int count) {
		return dateService.now(savepoint, count);
	}

}
