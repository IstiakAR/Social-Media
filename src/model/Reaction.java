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

  @Override
  public void getInteraction() {
    System.out.println("Reaction by User " + userID + " on Post " + postID + ": " + (react == 1 ? "Like" : "Dislike"));
  }
}