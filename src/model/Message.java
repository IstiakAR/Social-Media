package model;


public class Message {
    private final int senderId;
    private final int receiverId;
    private final String content;
   

    public Message(int senderId, int receiverId, String content) {
        this.senderId = senderId;
        this.receiverId = receiverId;
        this.content = content;
       
    }

    public int getSenderId() {
        return senderId;
    }

    public int getReceiverId() {
        return receiverId;
    }

    public String getContent() {
        return content;
    }

   
}

