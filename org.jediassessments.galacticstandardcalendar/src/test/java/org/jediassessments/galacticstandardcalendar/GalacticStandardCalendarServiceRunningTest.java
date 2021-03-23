package org.jediassessments.galacticstandardcalendar;

import static io.restassured.RestAssured.given;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.awaitility.Awaitility.await;

import java.time.Instant;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.json.bind.Jsonb;
import javax.json.bind.JsonbBuilder;
import javax.json.bind.JsonbConfig;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.sse.SseEventSource;

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
		RestAssured.port = 8080;
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
	
	@Test public void nowServerSentEventsTest() {
		Client client = ClientBuilder.newClient();
        WebTarget target = client.target("http://localhost:" + RestAssured.port + "/galacticstandardcalendar/now/"+Instant.now()+"/2");
        SseEventSource source = SseEventSource.target(target).build();
        List<GalacticDate> ticks = new CopyOnWriteArrayList<>();
        System.out.println("********** START *****************");
        JsonbConfig config = (new JsonbConfig()).withStrictIJSON(true);
        Jsonb jsonb = JsonbBuilder.create(config);
        try (SseEventSource eventSource = source) {
            eventSource.register(event -> {
            	System.out.println("*************************** EVENT *****************");
            	String galacticDate = event.readData(String.class, MediaType.TEXT_PLAIN_TYPE);
            	System.out.println("********"+ galacticDate +"**********");
                ticks.add(jsonb.fromJson(galacticDate, GalacticDate.class));
            }, ex -> {
                throw new IllegalStateException("SSE failure", ex);
            });
            eventSource.open();
            await().until(() -> ticks.size() == 5);
        }
        assertAll("Should return each days in order",
        	    () -> assertEquals(1, ticks.get(0).getDay()),
        	    () -> assertEquals(2, ticks.get(1).getDay()),
        	    () -> assertEquals(3, ticks.get(2).getDay()),
        	    () -> assertEquals(4, ticks.get(3).getDay()),
        	    () -> assertEquals(5, ticks.get(4).getDay())
        	);
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
