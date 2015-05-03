package tudelft.ti2806.pl3.data.graph.positioning;

/**
 * Represents a line as<br>
 * y = {@link #slope}*x + {@link #constant}.<br>
 * Limited by {@link #x1} & {@link #x2}.
 * 
 * @author Sam Smulders
 */
public class Line {
	public final long x1;
	public final long x2;
	public final float slope;
	public final float constant;
	
	/**
	 * Initialise a line in the form y = {@link #slope}*x + {@link #constant}.<br>
	 * The line should be from left to right.
	 * 
	 * @param x1
	 *            x coordinate of the position on the left
	 * @param y1
	 *            y coordinate of the position on the left
	 * @param x2
	 *            x coordinate of the position on the right
	 * @param y2
	 *            y coordinate of the position on the right
	 */
	public Line(long x1, int y1, long x2, int y2) {
		this.x1 = x1;
		this.x2 = x2;
		this.slope = calculateSlope(y1, y2);
		this.constant = calculateConstant(y1);
	}
	
	/**
	 * Initialise a line in the form y = {@link #slope}*x + {@link #constant}.<br>
	 * 
	 * @param x1
	 *            the smallest x on the line
	 * @param x2
	 *            the biggest x on the line
	 * @param slope
	 *            the slope of the line
	 * @param constant
	 *            the constant of the line
	 */
	public Line(long x1, long x2, float slope, float constant) {
		this.x1 = x1;
		this.x2 = x2;
		this.slope = slope;
		this.constant = constant;
	}
	
	/**
	 * Checks for the intersection between two lines.
	 * 
	 * @param other
	 *            the other line
	 * @return true if there is an intersection between the two lines<br>
	 *         else false
	 */
	public boolean intersect(Line other) {
		if (this.slope - other.slope == 0f) {
			return false;
		}
		float hit = getXOfIntersection(other);
		return hit > this.x1 && hit > other.x1 && hit < this.x2
				&& hit < other.x2;
	}
	
	/**
	 * Calculates the x of the point of intersection between the two lines. This
	 * position may be out of the range of one of the lines. <br>
	 * Precondition: this.slope - other.slope != 0
	 * 
	 * @param other
	 *            the other line
	 * @return the x of intersection
	 */
	public float getXOfIntersection(Line other) {
		return (other.constant - this.constant) / (this.slope - other.slope);
	}
	
	/**
	 * Calculates the y for the given x on the line.
	 * 
	 * @param x
	 *            the x position on the line
	 * @return the belonging y position on the given x position.
	 */
	public float getY(float x) {
		return this.constant + x * this.slope;
	}
	
	private float calculateSlope(int y1, int y2) {
		return ((float) (y2 - y1)) / (x2 - x1);
	}
	
	private float calculateConstant(int y1) {
		return y1 - slope * x1;
	}
}
