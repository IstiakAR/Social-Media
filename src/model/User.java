
package model;

public class User {
  public String username;
  public String password;
  public String name;
  public int userID;
  public int[] friends = new int[1000];
  public Post[] posts = new Post[1000];
  private int postCount = 0;

  public User(String username, String password, String name, int uid) {
    this.username = username;
    this.password = password;
    this.name = name;
    this.userID = uid;
  }

  public void addFriend(int id) {
    for (int i = 0; i < friends.length; i++) {
      if (friends[i] == 0) {
        friends[i] = id;
        break;
      }
    }
  }

  public void removeFriend(int id) {
    for (int i = 0; i < friends.length; i++) {
      if (friends[i] == id) {
        friends[i] = 0;
        break;
      }
    }
  }

  public void addPost(Post post) {
    posts[postCount] = post;
    postCount++;
  }
}
