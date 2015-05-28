package tudelft.ti2806.pl3.data;

public class Genome {
	private int hashCode = -1;
	private final String identifier;
	
	public Genome(String identifier) {
		this.identifier = identifier;
	}
	
	@Override
	public int hashCode() {
		if (hashCode == -1) {
			hashCode = identifier.hashCode();
		}
		return hashCode;
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

	public String getIdentifier() {
		return identifier;
	}
}
