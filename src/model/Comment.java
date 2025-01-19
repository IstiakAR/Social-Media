
package model;

public class Comment extends Interaction {
	private String commentText;

	public Comment(String text, int commentID, int postID, int userID) {
		super(commentID, userID, postID);
		this.commentText = text;
	}

    @Override
    public void getInteraction() {
        System.out.println("Comment by User " + userID + " on Post " + postID + ": " + commentText);
    }
}
