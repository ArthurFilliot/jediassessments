package org.jediassessments.galacticstandardcalendar;

import java.time.temporal.ChronoUnit;
import java.time.temporal.Temporal;
import java.time.temporal.TemporalField;
import java.time.temporal.TemporalUnit;
import java.time.temporal.UnsupportedTemporalTypeException;

import io.quarkus.runtime.annotations.RegisterForReflection;

@RegisterForReflection
public class GalacticDate implements Temporal {
	public static GalacticDate BATTLEOFNABOO = new GalacticDate(-35,1,1);
	private int year;
	private int month;
	private int day;
	
	public GalacticDate() {
		super();
	}
	
	public GalacticDate(int year, int month, int day) {
		super();
		this.year = year;
		this.month = month;
		this.day = day;
	}

	@Override
	public boolean isSupported(TemporalField field) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public long getLong(TemporalField field) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public boolean isSupported(TemporalUnit unit) {
		if (ChronoUnit.DAYS.equals(unit)
				|| ChronoUnit.WEEKS.equals(unit)) {
			return true;
		}
		return false;
	}

	@Override
	public Temporal with(TemporalField field, long newValue) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Temporal plus(long amountToAdd, TemporalUnit unit) {
		if (unit instanceof ChronoUnit) {
            ChronoUnit f = (ChronoUnit) unit;
            switch (f) {
                case DAYS: return plusDays(amountToAdd);
                case WEEKS: return plusWeeks(amountToAdd);
//                case MONTHS: return plusMonths(amountToAdd);
//                case YEARS: return plusYears(amountToAdd);
//                case DECADES: return plusYears(Math.multiplyExact(amountToAdd, 10));
//                case CENTURIES: return plusYears(Math.multiplyExact(amountToAdd, 100));
//                case MILLENNIA: return plusYears(Math.multiplyExact(amountToAdd, 1000));
//                case ERAS: return with(ERA, Math.addExact(getLong(ERA), amountToAdd));
            }
            throw new UnsupportedTemporalTypeException("Unsupported unit: " + unit);
        }
        return unit.addTo(this, amountToAdd);
	}
	
	@Override
	public long until(Temporal endExclusive, TemporalUnit unit) {
		// TODO Auto-generated method stub
		return 0;
	}
	
	public GalacticDate plusWeeks(long weeksToAdd) {
        return plusDays(Math.multiplyExact(weeksToAdd, 7));
    }
	
	public GalacticDate plusDays(long daysToAdd) {
        if (daysToAdd == 0) {
            return this;
        }
        long dom = day + daysToAdd;
        if (dom > 0) {
        	if (dom < lengthOfYearInDays()) {
        		return plusDays(this.year, dom);
        	}else {
        		int y = (int)(this.year + (dom / lengthOfYearInDays()));
                dom = dom % lengthOfYearInDays();
        		return plusDays(y, dom);
        	}           
        }
        return null;
    }
	
	private GalacticDate plusDays(int y, long dom) {
		long monthLen = lengthOfMonth();
    	long yearLen = lengthOfYearInDays();
    	if (dom<yearLen) {
    		if (dom <= monthLen) {
                return new GalacticDate(y, month, (int) dom);
            } 
            long nbDays = ((month-1)*monthLen + dom);
            if (nbDays < yearLen) {
                int moy = this.month + ((int) (nbDays / monthLen));
                return new GalacticDate(y, moy, (int)(nbDays % monthLen));
            }
    	}
        return null;
	}
	
	public int lengthOfYearInDays() {
		return 5*7*10;
	}
	
	public int lengthOfMonth() {
		return 5*7;
    }

	@Override
	public String toString() {
		return "GalacticDate [year=" + year + ", month=" + month + ", day=" + day + "]";
	}

	public int getYear() {
		return year;
	}

	public void setYear(int year) {
		this.year = year;
	}

	public int getMonth() {
		return month;
	}

	public void setMonth(int month) {
		this.month = month;
	}

	public int getDay() {
		return day;
	}

	public void setDay(int day) {
		this.day = day;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + day;
		result = prime * result + month;
		result = prime * result + year;
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		GalacticDate other = (GalacticDate) obj;
		if (day != other.day)
			return false;
		if (month != other.month)
			return false;
		if (year != other.year)
			return false;
		return true;
	}
	
}
