package org.jediassessments.galacticstandardcalendar.date;

import java.time.Duration;
import java.time.Instant;
import java.util.function.BiFunction;

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
	
	public GalacticDate next(GalacticDate from, Speed speed, Long nbSeconds) {
		BiFunction<GalacticDate, Long, GalacticDate> tickfun = 
		switch(speed) {
			case ONEDAY_PER_SEC 
				-> GalacticDate::plusDays;
			case ONEWEEK_PER_SEC 
				-> GalacticDate::plusWeeks;
			case TWOYEARS_PER_MINUTE 
				->	new BiFunction<GalacticDate, Long, GalacticDate>() {
						@Override
						public GalacticDate apply(GalacticDate t, Long u) {
							System.out.print(t + " : ");
							System.out.print(u + " - ");
							long nbDays = u*12 + ((u/60)*18);// One galactic year = 369 days
							System.out.print(nbDays);
							GalacticDate dt =  t.plusDays(nbDays); 
							System.out.println(" : " + dt);
							return dt;
						}
					};
			case PAUSE 
				-> (gdt,n)->gdt;
		};
		return tickfun.apply(from, nbSeconds);
	}

	public Multi<GalacticDate> now(Instant userInstant, GalacticDate galacticDate, Speed savepointSpeed, Integer count, Integer interval) {
		Instant start 					= timeService.now();
		long numSecondsSinceSavePoint 	= start.getEpochSecond() - userInstant.getEpochSecond();
		GalacticDate nowGalactic 		= next(galacticDate, savepointSpeed, numSecondsSinceSavePoint);
		Multi<GalacticDate> result = Multi.createFrom()
				.ticks()
				.every(Duration.ofSeconds(interval))
                .onItem()
                .transform(n -> this.next(nowGalactic, savepointSpeed, n*interval));
		if (count==null) {
			return result;
		}
		return result
				.select()
				.first(count);
	}
}
