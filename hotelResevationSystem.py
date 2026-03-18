import time

class FoodDelivery:
    def __init__(self):
        self.menu = {
            'veg': {'Salad': 100, 'Paneer Butter Masala': 250, 'Tacos': 150},
            'non_veg': {'Chicken Curry': 300, 'Mutton Biryani': 400, 'Haleem': 350}
        }
        self.order = []
        self.total_cost = 0
        self.discount = 0
        self.first_order_discount = 0.20
        self.extra_discount = 0.05
        self.order_placed_time = None 
        self.payment_made = False 

    def display_menu(self):
        print("\nMenu:")
        print("Vegetarian Items:")
        for item, price in self.menu['veg'].items():
            print(f"{item}: ₹{price}")
        print("\nNon-Vegetarian Items:")
        for item, price in self.menu['non_veg'].items():
            print(f"{item}: ₹{price}")

    def take_order(self):
        print("\nWhat would you like to have? Veg or Non-Veg:")
        choice = input("Enter 'veg' for Vegetarian or 'non_veg' for Non-Vegetarian: ").lower()
        if choice in self.menu:
            print(f"\nYou selected {choice}. Choose items:")
            for item, price in self.menu[choice].items():
                print(f"{item}: ₹{price}")
            while True:
                selected_item = input("Enter the item you want to order (or type 'done' to finish): ")
                if selected_item.lower() == 'done':
                    break
                if selected_item in self.menu[choice]:
                    self.order.append(selected_item)
                    self.total_cost += self.menu[choice][selected_item]
                    print(f"{selected_item} added to your order. Current total: ₹{self.total_cost}")
                else:
                    print(f"Sorry, we don't have {selected_item}.")

    def apply_coupon(self, is_first_order=False):
        if is_first_order:
            self.discount += self.first_order_discount * self.total_cost
            print(f"\nFirst-order discount applied: ₹{self.discount:.2f}")

        if self.total_cost > 500:
            extra_discount = self.extra_discount * self.total_cost
            self.discount += extra_discount
            print(f"Extra discount for orders above ₹500 applied: ₹{extra_discount:.2f}")
        
        self.total_cost -= self.discount
        print(f"Final total after discounts: ₹{self.total_cost:.2f}")

    def make_payment(self):
        payment_method = input("\nChoose payment method (card/cash): ").lower()
        if payment_method == "card":
            print(f"\nProcessing payment of ₹{self.total_cost:.2f}...")
            time.sleep(1)
            print("Payment successful!")
            self.payment_made = True
        else:
            print("You chose cash on delivery.")
            self.payment_made = False

    def track_order(self):
        print("\nTracking your order...")
        print("Fetching delivery time based on your location...")
        print("Your food will be delivered in approximately 30 minutes.")

    def cancel_order(self):
        if self.order_placed_time:
            current_time = time.time()
            elapsed_time = current_time - self.order_placed_time
            cancel_window = 5 * 60  # 5 minutes
            if elapsed_time < cancel_window:
                print("\nCancelling your order...")
                if self.payment_made:
                    print("Processing refund...")
                    time.sleep(1)
                    print(f"₹{self.total_cost:.2f} refunded to your account.")
                else:
                    print("Order cancelled. No refund needed as payment was Cash on Delivery.")
                self.order = []
                self.total_cost = 0
                self.discount = 0
            else:
                print("Sorry, the cancellation window has passed. Your order cannot be cancelled.")
        else:
            print("No active order to cancel.")

    def feedback(self):
        print("\nThank you for ordering with us!")
        rating = input("Please rate your experience out of 5: ")
        feedback = input("Leave any additional feedback (optional): ")
        print(f"Thank you for your rating of {rating} and feedback: '{feedback}'")
    
    def start(self):
        print("Welcome to FoodieHub!")
        print("We are open for online delivery.")

        first_order = input("\nIs this your first time ordering with us? (yes/no): ").lower()
        is_first_order = first_order == 'yes'

        if is_first_order:
            print("Please use the coupon 'FIRST20' to get 20% off on your first order.")
        
        self.display_menu()
        self.take_order()
        self.apply_coupon(is_first_order)
        
        self.make_payment()

        # Mark the time when the order is placed
        self.order_placed_time = time.time()

        cancel_choice = input("\nDo you want to cancel your order? (yes/no): ").lower()
        if cancel_choice == 'yes':
            self.cancel_order()
            if not self.order:
                return  # Exit if the order is canceled

        self.track_order()

        delivered = input("\nHas your food been delivered? (yes/no): ").lower()
        if delivered == 'yes':
            self.feedback()
        print("\nThank you for choosing FoodieHub!")


delivery_system = FoodDelivery()
delivery_system.start()