package tudelft.ti2806.pl3.data.gene;

/**
 * A gene from the gene annotations database.
 * A gene consists of a name and a start and end index.
 */
public class Gene implements Comparable<Gene> {

	private final String name;
	private final int end;
	private final int start;

	/**
	 * Construct a gene containing a name and start and stop index.
	 * @param name The gene's name
	 * @param start The gene's start index
	 * @param end The gene's end index
	 */
	public Gene(String name, int start, int end) {
		this.name = name;
		this.start = start;
		this.end = end;
	}

	public String getName() {
		return name;
	}

	public int getEnd() {
		return end;
	}

	public int getStart() {
		return start;
	}

	/**
	 * Check whether two genes are equal
	 * based on name and start and end index.
	 *
	 * @param obj reference gene
	 * @return whether the genes are equal or not
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null || getClass() != obj.getClass()) {
			return false;
		}

		Gene gene = (Gene) obj;

		if (end != gene.end) {
			return false;
		}
		if (start != gene.start) {
			return false;
		}
		return !(name != null ? !name.equals(gene.name) : gene.name != null);
	}

	/**
	 * Returns the hash code value of the gene.
	 * @return a hash code value for this gene
	 */
	@Override
	public int hashCode() {
		int result = name.hashCode();
		result = 31 * result + end;
		result = 31 * result + start;
		return result;
	}

	@Override
	public String toString() {
		return name;
	}

	@Override
	public int compareTo(Gene o) {
		return name.compareTo(o.getName());
	}
}
