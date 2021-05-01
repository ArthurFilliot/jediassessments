package org.jediassessments.galacticstandardcalendar.date;

import java.time.Instant;
import java.util.LinkedList;
import java.util.Map;
import java.util.Queue;
import java.util.Set;
import java.util.TreeMap;
import java.util.TreeSet;
import java.util.function.Supplier;
import java.util.stream.Collectors;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;

import org.jboss.logging.Logger;
import org.jediassessments.galacticstandardcalendar.Service;
import org.jediassessments.galacticstandardcalendar.calendar.Speed;

import io.smallrye.mutiny.Multi;

@ApplicationScoped
public class NowService implements Service {

	private static final Logger LOG = Logger.getLogger(NowService.class);

	// Business Delegate
	@Inject
	GalacticDateService dateService;
	
	private Supplier<Long> createEpochMilliIterator(Instant userInstant, Speed speed) {
		return switch(speed) {
			case ONEDAY_PER_SEC, ONEWEEK_PER_SEC 
				-> new Supplier<Long>() {
					private Long beginning = userInstant.toEpochMilli();
					private Integer increment = -1;
					@Override
					public Long get() {
						return beginning + ((++increment)*1000);
					}
				};
			case TWOYEARS_PER_MINUTE
				-> new Supplier<Long>() {
					private Long beginning = userInstant.toEpochMilli();
					private Integer increment = -1;
					@Override
					public Long get() {
						return beginning + ((++increment)*60000);
					}
				};
			case PAUSE
				-> () -> userInstant.toEpochMilli();
		};
	}
	
	private Map<Long, GalacticDate> buildNow(GalacticDate start, Speed speed, Integer interval, Supplier<Long> epochMilliIterator) {
		LinkedList<Long> iterations = new LinkedList<Long>();
		for (int i=0;i<interval;i++) {
			iterations.add(epochMilliIterator.get());
		}
		Integer nbDates = iterations.size();
		var dates = new TreeSet<>(Set.of(start));
		var currentIteration = 0;
		do {
			Long diff = Math.abs(iterations.get(currentIteration) - iterations.get(++currentIteration)) / 1000;
			LOG.debug("iterations diff : " + diff);
			GalacticDate nextDate=dateService.next(dates.last(), speed, diff);
			LOG.debug("next date : " + nextDate);
			dates.add(nextDate);
		}while(--nbDates>1);
		var datas = dates.stream()
				.map(date -> Map.entry(iterations.poll(), date))
				.collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue, (a,b) -> a, TreeMap<Long,GalacticDate>::new));
		return datas;
	}
	
	public Multi<Map<Long, GalacticDate>> now(Instant userInstant, GalacticDate start, Speed speed, Integer count, Integer interval) {
		var epochMilliIterator = createEpochMilliIterator(userInstant, speed);
		Multi<GalacticDate> now = dateService.now(userInstant, start, speed, count, interval);
		return	now
				.onItem()
				.transform(dt->buildNow(dt, speed, interval, epochMilliIterator))
				.select()
                .first(count);
	}
}
