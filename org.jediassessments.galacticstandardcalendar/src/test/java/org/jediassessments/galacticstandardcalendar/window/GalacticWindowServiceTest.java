package org.jediassessments.galacticstandardcalendar.window;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.time.Instant;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.stream.Collectors;

import org.jediassessments.galacticstandardcalendar.calendar.Speed;
import org.jediassessments.galacticstandardcalendar.date.GalacticDate;
import org.jediassessments.galacticstandardcalendar.date.GalacticDateService;
import org.jediassessments.galacticstandardcalendar.date.RealTimeService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import io.smallrye.mutiny.Multi;

public class GalacticWindowServiceTest {
	
	private GalacticWindowService service = new GalacticWindowService();

	@BeforeEach
	public void init() {
		GalacticDateService galacticDateService = new GalacticDateService();
		galacticDateService.setTimeService(new RealTimeService () {
			public Instant now() {
				return Instant.ofEpochMilli(1619099001000L);
			}
		});
		service.setDateService(galacticDateService);
	}
	
	@Test
	public void nowFunDaysTestUnique() {
		Instant now = Instant.ofEpochMilli(1619099001000L);
		Multi<GalacticWindow> stream = service.now(now, GalacticDate.BATTLEOFNABOO, Speed.ONEDAY_PER_SEC, 1, 5);
		assertNotNull(stream);
		assertEquals(List.of(
			new GalacticWindow(new TreeMap<>(Map.of(
					new GalacticDate(-35, 1, 1), 1619099001000L, 
					new GalacticDate(-35, 1, 2), 1619099002000L,
					new GalacticDate(-35, 1, 3), 1619099003000L, 
					new GalacticDate(-35, 1, 4), 1619099004000L, 
					new GalacticDate(-35, 1, 5), 1619099005000L
			)))),
			stream.subscribe().asStream().collect(Collectors.toList()));
	}
	
	@Test
	public void nowFunDaysTestMultiple() {
		Instant now = Instant.ofEpochMilli(1619099001000L);
		Multi<GalacticWindow> stream = service.now(now, GalacticDate.BATTLEOFNABOO, Speed.ONEDAY_PER_SEC, 5, 5);
		assertNotNull(stream);
		assertEquals(List.of(
			new GalacticWindow(new TreeMap<>(Map.of(
					new GalacticDate(-35, 1, 1), 1619099001000L, 
					new GalacticDate(-35, 1, 2), 1619099002000L,
					new GalacticDate(-35, 1, 3), 1619099003000L, 
					new GalacticDate(-35, 1, 4), 1619099004000L, 
					new GalacticDate(-35, 1, 5), 1619099005000L
			))),
			new GalacticWindow(new TreeMap<>(Map.of(
					new GalacticDate(-35, 1, 6), 1619099006000L, 
					new GalacticDate(-35, 1, 7), 1619099007000L,
					new GalacticDate(-35, 1, 8), 1619099008000L, 
					new GalacticDate(-35, 1, 9), 1619099009000L, 
					new GalacticDate(-35, 1, 10), 1619099010000L
			))),
			new GalacticWindow(new TreeMap<>(Map.of(
					new GalacticDate(-35, 1, 11), 1619099011000L, 
					new GalacticDate(-35, 1, 12), 1619099012000L,
					new GalacticDate(-35, 1, 13), 1619099013000L, 
					new GalacticDate(-35, 1, 14), 1619099014000L, 
					new GalacticDate(-35, 1, 15), 1619099015000L
			))),
			new GalacticWindow(new TreeMap<>(Map.of(
					new GalacticDate(-35, 1, 16), 1619099016000L, 
					new GalacticDate(-35, 1, 17), 1619099017000L,
					new GalacticDate(-35, 1, 18), 1619099018000L, 
					new GalacticDate(-35, 1, 19), 1619099019000L, 
					new GalacticDate(-35, 1, 20), 1619099020000L
			))),
			new GalacticWindow(new TreeMap<>(Map.of(
					new GalacticDate(-35, 1, 21), 1619099021000L, 
					new GalacticDate(-35, 1, 22), 1619099022000L,
					new GalacticDate(-35, 1, 23), 1619099023000L, 
					new GalacticDate(-35, 1, 24), 1619099024000L, 
					new GalacticDate(-35, 1, 25), 1619099025000L
			)))),
			stream.subscribe().asStream().collect(Collectors.toList()));
	}

}
