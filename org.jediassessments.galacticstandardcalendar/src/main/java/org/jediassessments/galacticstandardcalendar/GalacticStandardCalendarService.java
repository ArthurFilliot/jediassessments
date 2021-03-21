package org.jediassessments.galacticstandardcalendar;

import java.time.Duration;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;

import javax.enterprise.context.ApplicationScoped;

import org.jboss.logging.Logger;

import io.smallrye.mutiny.Multi;

@ApplicationScoped
public class GalacticStandardCalendarService implements Service {
	
	private static final Logger LOG = Logger.getLogger(GalacticStandardCalendarService.class);
	
	public Multi<GalacticDate> now(Instant start, int count) {
		GalacticDate battleofnaboo = new GalacticDate(-35,1,1);
		long numSecondsAfterStart = LocalDateTime.now().toEpochSecond(ZoneOffset.UTC) - start.getEpochSecond();
		LOG.info("numSeconds : " + numSecondsAfterStart);
		numSecondsAfterStart = 0;
		GalacticDate nowGalactic = battleofnaboo.plusDays(numSecondsAfterStart); 
		return Multi.createFrom()
				.ticks()
				.every(Duration.ofSeconds(1))
                .onItem()
                .transform(n -> (GalacticDate)nowGalactic.plusDays(n))
                .select().first(count);
	}

}
