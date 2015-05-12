package tudelft.ti2806.pl3.data.graph.node;

import tudelft.ti2806.pl3.data.Genome;

import java.util.List;

public interface Node {
	int getId();
	
	Genome[] getSource();
	
	int getRefStartPoint();
	
	int getRefEndPoint();
	
	byte[] getContent();
	
	String toString();
	
	boolean equals(Object obj);
	
	int hashCode();
	
	int getYaxisOrder();
	
	long getXStart();
	
	long getXEnd();
	
	long getWidth();
	
	int getPreviousNodesCount();
	
	List<SingleNode> getIncoming();
	
	List<SingleNode> getOutgoing();
	
	long calculateStartX();
	
	int calculatePreviousNodesCount();
}
