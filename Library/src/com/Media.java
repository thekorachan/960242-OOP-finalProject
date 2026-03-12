package com;

public abstract class Media implements Borrowable {
    protected String id;
    protected String title;
    protected boolean isAvailable;
    protected int borrowCount;
    protected String borrowerName; // เพิ่มตัวแปรเก็บชื่อคนยืม

    public Media(String id, String title) {
        this.id = id;
        this.title = title;
        this.isAvailable = true;
        this.borrowCount = 0;
        this.borrowerName = "-"; // ค่าเริ่มต้นคือไม่มีคนยืม
    }

    public String getId() { return id; }
    public String getTitle() { return title; }
    public boolean isAvailable() { return isAvailable; }
    public int getBorrowCount() { return borrowCount; }
    public String getBorrowerName() { return borrowerName; }

    // Overloading 1: แสดงข้อมูลแบบย่อ
    public void displayInfo() {
        System.out.printf("[%s] %s - สถานะ: %s (ยอดถูกยืม: %d ครั้ง)\n",
                id, title, isAvailable ? "ว่าง" : "ถูกยืมโดยคุณ " + borrowerName, borrowCount);
    }

    // Overloading 2: แสดงข้อมูลแบบมีรายละเอียด
    public void displayInfo(boolean showFull) {
        displayInfo();
        if (showFull) {
            System.out.println("   -> หมวดหมู่สื่อ: " + this.getClass().getSimpleName());
        }
    }

    @Override
    public boolean borrowItem(String memberName) {
        if (isAvailable) {
            isAvailable = false;
            borrowCount++;
            borrowerName = memberName; // บันทึกชื่อคนยืม
            return true;
        }
        return false;
    }

    @Override
    public void returnItem() {
        isAvailable = true;
        borrowerName = "-"; // ล้างชื่อคนยืมเมื่อคืนสำเร็จ
    }

    public abstract double calculateFine(int lateDays);
    public abstract String toCSV();
}