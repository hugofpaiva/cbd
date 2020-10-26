package com.hugopaiva.app;

import redis.clients.jedis.Jedis;
import java.io.File;
import java.util.Scanner;
import java.util.Set;

public class AutoComplete {
    private Jedis jedis;
    private String key;

    /**
     * Setup connection with redis db, flush database and take users from file to
     * redis db
     */
    public AutoComplete() {
        this.jedis = new Jedis("localhost");
        this.jedis.flushDB();
        this.key = "users";
        saveUsersFromFile("female-names.txt");
    }

    /**
     * @param filename Read users from a file, having a name in each line, and
     *                 saving them in a Sorted Set
     */
    public void saveUsersFromFile(String filename) {
        try {
            File file = new File(filename);
            Scanner sc = new Scanner(file);
            String line;

            while (sc.hasNextLine()) {
                line = sc.nextLine().trim();
                if (!line.equals(""))
                    this.jedis.zadd(this.key, 0, line);
            }

        } catch (Exception e) {
            System.err.println("Error reading from file to db: " + e);
            System.exit(0);

        }

    }

    /**
     * @param search
     * @return Set<String> Provide an AutoComplete search using Redis implementation
     *         of zrangeByLex, made possible because all elements have score of 0 in
     *         order to force lexicographical ordering. Search provide all name that
     *         start with the search argument, inclusive, using interval properties
     *         of this method.
     */
    public Set<String> getUsersStartedWith(String search) {
        return this.jedis.zrangeByLex(this.key, "[" + search, "[" + search + Character.MAX_VALUE);
    }

    /**
     * @param args
     */
    public static void main(String[] args) {
        AutoComplete auto = new AutoComplete();
        Scanner inputScanner = new Scanner(System.in);
        String input = "init";

        while (!input.equals("")) {

            auto.getUsersStartedWith(input).stream().forEach(System.out::println);

            System.out.print("Search for ('Enter' for quit): ");
            input = inputScanner.nextLine();

        }

        System.exit(0);
    }
}
