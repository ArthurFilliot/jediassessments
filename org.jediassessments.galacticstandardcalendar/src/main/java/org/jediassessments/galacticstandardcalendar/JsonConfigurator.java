package org.jediassessments.galacticstandardcalendar;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

import javax.inject.Singleton;
import javax.json.bind.JsonbConfig;
import javax.json.bind.config.PropertyVisibilityStrategy;

import io.quarkus.jsonb.JsonbConfigCustomizer;

@Singleton
public class JsonConfigurator implements JsonbConfigCustomizer {

    public void customize(JsonbConfig config) {
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
