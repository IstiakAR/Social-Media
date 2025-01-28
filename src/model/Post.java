package model;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import database.DatabaseUpdate;
import main.MainStorage;

public class Post {
    private String postContent;
    private int postID;
    private int totalReaction;
	private int commentCount;
    private int userID;
	private LocalDateTime creationTime;

    public Post(String content, int postID, int userID) {
        this.postContent = content;
		this.userID = userID;
        this.postID = postID;
		this.creationTime = LocalDateTime.now();
    }

    public Post(String content, int userID) {
        this.postContent = content;
		this.userID = userID;
        generatePostID();
        this.creationTime = LocalDateTime.now();
    }

    public Comment addComment(String text, int userID) {
        Comment comment = new Comment(text, this.postID, userID);
		// this.commentCount++;
        return comment;
    }

    // public void addReaction(int reactionID, int react, int userID) {
    //     // Reaction reaction = new Reaction(react, postID, userID);
    //     if (react == 1)
    //         this.totalReaction += 1;
    //     else if (react == -1)
    //         this.totalReaction -= 1;
    // }

    private void generatePostID() {
        int n;
        while(true){
            n = 1000000 + (int)(Math.random() * 9000000);
            if(!MainStorage.getAllPosts().containsKey(n)){
                break;
            }
        }
        postID = n;
    }

    public String getUnformattedCreationTime() {
        return creationTime.toString();
    }

	public String getCreationTime() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
        return creationTime.format(formatter);
    }
    
    public void setCreationTime(LocalDateTime localDateTime) {
        this.creationTime = localDateTime;
    }

    public String getPostContent() {
        return postContent;
    }

    public int getPostID() {
        return postID;
    }
    public void setPostID(int postID) {
        this.postID = postID;
    }



    public int getUserID() {
        return userID;
    }

	public int getCommentCount() {
		return commentCount;
	}

    public List<Comment> getComments(){
        List<Comment> comments = new ArrayList<>();
        for (Map.Entry<Integer, Comment> entry : MainStorage.getCommentsMap().entrySet()) {
            Comment comment = entry.getValue();
            System.out.println("Comment postID: " + comment.getPostID());
            System.out.println("Comment"+' '+comment.getCommentText()+' '+comment.getInteractionID()+' '+
            comment.getUserID()+' '+comment.getPostID());
            System.out.println("This postID: " + this.postID);
            if (comment.getPostID() == this.postID) {
                comments.add(comment);
            }
            if (!MainStorage.getAllPosts().containsKey(postID)) {
                throw new IllegalArgumentException("Post ID " + postID + " does not exist.");
            }
        }
        return comments;
    }

    //vote

    public int getReaction(int userID) {
        Reaction reaction = MainStorage.getReactions().get(userID);
        if (reaction == null) {
            return 0;
        }
        return reaction.getReact();
    }
    public int getTotalReaction() {
        return totalReaction;
    }
    public void setTotalReaction(int totalReaction) {
        this.totalReaction = totalReaction;
        DatabaseUpdate.updateTotalVotes(this.getPostID(), totalReaction);
    }
}