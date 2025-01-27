package model;

import java.util.ArrayList;
import java.util.List;

public class User {
    private int userId;
    private String name;
    private String bio;
    private byte[] profilePicture;
    private List<User> friends;

    public User(int userId, String name, String bio, byte[] profilePicture) {
        this.userId = userId;
        this.name = name;
        this.bio = bio;
        this.profilePicture = profilePicture;
        this.friends = new ArrayList<>();
    }

    public int getUserId() {
        return userId;
    }

    public String getName() {
        return name;
    }

    public String getBio() {
        return bio;
    }

    public byte[] getProfilePicture() {
        return profilePicture;
    }

    public void setProfilePicture(byte[] profilePicture) {
        this.profilePicture = profilePicture;
    }

    public void addFriend(User user) {
        if (!friends.contains(user)) {
            friends.add(user);
            user.addFriend(this);
        }
    }

    public void removeFriend(User user) {
        if (friends.contains(user)) {
            friends.remove(user);
            user.removeFriend(this);
        }
    }

    public int getFriendCount() {
        return friends.size();
    }
}