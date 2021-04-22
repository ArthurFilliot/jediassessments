package org.jediassessments.galacticstandardcalendar.date;

import java.time.Duration;
import java.time.Instant;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;

import org.jediassessments.galacticstandardcalendar.Service;
import org.jediassessments.galacticstandardcalendar.calendar.Speed;

import io.smallrye.mutiny.Multi;

@ApplicationScoped
public class GalacticDateService implements Service {
	
	// Business Delegate
	@Inject
	private RealTimeService timeService;
	
	public void setTimeService(RealTimeService timeService) {
		this.timeService = timeService;
	}

	public Multi<GalacticDate> now(Instant userInstant, GalacticDate galacticDate, Speed savepointSpeed, Integer count, Integer interval) {
		Instant start 					= timeService.now();
		long numSecondsSinceSavePoint 	= start.getEpochSecond() - userInstant.getEpochSecond();
		GalacticDate nowGalactic 		= savepointSpeed.getTickFun().apply(galacticDate, numSecondsSinceSavePoint);
		Multi<GalacticDate> result = Multi.createFrom()
				.ticks()
				.every(Duration.ofSeconds(interval))
                .onItem()
                .transform(n -> savepointSpeed.getTickFun().apply(nowGalactic, n*interval));
		if (count==null) {
			return result;
		}
		return result
				.select()
				.first(count);
	}
}
