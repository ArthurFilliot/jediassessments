package org.jediassessments.galacticstandardcalendar.date;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.time.Instant;
import java.util.List;
import java.util.stream.Collectors;

import org.jediassessments.galacticstandardcalendar.calendar.Speed;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import io.smallrye.mutiny.Multi;

public class GalacticDateServiceTest {

	private GalacticDateService service = new GalacticDateService();
	
	@BeforeEach
	public void init() {
		service.setTimeService(new RealTimeService());
	}
	
	@Test
	public void nowFunDaysTest() {
		Multi<GalacticDate> now = service.now(Instant.now(), GalacticDate.BATTLEOFNABOO, Speed.ONEDAY_PER_SEC, 5, 1);
		assertNotNull(now);
		assertEquals(List.of(
				new GalacticDate(-35, 1, 1),
				new GalacticDate(-35, 1, 2),
				new GalacticDate(-35, 1, 3),
				new GalacticDate(-35, 1, 4),
				new GalacticDate(-35, 1, 5)),
				now.subscribe().asStream().collect(Collectors.toList()));
	}
	
	@Test
	public void nowFunDaysWithIntervalTest() {
		Multi<GalacticDate> now = service.now(Instant.now(), GalacticDate.BATTLEOFNABOO, Speed.ONEDAY_PER_SEC, 5, 5);
		assertNotNull(now);
		assertEquals(List.of(
				new GalacticDate(-35, 1, 1),
				new GalacticDate(-35, 1, 6),
				new GalacticDate(-35, 1, 11),
				new GalacticDate(-35, 1, 16),
				new GalacticDate(-35, 1, 21)),
				now.subscribe().asStream().collect(Collectors.toList()));
	}
	
	@Test
	public void nowFunWeeksTest() {
		Multi<GalacticDate> now = service.now(Instant.now(), GalacticDate.BATTLEOFNABOO, Speed.ONEWEEK_PER_SEC, 5, 1);
		assertNotNull(now);
		assertEquals(List.of(
				new GalacticDate(-35, 1, 1),
				new GalacticDate(-35, 1, 6),
				new GalacticDate(-35, 1, 11),
				new GalacticDate(-35, 1, 16),
				new GalacticDate(-35, 1, 21)),
				now.subscribe().asStream().collect(Collectors.toList()));
	}
	
	@Test
	public void nowFunYearsTest() {
		Multi<GalacticDate> now = service.now(Instant.now(), GalacticDate.BATTLEOFNABOO, Speed.TWOYEARS_PER_MINUTE, 5, 1);
		assertNotNull(now);
		assertEquals(List.of(
				new GalacticDate(-35, 1, 1),
				new GalacticDate(-35, 1, 13),
				new GalacticDate(-35, 1, 25),
				new GalacticDate(-35, 2, 2),
				new GalacticDate(-35, 2, 14)),
				now.subscribe().asStream().collect(Collectors.toList()));
	}
	
	@Test
	public void nowFunPauseTest() {
		Multi<GalacticDate> now = service.now(Instant.now(), GalacticDate.BATTLEOFNABOO, Speed.PAUSE, 5, 1);
		assertNotNull(now);
		assertEquals(List.of(
				new GalacticDate(-35, 1, 1),
				new GalacticDate(-35, 1, 1),
				new GalacticDate(-35, 1, 1),
				new GalacticDate(-35, 1, 1),
				new GalacticDate(-35, 1, 1)),
				now.subscribe().asStream().collect(Collectors.toList()));
	}

}
