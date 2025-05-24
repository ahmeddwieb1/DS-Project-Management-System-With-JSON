package DataStr;

public class Stack {
    public Node top;

    public Stack() {
        this.top = null;
    }
    public void push(Item item) {
        Node newNode = new Node(item);
        newNode.next = top;
        top = newNode;
    }

    public Item pop() {
        if (top == null) {
            return null;
        }
        Item item = top.item;
        top = top.next;
        return item;
    }
    public void displayAll() {
        Node current = top;
        while (current != null) {
            System.out.println("ID: " + current.item.id + ", Name: " + current.item.name +
                    ", Description: " + current.item.description +
                    ", Category: " + current.item.category +
                    ", Priority: " + current.item.priority);
            current = current.next;
        }
    }
}
