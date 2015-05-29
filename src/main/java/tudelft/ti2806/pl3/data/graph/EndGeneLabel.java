package tudelft.ti2806.pl3.data.graph;

/**
 * Created by tombrouws on 27/05/15.
 */
public class EndGeneLabel extends Label {

	private final int endRef;

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
		EndGeneLabel that = (EndGeneLabel) o;

		return endRef == that.endRef;

	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + super.hashCode();
		result = prime * result + endRef;
		return result;
	}

	public EndGeneLabel(String name, int startRef) {
		super(name);
		this.endRef = startRef;
	}

	public int getEndRef() {
		return endRef;
	}
}
