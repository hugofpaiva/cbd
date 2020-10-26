package com.hugopaiva.app;

import java.util.Map;
import java.util.HashMap;
import java.util.Set;
import redis.clients.jedis.Jedis;

public class SimplePostHash {
    private Jedis jedis;
    public static String USERS = "usersHash"; // Key set for users' name

    /**
     * Setup connection with redis db and flush database
     */
    public SimplePostHash() {
        this.jedis = new Jedis("localhost");
        this.jedis.flushDB();
    }

    /**
     * @param username
     * @param specs    Save user in a Hash and his properties
     */
    public void saveUser(String username, Map<String, String> specs) {
        jedis.hmset(USERS + ":" + username, specs);
    }

    /**
     * @param user
     * @return Map<String, String> Get user properties from Hash
     */
    public Map<String, String> getUser(String user) {
        return jedis.hgetAll(user);
    }

    /**
     * @return Set<String> Get all redis keys
     */
    public Set<String> getAllKeys() {
        return jedis.keys("*");
    }

    /**
     * @param args Creation and setup of simple users and their properties on Redis
     *             db to see the power of Hash
     */
    public static void main(String[] args) {
        SimplePostHash board = new SimplePostHash();
        // Set some users
        String[] users = { "Ana", "Pedro", "Maria", "Luis" };
        Map<String, String> specs = new HashMap<String, String>();
        // Simple example to demonstrate the power of Hash
        specs.put("hair", "black");
        specs.put("skin", "yellow");
        specs.put("type", "simpson");
        for (String user : users)
            board.saveUser(user, specs);
        board.getAllKeys().stream().forEach(System.out::println);

        for (String user : users) {
            System.out.println("Properties of " + user);
            board.getUser(USERS + ":" + user).forEach((key, value) -> System.out.println(key + " - " + value));
        }
    }
}