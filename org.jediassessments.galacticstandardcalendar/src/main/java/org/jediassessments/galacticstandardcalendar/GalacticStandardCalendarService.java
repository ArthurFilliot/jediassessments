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
	
	public Multi<GalacticDate> now(int count) {
		return now(GalacticCalendarSavePoint.of(Instant.now(),GalacticDate.BATTLEOFNABOO,Speed.PAUSE), count);
	}
	
	public Multi<GalacticDate> now(Instant instant, GalacticDate savepoint, Speed speed, int count) {
		return now(GalacticCalendarSavePoint.of(instant,savepoint,speed), count);
	}
	
	@SuppressWarnings("preview")
	public Multi<GalacticDate> now(GalacticCalendarSavePoint savepoint, int count) {
		Instant start = Instant.now();
		GalacticDate savePointGdt = savepoint.galacticCalendarDate();
		Speed savepointSpeed = savepoint.speed();
		long numSecondsSinceSavePoint = start.getEpochSecond() - savepoint.userInstant().getEpochSecond();
		var tickFunPerTwoMinute = new BiFunction<GalacticDate, Long, GalacticDate>() {
			@Override
			public GalacticDate apply(GalacticDate t, Long u) {
				System.out.print(u + " - ");
				long nbDays = u*12 + ((u/60)*16);// One galactic year = 368 days
				System.out.print(nbDays);
				GalacticDate dt =  t.plusDays(nbDays); 
				System.out.println(" : " + dt);
				return dt;
			}
		};
		record TickParams(GalacticDate nowGalactic, BiFunction<GalacticDate, Long, GalacticDate> tickFun) {};
		TickParams params = switch (savepointSpeed) {
			case ONEDAY_PER_SEC 	-> new TickParams(savePointGdt.plusDays(numSecondsSinceSavePoint),	GalacticDate::plusDays);
			case ONEWEEK_PER_SEC 	-> new TickParams(savePointGdt.plusWeeks(numSecondsSinceSavePoint),GalacticDate::plusWeeks);
			case TWOYEARS_PER_MINUTE -> new TickParams(tickFunPerTwoMinute.apply(savePointGdt, numSecondsSinceSavePoint), tickFunPerTwoMinute);
			case PAUSE				-> new TickParams(savePointGdt, (gdt,n)->gdt);
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
