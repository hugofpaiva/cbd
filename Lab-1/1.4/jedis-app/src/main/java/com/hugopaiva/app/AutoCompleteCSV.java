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

    /**
     * Setup connection with redis db, flush database and take users from file to
     * redis db
     */
    public AutoCompleteCSV() {
        this.jedis = new Jedis("localhost");
        this.jedis.flushDB();
        this.userskey = "users";
        this.lexicaluserskey = "lexicalorderusers";
        saveUsersFromFile("nomes-registados-2020.csv");
    }

    /**
     * @param filename Read users from a file, having a name in each line with his
     *                 popularity as an integer, and saving them in a Sorted Set
     *                 (member-name, score-popularity) and also another Sorted Set
     *                 with just the member-name
     */
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

    /**
     * @param search
     * @return Stream<String> Provide an AutoComplete search using Redis
     *         implementation of zrangeByLex, made possible because all elements of
     *         one of the Sorted Sets have score of 0 in order to force
     *         lexicographical ordering. Search provide all name that start with the
     *         search argument, inclusive, using interval properties of this method.
     *         In this case the search would have to be ordered decreasing by the
     *         score on the main Sorted Set so, maintaining the order, it was
     *         checked if the users ordered decreasing by score where the ones of
     *         the search
     */
    public Stream<String> getUsersStartedWith(String search) {
        Set<String> autocomplete = this.jedis.zrangeByLex(this.lexicaluserskey, "[" + search,
                "[" + search + Character.MAX_VALUE);
        Set<String> userByScore = this.jedis.zrevrangeByScore(this.userskey, Integer.MAX_VALUE, Integer.MIN_VALUE);

        return userByScore.stream().filter(user -> autocomplete.contains(user));

    }

    /**
     * @param args
     */
    public static void main(String[] args) {
        AutoCompleteCSV auto = new AutoCompleteCSV();
        Scanner inputScanner = new Scanner(System.in);
        String input = "init";

        while (!input.equals("")) {

            auto.getUsersStartedWith(input).forEach(System.out::println);

            System.out.print("Search for ('Enter' for quit): ");
            input = inputScanner.nextLine();

        }

        System.exit(0);
    }
}
