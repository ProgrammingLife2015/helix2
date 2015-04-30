package tudelft.ti2806.pl3.data;

public interface Node {
    int getNodeId();

    String[] getSource();

    int getRefStartPoint();

    int getRefEndPoint();

    Gene[] getContent();

    String toString();

    boolean equals(Object obj);
    int hashCode();
}
