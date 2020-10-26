package com.hugopaiva.app;

import redis.clients.jedis.Jedis;
import java.io.File;
import java.util.Scanner;
import java.util.Set;

public class AutoComplete {
    private Jedis jedis;
    private String key;

    public AutoComplete() {
        this.jedis = new Jedis("localhost");
        this.jedis.flushDB();
        this.key = "users";
        saveUsersFromFile("female-names.txt");
    }

    public void saveUsersFromFile(String filename) {
        try{
            File file = new File(filename);
            Scanner sc = new Scanner(file);
            String line;

            while (sc.hasNextLine()){
                line = sc.nextLine().trim();
                if(!line.equals(""))
                    this.jedis.zadd(this.key, 0, line);
            }

        } catch(Exception e){
            System.err.println("Error reading from file to db: "+ e);
            System.exit(0);
            
        }

    }

    public Set<String> getUsersStartedWith(String search){
        return this.jedis.zrangeByLex(this.key, "["+search, "["+search+Character.MAX_VALUE);
    }

    public static void main(String[] args) {
        AutoComplete auto = new AutoComplete();
        Scanner inputScanner = new Scanner(System.in); // Create a Scanner object
        String input = "init";

        while (!input.equals("")) {

            auto.getUsersStartedWith(input).stream().forEach(System.out::println);

            System.out.print("Search for ('Enter' for quit): ");
            input = inputScanner.nextLine();

        }

        System.exit(0);
    }
}
