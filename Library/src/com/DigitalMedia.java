package com;

public class DigitalMedia extends Media {
    private String fileFormat;

    public DigitalMedia(String id, String title, String fileFormat) {
        super(id, title);
        this.fileFormat = fileFormat;
    }

    @Override
    public void displayInfo(boolean showFull) {
        super.displayInfo(showFull);
        if (showFull) System.out.println("   -> ประเภทไฟล์: " + fileFormat);
    }

    @Override
    public double calculateFine(int lateDays) {
        return 0.0; // ดิจิทัลไม่มีค่าปรับ
    }

    @Override
    public String toCSV() {
        return "Digital," + id + "," + title + "," + isAvailable + "," + borrowCount + "," + borrowerName + "," + fileFormat;
    }
}