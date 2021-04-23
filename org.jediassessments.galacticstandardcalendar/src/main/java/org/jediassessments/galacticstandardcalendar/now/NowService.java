package org.jediassessments.galacticstandardcalendar.now;

import java.time.Instant;
import java.util.Map;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;

import org.jboss.logging.Logger;
import org.jediassessments.galacticstandardcalendar.Service;
import org.jediassessments.galacticstandardcalendar.calendar.Speed;
import org.jediassessments.galacticstandardcalendar.date.GalacticDate;
import org.jediassessments.galacticstandardcalendar.date.GalacticDateService;

import io.smallrye.mutiny.Multi;

@ApplicationScoped
public class NowService implements Service {

	private static final Logger LOG = Logger.getLogger(NowService.class);

	// Business Delegate
	@Inject
	private GalacticDateService dateService;

	public void setDateService(GalacticDateService dateService) {
		this.dateService = dateService;
	}
	
	public Multi<Map<Long, GalacticDate>> now(Instant userInstant, GalacticDate start, Speed speed, Integer count, Integer interval) {
		NowBuilder nowBuilder = new NowBuilder(userInstant, speed, interval);
		Multi<GalacticDate> now = dateService.now(userInstant, start, speed, count, interval);
		return	now
				.onItem()
				.transform(dt->nowBuilder.build(dt))
				.select()
                .first(count);
	}
}
