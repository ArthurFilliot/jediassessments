package org.jediassessments.galacticstandardcalendar;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.jupiter.api.Assertions.*;

import java.time.Instant;
import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

import javax.inject.Inject;

import org.junit.jupiter.api.Test;

import io.quarkus.test.junit.QuarkusTest;
import io.smallrye.mutiny.Multi;

//@QuarkusTest
public class GalacticStandardCalendarServiceTest {

//	@Inject
	GalacticStandardCalendarService service = new GalacticStandardCalendarService();
	
	@Test
	public void nowFunDaysTest() {
		Multi<GalacticDate> now = service.now(Instant.now(), GalacticDate.BATTLEOFNABOO, Speed.ONEDAY_PER_SEC, 5);
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
	public void nowFunWeeksTest() {
		Multi<GalacticDate> now = service.now(Instant.now(), GalacticDate.BATTLEOFNABOO, Speed.ONEWEEK_PER_SEC, 5);
		assertNotNull(now);
		assertEquals(List.of(
				new GalacticDate(-35, 1, 1),
				new GalacticDate(-35, 1, 8),
				new GalacticDate(-35, 1, 15),
				new GalacticDate(-35, 1, 22),
				new GalacticDate(-35, 1, 29)),
				now.subscribe().asStream().collect(Collectors.toList()));
	}
	
	@Test
	public void nowFunYearsTest() {
		Multi<GalacticDate> now = service.now(Instant.now(), GalacticDate.BATTLEOFNABOO, Speed.TWOYEARS_PER_MINUTE, 5);
		assertNotNull(now);
		assertEquals(List.of(
				new GalacticDate(-35, 1, 1),
				new GalacticDate(-35, 1, 7),
				new GalacticDate(-35, 1, 13),
				new GalacticDate(-35, 1, 19),
				new GalacticDate(-35, 1, 25)),
				now.subscribe().asStream().collect(Collectors.toList()));
	}
	
	@Test
	public void nowFunPauseTest() {
		Multi<GalacticDate> now = service.now(Instant.now(), GalacticDate.BATTLEOFNABOO, Speed.PAUSE, 5);
		assertNotNull(now);
		assertEquals(List.of(
				new GalacticDate(-35, 1, 1),
				new GalacticDate(-35, 1, 1),
				new GalacticDate(-35, 1, 1),
				new GalacticDate(-35, 1, 1),
				new GalacticDate(-35, 1, 1)),
				now.subscribe().asStream().collect(Collectors.toList()));
	}
	
	@Test
	public void nowResTest() {
		given()
        .when().get("/galacticstandardcalendar/now/2018-11-30T18:35:24.00Z/5")
        .then()
           .statusCode(200)
           .body(is("hello"));
	}

}
