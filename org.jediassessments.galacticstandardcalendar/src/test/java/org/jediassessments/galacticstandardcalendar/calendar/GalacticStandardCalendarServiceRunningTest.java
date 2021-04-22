package org.jediassessments.galacticstandardcalendar.calendar;

import static org.awaitility.Awaitility.await;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;

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

import org.jediassessments.galacticstandardcalendar.date.GalacticDate;
import org.jediassessments.galacticstandardcalendar.date.GalacticDateDay;
import org.jediassessments.galacticstandardcalendar.date.GalacticDateFormatterTest;
import org.jediassessments.galacticstandardcalendar.window.GalacticWindow;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jdk8.Jdk8Module;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

import io.quarkus.test.junit.QuarkusTest;
import io.restassured.RestAssured;
import io.restassured.config.ObjectMapperConfig;

import static org.jediassessments.galacticstandardcalendar.date.GalacticDateDay.*;
import static org.jediassessments.galacticstandardcalendar.date.GalacticDatePeriod.*;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.*;

@QuarkusTest
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
	public void nowDefaultGET() {
		List<GalacticWindow> result = listenFor(
				"http://localhost:" + RestAssured.port + "/galacticstandardcalendar/now/2/2",
				"",
				GalacticWindow.class, 2);
		assertAll("Should return each days in order",
        	    () -> assertEquals(Atunda, 	result.get(0).getDates().keySet().iterator().next().getDay()),
        	    () -> assertEquals(Elona,  	result.get(0).getDates().keySet().iterator().next().getPeriod()),
        	    () -> assertEquals(Satunda, result.get(1).getDates().keySet().iterator().next().getDay()),
        	    () -> assertEquals(Elona,  	result.get(1).getDates().keySet().iterator().next().getPeriod())
        	);
	}
	
	@Test
	public void nowSavepointPOST() {
		given()
			.contentType("application/json")
        	.when()
        	.body(JsonbBuilder.create().toJson(GalacticCalendarSavePoint.of(Instant.now(), new GalacticDate(-35,2,2), Speed.PAUSE)))
        	.post("/galacticstandardcalendar/now/1/2")
        	.then()
            .statusCode(200)
            .body(containsString("data:{"));
	}
	
	private <T> T lastData(String result, Class<T> clazz) {
		JsonbConfig config = (new JsonbConfig()).withStrictIJSON(true);
        Jsonb jsonb = JsonbBuilder.create(config);
		Matcher m = Pattern.compile("(\\}[^\\{]*\\{)").matcher(new StringBuilder(result).reverse());
	    if(m.find()) {
	      return jsonb.fromJson((new StringBuilder(m.group(1)).reverse()).toString(), clazz);
	    }
	    return null;
	}
	
	private <T> List<T> listenFor(String url, String body, Class<T> elementsClazz, int expectedElements) {
		Client client = ClientBuilder.newClient();
        WebTarget target = client.target(url);
        SseEventSource source = SseEventSource.target(target).build();
        List<T> result = new CopyOnWriteArrayList<>();
        System.out.println("********** START *****************");
        JsonbConfig config = (new JsonbConfig()).withStrictIJSON(true);
        Jsonb jsonb = JsonbBuilder.create(config);
        try (SseEventSource eventSource = source) {
            eventSource.register(event -> {
            	System.out.println("*************************** EVENT *****************");
            	String element = event.readData(String.class, MediaType.APPLICATION_JSON_TYPE);
            	System.out.println("********"+ element +"**********");
            	T ele = jsonb.fromJson(element, elementsClazz);
                result.add(ele);
            }, ex -> {
                throw new IllegalStateException("SSE failure", ex);
            });
            eventSource.open();
            await().until(() -> result.size() == expectedElements);
        }
        return result;
	}

}
