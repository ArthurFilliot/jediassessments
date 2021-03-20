package org.jediassessments.galacticstandardcalendar;

import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;

import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.jboss.resteasy.reactive.RestSseElementType;

import io.smallrye.mutiny.Multi;

@Path("/galacticstandardcalendar")
public class GalacticStandardCalendarResource {
	
	@Inject
	GalacticStandardCalendarService service;
	
	@GET
    @Produces(MediaType.SERVER_SENT_EVENTS)
    @RestSseElementType(MediaType.APPLICATION_JSON)
    @Path("/now/{battleofnaboo}/{count}")
	public Multi<OneDay> now(String battleofnaboo, int count) { // 2018-11-30T18:35:24.00Z
		LocalDate start = LocalDate.ofInstant(Instant.parse(battleofnaboo), ZoneOffset.UTC);
		return service
				.now(start)
				.onItem().transform(dt -> dt.toString())
		 		.onItem().transform(OneDay::new)
                .select().first(count);
	}

}