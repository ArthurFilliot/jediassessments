package org.jediassessments.galacticstandardcalendar;

import java.util.function.BiFunction;

public enum GalacticDatePeriod {
	Elona(5*7), Kelona(5*7), Tapani(1), Selona(5*7),
	ExpansionWeek(5), Telona(5*7), Nelona(5*7), ProductivityDay(1),
	Helona(5*7), Shelova(5), Menola(5*7), Yelona(5*7), HarvestDay(1),
	Relona(5*7), Welona(5*7), WinterFestival(5);
	
	private Integer nbDays;
	
	private GalacticDatePeriod(Integer nbDays) {
		this.nbDays = nbDays;
	}
	
	public Integer getDayInYear() {
		var dayInYearAcc = new BiFunction<Integer,Integer,Integer>() {
			@Override
			public Integer apply(Integer acc, Integer ordinal) {
				if (ordinal<0) {
					return acc;
				}
				Integer nbDays = GalacticDatePeriod.values()[ordinal].nbDays;
				return this.apply(acc+nbDays, ordinal-1);
			}
		};
		return dayInYearAcc.apply(1, this.ordinal()-1);
	}
	
}
