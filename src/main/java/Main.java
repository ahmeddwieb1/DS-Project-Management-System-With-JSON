import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import DataStr.*;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;

public class Main {
    private static Management_System system = new Management_System();
    private static Scanner sc = new Scanner(System.in);
    static boolean isRunning = true;
    private static final String JSON_FILE = "src/main/resources/items.json";
    private static final ObjectMapper mapper = new ObjectMapper()
            .enable(SerializationFeature.INDENT_OUTPUT);

    public static void main(String[] args) {
        while (isRunning) {
            printMenu();
            try {
                int choice = sc.nextInt();
                sc.nextLine();

                switch (choice) {
                    case 1 -> addItem();
                    case 2 -> viewAllItems();
                    case 3 -> updateItem();
                    case 4 -> searchItem();
                    case 5 -> deleteItem();
                    case 6 -> undoDelete();
                    case 7 -> processUrgentItems();
                    case 8 -> saveToFile();
                    case 9 -> loadFromFile();
                    case 10 -> {
                        System.out.println("Exiting...");
                        System.exit(0);
                    }
                    default -> System.out.println("Invalid choice!");
                }
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }
    }

    private static void printMenu() {
        System.out.println("\n===== Management System =====");
        System.out.println("1. Add Item");
        System.out.println("2. View All Items");
        System.out.println("3. Update Item by ID");
        System.out.println("4. Search Item by ID");
        System.out.println("5. Delete Item");
        System.out.println("6. Undo Last Delete");
        System.out.println("7. Process Urgent Items");
        System.out.println("8. Save to File");
        System.out.println("9. Load from File");
        System.out.println("10. Exit");
        System.out.print("Enter your choice: ");
    }

    private static void addItem() {
        System.out.println("\n--- Add New Item ---");
        System.out.print("Enter ID: ");
        int id = sc.nextInt();
        sc.nextLine();
        if (system.bst.search(id) != null) {
            System.out.println("An item with this ID already exists!");
            return;
        }
        System.out.print("Enter Name: ");
        String name = sc.nextLine();

        System.out.print("Enter Description: ");
        String desc = sc.nextLine();

        System.out.print("Enter Category: ");
        String category = sc.nextLine();

        System.out.print("Enter Priority (urgent/normal): ");
        String priority = sc.next();

        Item newItem = new Item(id, name, desc, category, priority);
        system.insert(newItem);
        System.out.println("Item added successfully!");
    }

    private static void updateItem() {
        System.out.println("\n--- Update Existing Item ---");
        System.out.print("Enter ID of the item to update: ");
        int id = sc.nextInt();
        sc.nextLine();
        Item existingItem = system.bst.search(id);
        if (existingItem == null) {
            System.out.println("No item found with this ID!");
            return;
        }

        System.out.print("Enter New Name (leave empty to keep current: " + existingItem.name + "): ");
        String name = sc.nextLine();
        if (name.isEmpty()) {
            name = existingItem.name;
        }

        System.out.print("Enter New Description (leave empty to keep current: " + existingItem.description + "): ");
        String desc = sc.nextLine();
        if (desc.isEmpty()) {
            desc = existingItem.description;
        }

        System.out.print("Enter New Category (leave empty to keep current: " + existingItem.category + "): ");
        String category = sc.nextLine();
        if (category.isEmpty()) {
            category = existingItem.category;
        }

        System.out.print("Enter New Priority (leave empty to keep current: " + existingItem.priority + "): ");
        String priority = sc.next();
        if (priority.isEmpty()) {
            priority = existingItem.priority;
        }

        Item updatedItem = new Item(id, name, desc, category, priority);
        system.update(updatedItem);
        System.out.println("Item updated successfully!");
        while (!system.queue.isEmpty()){
            system.queue.dequeue();
        }
        Node temp = system.sll.head;
        while (temp != null){
            system.queue.enqueue(temp.item);
            temp = temp.next;
        }
    }

    private static void viewAllItems() {
        System.out.println("\n--- All Items ---");
        SLL items = system.sll;
        if (system.sll == null) {
            System.out.println("No items to display.");
        } else {
            items.displayAll();
        }

    }

    private static void searchItem() {
        System.out.print("\nEnter Item ID to search: ");
        int id = sc.nextInt();
        sc.nextLine();

        Item found = system.bst.search(id);
        if (found != null) {
            System.out.println("Found: " + found.name);
        } else {
            System.out.println("Item not found!");
        }
    }

    private static void deleteItem() {
        System.out.print("\nEnter Item ID to delete: ");
        int id = sc.nextInt();
        sc.nextLine();

        system.delete(id);
        System.out.println("Item deleted (use Option 5 to undo)");
    }

    private static void undoDelete() {
        Item restored = system.stack.pop();
        if (restored != null) {
            system.insert(restored);
            System.out.println("Undo successful: " + restored.name + "is back");
        } else {
            System.out.println("Nothing to undo!");
        }
    }

    private static void processUrgentItems() {
        System.out.println("\n--- Processing Urgent Items ---");
        Queue queue = system.queue;
        if (system.queue.isEmpty()) {
            System.out.println("No urgent items to process.");
            return;
        }
        while (!queue.isEmpty()) {
            queue.displayAll();
            break;
        }
    }

    private static void saveToFile() {
        try {
            // Convert SLL to List
            List<Item> items = new ArrayList<>();
            Node current = system.sll.head;
            while (current != null) {
                items.add(current.item);
                current = current.next;
            }

            mapper.writeValue(new File(JSON_FILE), items);
            System.out.println("Data saved successfully to " + JSON_FILE);
        } catch (IOException e) {
            System.out.println("Error saving JSON file: " + e.getMessage());
        }
    }

    private static void loadFromFile() {
        try {
            Item[] items = mapper.readValue(new File(JSON_FILE), Item[].class);

            system = new Management_System();

            for (Item item : items) {
                system.insert(item);
            }
            System.out.println("Data loaded successfully from " + JSON_FILE);
        } catch (FileNotFoundException e) {
            System.out.println("JSON file not found. Starting with empty system.");
        } catch (IOException e) {
            System.out.println("Error loading JSON file: " + e.getMessage());
        }
    }
}