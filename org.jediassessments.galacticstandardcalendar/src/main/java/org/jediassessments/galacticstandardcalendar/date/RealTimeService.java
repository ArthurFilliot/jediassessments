package org.jediassessments.galacticstandardcalendar.date;

import java.time.Instant;

import javax.enterprise.context.ApplicationScoped;

import org.jediassessments.galacticstandardcalendar.Service;

@ApplicationScoped
public class RealTimeService implements Service {

	public Instant now() {
		return Instant.now();
	}
}
