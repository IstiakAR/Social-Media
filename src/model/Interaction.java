package model;

public abstract class Interaction {
    protected int interactionID;
    protected int userID;
    protected int postID;

    public Interaction(int interactionID, int userID, int postID) {
        this.interactionID = interactionID;
        this.userID = userID;
        this.postID = postID;
    }

    public int getInteractionID() {
        return interactionID;
    }

    public int getUserID() {
        return userID;
    }

    public int getPostID() {
        return postID;
    }

    public abstract void getInteraction();
}