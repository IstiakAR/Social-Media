package model;

public class Reaction extends Interaction {
  private int react;

  public Reaction(int react, int postID, int userID) {
		super(postID, userID);
    this.react = react;
    System.out.println("Reaction created: react=" + react + ", postID=" + postID + ", userID=" + userID);
  }
  public int getReact() {
    return react;
  }
}
