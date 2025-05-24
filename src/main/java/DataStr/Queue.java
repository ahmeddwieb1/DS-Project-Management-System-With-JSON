package DataStr;

public class Queue {
    public Node head;

    public Queue() {
        this.head = null;
    }

    public void enqueue(Item item) {
        Node newNode = new Node(item);
        if (head == null || "urgent".equals(item.priority)) {
            newNode.next = head;
            head = newNode;
        } else {
            Node current = head;
            while (current.next != null && "urgent".equals(current.next.item.priority)) {
                current = current.next;
            }
            newNode.next = current.next;
            current.next = newNode;
        }
    }

    public Item update(Item item) {
        if (head == null) {
            return null;
        }
        Node current = head;
        while (current != null) {
            if (current.item.id == item.id) {
                current.item = item;
                return item;
            }
            current = current.next;
        }
        return null;
    }

    public Item dequeue() {
        if (head == null) return null;
        Item item = head.item;
        head = head.next;
        return item;
    }

    public Item delete(int id) {
        if (head == null) {
            return null;
        }
        if (head.item.id == id) {
            Item item = head.item;
            head = head.next;
            return item;
        }
        Node current = head;
        while (current.next != null && current.next.item.id != id) {
            current = current.next;
        }
        if (current.next != null) {
            Item item = current.next.item;
            current.next = current.next.next;
            return item;
        }
        return null;
    }

    public boolean isEmpty() {
        return head == null;
    }

    public void displayAll() {
        Node current = head;
        while (current != null) {
            System.out.println("ID: " + current.item.id + ", Name: " + current.item.name +
                    ", Priority: " + current.item.priority);
            current = current.next;
        }
    }
}
