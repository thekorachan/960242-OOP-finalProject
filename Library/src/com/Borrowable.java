package com;

public interface Borrowable {
    // บังคับให้ตอนยืม ต้องส่งชื่อคนยืมเข้ามาด้วย
    boolean borrowItem(String borrowerName);
    void returnItem();
}