package model;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

import main.MainStorage;

public class Post {
    private String postContent;
    private int postID;
    private int totalReaction;
	private int commentCount;
    private int userID;
	private LocalDateTime creationTime;
    public ArrayList<Interaction> interactions = new ArrayList<>();

    public Post(String content, int postID, int userID) {
        this.postContent = content;
		this.userID = userID;
        this.postID = postID;
		this.creationTime = LocalDateTime.now();
        User user = MainStorage.getUsersIMap().get(userID);
        user.addPost(this);
    }

    public void addComment(String text, int userID) {
        int commentID = generateInteractionID();
        Comment comment = new Comment(text, commentID, postID, userID);
        interactions.add(comment);
		this.commentCount++;
    }

    public void addReaction(int react, int userID) {
        int reactionID = generateInteractionID();
        Reaction reaction = new Reaction(react, reactionID, postID, userID);
        interactions.add(reaction);
        if (react == 1)
            this.totalReaction += 1;
        else if (react == -1)
            this.totalReaction -= 1;
    }

    private int generateInteractionID() {
        return interactions.size() + 1;
    }

    public void displayInteractions() {
        for (Interaction interaction : interactions) {
            interaction.getInteraction();
        }
    }
	public String getCreationTime() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
        return creationTime.format(formatter);
    }

    public String getPostContent() {
        return postContent;
    }

    public int getPostID() {
        return postID;
    }

    public int getTotalReaction() {
        return totalReaction;
    }

    public int getUserID() {
        return userID;
    }

	public void setCreationTime(LocalDateTime localDateTime) {
		this.creationTime = localDateTime;
	}
	public int getCommentCount() {
		return commentCount;
	}
}
