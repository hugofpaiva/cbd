package com.hugopaiva.app;

import java.util.Map;
import java.util.HashMap;
import java.util.Set;
import redis.clients.jedis.Jedis;

public class SimplePostHash {
    private Jedis jedis;
    public static String USERS = "usersHash"; // Key set for users' name

    public SimplePostHash() {
        this.jedis = new Jedis("localhost");
        this.jedis.flushDB();
    }

    public void saveUser(String username, Map<String, String> specs) {
        jedis.hmset(USERS+":"+username, specs);
    }

    public Map<String, String> getUser(String user) {
        return jedis.hgetAll(user);
    }

    public Set<String> getAllKeys() {
        return jedis.keys("*");
    }

    public static void main(String[] args) {
        SimplePostHash board = new SimplePostHash();
        // set some users
        String[] users = { "Ana", "Pedro", "Maria", "Luis" };
        Map<String, String> specs = new HashMap<String, String>();
        specs.put("hair", "black");
        specs.put("skin", "yellow");
        specs.put("type", "simpson");
        for (String user : users)
            board.saveUser(user, specs);
        board.getAllKeys().stream().forEach(System.out::println);

        for (String user : users) 
            board.getUser(USERS+":"+user).keySet().forEach(System.out::println);    
    }
}