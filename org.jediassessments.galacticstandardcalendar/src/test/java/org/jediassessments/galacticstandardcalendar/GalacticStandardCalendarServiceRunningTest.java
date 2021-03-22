package org.jediassessments.galacticstandardcalendar;

import static io.restassured.RestAssured.given;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.Instant;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jdk8.Jdk8Module;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

import io.restassured.RestAssured;
import io.restassured.config.ObjectMapperConfig;

public class GalacticStandardCalendarServiceRunningTest {

	@BeforeAll
	static void initAll() {
	    RestAssured.config
	            .objectMapperConfig(new ObjectMapperConfig().jackson2ObjectMapperFactory((type, s) -> new ObjectMapper()
	                    .registerModule(new Jdk8Module())
	                    .registerModule(new JavaTimeModule())
	                    .disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS)));
	}
	
	@Test
	public void nowResTest() {
		String result = 
			given()
        		.when().get("/galacticstandardcalendar/now/"+Instant.now()+"/2")
        		.then().statusCode(200).extract().asString();
		assertEquals("{\"day\":2,\"month\":1,\"year\":-35}", lastData(result));
	}
	
	@Test
	public void nowSavePointTest() {
		String result = 
			given()
        		.when()
        		.body(GalacticCalendarSavePoint.of(Instant.now(), new GalacticDate(-35, 1, 1)))
        		.get("/galacticstandardcalendar/now/"+Instant.now()+"/2")
        		.then().statusCode(200).extract().asString();
		assertEquals("{\"day\":2,\"month\":1,\"year\":-35}", lastData(result));
	}
	
	private String lastData(String result) {
		Matcher m = Pattern.compile("(\\}[^\\{]*\\{)").matcher(new StringBuilder(result).reverse());
	    if(m.find()) {
	      return (new StringBuilder(m.group(1)).reverse()).toString();
	    }
	    return "";
	}
}
