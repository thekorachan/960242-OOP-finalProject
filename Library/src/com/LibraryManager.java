package com;

import java.io.*;
import java.util.ArrayList;

public class LibraryManager {
    private ArrayList<Media> mediaList = new ArrayList<>();
    private final String FILE_NAME = "library_data.csv";

    public LibraryManager() {
        loadFromCSV();
    }

    public void displayAllMedia() {
        System.out.println("\n--- รายการสื่อทั้งหมด ---");
        if (mediaList.isEmpty()) {
            System.out.println("ยังไม่มีข้อมูลสื่อในระบบ");
            return;
        }
        for (Media m : mediaList) {
            m.displayInfo(true);
            System.out.println("-------------------------");
        }
    }

    // เมธอดใหม่: สำหรับเพิ่มข้อมูล (ตอบโจทย์จัดการข้อมูล)
    public void addMedia(Media newMedia) {
        mediaList.add(newMedia);
        System.out.println("✅ เพิ่มสื่อรหัส '" + newMedia.getId() + "' เข้าระบบเรียบร้อยแล้ว!");
    }

    public Media findMedia(String id) {
        for (Media m : mediaList) {
            if (m.getId().equalsIgnoreCase(id)) return m;
        }
        return null;
    }

    public void showReports() {
        System.out.println("\n📊 --- รายงานระบบห้องสมุด ---");

        System.out.println("[รายงานที่ 1: รายการที่กำลังถูกยืมอยู่]");
        boolean hasBorrowed = false;
        for (Media m : mediaList) {
            if (!m.isAvailable()) {
                System.out.println(" - " + m.getTitle() + " (รหัส: " + m.getId() + ") | ผู้ยืม: " + m.getBorrowerName());
                hasBorrowed = true;
            }
        }
        if (!hasBorrowed) System.out.println(" - ไม่มีรายการถูกยืม");

        System.out.println("\n[รายงานที่ 2: สื่อที่ฮิตที่สุด (ถูกยืมมากที่สุด)]");
        if (mediaList.isEmpty()) {
            System.out.println(" - ไม่มีข้อมูลในระบบ");
            return;
        }
        Media top = mediaList.get(0);
        for (Media m : mediaList) {
            if (m.getBorrowCount() > top.getBorrowCount()) top = m;
        }
        if (top.getBorrowCount() > 0) {
            System.out.println(" 🏆 " + top.getTitle() + " (ยอดถูกยืม: " + top.getBorrowCount() + " ครั้ง)");
        } else {
            System.out.println(" - ยังไม่มีการยืมเกิดขึ้นในระบบ");
        }
    }

    public void saveToCSV() {
        try (PrintWriter writer = new PrintWriter(new FileWriter(FILE_NAME))) {
            for (Media m : mediaList) {
                writer.println(m.toCSV());
            }
        } catch (IOException e) {
            System.out.println("❌ เกิดข้อผิดพลาดในการบันทึกไฟล์: " + e.getMessage());
        }
    }

    private void loadFromCSV() {
        File file = new File(FILE_NAME);
        if (!file.exists()) {
            // Mock Data เริ่มต้น
            mediaList.add(new Book("B01", "Java Programming", "John Doe"));
            mediaList.add(new Book("B02", "Clean Code", "Robert C. Martin"));
            mediaList.add(new DigitalMedia("D01", "OOP for Beginners", "PDF"));
            return;
        }

        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts.length < 7) continue; // เช็ค 7 คอลัมน์

                Media m;
                if (parts[0].equals("Book")) {
                    m = new Book(parts[1], parts[2], parts[6]);
                } else {
                    m = new DigitalMedia(parts[1], parts[2], parts[6]);
                }

                if (parts[3].equals("false")) {
                    m.isAvailable = false;
                }
                m.borrowCount = Integer.parseInt(parts[4]);
                m.borrowerName = parts[5];

                mediaList.add(m);
            }
        } catch (Exception e) {
            System.out.println("❌ ข้อผิดพลาดในการอ่านไฟล์: " + e.getMessage());
        }
    }
}