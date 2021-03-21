package org.jediassessments.galacticstandardcalendar;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import javax.inject.Singleton;
import javax.json.bind.JsonbConfig;
import javax.json.bind.config.PropertyVisibilityStrategy;
import org.jboss.logging.Logger;
import io.quarkus.jsonb.JsonbConfigCustomizer;

@Singleton
public class JsonbConfigurator implements JsonbConfigCustomizer {

    private static final Logger LOG = Logger.getLogger(JsonbConfigurator.class);

	public void customize(JsonbConfig config) {
    	LOG.info("************************************************************");
        config.withPropertyVisibilityStrategy(new PropertyVisibilityStrategy() {
		    @Override
		    public boolean isVisible(Field field) {
		        return true;
		    }
		    @Override
		    public boolean isVisible(Method method) {
		        return false;
		    }
		});
    }
}
