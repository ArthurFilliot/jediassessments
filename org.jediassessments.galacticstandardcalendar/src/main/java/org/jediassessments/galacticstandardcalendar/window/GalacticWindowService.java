package org.jediassessments.galacticstandardcalendar.window;

import java.time.Instant;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;
import java.util.function.Supplier;
import java.util.stream.Collectors;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;

import org.jboss.logging.Logger;
import org.jediassessments.galacticstandardcalendar.Service;
import org.jediassessments.galacticstandardcalendar.calendar.GalacticCalendarSavePoint;
import org.jediassessments.galacticstandardcalendar.calendar.GalacticStandardCalendarService;
import org.jediassessments.galacticstandardcalendar.calendar.Speed;
import org.jediassessments.galacticstandardcalendar.date.GalacticDate;
import org.jediassessments.galacticstandardcalendar.date.GalacticDateService;

import io.smallrye.mutiny.Multi;

@ApplicationScoped
public class GalacticWindowService implements Service {

	private static final Logger LOG = Logger.getLogger(GalacticWindowService.class);

	// Business Delegate
	@Inject
	private GalacticDateService dateService;

	public void setDateService(GalacticDateService dateService) {
		this.dateService = dateService;
	}
	
	public Multi<GalacticWindow> now(Instant userInstant, GalacticDate start, Speed speed, Integer count, Integer interval) {
		GalacticWindowBuilder windowBuilder = new GalacticWindowBuilder(userInstant, speed, interval);
		Multi<GalacticDate> now = dateService.now(userInstant, start, speed, count, interval);
		return now
				.onItem()
				.transform(dt->windowBuilder.buildWindow(dt))
				.select()
                .first(count);
	}
}
