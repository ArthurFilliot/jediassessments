package org.jediassessments.galacticstandardcalendar;

import java.time.Instant;

import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.jboss.resteasy.reactive.RestSseElementType;

import io.smallrye.mutiny.Multi;

@Path("/galacticstandardcalendar")
public class GalacticStandardCalendarResource implements Resource<GalacticStandardCalendarService> {

	@Inject
	GalacticStandardCalendarService service;

	@Override
	public GalacticStandardCalendarService getService() {
		return service;
	}
	
	@POST
	@Produces(MediaType.SERVER_SENT_EVENTS)
	@RestSseElementType(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
	@Path("/now/{count}")
	public Multi<GalacticCalendarSavePoint> now(int count, GalacticCalendarSavePoint savepoint) { 
		return Multi.createFrom().item(savepoint);
	}

	@GET
	@Produces(MediaType.SERVER_SENT_EVENTS)
	@RestSseElementType(MediaType.APPLICATION_JSON)
	@Path("/now/{battleofnaboo}/{speed}/{count}")
	public Multi<GalacticDate> now(Instant battleofnaboo, Speed speed, int count) { // 2018-11-30T18:35:24.00Z
		return service.now(Instant.now(), Instant.now(), speed, count);
	}

}