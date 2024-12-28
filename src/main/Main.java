
package main;

import model.*;
import view.*;

public class Main {
  public static void main(String[] args) {
    User user1 = new User("user1", "password1", "John Doe", 1);
    User user2 = new User("user2", "password", "Tom", 2);
    // LoginScreen.launch(LoginScreen.class, args);
    System.out.println(user1.username + ' ' + user1.password + ' ' + user1.name + ' ' + user1.userID);
    Post post1 = new Post("This is a test post", 1, user1);
    System.out.println(post1.postContent + ' ' + post1.postID + ' ' + post1.user.username);
    Post post2 = new Post("2nd post", 2, user1);
    post1.addReaction(1, user2, post1);
    post2.addReaction(1, user1, post2);
    post2.addReaction(1, user1, post1);
    System.out.println(post1.totalReaction);
    System.out.println(post2.totalReaction);
    System.out.println(post1.getReaction(user1));
    // comment
  }
}
