package com.hugopaiva.app;

import java.util.Set;
import java.util.List;
import redis.clients.jedis.Jedis;

public class SimplePostList {
    private Jedis jedis;
    public static String USERS = "usersList"; // Key set for users' name

    public SimplePostList() {
        this.jedis = new Jedis("localhost");
        this.jedis.flushDB();
    }

    public void saveUser(String username) {
        jedis.lpush(USERS, username);
    }

    public List<String> getUser() {
        return jedis.lrange(USERS, 0, -1);
    }

    public Set<String> getAllKeys() {
        return jedis.keys("*");
    }

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