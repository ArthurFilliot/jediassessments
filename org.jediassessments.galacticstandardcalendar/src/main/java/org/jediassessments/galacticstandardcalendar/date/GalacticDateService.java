package org.jediassessments.galacticstandardcalendar.date;

import java.time.Duration;
import java.time.Instant;
import java.util.function.BiFunction;

import javax.enterprise.context.ApplicationScoped;

import org.jboss.logging.Logger;
import org.jediassessments.galacticstandardcalendar.GalacticCalendarSavePoint;
import org.jediassessments.galacticstandardcalendar.Service;
import org.jediassessments.galacticstandardcalendar.Speed;

import io.smallrye.mutiny.Multi;

@ApplicationScoped
public class GalacticDateService implements Service {
	
	public Multi<GalacticDate> now(int count) {
		return now(GalacticCalendarSavePoint.of(Instant.now(),GalacticDate.BATTLEOFNABOO,Speed.PAUSE), count);
	}
	
	public Multi<GalacticDate> now(Instant userInstant, GalacticDate galacticDate, Speed speed, int count) {
		return now(GalacticCalendarSavePoint.of(userInstant,galacticDate,speed), count);
	}
	
	public Multi<GalacticDate> now(GalacticCalendarSavePoint savepoint, int count) {
		return now(savepoint)
                .select()
                .first(count);
	}
	
	public Multi<GalacticDate> now(GalacticCalendarSavePoint savepoint) {
		Instant start 					= Instant.now();
		Instant userInstant				= savepoint.userInstant();
		GalacticDate galacticDate 		= savepoint.galacticCalendarDate();
		Speed savepointSpeed 			= savepoint.speed();
		long numSecondsSinceSavePoint 	= start.getEpochSecond() - userInstant.getEpochSecond();
		GalacticDate nowGalactic 		= savepointSpeed.getTickFun().apply(galacticDate, numSecondsSinceSavePoint);
		return Multi.createFrom()
				.ticks()
				.every(Duration.ofSeconds(1))
                .onItem()
                .transform(n -> savepointSpeed.getTickFun().apply(nowGalactic, n));
	}
}
