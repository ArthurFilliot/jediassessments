package org.jediassessments.galacticstandardcalendar;

import java.time.Instant;
import java.time.LocalDateTime;

import javax.ws.rs.ext.ParamConverter;

import org.jboss.logging.Logger;

public class InstantConverter  implements ParamConverter<Instant> {

    private static final Logger LOG = Logger.getLogger(InstantConverter.class);

    
	@Override
    public Instant fromString(String value) {
        if (value == null)
            return null;
        try {
            return Instant.parse(value);
        }catch(Exception e) {
            LOG.info(e.getMessage());
        }
        return null;
    }

    @Override
    public String toString(Instant value) {
        if (value == null)
            return null;
        return value.toString();
    }

}
