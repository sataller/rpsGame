package com.company;

import org.apache.commons.codec.binary.Hex;

import java.math.BigInteger;
import java.security.SecureRandom;
import java.util.Random;
import java.util.Scanner;

import static org.apache.commons.codec.digest.HmacUtils.hmacSha512;


public class RockPaperScissorsGame {

    public static int generateBotRandom(Random random) {
        int botNumber;
        botNumber = random.nextInt(3) + 1;
        return botNumber;
    }

    public static int getUserChoice(Scanner scanner) {
        System.out.print("Enter you move:");
        int userChoice;
        userChoice = scanner.nextInt();
        return userChoice;
    }

    public static String choseThereParams(int number, int gameNumber) {
        String param = "";
        if (number == 1) {
            param = "Rock";
        } else if (number == 2) {
            param = "Paper";
        } else if (number == 3) {
            param = "Scissors";
        } else if (number == 4 && gameNumber == 5) {
            param = "Lizard";
        } else if (number == 5 && gameNumber == 5) {
            param = "Spock";
        } else if (number == 0) {
            param = "Exit";
        } else {
            param = "You move mot available";
        }
        return param;
    }

    public static void showMenu(int gameNumber) {
        System.out.println("Available moves:");
        if (gameNumber == 5) {
            System.out.println("1 - Rock\n" +
                    "2 - Paper\n" +
                    "3 - Scissors\n" +
                    "4 - Lizard\n" +
                    "5 - Spock\n" +
                    "0 - exit");
        } else {
            System.out.println("1 - rock\n" +
                    "2 - paper\n" +
                    "3 - scissors\n" +
                    "0 - exit");
        }
    }

    public static void choseAWinner(int botNumber, int userChoice, int gameNumber) {
        if (botNumber == userChoice) {
            System.out.println("Draw!");
        } else if (gameNumber == 3 && (botNumber == 1 && userChoice == 2 || botNumber == 2 && userChoice == 3 ||
                botNumber == 3 && userChoice == 1)) {
            System.out.println("You win!");
        } else if (gameNumber == 5 && (botNumber == 1 && (userChoice == 2 || userChoice == 3) ||
                botNumber == 2 && (userChoice == 3 || userChoice == 4) ||
                botNumber == 3 && (userChoice == 4 || userChoice == 5) ||
                botNumber == 4 && (userChoice == 5 || userChoice == 1) ||
                botNumber == 5 && (userChoice == 1 || userChoice == 2))) {
            System.out.println("You win!");
        } else {
            System.out.println("You lose!");
        }
    }

    public static String generateRandomHexToken() {
        SecureRandom secureRandom = new SecureRandom();
        byte[] token = new byte[32];
        secureRandom.nextBytes(token);
        return new BigInteger(1, token).toString(16);
    }

    public static String hmacSha512Hex(final byte[] key, final byte[] valueToDigest) {
        return Hex.encodeHexString(hmacSha512(key, valueToDigest));
    }

    public static void main(String[] args) {

        int gameNumber = 0;

        if (args.length == 3 && args[0].toLowerCase().equals("rock") &&
                args[1].toLowerCase().equals("paper") &&
                args[2].toLowerCase().equals("scissors")) {
            gameNumber = 3;
        } else if (args.length == 5 && args[0].toLowerCase().equals("rock") &&
                args[1].toLowerCase().equals("paper") &&
                args[2].toLowerCase().equals("scissors") &&
                args[3].toLowerCase().equals("lizard") &&
                args[4].toLowerCase().equals("spock")) {
            gameNumber = 5;
        }

        if (gameNumber == 0) {
            System.out.println("Incorrect arguments! ");
            System.out.println("Possible: rock, paper, scissors or rock, paper, scissors, lizard, Spock");
            return;
        }

        Random random = new Random();
        Scanner scanner = new Scanner(System.in);

        int botNumber = generateBotRandom(random);
        String botChoice = choseThereParams(botNumber, gameNumber);

        String key = generateRandomHexToken();
        String move = botChoice;
        System.out.println("HMAC:" + hmacSha512Hex(key.getBytes(), move.getBytes()));

        showMenu(gameNumber);
        int userNumber = getUserChoice(scanner);
        String userChoice = choseThereParams(userNumber, gameNumber);


        while (userChoice.length() > 8) {
            System.out.println(userChoice);
            showMenu(gameNumber);
            userNumber = getUserChoice(scanner);
        }
        if (userNumber == 0) {
            System.out.println("Exit");
            return;
        } else {
            System.out.println("You move: " + userChoice);
            System.out.println("Computer move: " + botChoice);

            choseAWinner(botNumber, userNumber, gameNumber);
            System.out.println("HMAC key:" + key);
        }
    }
}

