
package model;

public class Reaction {
  int react;
  User user;
  Post post;

  Reaction(int react, User user, Post post) { // constructor
    this.react = react;
    this.user = user;
    this.post = post;
  }
}
