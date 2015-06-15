package nl.tudelft.ti2806.pl3.data.wrapper.operation.unwrap;

import nl.tudelft.ti2806.pl3.data.wrapper.CombineWrapper;
import nl.tudelft.ti2806.pl3.data.wrapper.operation.unwrap.Unwrap;

public class UnwrapTest extends Unwrap {
    @Override
    protected boolean isConditionMet(CombineWrapper node) {
        return true;
    }
}
