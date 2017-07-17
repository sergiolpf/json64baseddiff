package br.com.waes.jb64diff.model;

import java.util.ArrayList;
import java.util.List;

/**
 * Describes the differences between two same length json 64based String.
 *
 * @constructor creates a new Insight with default values.
 * 
 * @param areEqual determines if strings are equal.
 * @param areSameSize determines if strings have the same size.
 * @param diffs list of {@link Diff} found.
 */
public class Insight {
	private boolean areEqual;
	private boolean areSameSize;
	private List<Diff> diffs;
	
	public Insight(){
		areEqual = false;
		areSameSize = false;
		diffs = new ArrayList<>();
	}
	
	
	
	public Insight(boolean areEqual, boolean areSameSize, List<Diff> diffs) {
		super();
		this.areEqual = areEqual;
		this.areSameSize = areSameSize;
		this.diffs = diffs;
	}

	public boolean isAreEqual() {
		return areEqual;
	}
	public void setAreEqual(boolean areEqual) {
		this.areEqual = areEqual;
	}
	public boolean isAreSameSize() {
		return areSameSize;
	}
	public void setAreSameSize(boolean areSameSize) {
		this.areSameSize = areSameSize;
	}
	public List<Diff> getDiffs() {
		return diffs;
	}
	public void setDiffs(List<Diff> diffs) {
		this.diffs = diffs;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + (areEqual ? 1231 : 1237);
		result = prime * result + (areSameSize ? 1231 : 1237);
		result = prime * result + ((diffs == null) ? 0 : diffs.hashCode());
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
		Insight other = (Insight) obj;
		if (areEqual != other.areEqual)
			return false;
		if (areSameSize != other.areSameSize)
			return false;
		if (diffs == null) {
			if (other.diffs != null)
				return false;
		} else if (!diffs.equals(other.diffs))
			return false;
		return true;
	}
	
	
	
	
}
