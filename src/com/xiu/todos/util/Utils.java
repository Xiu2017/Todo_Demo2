package com.xiu.todos.util;

import javax.servlet.http.HttpServletResponse;

/**
 * @author xiu
 * @date 2019/1/10 17:12
 */
public class Utils {
    /**
     * 跨域请求设置
     */
    public static void allowCORS(HttpServletResponse response){
        //允许跨域的主机地址
        response.setHeader("Access-Control-Allow-Origin", "*");
        //允许跨域的请求方法GET, POST, HEAD 等
        response.setHeader("Access-Control-Allow-Methods", "*");
        //重新预检验跨域的缓存时间 (s)
        response.setHeader("Access-Control-Max-Age", "3600");
        //允许跨域的请求头
        response.setHeader("Access-Control-Allow-Headers", "*");
        //是否携带cookie
        response.setHeader("Access-Control-Allow-Credentials", "true");
    }
}
