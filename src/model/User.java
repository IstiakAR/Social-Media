
package model;

import java.util.ArrayList;
import java.util.Random;

import main.MainStorage;

public class User {
  private String username;
  private String password;
  private String name;
  private String clue;
  private String bio;
  private String picturePath;
  private int userID;
  private ArrayList<Post> posts = new ArrayList<>();
  private ArrayList<User> friends = new ArrayList<>();

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
  public String getUsername() {
    return username;
  }
  public String getPassword() {
    return password;
  }
  public String getName() {
    return name;
  }
  public String getClue() {
    return clue;
  }
  public String getBio() {
    return bio;
  }
  public String getPicturePath() {
    return picturePath;
  }
  public int getUserID() {
    return userID;
  }
  public int genRandom(){
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
