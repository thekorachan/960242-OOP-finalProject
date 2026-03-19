package com;

import java.util.Scanner;

public abstract class BaseMenu implements Menu {
    protected final LibraryManager manager;
    protected final Scanner scanner;

    protected BaseMenu(LibraryManager manager, Scanner scanner) {
        this.manager = manager;
        this.scanner = scanner;
    }

    protected int getIntInput(String prompt) {
        while (true) {
            System.out.print(prompt);
            try {
                return Integer.parseInt(scanner.nextLine().trim());
            } catch (NumberFormatException e) {
                System.out.println("\n❌ Error: Please enter a valid integer number!❌");
            }
        }
    }

    protected double getDoubleInput(String prompt) {
        while (true) {
            System.out.print(prompt);
            try {
                return Double.parseDouble(scanner.nextLine().trim());
            } catch (NumberFormatException e) {
                System.out.println("\n❌ Error: Please enter a valid decimal number!❌");
            }
        }
    }
}
