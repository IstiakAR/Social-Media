package main;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

import database.*;
import model.*;

public class Main {
  public static void main(String[] args) {
    Database.connect();
    Database.createTables();
    // DatabaseUpdate.addProfilePictureColumn();
    MainStorage storage = new MainStorage();
    storage.loadUsers();

    // Convert the image file to a byte array
    // byte[] profilePicture = null;
    // File imageFile = new File("res/pictures/bocchi.jpg");
    // try (InputStream inputStream = new FileInputStream(imageFile)) {
    //   profilePicture = inputStream.readAllBytes();
    // } catch (IOException e) {
    //   e.printStackTrace();
    // }

    // // Create a User object with the image BLOB
    // User user1 = new User("e", "d", "John", "clue1", 9, profilePicture);

    // // Insert the user into the database
    // DatabaseInsert.insertUser(user1);



    // User user1 = new User("a", "a", "John", "clue1", 1);
    // DatabaseInsert.insertUser(user1);
    // Post post1 = new Post("This is a test ", 4, 38114975);
    // DatabaseInsert.insertPost(post1);

    // post1.addReaction(1, user1.getUserID());
    // DatabaseInsert.insertComment(post1.addComment("this is 4th comment", 1));
    MainController.launch(MainController.class, args);
  }
}