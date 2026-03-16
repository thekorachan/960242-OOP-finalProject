package com;

public class EBook extends LibraryItem {
    private String downloadUrl;
    private double fileSize;

    public EBook(String id, String title, String author, double price, String downloadUrl, double fileSize) {
        super(id, title, author, price);
        this.downloadUrl = downloadUrl;
        this.fileSize = fileSize;
    }

    public void setDownloadUrl(String url) { this.downloadUrl = url; }
    public void setFileSize(double size) { this.fileSize = size; }

    @Override
    public double calculateFine(int lateDays) {
        return 0.0;
    }

    @Override
    public void displayDetails(boolean showFull) {
        if (showFull) {
            String dueStr = isAvailable ? "-" : dueDate.toString();
            System.out.printf("📱 [E-Book] ID: %s | Title: %s | Borrow fee: ฿%.2f | Status: %s | Stream expires: %s | 📈 Total borrows: %d times\n",
                    id, title, price, isAvailable ? "Available" : "Checked Out", dueStr, borrowCount);
        } else {
            super.displayDetails();
        }
    }

    @Override
    public String toCSV() {
        String borrowerId = (borrowedBy != null) ? borrowedBy.getId() : "none";
        String dueStr = (dueDate != null) ? dueDate.toString() : "null";
        // 📌 Save borrowCount to file as well
        return "EBook," + id + "," + title + "," + author + "," + price + "," + isAvailable + "," + borrowerId + "," + downloadUrl + "," + fileSize + "," + dueStr + "," + borrowCount;
    }
}