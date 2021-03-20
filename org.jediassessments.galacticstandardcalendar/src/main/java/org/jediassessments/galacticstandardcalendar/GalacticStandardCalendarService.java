package org.jediassessments.galacticstandardcalendar;

import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.ZoneOffset;

import javax.enterprise.context.ApplicationScoped;

import io.smallrye.mutiny.Multi;

@ApplicationScoped
public class GalacticStandardCalendarService {
	
	public Multi<GalacticDate> now(LocalDate start) {
		GalacticDate battleofnaboo = new GalacticDate(-35,1,1);
		LocalDate now = LocalDate.now();
		long numSecondsAfterStart = now.toEpochSecond(LocalTime.MIN, ZoneOffset.UTC) - start.toEpochSecond(LocalTime.MIN, ZoneOffset.UTC);
		GalacticDate nowGalactic = battleofnaboo.plusDays(numSecondsAfterStart); 
		return Multi.createFrom()
				.ticks()
				.every(Duration.ofSeconds(1))
                .onItem()
                .transform(n -> (GalacticDate)nowGalactic.plusDays(n));
	}

}
