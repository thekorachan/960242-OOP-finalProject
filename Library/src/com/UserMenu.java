package com;

import java.util.Scanner;

public class UserMenu extends BaseMenu {
    private final Member user;

    public UserMenu(LibraryManager manager, Scanner scanner, Member user) {
        super(manager, scanner);
        this.user = user;
    }

    @Override
    public void run() {
        boolean running = true;
        while (running) {
            System.out.println("\n👤 Welcome, " + user.getName() + " | Wallet: ฿" + user.getBalance());
            System.out.println("1. View all items");
            System.out.println("2. Borrow a book");
            System.out.println("3. Return a book(s)");
            System.out.println("4. Top up Wallet");
            System.out.println("5. My Profile & Borrowed Books");
            System.out.println("6. Upgrade / Renew VIP");
            System.out.println("7. Logout to Main Menu");

            int choice = getIntInput("👉 Select menu (1-7): ");

            switch (choice) {
                case 1: manager.showAllItems(); break;
                case 2: borrowBook(); break;
                case 3: returnBooks(); break;
                case 4:
                    double topup = getDoubleInput("Amount to top up (THB): ");
                    if (topup > 0) user.addBalance(topup);
                    break;
                case 5:
                    user.displayMember();
                    System.out.println("📚 Your Borrowed Books:");
                    user.showBorrowedBooks();
                    break;
                case 6: upgradeVip(); break;
                case 7: running = false; break;
                default: System.out.println("⚠️ Please select between 1-7 only.");
            }
        }
    }

    private void borrowBook() {
        System.out.print("Enter Book ID to borrow: ");
        LibraryItem item = manager.findItem(scanner.nextLine().trim());
        if (item == null) {
            System.out.println("❌ Book ID not found.");
            return;
        }
        if (item.borrowItem(user)) {
            System.out.println("✅ Borrowed successfully! Charged ฿" + item.getPrice());
            System.out.println("📅 Due date: " + item.getDueDate());
        }
    }

    private void returnBooks() {
        if (user.getBorrowedItems().isEmpty()) {
            System.out.println("✅ You have no books to return.");
            return;
        }
        System.out.println("\n📚 Your Borrowed Books:");
        user.showBorrowedBooks();

        System.out.print("\nEnter Book ID(s) to return (separated by space, e.g., B01 E01): ");
        String[] bookIds = scanner.nextLine().trim().split("[,\\s]+");

        for (String id : bookIds) {
            LibraryItem item = manager.findItem(id);
            System.out.println("\n🔄 Processing: " + id);

            if (item != null && !item.isAvailable() && item.getBorrowedBy() == user) {
                int lateDays = getIntInput("How many days late? (0 if on time): ");
                double fine = item.calculateFine(lateDays);
                if (fine > 0) user.payFine(fine);
                item.returnItem();
                System.out.println("✅ Book returned successfully!");
            } else {
                System.out.println("❌ You didn't borrow this book or ID is incorrect.");
            }
        }
    }

    private void upgradeVip() {
        System.out.println("\n--- 🌟 Upgrade / Renew VIP ---");
        System.out.println("Current status: " + (user.isPremium()
                ? "👑 VIP (Exp: " + user.getVipExpiryDate() + ")" : "👤 Regular"));
        System.out.println("1. 1 Month Plan (150 THB)");
        System.out.println("2. 1 Year Plan (1,500 THB)");
        System.out.println("3. Cancel");

        int renewChoice = getIntInput("👉 Choose plan (1-3): ");
        double cost = (renewChoice == 1) ? 150 : (renewChoice == 2 ? 1500 : 0);

        if (cost > 0) {
            if (user.deductBalance(cost)) {
                user.applyVip(renewChoice == 1 ? 1 : 12);
            } else {
                System.out.println("❌ Insufficient Wallet balance! Please top up first.");
            }
        }
    }
}
