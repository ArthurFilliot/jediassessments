package org.jediassessments.galacticstandardcalendar;

import static io.restassured.RestAssured.given;
import static org.awaitility.Awaitility.await;
import static org.jediassessments.galacticstandardcalendar.date.GalacticDateDay.Atunda;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;

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
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jdk8.Jdk8Module;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

import io.quarkus.test.junit.QuarkusTest;
import io.restassured.RestAssured;
import io.restassured.config.ObjectMapperConfig;

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
	
//	@Test
//	public void nowEventsTest() {
//		List<GalacticDate> result = listenFor(
//				"http://localhost:" + RestAssured.port + "/galacticstandardcalendar/now/2",
//				"",
//				GalacticDate.class, 5);
//		assertAll("Should return each days in order",
//        	    () -> assertEquals(Atunda, result.get(0).getDay()),
//        	    () -> assertEquals(Atunda, result.get(1).getDay()),
//        	    () -> assertEquals(Atunda, result.get(2).getDay()),
//        	    () -> assertEquals(Atunda, result.get(3).getDay()),
//        	    () -> assertEquals(Atunda, result.get(4).getDay())
//        	);
//	}
//
//	@Test
//	public void nowRessourceTest() {
//		String result = 
//			given()
//        		.when().get("/galacticstandardcalendar/now/2")
//        		.then().statusCode(200).extract().asString();
//		System.out.println(result);
//		assertEquals(GalacticDate.BATTLEOFNABOO, lastData(result, GalacticDate.class));
//	}
	
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
