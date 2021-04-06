package org.jediassessments.galacticstandardcalendar.window;

import java.time.Instant;
import java.util.HashSet;
import java.util.Set;
import java.util.TreeSet;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;

import org.jboss.logging.Logger;
import org.jediassessments.galacticstandardcalendar.GalacticCalendarSavePoint;
import org.jediassessments.galacticstandardcalendar.GalacticStandardCalendarService;
import org.jediassessments.galacticstandardcalendar.Service;
import org.jediassessments.galacticstandardcalendar.Speed;
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

	public Multi<GalacticWindow> now(int count) {
		return now(GalacticCalendarSavePoint.of(Instant.now(),GalacticDate.BATTLEOFNABOO,Speed.PAUSE), count);
	}
	
	public Multi<GalacticWindow> now(Instant instant, GalacticDate savepoint, Speed speed, int count) {
		return now(GalacticCalendarSavePoint.of(instant,savepoint,speed), count);
	}
	
	public Multi<GalacticWindow> now(GalacticCalendarSavePoint savepoint, int count) {
		Integer tickCount = count;
		var dates = new TreeSet<>(Set.of(savepoint.galacticCalendarDate()));
		do {
			dates.add(savepoint.speed().getTickFun().apply(dates.last(), 1L));
		}while(--count>1);
		GalacticWindow window = new GalacticWindow(dates);
		Multi<GalacticDate> now = dateService.now(savepoint);
		return now
				.onItem()
				.transform(dt->window.switchTo(dt))
				.select()
                .first(tickCount);
	}
}
