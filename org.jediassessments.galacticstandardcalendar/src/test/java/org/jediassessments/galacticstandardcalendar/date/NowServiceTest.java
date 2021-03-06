package org.jediassessments.galacticstandardcalendar.date;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.time.Instant;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.stream.Collectors;

import org.jediassessments.galacticstandardcalendar.calendar.Speed;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import io.smallrye.mutiny.Multi;

public class NowServiceTest {
	
	private NowService service = new NowService();

	@BeforeEach
	public void init() {
		GalacticDateService galacticDateService = new GalacticDateService();
		galacticDateService.timeService = new RealTimeService () {
			public Instant now() {
				return Instant.ofEpochMilli(1619099001000L);
			}
		};
		service.dateService = galacticDateService;
	}
	
	@Test
	public void nowFunDaysTestUnique() {
		Instant now = Instant.ofEpochMilli(1619099001000L);
		Multi<Map<Long, GalacticDate>> stream = service.now(now, GalacticDate.BATTLEOFNABOO, Speed.ONEDAY_PER_SEC, 1, 5);
		assertNotNull(stream);
		assertEquals(List.of(
			new TreeMap<>(Map.of(
					 1619099001000L, new GalacticDate(-35, 1, 1), 
					 1619099002000L, new GalacticDate(-35, 1, 2),
					 1619099003000L, new GalacticDate(-35, 1, 3), 
					 1619099004000L, new GalacticDate(-35, 1, 4), 
					 1619099005000L, new GalacticDate(-35, 1, 5)
			))),
			stream.subscribe().asStream().collect(Collectors.toList()));
	}
	
	@Test
	public void nowFunYearsPerMinute() {
		Instant now = Instant.ofEpochMilli(1619099001000L);
		Multi<Map<Long, GalacticDate>> stream = service.now(now, GalacticDate.BATTLEOFNABOO, Speed.TWOYEARS_PER_MINUTE, 1, 5);
		assertNotNull(stream);
		assertEquals(List.of(
			new TreeMap<>(Map.of(
					 1619099001000L, new GalacticDate(-35, 1, 1), 
					 1619099061000L, new GalacticDate(-33, 1, 1),
					 1619099121000L, new GalacticDate(-31, 1, 1), 
					 1619099181000L, new GalacticDate(-29, 1, 1), 
					 1619099241000L, new GalacticDate(-27, 1, 1)
			))),
			stream.subscribe().asStream().collect(Collectors.toList()));
	}
	
	@Test
	public void nowFunDaysTestMultiple() {
		Instant now = Instant.ofEpochMilli(1619099001000L);
		Multi<Map<Long, GalacticDate>> stream = service.now(now, GalacticDate.BATTLEOFNABOO, Speed.ONEDAY_PER_SEC, 5, 5);
		assertNotNull(stream);
		assertEquals(List.of(
			new TreeMap<>(Map.of(
					 1619099001000L, new GalacticDate(-35, 1, 1),
					 1619099002000L, new GalacticDate(-35, 1, 2), 
					 1619099003000L, new GalacticDate(-35, 1, 3),  
					 1619099004000L, new GalacticDate(-35, 1, 4),  
					 1619099005000L, new GalacticDate(-35, 1, 5) 
			)),
			new TreeMap<>(Map.of(
					1619099006000L, new GalacticDate(-35, 1, 6),  
					1619099007000L, new GalacticDate(-35, 1, 7), 
					1619099008000L, new GalacticDate(-35, 1, 8),  
					1619099009000L, new GalacticDate(-35, 1, 9),  
					1619099010000L, new GalacticDate(-35, 1, 10)
			)),
			new TreeMap<>(Map.of(
					1619099011000L, new GalacticDate(-35, 1, 11),  
					1619099012000L, new GalacticDate(-35, 1, 12), 
					1619099013000L, new GalacticDate(-35, 1, 13),  
					1619099014000L, new GalacticDate(-35, 1, 14),  
					1619099015000L, new GalacticDate(-35, 1, 15)
			)),
			new TreeMap<>(Map.of(
					1619099016000L, new GalacticDate(-35, 1, 16),  
					1619099017000L, new GalacticDate(-35, 1, 17), 
					1619099018000L, new GalacticDate(-35, 1, 18),  
					1619099019000L, new GalacticDate(-35, 1, 19),  
					1619099020000L, new GalacticDate(-35, 1, 20)
			)),
			new TreeMap<>(Map.of(
					1619099021000L, new GalacticDate(-35, 1, 21),  
					1619099022000L, new GalacticDate(-35, 1, 22), 
					1619099023000L, new GalacticDate(-35, 1, 23),  
					1619099024000L, new GalacticDate(-35, 1, 24),  
					1619099025000L, new GalacticDate(-35, 1, 25)
			))),
			stream.subscribe().asStream().collect(Collectors.toList()));
	}

}
