package com;

public class Book extends Media {
    private String author;

    public Book(String id, String title, String author) {
        super(id, title);
        this.author = author;
    }

    @Override
    public void displayInfo(boolean showFull) {
        super.displayInfo(showFull);
        if (showFull) System.out.println("   -> ผู้แต่ง: " + author);
    }

    @Override
    public double calculateFine(int lateDays) {
        return lateDays * 10.0; // หนังสือปรับวันละ 10 บาท
    }

    @Override
    public String toCSV() {
        // เพิ่ม borrowerName เข้าไปในไฟล์ CSV ด้วย
        return "Book," + id + "," + title + "," + isAvailable + "," + borrowCount + "," + borrowerName + "," + author;
    }
}