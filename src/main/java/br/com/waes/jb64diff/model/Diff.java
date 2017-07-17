package br.com.waes.jb64diff.model;


/**
 * Describes the offSet and lentgh of the diff between two strings.
 *
 * @constructor creates a new Diff with offset and length.
 * @param offset position at the start of the diff.
 * @param length size of the diff.
 */
public class Diff {
	private int offSet;
	private int length;
	
	public Diff(){
		
	}
	
	public Diff(int offSet, int length) {
		super();
		this.offSet = offSet;
		this.length = length;
	}
	public int getOffSet() {
		return offSet;
	}
	public void setOffSet(int offSet) {
		this.offSet = offSet;
	}
	public int getLength() {
		return length;
	}
	public void setLength(int length) {
		this.length = length;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + length;
		result = prime * result + offSet;
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
		Diff other = (Diff) obj;
		if (length != other.length)
			return false;
		if (offSet != other.offSet)
			return false;
		return true;
	}
	
	
	
}
