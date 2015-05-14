package tudelft.ti2806.pl3.visualization.node;

public class SingleWrapper extends NodePositionWrapper {
	
	private NodePositionWrapper target;
	
	public SingleWrapper(NodePositionWrapper target) {
		this.target = target;
	}
	
	@Override
	long getXStart() {
		return target.getXStart();
	}
	
	@Override
	long getXEnd() {
		return target.getXEnd();
	}
	
	@Override
	long getWidth() {
		return target.getWidth();
	}
	
	@Override
	public String getIdString() {
		return target.getIdString();
	}
	
}
