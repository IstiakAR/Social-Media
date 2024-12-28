
package model;

public class Reaction {
  int reaction;
  User user;
  Post post;

  public void addReaction(int reaction, User user, Post post) {
    this.reaction = reaction;
    this.user = user;
    this.post = post;
    post.reactions.put(user, reaction);
    if (reaction == 1)
      post.totalReaction += 1;
    else if (reaction == -1)
      post.totalReaction -= 1;
  }
  
}
