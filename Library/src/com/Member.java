package com;

public class Member {
    private String id;
    private String name;
    private double balance;
    private int borrowedCount;
    private boolean isPremium; // true = VIP ยืมไม่อั้น, false = ธรรมดา ยืมได้ 3 เล่ม
    private final int BORROW_LIMIT = 3;

    public Member(String id, String name, double balance, int borrowedCount, boolean isPremium) {
        this.id = id;
        this.name = name;
        this.balance = balance;
        this.borrowedCount = borrowedCount;
        this.isPremium = isPremium;
    }

    public String getId() { return id; }
    public String getName() { return name; }
    public double getBalance() { return balance; }
    public int getBorrowedCount() { return borrowedCount; }
    public boolean isPremium() { return isPremium; }

    public void addBalance(double amount) {
        this.balance += amount;
        System.out.println("💰 Top-up successful! Balance: ฿" + this.balance);
    }

    public boolean deductBalance(double amount) {
        if (this.balance >= amount) {
            this.balance -= amount;
            return true;
        }
        return false;
    }

    // เมธอดสำหรับหักค่าปรับ (ยอมให้เงินติดลบได้ หรือหักตามจริง)
    public void payFine(double amount) {
        this.balance -= amount;
        System.out.println("💸 Late fine deducted: ฿" + amount + " | Balance: ฿" + this.balance);
    }

    // เช็คสิทธิ์การยืม: ถ้าเป็น Premium ให้ผ่านเลย ถ้าไม่เป็นต้องไม่เกิน Limit
    public boolean canBorrow() {
        if (isPremium) return true;
        return borrowedCount < BORROW_LIMIT;
    }

    public void recordBorrow() { borrowedCount++; }
    public void recordReturn() { if (borrowedCount > 0) borrowedCount--; }

    public void displayMember() {
        String memberType = isPremium ? "👑 [VIP Unlimited]" : "👤 [Regular (limit 3)]";
        String limitText = isPremium ? "Unlimited" : String.valueOf(BORROW_LIMIT);

        System.out.printf("%s %s (ID: %s) | Balance: ฿%.2f | Borrowed: %d/%s books\n",
                memberType, name, id, balance, borrowedCount, limitText);
    }

    public String toCSV() {
        return id + "," + name + "," + balance + "," + borrowedCount + "," + isPremium;
    }
}