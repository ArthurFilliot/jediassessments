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
		Instant now1 = Instant.now();
		Multi<GalacticDate> now = service.now(now1, now1, Speed.ONEDAY_PER_SEC, 2);
		assertNotNull(now);
		assertEquals("[GalacticDate [year=-35, month=1, day=1], GalacticDate [year=-35, month=1, day=2]]",
				now.subscribe().asStream().collect(Collectors.toList()).toString());
	}
	
	@Test
	public void nowFunWeeksTest() {
		Instant now1 = Instant.now();
		Multi<GalacticDate> now = service.now(now1, now1, Speed.ONEWEEK_PER_SEC, 2);
		assertNotNull(now);
		assertEquals("[GalacticDate [year=-35, month=1, day=1], GalacticDate [year=-35, month=1, day=8]]",
				now.subscribe().asStream().collect(Collectors.toList()).toString());
	}
	
	@Test
	public void nowFunYearsTest() {
		Instant now1 = Instant.now();
		Multi<GalacticDate> now = service.now(now1, now1, Speed.ONEYEAR_PER_MINUTE, 65);
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
	public void nowResTest() {
		given()
        .when().get("/galacticstandardcalendar/now/2018-11-30T18:35:24.00Z/5")
        .then()
           .statusCode(200)
           .body(is("hello"));
	}

}
