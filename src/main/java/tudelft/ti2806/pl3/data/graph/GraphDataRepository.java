package tudelft.ti2806.pl3.data.graph;

import tudelft.ti2806.pl3.LoadingObservable;
import tudelft.ti2806.pl3.LoadingObserver;
import tudelft.ti2806.pl3.data.BasePair;
import tudelft.ti2806.pl3.data.Genome;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class GraphDataRepository extends AbstractGraphData implements LoadingObservable {
	private int longestnodepath;

	private ArrayList<LoadingObserver> observers = new ArrayList<>();

	/**
	 * Construct a empty {@code GraphDataRepository}.
	 */
	public GraphDataRepository() {
	}
	
	private ArrayList<LoadingObserver> observers;

	/**
	 * THIS CONSTRUCTOR IS ONLY USED FOR TESTING.
	 * Construct a empty {@code GraphDataRepository}
	 *
	 */
	GraphDataRepository() {
		this.observers = new ArrayList<>();
	}

	/**
	 * TODO: THIS CONSTRUCTOR IS ONLY USED FOR TESTING
	 * Construct a instance of {@code GraphDataRepository}.
	 *
	 * @param nodes
	 * 		the nodes of the graph
	 * @param edges
	 * 		the edges of the graph
	 * @param genomes
	 * 		all {@link Genome} that are present in the graph
	 */
	GraphDataRepository(List<DataNode> nodes, List<Edge> edges,
							   List<Genome> genomes) {
		this.nodes = nodes;
		this.edges = edges;
		this.genomes = genomes;
		this.observers = new ArrayList<>();
	}

	public void addNodes(List<DataNode> nodes){
		this.nodes = nodes;
	}

	public void addEdges(List<Edge> edges) {
		this.edges = edges;
	}

	public void addGenomes(List<Genome> genomes) {
		this.genomes = genomes;
	}

	@Override
	public List<DataNode> getNodes() {
		return this.getNodeListClone();
	}

	@Override
	public List<Edge> getEdges() {
		return this.getEdgeListClone();
	}

	@Override
	public List<Genome> getGenomes(){
		return this.getGenomeClone();
	}

	/**
	 * Parse a node and edge file of a graph into a {@code GraphData}.
	 *
	 * @param nodesFile
	 * 		the file of nodes to be read
	 * @param edgesFile
	 * 		the file of edges to be read
	 *            the file of edges to be read
	 * @throws FileNotFoundException
	 * 		if the file is not found
	 */
	public void parseGraph(File nodesFile, File edgesFile)
			throws FileNotFoundException {
		notifyLoadingObservers("Start parsing the graph.");
		Map<String, Genome> genomeMap = new HashMap<String, Genome>();
		Map<Integer, DataNode> nodeMap = parseNodes(nodesFile, genomeMap);
		List<DataNode> nodeList = new ArrayList<DataNode>();
		nodeList.addAll(nodeMap.values());
		List<Genome> genomeList = new ArrayList<>();
		genomeList.addAll(genomeMap.values());

		addNodes(nodeList);
		addEdges(parseEdges(edgesFile, nodeMap));
		addGenomes(genomeList);

		notifyLoadingObservers("Done parsing the graph.");
	}

	/**
	 * Parse the nodes file, creating nodes from the file its data.
	 *
	 * @param nodesFile
	 * 		the file of nodes to be read
	 * @param genomeMap
	 * 		{@link Genome} mapped on their identifier
	 * @return a list of all nodes, mapped by their node id
	 * @throws FileNotFoundException
	 * 		if the file is not found
	 */
	public Map<Integer, DataNode> parseNodes(File nodesFile,
			Map<String, Genome> genomeMap) throws FileNotFoundException {
		notifyLoadingObservers("Start parsing node in file " + nodesFile.getName());
		BufferedReader br = new BufferedReader(new InputStreamReader(
				new BufferedInputStream(new FileInputStream(nodesFile))));
		Map<Integer, DataNode> nodes = new HashMap<Integer, DataNode>();
		try {
			while (br.ready()) {
				DataNode node = parseNode(br, genomeMap);
				nodes.put(node.getId(), node);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		notifyLoadingObservers("Done parsing nodes.");
		return nodes;
	}

	/**
	 * Parses the next two lines of the scanner into a Node.
	 *
	 * @param br
	 * 		the BufferedReader with two available lines to read
	 * @return the read node
	 */
	protected DataNode parseNode(BufferedReader br,
			Map<String, Genome> genomes) {
		String[] indexData = new String[0];
		try {
			indexData = br.readLine().replaceAll("[> ]", "").split("\\|");
		} catch (IOException e) {
			e.printStackTrace();
		}
		DataNode node = null;
		try {
			node = new DataNode(Integer.parseInt(indexData[0]),
					parseGenomeIdentifiers(indexData[1].split(","), genomes),
					Integer.parseInt(indexData[2]),
					Integer.parseInt(indexData[3]),
					BasePair.getBasePairString(br.readLine()));
		} catch (IOException e) {
			e.printStackTrace();
		}
		return node;
	}

	private Set<Genome> parseGenomeIdentifiers(String[] identifiers,
											Map<String, Genome> genomes) {
		Set<Genome> result = new HashSet<>(identifiers.length);
		for (int i = 0; i < identifiers.length; i++) {
			identifiers[i] = identifiers[i].replaceAll("-", "_");
			Genome genome = genomes.get(identifiers[i]);
			if (genome == null) {
				genome = new Genome(identifiers[i]);
				genomes.put(identifiers[i], genome);
			}
			result.add(genome);
		}
		return result;
	}

	/**
	 * Parse the edges file, adding the connections between the nodes.
	 *
	 * @param edgesFile
	 * 		the file of edges to be read
	 * @param nodes
	 * 		a list of nodes mapped by their node id.
	 * @throws FileNotFoundException
	 * 		if the file is not found
	 */
	public List<Edge> parseEdges(File edgesFile,
			Map<Integer, DataNode> nodes) throws FileNotFoundException {
		notifyLoadingObservers("Start parsing egdes in file " + edgesFile.getName());
		BufferedReader br = new BufferedReader(new InputStreamReader(
				new BufferedInputStream(new FileInputStream(edgesFile))));
		List<Edge> list = new ArrayList<Edge>();
		try {
			while (br.ready()) {
				String[] index = br.readLine().split(" ");
				DataNode nodeFrom = nodes.get(Integer.parseInt(index[0]));
				DataNode nodeTo = nodes.get(Integer.parseInt(index[1]));
				list.add(new Edge(nodeFrom, nodeTo));
			}
			br.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		notifyLoadingObservers("Done parsing edges");
		return list;
	}

	/**
	 * Search for the node in the graph with the given id.
	 *
	 * @param id
	 * 		the id of the node to search
	 * @return the found node<br>
	 * {@code null} if there is no node with this id in the graph
	 */
	public DataNode getNodeByNodeId(int id) {
		for (DataNode node : nodes) {
			if (node.getId() == id) {
				return node;
			}
		}
		return null;
	}

	/**
	 * Search for the edge in the graph with the given from and to id.
	 *
	 * @param fromId
	 * 		the id of the from node on the edge
	 * @param toId
	 * 		the id of the to node on the edge
	 * @return the found edge<br>
	 * {@code null} if there is no node with this id in the graph
	 */
	public Edge getEdge(int fromId, int toId) {
		for (Edge edge : edges) {
			if (edge.getFrom().getId() == fromId
					&& edge.getTo().getId() == toId) {
				return edge;
			}
		}
		return null;
	}

	@Override
	public AbstractGraphData getOrigin() {
		return this;
	}

	@Override
	public int getLongestNodePath() {
		return longestnodepath;
	}

	@Override
	public void addLoadingObserver(LoadingObserver loadingObserver) {
		observers.add(loadingObserver);
	}

	@Override
	public void deleteLoadingObserver(LoadingObserver loadingObserver) {
		observers.remove(loadingObserver);
	}

	@Override
	public void notifyLoadingObservers(Object arguments) {
		for (LoadingObserver observer : observers) {
			observer.update(this,arguments);
		}
	}
}
