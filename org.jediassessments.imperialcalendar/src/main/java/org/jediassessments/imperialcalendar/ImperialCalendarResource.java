package org.jediassessments.imperialcalendar;

import java.time.Duration;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.jboss.resteasy.reactive.RestSseElementType;

import io.smallrye.mutiny.Multi;

@Path("/imperialcalendar")
public class ImperialCalendarResource {
	
	@GET
    @Produces(MediaType.SERVER_SENT_EVENTS)
    @RestSseElementType(MediaType.APPLICATION_JSON)
    @Path("/now/{count}")
	public Multi<OneDay> now(int count) {
		LocalDate now = LocalDate.now(); 
        return Multi.createFrom().ticks().every(Duration.ofSeconds(1))
                .onItem().transform(n -> OneDay.newInstance(DateTimeFormatter.ofPattern("dd/MM/yyyy").format(now.plus(n, ChronoUnit.DAYS))))
                .select().first(count);
	}

}
