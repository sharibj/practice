package random.practice.rotateLinkedList;

/**
 * @author sjafari
 *
 */
public class RotateLinkedList {
	public static void main(String[] args) {
		// Test Case
		final int totalNodes = 6;
		final int n = 2;
		final String nodeNamePrefix = "Node";
		Node head = null;

		// Create Linked List
		if (totalNodes > 0) {
			head = new Node();
			head.setName(nodeNamePrefix + "0");
			Node currNode = head;
			for (int i = 1; i < totalNodes; i++) {
				Node node = new Node();
				node.setName(nodeNamePrefix + i);
				currNode.setNext(node);
				currNode = node;
			}
		}

		// Print original list
		printNode(head, "Original Linked List...");

		// Rotate List
		RotateLinkedList test = new RotateLinkedList();
		head = test.rotateList(head, n);

		// Print rotated list
		printNode(head, "Rotated Linked List...");
	}

	/**
	 * Utility method to print the contents of a linked list, sequentially
	 * 
	 * @param head   Linked List Header
	 * @param header Header Message
	 */
	private static void printNode(Node head, String header) {
		System.out.println(header);
		if (head == null) {
			System.out.println("null");
		} else {
			for (; head.getNext() != null; head = head.getNext()) {
				System.out.print(head.getName() + " ->");
			}
			System.out.println(head.getName());
		}
	}

	/**
	 * Rotate a linked list, n elements, to the right
	 * 
	 * @param head Linked List Header
	 * @param n    Number of elements to be rotated
	 * @return
	 */
	public Node rotateList(Node head, int n) {
		// Null check
		if (head == null || n <= 0) {
			return head;
		}

		// Calculate length and get last node
		Node lastNode = head;
		int len = 1;
		for (; lastNode.getNext() != null; len++, lastNode = lastNode.getNext())
			;

		// Mod n to decide actual number of rotations
		n = n % len;

		// Check if rotation is needed
		if (n <= 0) {
			return head;
		}

		// Get new last node (after rotation)
		int newEnd = len - n - 1;
		Node newLastNode = head;
		for (int i = 0; i < newEnd; i++) {
			newLastNode = newLastNode.getNext();
		}

		// Get new head (After rotation)
		Node newHead = newLastNode.getNext();

		// Swap pointers
		// Last node should point to existing head
		lastNode.setNext(head);
		// New last node should point to null
		newLastNode.setNext(null);

		return newHead;
	}

}

/**
 * Linked list node
 * @author sjafari
 *
 */
class Node {
	private String name;
	private Node next;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Node getNext() {
		return next;
	}

	public void setNext(Node next) {
		this.next = next;
	}
}