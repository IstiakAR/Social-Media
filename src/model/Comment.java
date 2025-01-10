
package model;

public class Comment {
	public String commentText;
	User user;
	Post post;

	Comment(String text, Post post, User user) { // constructor
		this.commentText = text;
		this.user = user;
		this.post = post;
	}
}
