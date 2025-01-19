package main;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import model.User;
import database.DatabaseGetter;

public class MainStorage {
    private static Map<Integer, String> usersKeyMap = new HashMap<>();
    private static Map<String, User> usersSMap = new HashMap<>();
    private static Map<Integer, User> usersIMap = new HashMap<>();

    public void loadUsers() {
        List<User> users = DatabaseGetter.getUsers();
        for (User user : users) {
            usersKeyMap.put(user.getUserID(), user.getUsername());
            usersSMap.put(user.getUsername(), user);
            usersIMap.put(user.getUserID(), user);
        }
    }
    public static Map<Integer, String> getUsersKeyMap() {
        return usersKeyMap;
    }

    public static Map<String, User> getUsersSMap() {
        return usersSMap;
    }

    public static Map<Integer, User> getUsersIMap() {
        return usersIMap;
    }
}
