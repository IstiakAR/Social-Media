package model;

public class Reaction extends Interaction {
  private int react;

  public Reaction(int react, int reactionID, int postID, int userID) {
    super(reactionID, userID, postID);
    this.react = react;
  }

  public int getReact() {
    return react;
  }
}