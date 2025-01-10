
package model;

import java.util.ArrayList;

public class User {
  public String username;
  public String password;
  public String name;
  public int userID;
  public ArrayList<Post> posts = new ArrayList<>();
  public ArrayList<User> friends = new ArrayList<>();

  public User(String username, String password, String name, int uid) {
    this.username = username;
    this.password = password;
    this.name = name;
    this.userID = uid;
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
