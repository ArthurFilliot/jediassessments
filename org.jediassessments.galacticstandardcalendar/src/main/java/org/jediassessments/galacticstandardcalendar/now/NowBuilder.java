package org.jediassessments.galacticstandardcalendar.now;

import java.time.Instant;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;
import java.util.TreeSet;
import java.util.function.Supplier;
import java.util.stream.Collectors;

import org.jediassessments.galacticstandardcalendar.calendar.Speed;
import org.jediassessments.galacticstandardcalendar.date.GalacticDate;

class NowBuilder {

	private Supplier<Long> nextSecondEpochMilli;
	private Instant userInstant;
	private Speed speed;
	private Integer interval;
	
	NowBuilder(Instant userInstant, Speed speed, Integer interval) {
		this.userInstant = userInstant; 
		this.speed = speed;
		this.interval = interval;
	}

	private Supplier<Long> getNextSecondEpochMilli() {
		if (nextSecondEpochMilli == null)  {
			nextSecondEpochMilli = initNextSecondEpochMilli();
		}
		return nextSecondEpochMilli;
	}
	
	private Supplier<Long> initNextSecondEpochMilli() {
		return new Supplier<Long>() {
			private Long beginning = userInstant.toEpochMilli();
			private Integer increment = -1;
			@Override
			public Long get() {
				return beginning + ((++increment)*1000);
			}
		};
	}

	Map<Long, GalacticDate> build(GalacticDate start) {
		Integer nbDates = interval;
		var dates = new TreeSet<>(Set.of(start));
		do {
			dates.add(speed.getTickFun().apply(dates.last(), 1L));
		}while(--nbDates>1);
		var datas = dates.stream()
				.map(date -> Map.entry(getNextSecondEpochMilli().get(), date))
				.collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue, (a,b) -> a, TreeMap<Long,GalacticDate>::new));
		return datas;
	}
}
