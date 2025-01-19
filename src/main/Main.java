package main;

import database.Database;

public class Main {
  public static void main(String[] args) {
    Database.connect();
    Database.createTables();
    MainStorage storage = new MainStorage();
    storage.loadUsers();

    // User user1 = new User("a", "a", "John", "clue1", 1);
    // DatabaseInsert.insertUser(user1);
    // Post post1 = new Post("This is a test ", 4, 38114975);
    // DatabaseInsert.insertPost(post1);

    // post1.addComment("This is a comment", user1.getUserID());
    // post1.addReaction(1, user1.getUserID());

    MainController.launch(MainController.class, args);
  }
}