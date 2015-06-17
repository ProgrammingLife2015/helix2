package tudelft.ti2806.pl3.util;

import newick.NewickParser;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;

/**
 * Util class for parsing the Phylogenetic tree.
 * Created by Boris Mattijssen on 22-05-15.
 */
public class TreeParser {

	private TreeParser() {}

	/**
	 * Parse the phylogenetic tree with the Newick Parser.
	 *
	 * @param treeFile
	 * 		phylogenetic tree file (.nwk)
	 * @return Parsed tree
	 * @throws java.io.IOException
	 * 		if the file is wrong
	 * @throws newick.ParseException
	 * 		if the file is not formatted correctly
	 */
	public static NewickParser.TreeNode parseTreeFile(File treeFile) throws IOException, newick.ParseException {
		BufferedReader br = new BufferedReader(new InputStreamReader(
				new FileInputStream(treeFile), StandardCharsets.UTF_8));
		String line = br.readLine();
		NewickParser.TreeNode tree;
		if (line != null) {
			tree = new NewickParser(new ByteArrayInputStream(line
					.replaceAll("(\\d)\\.(\\d*)e-05", "0.0000$1$2")
					.replaceAll("(\\d)\\.(\\d*)e-06", "0.00000$1$2")
					.replaceAll("(\\d)\\.(\\d*)e-07", "0.000000$1$2")
					.replaceAll("-", "_").getBytes(StandardCharsets.UTF_8)))
					.tree();
		} else {
			tree = new NewickParser.TreeNode();
		}
		br.close();
		return tree;
	}
}
