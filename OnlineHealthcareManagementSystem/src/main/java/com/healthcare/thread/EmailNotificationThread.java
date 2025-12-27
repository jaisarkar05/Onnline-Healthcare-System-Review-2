package com.healthcare.thread;

public class EmailNotificationThread extends Thread {

    private String email;
    private String message;

    // Constructor
    public EmailNotificationThread(String email, String message) {
        this.email = email;
        this.message = message;
    }

    // Thread logic
    @Override
    public void run() {
        try {
            // Simulate delay (email sending time)
            Thread.sleep(3000);

            System.out.println("📧 Sending email to: " + email);
            System.out.println("📨 Message: " + message);
            System.out.println("✅ Email sent successfully");

        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
