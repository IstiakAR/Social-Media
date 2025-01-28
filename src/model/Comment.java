
package model;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Comment extends Interaction {
	private String commentText;
	private LocalDateTime creationTime;

	public Comment(String text, int postID, int userID) { // setter/insert
		super(generateInteractionID(), userID, postID);
		this.commentText = text;
		this.creationTime = LocalDateTime.now();
	}
	public Comment(String text, int commentID, int postID, int userID, LocalDateTime creationTime) { //getter
		super(commentID, userID, postID);
		this.commentText = text;
		this.creationTime = creationTime;
	}
	
	public int getPostID() {
        return postID;
    }
	public String getCommentText() {
		return commentText;
	}
	public LocalDateTime getCreationTime() {
        return creationTime;
    }
    public String getFormattedCreationTime() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        return creationTime.format(formatter);
    }
}
