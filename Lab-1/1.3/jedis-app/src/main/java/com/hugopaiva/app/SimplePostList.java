package com.hugopaiva.app;

import java.util.Set;
import java.util.List;
import redis.clients.jedis.Jedis;

public class SimplePostList {
    private Jedis jedis;
    public static String USERS = "usersList"; // Key set for users' name

    /**
     * Setup connection with redis db and flush database
     */
    public SimplePostList() {
        this.jedis = new Jedis("localhost");
        this.jedis.flushDB();
    }

    /**
     * @param username Save user name in a list, adding to the front
     */
    public void saveUser(String username) {
        jedis.lpush(USERS, username);
    }

    /**
     * @return List<String> Getting the user names from list
     */
    public List<String> getUser() {
        return jedis.lrange(USERS, 0, -1);
    }

    /**
     * @return Set<String> Get all Redis keys
     */
    public Set<String> getAllKeys() {
        return jedis.keys("*");
    }

    /**
     * @param args Creation and setup of simple users on Redis db to see the power
     *             of Lists
     */
    public static void main(String[] args) {
        SimplePostList board = new SimplePostList();
        // set some users
        String[] users = { "Ana", "Pedro", "Maria", "Luis" };
        for (String user : users)
            board.saveUser(user);
        board.getAllKeys().stream().forEach(System.out::println);
        board.getUser().stream().forEach(System.out::println);
    }
}