/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */

package com.mycompany.chatappv2;

/**
 *
 * @author lab_services_student
 */

import javax.swing.JOptionPane;

public class ChatAppV2 {

    // User data
    private static final int MAX_USERS = 1;
    private static final String[] usernames = new String[MAX_USERS];
    private static final String[] passwords = new String[MAX_USERS];
    private static final String[] contactNumbers = new String[MAX_USERS];

    public static void main(String[] args) {
        // User Registration
        for (int i = 0; i < MAX_USERS; i++) {
            String username = JOptionPane.showInputDialog("Register username:");

            String password;
            while (true) {
                password = JOptionPane.showInputDialog("Register password (Min 8 chars, 1 uppercase, 1 digit, 1 special):");
                if (isValidPassword(password)) break;
                JOptionPane.showMessageDialog(null, "Invalid password. Try again.");
            }

            String contact;
            while (true) {
                contact = JOptionPane.showInputDialog("Enter your contact number (e.g. +27XXXXXXXXX):");
                if (contact != null && contact.matches("^\\+27\\d{9}$")) break;
                JOptionPane.showMessageDialog(null, "Invalid SA number. Try again.");
            }

            usernames[i] = username;
            passwords[i] = password;
            contactNumbers[i] = contact;
        }

        // Login
        String loginUser = JOptionPane.showInputDialog("Login - Enter username:");
        String loginPass = JOptionPane.showInputDialog("Login - Enter password:");

        boolean loggedIn = false;
        String currentUser = null;
        for (int i = 0; i < usernames.length; i++) {
            if (usernames[i].equals(loginUser) && passwords[i].equals(loginPass)) {
                loggedIn = true;
                currentUser = usernames[i];
                break;
            }
        }

        if (!loggedIn) {
            JOptionPane.showMessageDialog(null, "Login failed!");
            return;
        }

        JOptionPane.showMessageDialog(null, "Welcome to QuickChat, " + currentUser + "!");

        // Menu loop
        while (true) {
            String menu = JOptionPane.showInputDialog("""
                Select an option:
                1: Send a Message
                2: Show Sender and Recipient
                3: Show Longest Message
                4: Search by Message ID
                5: Search by Recipient
                6: Delete Message by Hash
                7: Show Full Message Report
                8: Quit""");

            switch (menu) {
                case "1" -> sendMessage(currentUser);
                case "2" -> JOptionPane.showMessageDialog(null, Message.displaySendersAndRecipients());
                case "3" -> JOptionPane.showMessageDialog(null, Message.getLongestMessage());
                case "4" -> {
                    String id = JOptionPane.showInputDialog("Enter Message ID:");
                    JOptionPane.showMessageDialog(null, Message.searchByID(id));
                }
                case "5" -> {
                    String rec = JOptionPane.showInputDialog("Enter recipient number:");
                    JOptionPane.showMessageDialog(null, Message.searchByRecipient(rec));
                }
                case "6" -> {
                    String hash = JOptionPane.showInputDialog("Enter message hash:");
                    JOptionPane.showMessageDialog(null, Message.deleteByHash(hash));
                }
                case "7" -> JOptionPane.showMessageDialog(null, Message.getFullReport());
                case "8" -> {
                    JOptionPane.showMessageDialog(null, "Goodbye!");
                    return;
                }
                default -> JOptionPane.showMessageDialog(null, "Invalid option. Try again.");
            }
        }
    }

    // Password validation
    private static boolean isValidPassword(String pass) {
        return pass.matches("^(?=.*[A-Z])(?=.*\\d)(?=.*[!@#$%^&*()_+=<>?]).{8,}$");
    }

    // Send message logic
    private static void sendMessage(String sender) {
        String recipient;
        while (true) {
            recipient = JOptionPane.showInputDialog("Enter recipient number (e.g. +27XXXXXXXXX):");
            if (recipient != null && recipient.matches("^\\+27\\d{9}$")) break;
            JOptionPane.showMessageDialog(null, "Invalid recipient number.");
        }

        String msg = JOptionPane.showInputDialog("Enter your message (max 250 chars):");
        if (msg.length() > 250) {
            JOptionPane.showMessageDialog(null, "Please enter a message of less than 250 characters.");
            return;
        }

        Message message = new Message(sender, recipient, msg);
        JOptionPane.showMessageDialog(null, message.SentMessage());
        JOptionPane.showMessageDialog(null, message.printMessages());
        JOptionPane.showMessageDialog(null, "Total messages sent: " + Message.returnTotalMessages());
    }
}
