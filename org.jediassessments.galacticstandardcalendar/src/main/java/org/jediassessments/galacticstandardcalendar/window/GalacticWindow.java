package org.jediassessments.galacticstandardcalendar.window;

import java.util.Map;
import java.util.Set;
import java.util.TreeMap;
import java.util.stream.Collectors;
import org.jediassessments.galacticstandardcalendar.date.GalacticDate;
import com.datastax.oss.driver.shaded.guava.common.base.Functions;

public class GalacticWindow {
	
	private Map<GalacticDate, GalacticDateInWindowStatus> dates = new TreeMap<>();

	public GalacticWindow() {}
	
	public GalacticWindow(Set<GalacticDate> dates) {
		this(dates, null);
	}
	
	public GalacticWindow(Map<GalacticDate, GalacticDateInWindowStatus> dates) {
		this.dates = dates;
	}

	public GalacticWindow(Set<GalacticDate> mDates, GalacticDate activeDate) {
		dates.putAll(
			mDates.stream().collect(Collectors.toMap(
					Functions.identity(),
					dt->GalacticDateInWindowStatus.None)));
		if (activeDate!=null) {
			for (Map.Entry<GalacticDate, GalacticDateInWindowStatus> entry : dates.entrySet()) {
				switch (entry.getKey().compareTo(activeDate)) {
					case -1 : entry.setValue(GalacticDateInWindowStatus.Completed);break;
					case 0 : entry.setValue(GalacticDateInWindowStatus.Active);break;
					case 1 : entry.setValue(GalacticDateInWindowStatus.None);break;
				}
			}
		}
	}
	
	public GalacticWindow switchTo(GalacticDate dt) {
		return new GalacticWindow(dates.keySet(), dt);
	}

	public Map<GalacticDate, GalacticDateInWindowStatus> getDates() {
		return dates;
	}

	public void setDates(Map<GalacticDate, GalacticDateInWindowStatus> dates) {
		this.dates = dates;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((dates == null) ? 0 : dates.hashCode());
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
		GalacticWindow other = (GalacticWindow) obj;
		if (dates == null) {
			if (other.dates != null)
				return false;
		} else if (!dates.equals(other.dates))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "GalacticWindow [dates=" + dates + "]";
	}

}
