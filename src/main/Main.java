
package main;

import java.util.HashMap;
import java.util.Map;

import model.*;

public class Main {
  public static Map<String, User> users= new HashMap<>();

  public static void main(String[] args) {
    User n=new User("","","abc","abc",0);
    User user1 = new User("ghashkata", "password", "John", "clue1", 1);
    User user2 = new User("a", "b", "Tom","clue2", 2);
    User user3 = new User("user3", "password", "Jane","clue3", 3);
    User user4 = new User("user4", "password", "Bob","clue4", 4);
    users.put(user1.username,user1);
    users.put(user2.username,user2);
    users.put(user3.username,user3);
    
    user1.addFriend(user2);
    user1.addFriend(user4);
    user2.addFriend(user3);

    System.out.println(user1.getFriendCount());
    System.out.println(user2.getFriendCount());
    System.out.println(user3.getFriendCount());
    System.out.println(user4.getFriendCount());

    user3.removeFriend(user2);

    System.out.println(user1.getFriendCount());
    System.out.println(user2.getFriendCount());
    System.out.println(user3.getFriendCount());
    System.out.println(user4.getFriendCount());

    System.out.println(user1.username + ' ' + user1.password + ' ' + user1.name + ' ' + user1.userID);
    System.out.println(user2.username + ' ' + user2.password + ' ' + user2.name + ' ' + user2.userID);

    Post post1 = new Post("This is a test post", 1, user1);
    Post post2 = new Post("2nd post", 2, user1);
    System.out.println(post1.postContent + ' ' + post1.postID + ' ' + post1.user.username);

    post1.addComment("This is a comment", user1);
    post1.addComment("This is 2nd comment", user2);

    System.out.println(post1.commentList.get(0).commentText);
    System.out.println(post1.commentList.get(1).commentText);

    post1.addReaction(1, user2);
    post2.addReaction(1, user1);
    post2.addReaction(1, user2);
    post2.addReaction(1, user2);

    System.out.println(post1.totalReaction);
    System.out.println(post2.totalReaction);
    System.out.println(post1.getReaction(user1));
    System.out.println(post2.getReaction(user1));
    System.out.println(post2.getReaction(user2));

    for (int i = 0; i < user1.getPostCount(); i++) {
      System.out.println(user1.posts.get(i).postContent);
    }

    MainController.launch(MainController.class, args);
  }
}
