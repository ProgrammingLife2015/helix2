package tudelft.ti2806.pl3.data.label;

/**
 * Created by tombrouws on 27/05/15.
 */
public class StartGeneLabel extends Label {

	private final int startRef;

	@Override
	public boolean equals(Object o) {
		if (this == o) {
			return true;
		}
		if (o == null || getClass() != o.getClass()) {
			return false;
		}

		if (!super.equals((Label) o)) {
			return false;
		}
		StartGeneLabel that = (StartGeneLabel) o;

		return startRef == that.startRef;

	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + super.hashCode();
		result = prime * result + startRef;
		return result;
	}

	public StartGeneLabel(String name, int startRef) {
		super(name);
		this.startRef = startRef;
	}

	public int getStartRef() {
		return startRef;
	}
}
