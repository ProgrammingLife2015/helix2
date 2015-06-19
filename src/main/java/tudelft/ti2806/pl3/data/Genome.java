package tudelft.ti2806.pl3.data;

public class Genome {
	private final String identifier;
	private boolean hivStatus;
	private int age;
	private Gender gender;
	private String location;
	private String isolationDate;
	
	public Genome(String identifier) {
		this.identifier = identifier;
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

	@Override
	public String toString() {
		return identifier;
	}

	public String getIdentifier() {
		return identifier;
	}

	public boolean getHivStatus() {
		return hivStatus;
	}

	public void setHivStatus(boolean hivStatus) {
		this.hivStatus = hivStatus;
	}

	public int getAge() {
		return age;
	}

	public void setAge(int age) {
		this.age = age;
	}

	public Gender getGender() {
		return gender;
	}

	public void setGender(Gender gender) {
		this.gender = gender;
	}

	public String getLocation() {
		return location;
	}

	public void setLocation(String location) {
		this.location = location;
	}

	public String getIsolationDate() {
		return isolationDate;
	}

	public void setIsolationDate(String isolationDate) {
		this.isolationDate = isolationDate;
	}
}
