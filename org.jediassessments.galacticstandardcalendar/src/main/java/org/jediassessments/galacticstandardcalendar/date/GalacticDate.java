package org.jediassessments.galacticstandardcalendar.date;

import java.time.temporal.ChronoUnit;
import java.time.temporal.Temporal;
import java.time.temporal.TemporalField;
import java.time.temporal.TemporalUnit;
import java.time.temporal.UnsupportedTemporalTypeException;

import io.quarkus.runtime.annotations.RegisterForReflection;

@RegisterForReflection
public class GalacticDate implements Temporal, Comparable<GalacticDate> {
	public static GalacticDate BATTLEOFNABOO = new GalacticDate(-35,1,1);
	private int year;
	private int period;
	private int day;
	
	public GalacticDate() {
		super();
	}
	
	public GalacticDate(int year, int month, int day) {
		super();
		this.year = year;
		this.period = month;
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
	public Temporal with(TemporalField field, long newValue) {
		// TODO Auto-generated method stub
		return null;
	}
	
	@Override
	public long until(Temporal endExclusive, TemporalUnit unit) {
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
	
	public GalacticDate plusYears(long yearsToAdd) {
        return plusDays(Math.multiplyExact(yearsToAdd, lengthOfYear()));
	}
	
	public GalacticDate plusWeeks(long weeksToAdd) {
        return plusDays(Math.multiplyExact(weeksToAdd, lengthOfWeek()));
    }
	
	public GalacticDate plusDays(long daysToAdd) {
        if (daysToAdd == 0) {
            return this;
        }
        long dom = getPeriod().getDayInYear() + (this.day-1) + daysToAdd;
        if (dom > 0) {
        	if (dom < GalacticDatePeriod.getLenghtOfYear()) {
        		return plusDays(this.year, this.period, dom);
        	} else {
        		int y = (int)(this.year + (dom / GalacticDatePeriod.getLenghtOfYear()));
                dom = (dom % GalacticDatePeriod.getLenghtOfYear());
        		return plusDays(y, 1, dom);
        	}           
        }
        return null;
    }
	
	private GalacticDate plusDays(int mYear, int mPeriod, long dom) {
		long periodLen = getPeriod(mPeriod).getNbDays();
		int periodDayInYear = getPeriod(mPeriod).getDayInYear();
		if (dom < periodDayInYear + periodLen) {
			if (dom==0) {
				return new GalacticDate(mYear, mPeriod, periodDayInYear);
			}
            return new GalacticDate(mYear, mPeriod, (int)(dom - periodDayInYear)+1);
        }
		mPeriod++;
        return plusDays(mYear,mPeriod,(int)(dom));
	}
	
	public int lengthOfPeriod() {
		return lengthOfWeek()*7;
    }
	
	public int lengthOfWeek() {
		return 5;
	}
	
	public int lengthOfYear() {
		return GalacticDatePeriod.getLenghtOfYear();
	}

	@Override
	public String toString() {
		return "GalacticDate [year=" + year + ", period=" + period + ", day=" + day + "]";
	}

	public int getYear() {
		return year;
	}

	public Integer getDay() {
		return day;
	}
	
	private GalacticDatePeriod getPeriod(int mPeriod) {
		try {
			return GalacticDatePeriod.values()[mPeriod-1];
		}catch(ArrayIndexOutOfBoundsException e) {
			throw e;
		}
	}
	
	public GalacticDatePeriod getPeriod() {
		return getPeriod(this.period);
	}

	public void setYear(int year) {
		this.year = year;
	}

	public void setPeriod(int period) {
		this.period = period;
	}

	public void setDay(int day) {
		this.day = day;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + day;
		result = prime * result + period;
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
		if (period != other.period)
			return false;
		if (year != other.year)
			return false;
		return true;
	}
	
	private Integer getDaysFromBattleOfNaboo() {
		return (GalacticDatePeriod.getLenghtOfYear()*this.year) + (this.getPeriod().getDayInYear()-1) + this.day;
	}

	@Override
	public int compareTo(GalacticDate o) {
		return getDaysFromBattleOfNaboo().compareTo(o.getDaysFromBattleOfNaboo());
	}
	
}
