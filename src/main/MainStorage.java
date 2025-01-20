package main;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import model.Comment;
import model.Post;
import model.User;
import database.DatabaseGetter;

public class MainStorage {
    private static Map<Integer, String> usersKeyMap = new HashMap<>();
    private static Map<String, User> usersSMap = new HashMap<>();
    private static Map<Integer, User> usersIMap = new HashMap<>();
    private static Map<Integer, Comment> commentsMap = new HashMap<>();
    private static Map<Integer, Post> allPostsMap = new HashMap<>();

    public void loadUsers() {
        List<User> users = DatabaseGetter.getUsers();
        for (User user : users) {
            usersKeyMap.put(user.getUserID(), user.getUsername());
            usersSMap.put(user.getUsername(), user);
            usersIMap.put(user.getUserID(), user);
        }
        allPostsMap = DatabaseGetter.getAllPosts();
        commentsMap = DatabaseGetter.getCommentsMap();
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
    public static Map<Integer, Comment> getCommentsMap() {
        return commentsMap;
    }
    public static Map<Integer, Post> getAllPosts() {
        return allPostsMap;
    }
}
