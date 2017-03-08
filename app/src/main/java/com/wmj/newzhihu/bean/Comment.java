package com.wmj.newzhihu.bean;

/**
 * Created by wmj on 2017-3-8.
 */

public class Comment {
    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getLikes() {
        return likes;
    }

    public void setLikes(String likes) {
        this.likes = likes;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    @Override
    public String toString() {
        return "Comment{" +
                "author='" + author + '\'' +
                ", id='" + id + '\'' +
                ", content='" + content + '\'' +
                ", likes='" + likes + '\'' +
                ", time='" + time + '\'' +
                ", avatar='" + avatar + '\'' +
                '}';
    }

    private String author;
    private String id;
    private String content;
    private String likes;
    private String time;
    private String avatar;
}
