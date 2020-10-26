package com.hugopaiva.app;

import java.util.Set;
import redis.clients.jedis.Jedis;

public class SimplePostSet {
    private Jedis jedis;
    public static String USERS = "users"; // Key set for users' name

    /**
     * Setup connection with redis db and flush database
     */
    public SimplePostSet() {
        this.jedis = new Jedis("localhost");
        this.jedis.flushDB();
    }

    /**
     * @param username Add user name to a Set
     */
    public void saveUser(String username) {
        jedis.sadd(USERS, username);
    }

    /**
     * @return Set<String> Get all the users names saved on the set
     */
    public Set<String> getUser() {
        return jedis.smembers(USERS);
    }

    /**
     * @return Set<String> Get all Redis keys
     */
    public Set<String> getAllKeys() {
        return jedis.keys("*");
    }

    /**
     * @param args Creation and setup of simple users on Redis db to see the power
     *             of Sets
     */
    public static void main(String[] args) {

        SimplePostSet board = new SimplePostSet();
        // set some users
        String[] users = { "Ana", "Pedro", "Maria", "Luis" };
        for (String user : users)
            board.saveUser(user);
        board.getAllKeys().stream().forEach(System.out::println);
        board.getUser().stream().forEach(System.out::println);
    }
}