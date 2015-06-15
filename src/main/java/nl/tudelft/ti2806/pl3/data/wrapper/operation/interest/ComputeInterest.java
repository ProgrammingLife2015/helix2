package nl.tudelft.ti2806.pl3.data.wrapper.operation.interest;

import nl.tudelft.ti2806.pl3.data.wrapper.Wrapper;

public class ComputeInterest {
    private static CalculateWrapPressureInterest pressureInterest = new CalculateWrapPressureInterest();
    private static CalculateMultiplyWithContainerInterest multiplyInterest
            = new CalculateMultiplyWithContainerInterest();
    private static CalculateSizeInterest sizeInterest = new CalculateSizeInterest();
    private static CalculateNInterest nInterest = new CalculateNInterest();

    private ComputeInterest() {
    }

    /**
     * Computes the interest values on the graph.
     *
     * @param wrapper
     *         the wrapper
     */
    public static void compute(Wrapper wrapper) {
        pressureInterest.calculate(wrapper, null);
        multiplyInterest.calculate(wrapper, null);
        sizeInterest.calculate(wrapper, null);
        nInterest.calculate(wrapper, null);
    }
}
