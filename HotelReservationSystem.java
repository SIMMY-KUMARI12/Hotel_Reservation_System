import java.util.*;

class FoodDelivery {
    Map<String, Map<String, Integer>> menu = new HashMap<>();
    List<String> order = new ArrayList<>();
    double totalCost = 0;
    double discount = 0;
    double firstOrderDiscount = 0.20;
    double extraDiscount = 0.05;
    long orderPlacedTime = 0;
    boolean paymentMade = false;

    FoodDelivery() {
        Map<String, Integer> veg = new HashMap<>();
        veg.put("Salad", 100);
        veg.put("Paneer Butter Masala", 250);
        veg.put("Tacos", 150);

        Map<String, Integer> nonVeg = new HashMap<>();
        nonVeg.put("Chicken Curry", 300);
        nonVeg.put("Mutton Biryani", 400);
        nonVeg.put("Haleem", 350);

        menu.put("veg", veg);
        menu.put("non_veg", nonVeg);
    }

    void displayMenu() {
        System.out.println("\nMenu:");
        System.out.println("Vegetarian Items:");
        for (String key : menu.get("veg").keySet()) {
            System.out.println(key + ": Rs." + menu.get("veg").get(key));
        }

        System.out.println("\nNon-Vegetarian Items:");
        for (String key : menu.get("non_veg").keySet()) {
            System.out.println(key + ": Rs." + menu.get("non_veg").get(key));
        }
    }

    void takeOrder(Scanner sc) {
        System.out.println("\nWhat would you like to have?");
        System.out.print("Enter your choice (veg / non_veg): ");

        String choice = sc.nextLine().toLowerCase().trim();

        if (choice.equals("veg")) {
            choice = "veg";
        } else if (choice.equals("non-veg") || choice.equals("nonveg") || choice.equals("non_veg")) {
            choice = "non_veg";
        } else {
            System.out.println("Invalid input. Please enter 'veg' or 'non_veg'.");
            return;
        }

        if (menu.containsKey(choice)) {
            System.out.println("\nYou selected " + choice + ". Choose items:");

            for (String key : menu.get(choice).keySet()) {
                System.out.println(key + ": Rs." + menu.get(choice).get(key));
            }

            while (true) {
                System.out.print("Enter item (or 'done'): ");
                String item = sc.nextLine();

                if (item.equalsIgnoreCase("done")) break;

                if (menu.get(choice).containsKey(item)) {
                    order.add(item);
                    totalCost += menu.get(choice).get(item);
                    System.out.println(item + " added. Total: Rs." + totalCost);
                } else {
                    System.out.println("Item not available.");
                }
            }
        }
    }

    void applyCoupon(boolean isFirstOrder) {
        if (isFirstOrder) {
            discount += firstOrderDiscount * totalCost;
            System.out.println("First order discount: Rs." + discount);
        }

        if (totalCost > 500) {
            double extra = extraDiscount * totalCost;
            discount += extra;
            System.out.println("Extra discount: Rs." + extra);
        }

        totalCost -= discount;
        System.out.println("Final total: Rs." + totalCost);
    }

    void makePayment(Scanner sc) {
        System.out.print("Payment method (card/cash): ");
        String method = sc.nextLine();

        if (method.equalsIgnoreCase("card")) {
            System.out.println("Processing payment...");
            try { Thread.sleep(1000); } catch (Exception e) {}
            System.out.println("Payment successful!");
            paymentMade = true;
        } else {
            System.out.println("Cash on delivery selected.");
            paymentMade = false;
        }
    }

    void trackOrder() {
        System.out.println("Tracking order...");
        System.out.println("Delivery in ~30 minutes.");
    }

    void cancelOrder() {
        if (orderPlacedTime != 0) {
            long currentTime = System.currentTimeMillis();
            long elapsed = currentTime - orderPlacedTime;

            long cancelWindow = 5 * 60 * 1000;

            if (elapsed < cancelWindow) {
                System.out.println("Cancelling order...");

                if (paymentMade) {
                    System.out.println("Refunding Rs." + totalCost);
                } else {
                    System.out.println("No refund needed.");
                }

                order.clear();
                totalCost = 0;
                discount = 0;
            } else {
                System.out.println("Cancellation window passed.");
            }
        } else {
            System.out.println("No order to cancel.");
        }
    }

    void feedback(Scanner sc) {
        System.out.print("Rate (out of 5): ");
        String rating = sc.nextLine();

        System.out.print("Feedback: ");
        String fb = sc.nextLine();

        System.out.println("Thanks for rating " + rating);
    }

    void start() {
        Scanner sc = new Scanner(System.in);

        System.out.println("Welcome to FoodieHub!");

        System.out.print("First order? (yes/no): ");
        boolean isFirst = sc.nextLine().equalsIgnoreCase("yes");

        displayMenu();
        takeOrder(sc);
        applyCoupon(isFirst);
        makePayment(sc);

        orderPlacedTime = System.currentTimeMillis();

        System.out.print("Cancel order? (yes/no): ");
        if (sc.nextLine().equalsIgnoreCase("yes")) {
            cancelOrder();
            if (order.isEmpty()) return;
        }

        trackOrder();

        System.out.print("Delivered? (yes/no): ");
        if (sc.nextLine().equalsIgnoreCase("yes")) {
            feedback(sc);
        }

        System.out.println("Thank you!");
    }
}

public class FoodDeliverySystem {
    public static void main(String[] args) {
        FoodDelivery system = new FoodDelivery();
        system.start();
    }
}
