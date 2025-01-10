
package model;

import java.util.ArrayList;

public class Post {
	public String postContent;
	public int postID;
	public int totalReaction;
	public User user;
	public ArrayList<Comment> commentList = new ArrayList<>();
	public ArrayList<Reaction> reactions = new ArrayList<>();

	public Post(String content, int postID, User user) { // constructor
		this.postContent = content;
		this.user = user;
		this.user.addPost(this);
		this.postID = postID;
	}

	public int getReaction(User user) {
		int ret = 0;
		int n = reactions.size();
		for (int i = 0; i < n; i++) {
			if (reactions.get(i).user == user) {
				ret = reactions.get(i).react;
				break;
			}
		}
		return ret;
	}

	public void addComment(String text, User user) {
		Comment comment = new Comment(text, this, user);
		commentList.add(comment);
	}

	public void addReaction(int react, User user) {
		Reaction reaction = new Reaction(react, user, this);
		int n = reactions.size();
		for (int i = 0; i < n; i++) { // searchig for existing reaction.
																	// If same reaction exists by same user then skip else add/change.
			if (reactions.get(i).user == user) {
				reactions.get(i).react = react;
				return;
			}
		}
		this.reactions.add(reaction);
		if (react == 1)
			this.totalReaction += 1;
		else if (react == -1)
			this.totalReaction -= 1;
	}
}
