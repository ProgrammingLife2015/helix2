package tudelft.ti2806.pl3.util;

import org.hamcrest.BaseMatcher;
import org.hamcrest.Description;

import tudelft.ti2806.pl3.data.wrapper.Wrapper;

import java.util.Arrays;

public class ArrayOrderMatcher extends BaseMatcher<Wrapper[]> {

    private Wrapper[] order;

    public ArrayOrderMatcher(Wrapper[] order) {
        this.order = order;
    }

    @Override
    public void describeTo(Description description) {
        description.appendText("The given list its order should be in "
                + Arrays.toString(order));
    }

    @Override
    public boolean matches(Object item) {
        if (item == null) {
            return false;
        }
        if (item.getClass() != Wrapper[].class) {
            return false;
        }
        Wrapper[] subList = (Wrapper[]) item;
        int index = 0;
        for (Wrapper node : order) {
            if (subList[index].equals(node)) {
                index++;
                if (index == subList.length) {
                    return true;
                }
            }
        }
        return false;
    }
}
