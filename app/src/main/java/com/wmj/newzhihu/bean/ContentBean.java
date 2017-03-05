package com.wmj.newzhihu.bean;

import java.util.List;

/**
 * Created by wumingjun1 on 2017/2/17.
 */

public class ContentBean {
    private String body;
    private String image_source;
    private String title;
    private String image;
    private String share_url;
    private List<String> js;
    private List<Avatar> recommenders;
    private String ga_prefix;
    private Section section;
    private String type;
    private List<String> css;
    private List<String> images;

    public List<String> getImages() {
        return images;
    }

    public void setImages(List<String> images) {
        this.images = images;
    }

    @Override
    public String toString() {
        return "ContentBean{" +
                "body='" + body + '\'' +
                ", image_source='" + image_source + '\'' +
                ", title='" + title + '\'' +
                ", image='" + image + '\'' +
                ", share_url='" + share_url + '\'' +
                ", js=" + js +
                ", recommenders=" + recommenders +
                ", ga_prefix='" + ga_prefix + '\'' +
                ", section=" + section +
                ", type='" + type + '\'' +
                ", css=" + css +
                ", images=" + images +
                '}';
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public List<String> getCss() {
        return css;
    }

    public void setCss(List<String> css) {
        this.css = css;
    }

    public String getGa_prefix() {
        return ga_prefix;
    }

    public void setGa_prefix(String ga_prefix) {
        this.ga_prefix = ga_prefix;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getImage_source() {
        return image_source;
    }

    public void setImage_source(String image_source) {
        this.image_source = image_source;
    }

    public List<String> getJs() {
        return js;
    }

    public void setJs(List<String> js) {
        this.js = js;
    }

    public List<Avatar> getRecommenders() {
        return recommenders;
    }

    public void setRecommenders(List<Avatar> recommenders) {
        this.recommenders = recommenders;
    }

    public Section getSection() {
        return section;
    }

    public void setSection(Section section) {
        this.section = section;
    }

    public String getShare_url() {
        return share_url;
    }

    public void setShare_url(String share_url) {
        this.share_url = share_url;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    static class Avatar{
        String avatar;

        public String getAvatar() {
            return avatar;
        }

        public void setAvatar(String avatar) {
            this.avatar = avatar;
        }

        @Override
        public String toString() {
            return "Avatar{" +
                    "avatar='" + avatar + '\'' +
                    '}';
        }
    }
    static class Section{
        String thumbnail;
        String id;
        String name;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getThumbnail() {
            return thumbnail;
        }

        public void setThumbnail(String thumbnail) {
            this.thumbnail = thumbnail;
        }

        @Override
        public String toString() {
            return "Section{" +
                    "id='" + id + '\'' +
                    ", thumbnail='" + thumbnail + '\'' +
                    ", name='" + name + '\'' +
                    '}';
        }
    }
}
