package org.jediassessments.galacticstandardcalendar.calendar;

import java.util.function.BiFunction;

import org.jediassessments.galacticstandardcalendar.date.GalacticDate;

public enum Speed {
	ONEDAY_PER_SEC(GalacticDate::plusDays), 
	ONEWEEK_PER_SEC(GalacticDate::plusWeeks), 
	TWOYEARS_PER_MINUTE(new BiFunction<GalacticDate, Long, GalacticDate>() {
		@Override
		public GalacticDate apply(GalacticDate t, Long u) {
			System.out.print(u + " - ");
			long nbDays = u*12 + ((u/60)*16);// One galactic year = 368 days
			System.out.print(nbDays);
			GalacticDate dt =  t.plusDays(nbDays); 
			System.out.println(" : " + dt);
			return dt;
		}
	}), 
	PAUSE((gdt,n)->gdt);
	
	private BiFunction<GalacticDate, Long, GalacticDate> tickFun;
	
	private Speed(BiFunction<GalacticDate, Long, GalacticDate> tickFun) {
		this.tickFun = tickFun;
	}

	public BiFunction<GalacticDate, Long, GalacticDate> getTickFun() {
		return tickFun;
	}

}
