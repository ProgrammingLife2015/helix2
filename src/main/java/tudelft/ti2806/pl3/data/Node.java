package tudelft.ti2806.pl3.data;

public interface Node {
	int getNodeId();
	
	Genome[] getSource();
	
	int getRefStartPoint();
	
	int getRefEndPoint();
	
	byte[] getContent();
	
	String toString();
	
	boolean equals(Object obj);
	
	int hashCode();
}
