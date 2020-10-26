package com.hugopaiva.app;

import redis.clients.jedis.Jedis;
import java.util.*;

public class MessageNetwork {
    private Jedis jedis;
    private String usersSet;
    private String userFollowing;
    private String userHash;
    private String userMessage;

    /**
     * Setup connection with redis db, flush database and main keys of redis for
     * this exercise
     */
    public MessageNetwork() {
        this.jedis = new Jedis("localhost");
        this.usersSet = "MNUsers";
        this.userHash = "MNUser:";
        this.userFollowing = "MNFollowing:";
        this.userMessage = "MNMessages:";
    }

    /**
     * @return String Check if the user is in the system and saves it if not
     */
    public String saveUser() {
        Scanner inputScanner = new Scanner(System.in);
        System.out.print("\nInsert your username: ");
        String user = inputScanner.nextLine();

        if (!this.jedis.sismember(this.usersSet, user)) {
            HashMap<String, String> properties = new HashMap<String, String>();
            System.out.print("Insert your name: ");
            properties.put("name", inputScanner.nextLine());

            System.out.print("Insert your age: ");
            properties.put("age", inputScanner.nextLine());

            System.out.print("Insert you hobby: ");
            properties.put("hobby", inputScanner.nextLine());

            this.jedis.sadd(this.usersSet, user);
            this.jedis.hmset(this.userHash + user, properties);
        }

        return user;
    }

    /**
     * @param user Return the user info
     */
    public void userInfo(String user) {
        if (this.jedis.sismember(this.usersSet, user)) {
            Map<String, String> properties = this.jedis.hgetAll(this.userHash + user);
            System.out.println("\n-- " + user + " Info --");
            System.out.println("Name: " + properties.get("name"));
            System.out.println("Age: " + properties.get("age"));
            System.out.println("Hobby: " + properties.get("hobby"));

        } else {
            System.err.print("User does not exist!\n");
        }
    }

    /**
     * @param user Check if there are users available to follow and gives option to
     *             follow if so
     */
    public void followUser(String user) {
        Scanner inputScanner = new Scanner(System.in);
        Set<String> usersSet = this.jedis.sdiff(this.usersSet, this.userFollowing + user);
        List<String> users = new ArrayList<String>();
        users.addAll(usersSet);
        users.remove(user);

        if (users.size() == 0) {
            System.err.print("\nThere are no users!\n");
        } else {
            System.out.println("\n-- Users to follow --");
            users.stream().forEach(item -> System.out.println((users.indexOf(item) + 1) + " - " + item));
            System.out.print("Insert number to follow: ");
            Integer numberToFollow = inputScanner.nextInt();

            this.jedis.sadd(this.userFollowing + user, users.get(numberToFollow - 1));

            System.out.print(users.get(numberToFollow - 1) + " followed!\n");
        }
    }

    /**
     * @param user Registers messages in the system
     */
    public void sendMessages(String user) {
        Scanner inputScanner = new Scanner(System.in);
        System.out.println("\n-- Sending a message --");
        System.out.print("Insert message: ");
        String message = inputScanner.nextLine();

        this.jedis.lpush(this.userMessage + user, message);

        System.out.println("Message sent!\n");
    }

    /**
     * @param user Check if there are users available to unfollow and gives option
     *             to unfollow if so
     */
    public void unFollowUser(String user) {
        Scanner inputScanner = new Scanner(System.in);
        Set<String> usersSet = this.jedis.smembers(this.userFollowing + user);
        List<String> users = new ArrayList<String>();
        users.addAll(usersSet);

        if (users.size() == 0) {
            System.err.print("\nYou are following no users!\n");
        } else {
            System.out.println("\n-- Users to unfollow --");
            users.stream().forEach(item -> System.out.println((users.indexOf(item) + 1) + " - " + item));
            System.out.print("Insert number to follow: ");
            Integer numberToUnFollow = inputScanner.nextInt();

            this.jedis.srem(this.userFollowing + user, users.get(numberToUnFollow - 1));

            System.out.print(users.get(numberToUnFollow - 1) + " unfollowed!\n");
        }
    }

    /**
     * @param user Provides the last 5 messages form the people the user follow
     */
    public void messagesFromFollowing(String user) {
        Set<String> usersSet = this.jedis.smembers(this.userFollowing + user);
        List<String> users = new ArrayList<String>();
        users.addAll(usersSet);

        if (users.size() == 0) {
            System.err.print("\nYou are following no users!\n");
        } else {
            Map<String, List<String>> messages = new HashMap<String, List<String>>();

            Boolean existsMessage = false;

            for (String item : users) {
                List<String> messagesList = this.jedis.lrange(this.userMessage + item, 0, 4);

                if (messagesList.size() > 0) {
                    existsMessage = true;
                    messages.put(item, messagesList);
                }

            }

            if (!existsMessage) {
                System.err.print("\nThere are no messages in your feed!\n");
            } else {
                for (String friend : messages.keySet()) {
                    System.out.println("\n-- Last 5 messages from " + friend + " --");
                    messages.get(friend).forEach(message -> System.out.println("- " + message));
                    System.out.println();

                }
            }

        }
    }

    /**
     * @param user Show the last 5 messages of the user
     */
    public void myMessages(String user) {
        List<String> messagesList = this.jedis.lrange(this.userMessage + user, 0, 4);

        if (messagesList.size() == 0) {
            System.err.print("\nYou have sent no messages!\n");
        } else {
            System.out.println("\n-- My Last 5 messages --");
            for (String message : messagesList) {
                System.out.println("- " + message);

            }
        }
    }

    /**
     * Lists all the users in the system and provides the information of them if
     * wanted
     */
    public void listUsers() {
        Scanner inputScanner = new Scanner(System.in);
        Set<String> usersSet = this.jedis.smembers(this.usersSet);
        List<String> users = new ArrayList<String>();
        users.addAll(usersSet);

        if (users.size() == 0) {
            System.err.print("\nThere are no users!\n");
        } else {
            System.out.println("\n-- Users on the system --");
            users.stream().forEach(item -> System.out.println((users.indexOf(item) + 1) + " - " + item));
            System.out.print("Insert number to see info or 0 to exit: ");
            Integer numberUser = inputScanner.nextInt();

            if (numberUser == 0) {
                return;
            } else {
                this.userInfo(users.get(numberUser - 1));
            }
        }
    }

    /**
     * @param args
     */
    public static void main(String[] args) {
        MessageNetwork network = new MessageNetwork();
        Scanner inputScanner = new Scanner(System.in);
        String input = "init";

        String username = network.saveUser();

        while (true) {

            switch (input) {
                case "1":
                    network.userInfo(username);
                    break;
                case "2":
                    network.followUser(username);
                    break;
                case "3":
                    network.unFollowUser(username);
                    break;
                case "4":
                    network.sendMessages(username);
                    break;
                case "5":
                    network.messagesFromFollowing(username);
                    break;
                case "6":
                    network.myMessages(username);
                    break;
                case "7":
                    network.listUsers();
                    break;
                case "q":
                    System.out.println("Come back soon, " + username + "!");
                    System.exit(0);
            }

            System.out.println("\n-- Select an option --\n" + " 1 - My Profile\n" + " 2 - Follow user\n"
                    + " 3 - Unfollow user\n" + " 4 - Send Message\n" + " 5 - Read Subscriptions\n"
                    + " 6 - Read my messages\n" + " 7 - List all users\n" + " q - Quit");
            System.out.print("Option: ");
            input = inputScanner.nextLine();

        }

    }
}
