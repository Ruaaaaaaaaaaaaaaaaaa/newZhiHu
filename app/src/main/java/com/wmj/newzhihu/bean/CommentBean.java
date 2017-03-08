package com.wmj.newzhihu.bean;

import java.util.List;

/**
 * Created by wmj on 2017-3-8.
 */

public class CommentBean {
    private List<Comment> comments;

    @Override
    public String toString() {
        return "CommentBean{" +
                "comments=" + comments +
                '}';
    }

    public List<Comment> getComments() {
        return comments;
    }

    public void setComments(List<Comment> comments) {
        this.comments = comments;
    }
}
