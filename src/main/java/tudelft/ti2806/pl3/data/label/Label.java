package tudelft.ti2806.pl3.data.label;

/**
 * Abstract label class
 * Created by tombrouws on 27/05/15.
 */
public abstract class Label {

	private final String text;

	/**
	 * Constructs an instance of the label.
	 * @param s
	 * 			the String label to set
	 */
	Label(String s) {
		if (s != null) {
			text = s;
		} else {
			text = "";
		}
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) {
			return true;
		}
		if (o == null || getClass() != o.getClass()) {
			return false;
		}

		Label label = (Label) o;

		return !(!text.equals(label.text));

	}

	@Override
	public int hashCode() {
		return text.hashCode();
	}

	public String getText() {
		return text;
	}

	@Override
	public String toString() {
		return text;
	}
}
