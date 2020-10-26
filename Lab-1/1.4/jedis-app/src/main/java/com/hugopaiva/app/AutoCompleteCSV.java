package com.hugopaiva.app;

import redis.clients.jedis.Jedis;
import java.io.File;
import java.util.Scanner;
import java.util.stream.*;
import java.util.Set;

public class AutoCompleteCSV {
    private Jedis jedis;
    private String userskey;
    private String lexicaluserskey;

    public AutoCompleteCSV() {
        this.jedis = new Jedis("localhost");
        this.jedis.flushDB();
        this.userskey = "users";
        this.lexicaluserskey = "lexicalorderusers";
        saveUsersFromFile("nomes-registados-2020.csv");
    }

    public void saveUsersFromFile(String filename) {
        try {
            File file = new File(filename);
            Scanner sc = new Scanner(file);
            String rawLine;
            String[] line;

            while (sc.hasNextLine()) {
                rawLine = sc.nextLine().trim();
                if (!rawLine.equals("")) {
                    line = rawLine.split(";");
                    this.jedis.zadd(this.userskey, Integer.parseInt(line[2]), line[0]);
                    this.jedis.zadd(this.lexicaluserskey, 0, line[0]);
                }
            }

        } catch (Exception e) {
            System.err.println("Error reading from file to db: " + e);
            System.exit(0);

        }

    }

    public Stream<String> getUsersStartedWith(String search) {
        Set<String> autocomplete = this.jedis.zrangeByLex(this.lexicaluserskey, "[" + search, "[" + search + Character.MAX_VALUE);
        Set<String> userByScore = this.jedis.zrevrangeByScore(this.userskey, Integer.MAX_VALUE, Integer.MIN_VALUE);

        return userByScore.stream().filter(user -> autocomplete.contains(user));

    }

    public static void main(String[] args) {
        AutoCompleteCSV auto = new AutoCompleteCSV();
        Scanner inputScanner = new Scanner(System.in); // Create a Scanner object
        String input = "init";

        while (!input.equals("")) {

            auto.getUsersStartedWith(input).forEach(System.out::println);

            System.out.print("Search for ('Enter' for quit): ");
            input = inputScanner.nextLine();

        }

        System.exit(0);
    }
}
