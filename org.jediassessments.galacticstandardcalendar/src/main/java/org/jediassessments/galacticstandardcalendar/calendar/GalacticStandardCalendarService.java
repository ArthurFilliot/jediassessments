package org.jediassessments.galacticstandardcalendar.calendar;

import java.util.Map;
import java.util.TreeMap;
import java.util.stream.Collectors;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;

import org.jboss.logging.Logger;
import org.jediassessments.galacticstandardcalendar.Service;
import org.jediassessments.galacticstandardcalendar.date.GalacticDate;
import org.jediassessments.galacticstandardcalendar.date.GalacticDateFormatter;
import org.jediassessments.galacticstandardcalendar.now.NowService;

import io.smallrye.mutiny.Multi;

@ApplicationScoped
public class GalacticStandardCalendarService implements Service, GalacticDateFormatter {
	
	private static final Logger LOG = Logger.getLogger(GalacticStandardCalendarService.class);
	
	// Business Delegate
	@Inject
	private NowService windowService;
	
	public Multi<Map<Long,String>> now(int count, int interval) {
		GalacticCalendarSavePoint savepoint = new GalacticCalendarSavePoint();
		var result = windowService.now(savepoint.userInstant(), savepoint.galacticCalendarDate(), savepoint.speed(), count, interval);
		return result
				.onItem()
				.transform(this::format);
	}
	
	public Multi<Map<Long,String>> now(GalacticCalendarSavePoint savepoint, int count, int interval) {
		var result = windowService.now(savepoint.userInstant(), savepoint.galacticCalendarDate(), savepoint.speed(), count, interval);
		return result
				.onItem()
				.transform(this::format);
	}
	
	private Map<Long, String> format(Map<Long, GalacticDate> now) {
		return now.entrySet().stream()
				.map(entry->Map.entry(entry.getKey(), format(entry.getValue())))
				.collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue, (a,b)->a, TreeMap<Long, String>::new));
	}

}
