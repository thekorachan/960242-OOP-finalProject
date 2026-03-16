package com;

import java.time.LocalDate;

public abstract class LibraryItem implements Borrowable {
    protected String id;
    protected String title;
    protected String author;
    protected final double price;
    protected boolean isAvailable;
    protected Member borrowedBy;
    protected LocalDate dueDate;
    protected int borrowCount; // 📌 เพิ่มตัวแปรเก็บประวัติว่าถูกยืมไปกี่ครั้ง

    public LibraryItem(String id, String title, String author, double price) {
        this.id = id;
        this.title = title;
        this.author = author;
        this.price = price;
        this.isAvailable = true;
        this.borrowedBy = null;
        this.dueDate = null;
        this.borrowCount = 0; // เริ่มต้นที่ 0 ครั้ง
    }

    public String getId() { return id; }
    public double getPrice() { return price; }
    public boolean isAvailable() { return isAvailable; }
    public Member getBorrowedBy() { return borrowedBy; }
    public LocalDate getDueDate() { return dueDate; }
    public int getBorrowCount() { return borrowCount; }

    public void setDueDate(LocalDate date) { this.dueDate = date; }
    public void setTitle(String title) { this.title = title; }
    public void setAuthor(String author) { this.author = author; }
    public void setAvailable(boolean available) { this.isAvailable = available; }
    public void setBorrowedBy(Member m) { this.borrowedBy = m; }
    public void setBorrowCount(int count) { this.borrowCount = count; } // 📌 สำหรับโหลดไฟล์ CSV

    public void displayDetails() {
        System.out.printf("[%s] %s - Status: %s\n",
                id, title, isAvailable ? "Available" : "Borrowed by " + borrowedBy.getName() + " (due: " + dueDate + ")");
    }

    public abstract void displayDetails(boolean showFull);
    public abstract double calculateFine(int lateDays);

    @Override
    public boolean borrowItem(Member member) {
        if (isAvailable && member.canBorrow()) {
            if (member.deductBalance(this.price)) {
                this.isAvailable = false;
                this.borrowedBy = member;
                this.dueDate = LocalDate.now().plusDays(7);
                this.borrowCount++;
                member.recordBorrow();
                return true;
            } else {
                System.out.println("❌ Insufficient balance! Short by ฿" + (this.price - member.getBalance()));
                return false;
            }
        }
        if (!member.canBorrow()) {
            System.out.println("❌ Cannot borrow! You are a regular member and have reached the 3-book limit");
        } else {
            System.out.println("❌ This book is already checked out");
        }
        return false;
    }

    @Override
    public void returnItem() {
        if (!isAvailable && borrowedBy != null) {
            borrowedBy.recordReturn();
            this.isAvailable = true;
            this.borrowedBy = null;
            this.dueDate = null;
        }
    }

    public abstract String toCSV();
}