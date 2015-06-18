package tudelft.ti2806.pl3.visualization;

import tudelft.ti2806.pl3.data.wrapper.CombineWrapper;
import tudelft.ti2806.pl3.data.wrapper.DataNodeWrapper;
import tudelft.ti2806.pl3.data.wrapper.FixWrapper;
import tudelft.ti2806.pl3.data.wrapper.HorizontalWrapper;
import tudelft.ti2806.pl3.data.wrapper.SingleWrapper;
import tudelft.ti2806.pl3.data.wrapper.SpaceWrapper;
import tudelft.ti2806.pl3.data.wrapper.VerticalWrapper;
import tudelft.ti2806.pl3.data.wrapper.Wrapper;
import tudelft.ti2806.pl3.data.wrapper.WrapperClone;
import tudelft.ti2806.pl3.data.wrapper.WrapperPlaceholder;
import tudelft.ti2806.pl3.data.wrapper.operation.WrapperOperation;

public class WrapperPrinter extends WrapperOperation {

    private void print(CombineWrapper wrapper, Wrapper container) {
        System.out.println(wrapper+"{");
        for (Wrapper node : wrapper.getNodeList()) {
            calculate(node, wrapper);
        }
        System.out.println("}");
    }
    
    @Override
    public void calculate(HorizontalWrapper wrapper, Wrapper container) {
        print(wrapper, container);        
    }
    
    @Override
    public void calculate(VerticalWrapper wrapper, Wrapper container) {
        print(wrapper, container);
    }

    @Override
    public void calculate(SpaceWrapper wrapper, Wrapper container) {
        print(wrapper, container);
    }
    
    @Override
    public void calculate(SingleWrapper wrapper, Wrapper container) {
        System.out.println(wrapper+"{");
        super.calculate(wrapper, container);
        System.out.println("}");
    }
    
    @Override
    public void calculate(DataNodeWrapper wrapper, Wrapper container) {
        System.out.println(wrapper);
    }
    
    @Override
    public void calculate(FixWrapper wrapper, Wrapper container) {
        System.out.println(wrapper);
    }
    
    @Override
    public void calculate(WrapperClone wrapper, Wrapper nodeWrapper) {
        System.out.println(wrapper);
    }
    
    @Override
    public void calculate(WrapperPlaceholder wrapper,
            Wrapper nodeWrapper) {
        System.out.println(wrapper);
    }
}
