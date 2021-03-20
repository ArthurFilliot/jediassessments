package org.jediassessments.galacticstandardcalendar;

import static java.time.temporal.ChronoField.YEAR;

import java.time.temporal.ChronoUnit;
import java.time.temporal.Temporal;
import java.time.temporal.TemporalField;
import java.time.temporal.TemporalUnit;
import java.time.temporal.UnsupportedTemporalTypeException;

public class GalacticDate implements Temporal {
	private int year;
	private int month;
	private int day;
	
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
            if (dom <= 28) {
                return new GalacticDate(year, month, (int) dom);
            } else if (dom <= 59) { // 59th Jan is 28th Feb, 59th Feb is 31st Mar
                long monthLen = lengthOfMonth();
                if (dom <= monthLen) {
                    return new GalacticDate(year, month, (int) dom);
                } else if (month < 12) {
                    return new GalacticDate(year, month + 1, (int) (dom - monthLen));
                } else {
                    YEAR.checkValidValue(year + 1);
                    return new GalacticDate(year + 1, 1, (int) (dom - monthLen));
                }
            }
        }
        return null;
    }
	
	public int lengthOfMonth() {
        switch (month) {
            case 2:
                return /*(isLeapYear() ? */29 /*: 28)*/;
            case 4:
            case 6:
            case 9:
            case 11:
                return 30;
            default:
                return 31;
        }
    }

	@Override
	public String toString() {
		return "GalacticDate [year=" + year + ", month=" + month + ", day=" + day + "]";
	}
	
}
