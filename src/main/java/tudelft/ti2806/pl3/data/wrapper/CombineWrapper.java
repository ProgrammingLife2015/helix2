package tudelft.ti2806.pl3.data.wrapper;

import tudelft.ti2806.pl3.data.graph.DataNode;
import tudelft.ti2806.pl3.data.label.Label;

import java.util.List;
import java.util.Set;

public abstract class CombineWrapper extends Wrapper {
	protected List<Wrapper> nodeList;
	
	private float collapse = 0;
	
	public float getCollapse() {
		return this.collapse;
	}
	
	public void addCollapse(float value) {
		this.collapse += value;
	}
	
	/**
	 * CombineWrapper is an abstract class which should not be constructed
	 * directly.
	 * 
	 * <p>
	 * When a class extending this class is constructed, the given list should
	 * fulfil the following conditions. <br>
	 * Preconditions:<br>
	 * <ul>
	 * <li>The list should be sorted on previousNodesCount, with the smallest
	 * value first and the largest last.
	 * <li>All incoming nodes from outside the graph should never be an outgoing
	 * node within the list. And the outgoing nodes from outside the graph
	 * should never be an incoming node within the list.
	 * </ul>
	 * 
	 * @param nodeList
	 *            a list of nodes
	 */
	public CombineWrapper(List<Wrapper> nodeList) {
		this.nodeList = nodeList;
	}
	
	public Wrapper getFirst() {
		return nodeList.get(0);
	}
	
	public Wrapper getLast() {
		return nodeList.get(nodeList.size() - 1);
	}
	
	public List<Wrapper> getNodeList() {
		return nodeList;
	}
	
	@Override
	public String getIdString() {
		StringBuilder str = new StringBuilder("{");
		for (Wrapper node : nodeList) {
			str.append("[" + node.getIdString() + "]");
		}
		return str.toString() + "}";
	}
	
	@Override
	public int getId() {
		return nodeList.get(0).getId();
	}

	@Override
	public void collectLabels(Set<Label> labels) {
		nodeList.forEach(n -> n.collectLabels(labels));
	}

	@Override
	public void collectDataNodes(Set<DataNode> set) {
		nodeList.forEach(n -> n.collectDataNodes(set));
	}
	
	@Override
	public void calculateX() {
		this.x = 0f;
		for (Wrapper node : this.getNodeList()) {
			this.x += node.getX();
		}
		this.x /= this.getNodeList().size();
	}
	
	public boolean canUnwrap() {
		return true;
	}

    @Override
    public boolean contains(Wrapper object) {
        for(Wrapper wrapper : this.nodeList){
            if(object == wrapper || wrapper.contains(object)){
                return true;
            }
        }
        return false;
    }
}
