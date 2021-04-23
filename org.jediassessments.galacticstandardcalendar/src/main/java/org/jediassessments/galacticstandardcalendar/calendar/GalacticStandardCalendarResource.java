package org.jediassessments.galacticstandardcalendar.calendar;

import java.util.Map;

import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.jboss.resteasy.reactive.RestSseElementType;
import org.jediassessments.galacticstandardcalendar.Resource;

import io.smallrye.mutiny.Multi;

@Path("/galacticstandardcalendar")
public class GalacticStandardCalendarResource implements Resource<GalacticStandardCalendarService> {

	@Inject
	GalacticStandardCalendarService service;

	@Override
	public GalacticStandardCalendarService getService() {
		return service;
	}
	
	@GET
	@Produces(MediaType.SERVER_SENT_EVENTS)
	@RestSseElementType(MediaType.APPLICATION_JSON)
	@Path("/now/{count}/{interval}")
	public Multi<Map<Long, String>> now(int count, int interval) { 
		return service.now(count, interval);
	}
	
	@POST
	@Produces(MediaType.SERVER_SENT_EVENTS)
	@RestSseElementType(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
	@Path("/now/{count}/{interval}")
	public Multi<Map<Long, String>> now(GalacticCalendarSavePoint savepoint, int count, int interval) { 
		return service.now(savepoint, count, interval);
	}

}