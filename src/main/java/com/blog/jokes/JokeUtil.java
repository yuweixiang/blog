package com.blog.jokes;


import com.blog.spider.BfsSpider;
import com.jfinal.plugin.activerecord.Page;
import org.htmlparser.Node;
import org.htmlparser.NodeFilter;
import org.htmlparser.Parser;
import org.htmlparser.filters.NodeClassFilter;
import org.htmlparser.filters.OrFilter;
import org.htmlparser.tags.LinkTag;
import org.htmlparser.util.NodeList;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/**
 * Created by yuweixiang on 17/2/7.
 *
 * @author yuweixiang
 * @date 2017/02/07
 */
public class JokeUtil {

    public static List<Joke> buildPageJokes() {
        List<Joke> jokeList = new ArrayList<Joke>();
        BfsSpider spider = new BfsSpider();
        try {
            BfsSpider bfsSpider = new BfsSpider();
            String[] ss = new String[1];
            ss[0]="http://neihanshequ.com";
            String s = bfsSpider.crawling(ss);
            int aaa = s.indexOf("div class=\"content-wrapper\"\n");
            while(s.indexOf("div class=\"content-wrapper\"")!=-1) {
                Joke joke = new Joke();
                int index = s.indexOf("div class=\"content-wrapper\"");
                s = s.substring(index+27, s.length() - 1);
                int startText = s.indexOf("<p>");
                int endText = s.indexOf("</p>");
                joke.setText(s.substring(startText+3,endText));
                joke.set("text",joke.getText());

                int startNum = s.indexOf("<span");
                int endNum = s.indexOf("</span>");
                joke.setNum(Integer.parseInt(s.substring(startNum+19,endNum)));
                jokeList.add(joke);
            }

        } catch (Exception e) {
//            e.printStackTrace();
        }

        if (!jokeList.isEmpty()){
            Collections.sort(jokeList, new Comparator<Joke>() {
                @Override
                public int compare(Joke o1, Joke o2) {
                    return o1.getNum()>o2.getNum()?-1:1;
                }
            });
        }
        return jokeList.subList(0,9);
    }

    public static void main(String[] args) {
        JokeUtil.buildPageJokes();
    }
}
