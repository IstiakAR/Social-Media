package model;

import main.MainStorage;

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

    public static int generateInteractionID() {
        while(true){
            int n = 100000000 + (int)(Math.random() * 900000000);
            if(!MainStorage.getCommentsMap().containsKey(n)){
                return n;
            }
        }
    }

    // public abstract void getInteraction();
}