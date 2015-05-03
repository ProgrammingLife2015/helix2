package tudelft.ti2806.pl3.data.graph;

import tudelft.ti2806.pl3.data.Genome;

public interface Node {
	int getNodeId();
	
	Genome[] getSource();
	
	int getRefStartPoint();
	
	int getRefEndPoint();
	
	byte[] getContent();
	
	String toString();
	
	boolean equals(Object obj);
	
	int hashCode();
	
	int getYaxisOrder();
	
	long getXaxisStart();
	
	long getXEnd();
	
	long getWidth();
}
