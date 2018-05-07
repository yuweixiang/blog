package com.blog.spider;

import com.ql.util.express.DefaultContext;
import com.ql.util.express.ExpressRunner;

/**
 * @author yuweixiang
 * @date 2018/03/19
 */
public class QLExpressUtil {

    public static void main(String[] args){
        try {
            ExpressRunner runner = new ExpressRunner();
            DefaultContext<String, Object> context = new DefaultContext<String, Object>();
            context.put("a", 1);
            context.put("b", 2);
            context.put("c", 3);
            String express = "if a > 0 b+c";
            Object r = runner.execute(express, context, null, true, false);
            System.out.println(r);
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}
