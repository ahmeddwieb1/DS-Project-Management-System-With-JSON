package DataStr;

public class SLL {
    public Node head;

    public SLL() {
        this.head = null;
    }

    public void insert(Item item) {
        Node newNode = new Node(item);
        if (head == null) {
            head = newNode;
        } else {
            Node current = head;
            while (current.next != null) {
                current = current.next;
            }
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

    public void displayAll() {
        Node current = head;
        while (current != null) {
            System.out.println("ID: " + current.item.id + ", Name: " + current.item.name +
                    ", Description: " + current.item.description +
                    ", Category: " + current.item.category +
                    ", Priority: " + current.item.priority);
            current = current.next;
        }
    }

}
