
package model;

public class Comment {
	String commentText;
	User user;
	
	public void addComment(String text, int id) {
		user.userID=id;
		commentText = text;
	}
}