package tudelft.ti2806.pl3.math;

/**
 * Represents a line as<br>
 * y = {@link #slope}*x + {@link #constant}.<br>
 * Limited by {@link #x1} & {@link #x2}.
 *
 * @author Sam Smulders
 */
public class Line {
    public final float x1;
    public final float x2;
    public final float slope;
    public final float constant;

    /**
     * Initialise a line in the form y = {@link #slope}*x + {@link #constant}.<br>
     * The line should be from left to right.
     *
     * @param f
     *         x coordinate of the position on the left
     * @param yy
     *         y coordinate of the position on the left
     * @param g
     *         x coordinate of the position on the right
     * @param yy2
     *         y coordinate of the position on the right
     */
    public Line(float f, float yy, float g, float yy2) {
        this.x1 = f;
        this.x2 = g;
        this.slope = calculateSlope(yy, yy2);
        this.constant = calculateConstant(yy);
    }

    /**
     * Initialise a line in the form y = {@link #slope}*x + {@link #constant}.<br>
     *
     * @param x1
     *         the smallest x on the line
     * @param x2
     *         the biggest x on the line
     * @param slope
     *         the slope of the line
     * @param constant
     *         the constant of the line
     */
    public Line(int x1, int x2, float slope, float constant) {
        this.x1 = x1;
        this.x2 = x2;
        this.slope = slope;
        this.constant = constant;
    }

    /**
     * Checks for the intersection between two lines.
     *
     * @param other
     *         the other line
     * @return true if there is an intersection between the two lines<br>
     * else false
     */
    @SuppressWarnings("FE_FLOATING_POINT_EQUALITY")
    public boolean intersect(Line other) {
        if (!isWithingDomain(other)) {
            return false;
        }
        if (this.slope == other.slope) {
            return this.constant == other.constant;
        }
        float hit = getXOfIntersection(other);
        return isWithinDomain(hit) && other.isWithinDomain(hit);
    }

    /**
     * Checks if the given line shares space on the x axis, excluding the exact
     * borders of the domain.
     *
     * @param other
     *         the other line to compare its space with
     * @return {@code true} if {@code other} shares space on the x axis with
     * this line<br>
     * {@code false} if {@code hit} shares no space on the x axis with
     * this line
     */
    public boolean isWithingDomain(Line other) {
        return !(other.x2 <= this.x1 || other.x1 >= this.x2);
    }

    /**
     * Checks if the given x is within the domain of the line, excluding the
     * exact borders of the domain.
     *
     * @param hit
     *         the x position to check for
     * @return {@code true} if {@code hit} is within the line its domain<br>
     * {@code false} if {@code hit} is outside the line its domain
     */
    public boolean isWithinDomain(float hit) {
        return hit > this.x1 && hit < this.x2;
    }

    /**
     * Calculates the x of the point of intersection between the two lines. This
     * position may be out of the range of one of the lines. <br>
     * Precondition: this.slope - other.slope != 0
     *
     * @param other
     *         the other line
     * @return the x of intersection
     */
    public float getXOfIntersection(Line other) {
        return (other.constant - this.constant) / (this.slope - other.slope);
    }

    /**
     * Calculates the y for the given x on the line.
     *
     * @param xvalue
     *         the x position on the line
     * @return the belonging y position on the given x position.
     */
    public float getY(float xvalue) {
        return this.constant + xvalue * this.slope;
    }

    private float calculateSlope(float y1, float y2) {
        return ((float) (y2 - y1)) / (x2 - x1);
    }

    private float calculateConstant(float y1) {
        return y1 - slope * x1;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + Float.floatToIntBits(constant);
        result = prime * result + Float.floatToIntBits(slope);
        result = prime * result + Float.floatToIntBits(x1);
        result = prime * result + Float.floatToIntBits(x2);
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
        Line other = (Line) obj;
        if (Float.floatToIntBits(constant) != Float
                .floatToIntBits(other.constant)) {
            return false;
        }
        if (Float.floatToIntBits(slope) != Float.floatToIntBits(other.slope)) {
            return false;
        }
        if (Float.floatToIntBits(x1) != Float.floatToIntBits(other.x1)) {
            return false;
        }
        if (Float.floatToIntBits(x2) != Float.floatToIntBits(other.x2)) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Line [x1=" + x1 + ", x2=" + x2 + ", slope=" + slope
                + ", constant=" + constant + "]";
    }
}
