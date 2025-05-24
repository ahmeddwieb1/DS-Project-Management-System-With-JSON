package DataStr;

public class BST {
    private Node root;

    public BST() {
        this.root = null;
    }

    public void insert(Item item) {
        root = insert(root, item);
    }

    private Node insert(Node root, Item newitem) {
        Node newNode = new Node(newitem);
        if (root == null) {
            root = newNode;
            return root;
        }

        if (newitem.id < root.item.id) {
            if (root.left == null) {
                root.left = newNode;
            } else {
                root.left = insert(root.left, newitem);
            }
        } else if (newitem.id > root.item.id) {
            if (root.right == null) {
                root.right = newNode;
            } else {
                root.right = insert(root.right, newitem);
            }
        }

        return root;
    }

    public Item update(Item newitem) {
        Item oldItem = search(newitem.id);
        if (oldItem != null) {
            root = update(root, newitem);
        }
        return oldItem;
    }

    public Node update(Node root, Item newitem) {
        if (root == null) {
            return null;
        }
        if (root.item.id == newitem.id) {
            root.item = newitem;
            return root;
        } else if (newitem.id < root.item.id) {
            return update(root.left, newitem);
        } else {
            return update(root.right, newitem);
        }
    }

    private Item search(Node root, int id) {
        if (root == null || root.item.id == id) {
            return (root == null) ? null : root.item;
        }

        if (id < root.item.id) {
            return search(root.left, id);
        } else {
            return search(root.right, id);
        }
    }

    public Item search(int id) {
        return search(root, id);
    }

    public Node delete(Node root, int id) {
        if (root == null) {
            return null;
        }

        if (id < root.item.id) {
            root.left = delete(root.left, id);
        } else if (id > root.item.id) {
            root.right = delete(root.right, id);
        } else {

            if (root.left == null && root.right == null) {
                return null;
            } else if (root.left == null && root.right != null) {
                return root.right;
            } else if (root.left != null && root.right == null) {
                return root.left;
            } else {
                root.item = minValue(root.right);
                root.right = delete(root.right, root.item.id);
            }
        }

        return root;
    }

    public Item minValue(Node root) {
        Item minItem = root.item;
        while (root.left != null) {
            root = root.left;
            minItem = root.item;
        }
        return minItem;
    }

    public Item delete(int id) {
        Item deletedItem = search(root, id);
        if (deletedItem != null) {
            root = delete(root, id);
        }
        return deletedItem;
    }
}