package model;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

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
        User user = MainStorage.getUsersIMap().get(userID);
        if (user != null) {
            user.addPost(this);
        } else {
            throw new IllegalArgumentException("User not found for userID: " + userID);
        }
    }

    public Post(String content, int userID) {
        this.postContent = content;
		this.userID = userID;
        generatePostID();
        this.creationTime = LocalDateTime.now();
        User user = MainStorage.getUsersIMap().get(userID);
        if (user != null) {
            user.addPost(this);
        } else {
            throw new IllegalArgumentException("User not found for userID: " + userID);
        }
    }

    public Comment addComment(String text, int userID) {
        int commentID = generateInteractionID();
        Comment comment = new Comment(text, commentID, this.postID, userID);
		// this.commentCount++;
        return comment;
    }

    public void addReaction(int react, int userID) {
        // int reactionID = generateInteractionID();
        // Reaction reaction = new Reaction(react, reactionID, postID, userID);
        if (react == 1)
            this.totalReaction += 1;
        else if (react == -1)
            this.totalReaction -= 1;
    }

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

    private int generateInteractionID() {
        while(true){
            int n = 100000000 + (int)(Math.random() * 900000000);
            if(!MainStorage.getCommentsMap().containsKey(n)){
                return n;
            }
        }
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

    public int getTotalReaction() {
        return totalReaction;
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
}
