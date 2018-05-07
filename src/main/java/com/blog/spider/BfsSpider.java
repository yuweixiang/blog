package com.blog.spider;


import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpStatus;
import org.apache.commons.httpclient.methods.GetMethod;
import org.apache.commons.lang3.StringUtils;

import javax.annotation.Resource;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

/**
 * Created by yuweixiang on 16/1/16.
 */
public class BfsSpider {

    /**
     * 下载器
     */
    private DownTool downTool = new DownTool();

    /**
     * 使用种子初始化URL队列
     *
     * @param seeds 种子地址
     */
    private void initCrawlerWithSeeds(String[] seeds) {
        for (int i = 0; i < seeds.length; i++) {
            SpiderQueue.addUnvisitedUrl(seeds[i]);
        }
    }

    // 定义过滤器，提取以 http://www.xxxx.com开头的链接
    public String crawling(String[] seeds) {
        LinkFilter filter = new LinkFilter() {
            @Override
            public boolean accept(String url) {
                if (url.startsWith("http://www.")) {
                    return true;
                } else {
                    return false;
                }
            }
        };
        // 初始化 URL 队列
        initCrawlerWithSeeds(seeds);
        // 循环条件：待抓取的链接不空且抓取的网页不多于 1000
        while (!SpiderQueue.unVisitedUrlsEmpty()
                && SpiderQueue.getVisitedUrlNum() <= 1000) {
            // 队头 URL 出队列
            String visitUrl = (String) SpiderQueue.unVisitedUrlDeQueue();
            if (visitUrl == null) {
                continue;
            }
            // 下载网页
            try {
                return downTool.downloadFile(visitUrl);
            } catch (Exception e) {
                System.out.println("拉取失败,url:" + visitUrl + "e:" + e);
            }
//            // 该 URL 放入已访问的 URL 中
//            SpiderQueue.addVisitedUrl(visitUrl);
//            // 提取出下载网页中的 URL
//            Set<String> links = HtmlParserTool.extracLinks(visitUrl, filter);
//            // 新的未访问的 URL 入队
//            for (String link : links) {
//                SpiderQueue.addUnvisitedUrl(link);
//            }
        }
        return "";
    }

    public static void main(String[] args){
        List<String> info = new ArrayList<String>();
        // 定义输入输出流
        InputStream input = null;
        // 得到 post 方法
        GetMethod getMethod = new GetMethod("https://s.m.taobao.com/search?nid=538814445976");
        try {
            HttpClient httpClient = new HttpClient();
            // 执行，返回状态码
            int statusCode = httpClient.executeMethod(getMethod);
            // 针对状态码进行处理
            // 简单起见，只处理返回值为 200 的状态码
            if (statusCode == HttpStatus.SC_OK) {
                input = getMethod.getResponseBodyAsStream();
                BufferedReader bufferedReader = new BufferedReader(
                        new InputStreamReader(input, "utf-8"));
                String result = bufferedReader.readLine();
                if (StringUtils.isBlank(result)) {
                }
                JSONObject object = JSONObject.parseObject(result);
                JSONArray jsonArray = object.getJSONArray("itemsArray");
                if (jsonArray == null || jsonArray.size() == 0) {
                }
                JSONObject jsonObject = jsonArray.getJSONObject(0);
                String originTag = jsonObject.getString("auctionTag");
                System.out.println(originTag);
            }
            // 关闭输入流
            if (input != null) {
                input.close();
            }
        } catch (Exception e) {
        } finally {
            getMethod.releaseConnection();
        }
    }
}
