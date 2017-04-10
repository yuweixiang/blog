package com.blog.spider;

/**
 * Created by yuweixiang on 16/1/16.
 */
public interface LinkFilter {
    public boolean accept(String url);
}
