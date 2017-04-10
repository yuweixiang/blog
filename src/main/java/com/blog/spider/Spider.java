package com.blog.spider;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpStatus;
import org.apache.commons.httpclient.methods.GetMethod;

import java.io.*;

/**

import java.io.*;
import java.net.URL;
import java.net.URLConnection;

/**
 * 抓取http信息处理器
 *
 * Created by yuweixiang on 16/1/14.
 */
public class Spider {

    private static HttpClient httpClient = new HttpClient();

    /**
     * 抓取网络信息
     *
     * @param path
     *            目标网页的链接
     * @return 返回布尔值，表示是否正常下载目标页面
     * @throws Exception
     *             读取网页流或写入本地文件流的IO异常
     */
    public static boolean downloadPage(String path) throws Exception {
        // 定义输入输出流
        InputStream input = null;
        OutputStream output = null;
        // 得到 post 方法
        GetMethod getMethod = new GetMethod(path);
        // 执行，返回状态码
        int statusCode = httpClient.executeMethod(getMethod);
        // 针对状态码进行处理
        // 简单起见，只处理返回值为 200 的状态码
        if (statusCode == HttpStatus.SC_OK) {
            input = getMethod.getResponseBodyAsStream();
            // 通过对URL的得到文件名
            String filename = path.substring(path.lastIndexOf('/') + 1)
                    + ".html";
            // 获得文件输出流
            output = new FileOutputStream(filename);
            // 输出到文件
            int tempByte = -1;
            while ((tempByte = input.read()) > 0) {
                output.write(tempByte);
            }

            StringBuilder stringBuilder = new StringBuilder();
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(input));
            boolean firstLine = true;
            String line = null; ;
            while((line = bufferedReader.readLine()) != null){
                if(!firstLine){
                    stringBuilder.append(System.getProperty("line.separator"));
                }else{
                    firstLine = false;
                }
                stringBuilder.append(line);
            }
            System.out.println(stringBuilder.toString());
        }
        // 关闭输入流
        if (input != null) {
            input.close();
        }
        // 关闭输出流
        if (output != null) {
            output.close();
        }
        return false;
    }
    public static void main(String[] args) {
        String s = "http://neihanshequ.com";
        System.out.print(s.substring(s.lastIndexOf('/') + 1));
        try {
            // 抓取百度首页，输出
//            Spider.downloadPage(s);
            BfsSpider bfsSpider = new BfsSpider();
            String[] ss = new String[1];
            ss[0]="http://neihanshequ.com";
            bfsSpider.crawling(ss);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
