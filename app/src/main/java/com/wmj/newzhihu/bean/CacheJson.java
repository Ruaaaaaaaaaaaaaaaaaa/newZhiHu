package com.wmj.newzhihu.bean;

import org.litepal.crud.DataSupport;

/**
 * Created by wmj on 2017-3-20.
 */

public class CacheJson extends DataSupport{
    private String url;
    private String JsonResponse;

    @Override
    public String toString() {
        return "CacheJson{" +
                "url='" + url + '\'' +
                ", JsonResponse='" + JsonResponse + '\'' +
                '}';
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getJsonResponse() {
        return JsonResponse;
    }

    public void setJsonResponse(String jsonResponse) {
        JsonResponse = jsonResponse;
    }
}
