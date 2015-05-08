package tudelft.ti2806.pl3.data;

public class Genome {
	private final String identifier;
	private int yposition;
	
	public Genome(String identifier, int yposition) {
		this.identifier = identifier;
		this.yposition = yposition;
	}
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((identifier == null) ? 0 : identifier.hashCode());
		return result;
	}
	
	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null) {
			return false;
		}
		if (getClass() != obj.getClass()) {
			return false;
		}
		Genome other = (Genome) obj;
		if (identifier == null) {
			if (other.identifier != null) {
				return false;
			}
		} else if (!identifier.equals(other.identifier)) {
			return false;
		}
		return true;
	}
	
	public int getYposition() {
		return yposition;
	}
	
	public void setYposition(int yposition) {
		this.yposition = yposition;
	}
	
	public String getIdentifier() {
		return identifier;
	}
}
