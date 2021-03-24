package org.jediassessments.galacticstandardcalendar;

import java.time.Duration;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.function.BiFunction;

import javax.enterprise.context.ApplicationScoped;

import org.apache.commons.lang3.tuple.Pair;
import org.jboss.logging.Logger;

import io.smallrye.mutiny.Multi;

@ApplicationScoped
public class GalacticStandardCalendarService implements Service {
	
	private static final Logger LOG = Logger.getLogger(GalacticStandardCalendarService.class);
	
	@SuppressWarnings("preview")
	public Multi<GalacticDate> now(Instant savepoint, Instant start, Speed speed, int count) {
		GalacticDate battleofnaboo = new GalacticDate(-35,1,1);
		long numSecondsAfterStart = savepoint.getEpochSecond() - start.getEpochSecond();
		var tickFunPerMinute = new BiFunction<GalacticDate, Long, GalacticDate>() {
			@Override
			public GalacticDate apply(GalacticDate t, Long u) {
				long nbDays = u*6 + ((u/60)*8);// One galactic year = 368 days
				System.out.print(nbDays);
				GalacticDate dt =  t.plusDays(nbDays); 
				System.out.println(" : " + dt);
				return dt;
			}
		};
		record TickParams(GalacticDate nowGalactic, BiFunction<GalacticDate, Long, GalacticDate> tickFun) {};
		TickParams params = switch (speed) {
			case ONEDAY_PER_SEC 	-> new TickParams(battleofnaboo.plusDays(numSecondsAfterStart),	GalacticDate::plusDays);
			case ONEWEEK_PER_SEC 	-> new TickParams(battleofnaboo.plusWeeks(numSecondsAfterStart),GalacticDate::plusWeeks);
			case ONEYEAR_PER_MINUTE -> new TickParams(tickFunPerMinute.apply(battleofnaboo, numSecondsAfterStart), tickFunPerMinute);
		};
		return Multi.createFrom()
				.ticks()
				.every(Duration.ofSeconds(1))
                .onItem()
                .transform(n -> params.tickFun.apply(params.nowGalactic, n))
                .select()
                .first(count);
	}

}
