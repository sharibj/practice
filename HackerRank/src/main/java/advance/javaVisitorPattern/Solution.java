package advance.javaVisitorPattern;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;
import java.util.Set;
import java.util.TreeSet;

enum Color {
	RED, GREEN
}

abstract class Tree {

	private int value;
	private Color color;
	private int depth;

	public Tree(int value, Color color, int depth) {
		this.value = value;
		this.color = color;
		this.depth = depth;
	}

	public int getValue() {
		return value;
	}

	public Color getColor() {
		return color;
	}

	public int getDepth() {
		return depth;
	}

	public abstract void accept(TreeVis visitor);
}

class TreeNode extends Tree {

	private ArrayList<Tree> children = new ArrayList<>();

	public TreeNode(int value, Color color, int depth) {
		super(value, color, depth);
	}

	public void accept(TreeVis visitor) {
		visitor.visitNode(this);

		for (Tree child : children) {
			child.accept(visitor);
		}
	}

	public void addChild(Tree child) {
		children.add(child);
	}
}

class TreeLeaf extends Tree {

	public TreeLeaf(int value, Color color, int depth) {
		super(value, color, depth);
	}

	public void accept(TreeVis visitor) {
		visitor.visitLeaf(this);
	}
}

abstract class TreeVis {
	public abstract int getResult();

	public abstract void visitNode(TreeNode node);

	public abstract void visitLeaf(TreeLeaf leaf);

}

class SumInLeavesVisitor extends TreeVis {
	private int sum;

	SumInLeavesVisitor() {
		sum = 0;
	}

	public int getResult() {
		return sum;
	}

	public void visitNode(TreeNode node) {
		// Do Nothing
	}

	public void visitLeaf(TreeLeaf leaf) {
		sum += leaf.getValue();
	}
}

class ProductOfRedNodesVisitor extends TreeVis {
	private static final int MOD = 1000000007;
	private BigInteger prod = BigInteger.valueOf(1);

	public int getResult() {
		return prod.mod(BigInteger.valueOf(MOD)).intValue();
	}

	public void visitNode(TreeNode node) {
		product(node);
	}

	public void visitLeaf(TreeLeaf leaf) {
		product(leaf);
	}

	private void product(Tree tree) {
		if (tree.getColor().equals(Color.RED)) {
			prod = prod.multiply(BigInteger.valueOf(tree.getValue()));
		}
	}
}

class FancyVisitor extends TreeVis {
	private int sumNonLeafEven = 0;
	private int sumGreen = 0;

	public int getResult() {
		return Math.abs(sumGreen - sumNonLeafEven);
	}

	public void visitNode(TreeNode node) {
		if (node.getDepth() % 2 == 0) {
			sumNonLeafEven += node.getValue();
		}
	}

	public void visitLeaf(TreeLeaf leaf) {
		if (leaf.getColor().equals(Color.GREEN)) {
			sumGreen += leaf.getValue();
		}
	}
}

/**
 * @author sjafari
 *
 */
public class Solution {

	private static int[] values;
	private static Color[] colors;
	private static Map<Integer, Set<Integer>> edges;

	/**
	 * Initialize static fields based on the tree size provided
	 * 
	 * @param n Tree size
	 */
	private static void initialize(int n) {
		values = new int[n];
		colors = new Color[n];
		edges = new HashMap<>();
		for (int i = 0; i < n; i++) {
			edges.put(i, new TreeSet<Integer>());
		}
	}

	/**
	 * Get directional edges to children
	 * 
	 * @param rootNodeId
	 * @param edges
	 * @param reverseEdges
	 */
	private static Tree createTree(int rootNodeId, Tree parent) {
		// Add children
		Set<Integer> children = edges.get(rootNodeId);
		Tree currentNode = null;
		if (parent == null) {
			// Create root node
			if (values.length > 1) {
				currentNode = new TreeNode(values[0], colors[0], 0);
			} else {
				currentNode = new TreeLeaf(values[0], colors[0], 0);
			}
		} else {
			// Create current node
			if (children.isEmpty()) {
				currentNode = new TreeLeaf(values[rootNodeId], colors[rootNodeId], parent.getDepth() + 1);
			} else {
				currentNode = new TreeNode(values[rootNodeId], colors[rootNodeId], parent.getDepth() + 1);
			}
		}
		// Recursively create trees for all children
		for (Integer child : children) {
			// Remove child to parent edge
			edges.get(child).remove(rootNodeId);

			// Create sub-tree for child
			TreeNode node = (TreeNode) currentNode;
			node.addChild(createTree(child, currentNode));
		}
		return currentNode;
	}

	public static Tree solve() {
		// Read the tree from STDIN and return its root as a return value of this
		// function
		Scanner in = new Scanner(System.in);
		// Total nodes
		int n = in.nextInt();

		// Initialize fields
		initialize(n);

		// Read node values
		for (int i = 0; i < n; i++) {
			values[i] = in.nextInt();
		}

		// Read node colors
		for (int i = 0; i < n; i++) {
			if (in.nextInt() == 0) {
				colors[i] = Color.RED;
			} else {
				colors[i] = Color.GREEN;
			}
		}

		// Read edges and reverse edges
		for (int i = 0; i < n - 1; i++) {
			int u = in.nextInt() - 1;
			int v = in.nextInt() - 1;
			edges.get(u).add(v);
			edges.get(v).add(u);
		}
		in.close();

		// Create entire tree
		return createTree(0, null);
	}

	public static void main(String[] args) {
		Tree root = solve();
		SumInLeavesVisitor vis1 = new SumInLeavesVisitor();
		ProductOfRedNodesVisitor vis2 = new ProductOfRedNodesVisitor();
		FancyVisitor vis3 = new FancyVisitor();

		root.accept(vis1);
		root.accept(vis2);
		root.accept(vis3);

		int res1 = vis1.getResult();
		int res2 = vis2.getResult();
		int res3 = vis3.getResult();

		System.out.println(res1);
		System.out.println(res2);
		System.out.println(res3);
	}
}
