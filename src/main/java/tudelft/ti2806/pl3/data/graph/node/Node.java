package tudelft.ti2806.pl3.data.graph.node;

import tudelft.ti2806.pl3.data.Genome;

public interface Node {
	int getId();
	
	Genome[] getSource();
	
	int getRefStartPoint();
	
	int getRefEndPoint();
	
	String toString();
	
	boolean equals(Object obj);
	
	int hashCode();
	
	long getWidth();
	
}
