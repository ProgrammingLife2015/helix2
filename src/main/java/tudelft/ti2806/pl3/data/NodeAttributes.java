package tudelft.ti2806.pl3.data;

/**
 * Node Attributes is a holder of attribute names used for storing the parsed
 * data from the edge and node file in the Node.
 * 
 * @author Sam Smulders
 */
public class NodeAttributes {
	/**
	 * Holds an {@link ArrayList}<{@link String}> with node ids of incoming
	 * connections.
	 */
	public static final String INCOMING_CONNECTIONS = "INCOMMING_CONNECTIONS";
	/**
	 * Holds an {@link ArrayList}<{@link String}> with node ids of outgoing
	 * connections.
	 */
	public static final String OUTGOING_CONNECTIONS = "OUTGOING_CONNECTIONS";
	/**
	 * Holds a {@link AttributeArray}<{@link String}> with genome names.
	 */
	public static final String SOURCE = "SOURCE";
	/**
	 * Holds an {@link Integer} with the start index of the node on the genome.
	 */
	public static final String REF_START_POINT = "REF_START_POINT";
	/**
	 * Holds an {@link Integer} with the end index of the node on the genome.
	 */
	public static final String REF_END_POINT = "REF_END_POINT";
	/**
	 * Holds a {@link AttributeArray}<{@link Gene}> with the content of the node.
	 */
	public static final String CONTENT = "CONTENT";
	
}
