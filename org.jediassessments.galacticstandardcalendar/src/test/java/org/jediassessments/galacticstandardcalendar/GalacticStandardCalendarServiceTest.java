package org.jediassessments.galacticstandardcalendar;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.jupiter.api.Assertions.*;

import java.time.Instant;
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
		Multi<GalacticDate> now = service.now(Instant.now(), 2);
		assertNotNull(now);
		assertEquals("[GalacticDate [year=-35, month=1, day=1], GalacticDate [year=-35, month=1, day=2]]",
				now.subscribe().asStream().collect(Collectors.toList()).toString());
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
