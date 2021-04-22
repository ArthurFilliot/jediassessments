package org.jediassessments.galacticstandardcalendar.window;

import java.util.Map;
import java.util.Set;
import java.util.TreeMap;
import java.util.function.Function;
import java.util.stream.Collectors;
import org.jediassessments.galacticstandardcalendar.date.GalacticDate;

public class GalacticWindow {
	
	private Map<GalacticDate, Long> dates = new TreeMap<>();

	public GalacticWindow() {}
	
	public GalacticWindow(Map<GalacticDate, Long> dates) {
		this.dates = dates;
	}

	public Map<GalacticDate, Long> getDates() {
		return dates;
	}

	public void setDates(Map<GalacticDate, Long> dates) {
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
