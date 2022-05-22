package org.example.servlet.servlet;

import org.example.servlet.redis.JedisFactory;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.concurrent.atomic.LongAdder;

// 扩展 HttpServlet 类
public class RedisServlet extends HttpServlet {

    private String message;
    private JedisPool jedis;
    private LongAdder adder = new LongAdder();

    public void init() throws ServletException {
        // 执行必需的初始化
        message = "{\"hello\":\"World\"}";
        jedis = JedisFactory.initJedisPool("default");
        System.out.println("init jedis");
    }

    public void doGet(HttpServletRequest request,
                      HttpServletResponse response)
            throws ServletException, IOException {
        // 设置响应内容类型
        response.setContentType("tapplication/json;charset=UTF-8");
        adder.add(1);

        // 实际的逻辑是在这里
        PrintWriter out = response.getWriter();

//        System.out.println("num: " + adder.longValue() + " active redis:" + jedis.getNumActive());
        Jedis resource = jedis.getResource();
        String msg = resource.get("msg");
        resource.close();
        out.println(msg);
    }

    public void destroy() {
        // 什么也不做
    }
}