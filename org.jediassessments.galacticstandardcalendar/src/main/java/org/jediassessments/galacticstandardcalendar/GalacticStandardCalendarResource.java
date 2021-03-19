package org.jediassessments.galacticstandardcalendar;

import java.time.Duration;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.jboss.resteasy.reactive.RestSseElementType;

import io.smallrye.mutiny.Multi;

@Path("/galacticstandardcalendar")
public class GalacticStandardCalendarResource {
	
	@GET
    @Produces(MediaType.SERVER_SENT_EVENTS)
    @RestSseElementType(MediaType.APPLICATION_JSON)
    @Path("/now/{battleofnaboo}/{count}")
	public Multi<OneDay> now(String battleofnaboo, int count) { // 2018-11-30T18:35:24.00Z
		LocalDate start = LocalDate.ofInstant(Instant.parse(battleofnaboo), ZoneOffset.UTC);
		return Multi.createFrom().ticks().every(Duration.ofSeconds(1))
                .onItem()
                .transform(n -> OneDay.of(DateTimeFormatter.ofPattern("dd/MM/yyyy").format(start.plus(n, ChronoUnit.DAYS))))
                .select().first(count);
	}

}