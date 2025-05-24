package DataStr;

public class Node {
    public Item item;
    public Node next;
    public Node left;
    public Node right;

    public Node(Item item) {
        this.item = item;
        this.next = null;
        this.left = null;
        this.right = null;
    }

}