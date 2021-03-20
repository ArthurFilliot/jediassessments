package org.jediassessments.galacticstandardcalendar;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.time.LocalDate;
import java.util.stream.Collectors;

import javax.inject.Inject;

import org.junit.jupiter.api.Test;

import io.quarkus.test.junit.QuarkusTest;
import io.smallrye.mutiny.Multi;

@QuarkusTest
public class GalacticStandardCalendarServiceTest {

	@Inject
	GalacticStandardCalendarService service;
	
	@Test
	public void nowFunTest() {
		Multi<GalacticDate> now = service.now(LocalDate.now());
		assertNotNull(now);
		assertEquals("[GalacticDate [year=-35, month=1, day=1], GalacticDate [year=-35, month=1, day=2]]",
				now.select().first(2).subscribe().asStream().collect(Collectors.toList()).toString());
	}
}
