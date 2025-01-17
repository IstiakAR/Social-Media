
package model;

import java.util.ArrayList;
import java.util.Random;

import main.MainStorage;

public class User {
  public String username;
  public String password;
  public String name;
  public String clue;
  public int userID;
  public ArrayList<Post> posts = new ArrayList<>();
  public ArrayList<User> friends = new ArrayList<>();

  public User(String username, String password, String name,String clue, int uid) {
    this.username = username;
    this.password = password;
    this.name = name;
    this.clue = clue;
    this.userID = uid;
  }
  public User(String u, String p, String n, String c) {
    this.username = u;
    this.password = p;
    this.name = n;
    this.clue = c;
    this.userID = genRandom();
  }
  private int genRandom(){
    while(true){
      Random rnd = new Random();
      int n = 10000000 + rnd.nextInt(90000000);
      if(!MainStorage.getUsersIMap().containsKey(n)){
        return n;
      }
    }
  }
  public void addFriend(User user) {
    if (friends.contains(user))
      return;
    friends.add(user);
    user.addFriend(this);
  }

  public void removeFriend(User user) {
    if (!friends.contains(user))
      return;
    friends.remove(user);
    user.removeFriend(this);
  }

  public int getFriendCount() {
    return friends.size();
  }

  public void addPost(Post post) {
    posts.add(post);
  }

  public void deletePost(Post post) {
    posts.remove(post);
  }

  public int getPostCount() {
    return posts.size();
  }
}
