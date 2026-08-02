package com.example.backend_lab.model;

public class Channel {

    private String channelId;
    private String name;
    private String lastChat;
    private int unReadCount;
    private String favoriteYn; // "Y" / "N"
    private String delYn;      // "Y" / "N"

    public Channel(String channelId, String name, String lastChat,
                   int unReadCount, String favoriteYn, String delYn) {
        this.channelId = channelId;
        this.name = name;
        this.lastChat = lastChat;
        this.unReadCount = unReadCount;
        this.favoriteYn = favoriteYn;
        this.delYn = delYn;
    }

    public String getChannelId() {return channelId;}
    public String getName() {return name;}
    public String getLastChat() {return lastChat;}
    public int getUnReadCount() {return unReadCount;}
    public String getFavoriteYn() {return favoriteYn;}
    public String getDelYn() {return delYn;}
}
