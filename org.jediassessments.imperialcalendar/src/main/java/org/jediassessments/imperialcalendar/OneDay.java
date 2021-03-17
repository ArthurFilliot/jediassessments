package org.jediassessments.imperialcalendar;

import javax.json.bind.annotation.JsonbCreator;
import javax.json.bind.annotation.JsonbProperty;

public class OneDay {
    public String dt;

    public OneDay() {
        super();
    }

    public static OneDay newInstance(String dt) {
        OneDay d = new OneDay();
        d.dt = dt;
        return d;
    }
    
    @JsonbCreator
    public static OneDay of(@JsonbProperty("dt")  String dt) {
        return newInstance(dt);
     } 
}
