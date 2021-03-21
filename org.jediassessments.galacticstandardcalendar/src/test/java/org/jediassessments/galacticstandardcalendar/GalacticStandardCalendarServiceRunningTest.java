package org.jediassessments.galacticstandardcalendar;

import static io.restassured.RestAssured.given;

import java.time.Instant;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

public class GalacticStandardCalendarServiceRunningTest {

	@Test
	public void nowResTest() {
		String result = 
			given()
        		.when().get("/galacticstandardcalendar/now/"+Instant.now()+"/2")
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
