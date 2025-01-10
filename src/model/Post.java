
package model;

import java.util.HashMap;
import java.util.Map;

public class Post extends Reaction {
	public String postContent;
	public int postID;
	Comment comments[] = new Comment[5000];
	public int totalReaction;
	public User user;
	Map<User, Integer> reactions = new HashMap<>();

	public Post(String content, int postID, User user) {
		this.postContent = content;
		this.user = user;
		this.user.addPost(post);
	}

	public int getReaction(User user) {
		return reactions.getOrDefault(user, 0);
	}
}
