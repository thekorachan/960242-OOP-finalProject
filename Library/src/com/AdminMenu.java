package com;

import java.util.Scanner;

public class AdminMenu extends BaseMenu {

    public AdminMenu(LibraryManager manager, Scanner scanner) {
        super(manager, scanner);
    }

    @Override
    public void run() {
        boolean running = true;
        while (running) {
            System.out.println("\n👑 [Admin Menu] Manage Library System");
            System.out.println("1. View all members");
            System.out.println("2. Edit Member VIP Status");
            System.out.println("3. Delete member");
            System.out.println("4. Add new item (Physical/E-Book)");
            System.out.println("5. Delete item");
            System.out.println("6. Report: Unreturned Items");
            System.out.println("7. Report: Top Borrowed Items");
            System.out.println("8. Logout to Login Page");

            int choice = getIntInput("👉 Select menu (1-8): ");

            switch (choice) {
                case 1: manager.showAllMembers(); break;
                case 2: editVipStatus(); break;
                case 3:
                    System.out.print("Enter Member ID to delete: ");
                    if (manager.deleteMember(scanner.nextLine().trim())) System.out.println("✅ Member deleted.");
                    break;
                case 4: addItem(); break;
                case 5:
                    System.out.print("Enter Item ID to delete: ");
                    if (manager.deleteItem(scanner.nextLine().trim())) System.out.println("✅ Item deleted.");
                    break;
                case 6: manager.showReportUnreturned(); break;
                case 7: manager.showReportMostBorrowed(); break;
                case 8: running = false; break;
                default: System.out.println("⚠️ Please select between 1-8 only.");
            }
        }
    }

    private void editVipStatus() {
        System.out.print("Enter Member ID to edit VIP status: ");
        Member editMember = manager.findMember(scanner.nextLine().trim());

        if (editMember == null) {
            System.out.println("❌ Member ID not found.");
            return;
        }

        System.out.println("Current Status: " + (editMember.isPremium()
                ? "👑 VIP (Exp: " + editMember.getVipExpiryDate() + ")" : "👤 Regular"));
        System.out.println("1. Grant / Extend VIP (+1 Month)");
        System.out.println("2. Grant / Extend VIP (+1 Year)");
        System.out.println("3. Revoke VIP (Change to Regular)");
        System.out.println("4. Cancel");

        int action = getIntInput("👉 Choose action (1-4): ");

        if (action == 1) {
            editMember.applyVip(1);
            System.out.println("✅ VIP status updated!");
        } else if (action == 2) {
            editMember.applyVip(12);
            System.out.println("✅ VIP status updated!");
        } else if (action == 3) {
            editMember.setVipExpiryDate(null);
            System.out.println("✅ VIP status revoked. Member is now Regular.");
        } else {
            System.out.println("❌ Action canceled.");
        }
    }

    private void addItem() {
        int type = getIntInput("Item Type (1 = Physical Book, 2 = E-Book): ");
        if (type != 1 && type != 2) { System.out.println("❌ Invalid Item Type!"); return; }

        System.out.print("Item ID: "); String iId = scanner.nextLine().trim();
        System.out.print("Title: "); String title = scanner.nextLine().trim();
        System.out.print("Author: "); String author = scanner.nextLine().trim();
        double price = getDoubleInput("Borrow fee (THB): ");

        if (type == 1) {
            System.out.print("Location (e.g. A-12): ");
            manager.addItem(new PhysicalBook(iId, title, author, price, scanner.nextLine().trim()));
        } else {
            System.out.print("Download URL: "); String url = scanner.nextLine().trim();
            double size = getDoubleInput("File Size (MB): ");
            manager.addItem(new EBook(iId, title, author, price, url, size));
        }
        System.out.println("✅ Item added successfully!");
    }
}
