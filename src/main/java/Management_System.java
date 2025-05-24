
import DataStr.*;

public class Management_System {
    public Stack stack = new Stack();
    public Queue queue = new Queue();
    public SLL sll = new SLL();
    public BST bst = new BST();

    public void insert(Item item) {
        bst.insert(item);
        sll.insert(item);
        queue.enqueue(item);
    }
    public void update(Item item) {
        bst.update(item);
        sll.update(item);
        queue.update(item);
    }

    public void delete(int id) {
        bst.delete(id);
        queue.delete(id);
        Item delitem = sll.delete(id);
        if (delitem != null) {
            stack.push(delitem);
        }
    }

}
