package tudelft.ti2806.pl3.data.label;

/**
 * Abstract label class
 * Created by tombrouws on 27/05/15.
 */
public abstract class Label {

    private String text;

    /**
     * Constructs an instance of the label.
     *
     * @param s
     *         the String label to set
     */
    public Label(String s) {
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

        return !(text != null ? !text.equals(label.text) : label.text != null);

    }

    @Override
    public int hashCode() {
        return text != null ? text.hashCode() : 0;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }
}
