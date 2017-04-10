package com.blog.jokes;

import com.blog.blog.Blog;
import com.jfinal.plugin.activerecord.Model;

/**
 * 段子
 *
 * @author yuweixiang
 * @date 2017/02/07
 */
public class Joke extends Model<Joke> {
    private String text;

    private int num;

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public int getNum() {
        return num;
    }

    public void setNum(int num) {
        this.num = num;
    }
}
