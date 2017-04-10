package com.blog.spider;


import javax.annotation.Resource;
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
}
