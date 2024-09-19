package org.example;

import java.security.SecureRandom;
//import java.util.Scanner;

public class PasswordGenerator {
    public String generateNewPassword(int passwordLength) {
        String symbols = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";
        SecureRandom random = new SecureRandom();

//        System.out.print("Enter password length: ");
//        Scanner scanner = new Scanner(System.in);
//        int passwordLength = scanner.nextInt();

        StringBuilder password = new StringBuilder();
        for (int i = 0; i < passwordLength; i++) {
            int randomIndex = random.nextInt(symbols.length());
            char randomSymbol = symbols.charAt(randomIndex);
            password.append(randomSymbol);
        }

//        System.out.print(password);
        return password.toString();
    }
}
