package org.jediassessments.galacticstandardcalendar.window;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.time.Instant;
import java.util.List;
import java.util.Map;
import java.util.SortedMap;
import java.util.TreeMap;
import java.util.stream.Collectors;

import org.jediassessments.galacticstandardcalendar.GalacticCalendarSavePoint;
import org.jediassessments.galacticstandardcalendar.Speed;
import org.jediassessments.galacticstandardcalendar.date.GalacticDate;
import org.jediassessments.galacticstandardcalendar.date.GalacticDateService;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import io.smallrye.mutiny.Multi;

public class GalacticWindowServiceTest {
	
	private GalacticWindowService service = new GalacticWindowService();

	@BeforeEach
	public void init() {
		service.setDateService(new GalacticDateService());
	}
	
	@Test
	public void nowFunDaysTest() {
		Multi<GalacticWindow> now = service.now(Instant.now(), GalacticDate.BATTLEOFNABOO, Speed.ONEDAY_PER_SEC, 5);
		assertNotNull(now);
		assertEquals(List.of(
			new GalacticWindow(new TreeMap<>(Map.of(
					new GalacticDate(-35, 1, 1), GalacticDateInWindowStatus.Active, 
					new GalacticDate(-35, 1, 2), GalacticDateInWindowStatus.None,
					new GalacticDate(-35, 1, 3), GalacticDateInWindowStatus.None, 
					new GalacticDate(-35, 1, 4), GalacticDateInWindowStatus.None, 
					new GalacticDate(-35, 1, 5), GalacticDateInWindowStatus.None
			))),
			new GalacticWindow(new TreeMap<>(Map.of(
					new GalacticDate(-35, 1, 1), GalacticDateInWindowStatus.Completed, 
					new GalacticDate(-35, 1, 2), GalacticDateInWindowStatus.Active,
					new GalacticDate(-35, 1, 3), GalacticDateInWindowStatus.None, 
					new GalacticDate(-35, 1, 4), GalacticDateInWindowStatus.None, 
					new GalacticDate(-35, 1, 5), GalacticDateInWindowStatus.None
			))),
			new GalacticWindow(new TreeMap<>(Map.of(
					new GalacticDate(-35, 1, 1), GalacticDateInWindowStatus.Completed, 
					new GalacticDate(-35, 1, 2), GalacticDateInWindowStatus.Completed,
					new GalacticDate(-35, 1, 3), GalacticDateInWindowStatus.Active, 
					new GalacticDate(-35, 1, 4), GalacticDateInWindowStatus.None, 
					new GalacticDate(-35, 1, 5), GalacticDateInWindowStatus.None
			))),
			new GalacticWindow(new TreeMap<>(Map.of(
					new GalacticDate(-35, 1, 1), GalacticDateInWindowStatus.Completed, 
					new GalacticDate(-35, 1, 2), GalacticDateInWindowStatus.Completed,
					new GalacticDate(-35, 1, 3), GalacticDateInWindowStatus.Completed, 
					new GalacticDate(-35, 1, 4), GalacticDateInWindowStatus.Active, 
					new GalacticDate(-35, 1, 5), GalacticDateInWindowStatus.None
			))),
			new GalacticWindow(new TreeMap<>(Map.of(
					new GalacticDate(-35, 1, 1), GalacticDateInWindowStatus.Completed, 
					new GalacticDate(-35, 1, 2), GalacticDateInWindowStatus.Completed,
					new GalacticDate(-35, 1, 3), GalacticDateInWindowStatus.Completed, 
					new GalacticDate(-35, 1, 4), GalacticDateInWindowStatus.Completed, 
					new GalacticDate(-35, 1, 5), GalacticDateInWindowStatus.Active
			)))),
			now.subscribe().asStream().collect(Collectors.toList()));
	}

}
