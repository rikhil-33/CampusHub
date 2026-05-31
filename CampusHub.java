import java.util.Scanner;

// Doubt Forum — uses a Singly Linked List for dynamic insertion
class DoubtNode {
    String question;
    boolean isAnonymous;
    DoubtNode next;

    public DoubtNode(String q, boolean anon) {
        this.question = q;
        this.isAnonymous = anon;
        this.next = null;
    }
}

class DoubtLinkedList {
    DoubtNode head;

    // Insert a new doubt at the end of the list
    public void insert(String q, boolean anon) {
        DoubtNode newNode = new DoubtNode(q, anon);
        if (head == null) {
            head = newNode;
        } else {
            DoubtNode temp = head;
            while (temp.next != null)
                temp = temp.next;
            temp.next = newNode;
        }
    }

    // Traverse and display all doubts
    public void display() {
        DoubtNode temp = head;
        while (temp != null) {
            String author = temp.isAnonymous ? "🤫 Anon" : "👤 User";
            System.out.println(author + " asks: " + temp.question);
            temp = temp.next;
        }
    }
}

// Notifications — uses a Stack (LIFO) so the most recent notification shows first
class NotificationStack {
    String[] stack = new String[50];
    int top = -1;

    public void push(String message) {
        if (top < 49)
            stack[++top] = message;
    }

    public void popAndDisplay() {
        if (top == -1) {
            System.out.println("No recent notifications.");
        } else {
            System.out.println("🔔 " + stack[top--]);
        }
    }
}

// Marketplace item model
class MarketItem {
    String title;
    double price;

    public MarketItem(String t, double p) {
        title = t;
        price = p;
    }
}

// Marketplace — uses a Hash Table with linear probing for fast item storage and lookup
class MarketHashTable {
    MarketItem[] table = new MarketItem[20];
    int size = 20;

    // Hash function: sum of character ASCII values mod table size
    public int hashFunction(String key) {
        int hash = 0;
        for (char c : key.toCharArray())
            hash += c;
        return hash % size;
    }

    // Insert with linear probing to handle collisions
    public void insert(MarketItem item) {
        int index = hashFunction(item.title);
        while (table[index] != null) {
            index = (index + 1) % size;
        }
        table[index] = item;
    }

    public MarketItem[] getAllValidItems() {
        int count = 0;
        for (MarketItem item : table)
            if (item != null)
                count++;
        MarketItem[] items = new MarketItem[count];
        int idx = 0;
        for (MarketItem item : table)
            if (item != null)
                items[idx++] = item;
        return items;
    }
}

// Sorting and searching algorithms for the marketplace
class Algorithms {

    // Bubble Sort — sorts marketplace items by price (cheapest first)
    public static void bubbleSortByPrice(MarketItem[] arr) {
        int n = arr.length;
        for (int i = 0; i < n - 1; i++) {
            for (int j = 0; j < n - i - 1; j++) {
                if (arr[j].price > arr[j + 1].price) {
                    MarketItem temp = arr[j];
                    arr[j] = arr[j + 1];
                    arr[j + 1] = temp;
                }
            }
        }
    }

    // Linear Search — finds a marketplace item by title
    public static void linearSearchTitle(MarketItem[] arr, String target) {
        boolean found = false;
        for (int i = 0; i < arr.length; i++) {
            if (arr[i].title.equalsIgnoreCase(target)) {
                System.out.println("✅ Found: " + arr[i].title + " for ₹" + arr[i].price);
                found = true;
                break;
            }
        }
        if (!found)
            System.out.println("❌ Item not found.");
    }
}

// Main application
public class CampusHub {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        DoubtLinkedList forum = new DoubtLinkedList();
        NotificationStack notifs = new NotificationStack();
        MarketHashTable market = new MarketHashTable();

        // Pre-loaded sample data
        forum.insert("Can someone explain Time Complexity?", true);
        market.insert(new MarketItem("DSA Hardcopy Book", 450.0));
        market.insert(new MarketItem("Unit 1-4 Softcopy PDF", 50.0));
        notifs.push("Welcome to CampusHub!");

        while (true) {
            System.out.println("\n--- CampusHub Menu ---");
            System.out.println("1. Post / View Doubts");
            System.out.println("2. View Marketplace (Sorted by Price)");
            System.out.println("3. Search Marketplace");
            System.out.println("4. Sell an Item");
            System.out.println("5. Check Latest Notification");
            System.out.println("6. Exit");
            System.out.print("Choice: ");
            String choice = sc.nextLine();

            if (choice.equals("1")) {
                System.out.print("Enter your doubt: ");
                String q = sc.nextLine();
                forum.insert(q, true);
                System.out.println("Doubt posted! Current Forum:");
                forum.display();
            } else if (choice.equals("2")) {
                MarketItem[] items = market.getAllValidItems();
                Algorithms.bubbleSortByPrice(items);
                System.out.println("--- Items (Cheapest First) ---");
                for (MarketItem item : items) {
                    System.out.println(item.title + " - ₹" + item.price);
                }
            } else if (choice.equals("3")) {
                System.out.print("Enter item title to search: ");
                String title = sc.nextLine();
                MarketItem[] items = market.getAllValidItems();
                Algorithms.linearSearchTitle(items, title);
            } else if (choice.equals("4")) {
                System.out.print("Item Title: ");
                String t = sc.nextLine();
                System.out.print("Price: ");
                double p = Double.parseDouble(sc.nextLine());
                market.insert(new MarketItem(t, p));
                notifs.push("New item '" + t + "' listed on marketplace!");
                System.out.println("Item listed successfully!");
            } else if (choice.equals("5")) {
                notifs.popAndDisplay();
            } else if (choice.equals("6")) {
                System.out.println("Exiting CampusHub. See you on campus!");
                break;
            } else {
                System.out.println("Invalid option. Please try again.");
            }
        }
        sc.close();
    }
}