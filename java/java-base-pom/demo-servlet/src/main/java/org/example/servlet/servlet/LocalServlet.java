package org.example.servlet.servlet;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.concurrent.atomic.LongAdder;

// 扩展 HttpServlet 类
public class LocalServlet extends HttpServlet {

    private String message;
    private LongAdder adder = new LongAdder();

    public void init() throws ServletException {
        // 执行必需的初始化
        message = "hello";
    }

    public void doGet(HttpServletRequest request,
                      HttpServletResponse response)
            throws ServletException, IOException {
        adder.add(1);
        // 设置响应内容类型
        response.setContentType("tapplication/json;charset=UTF-8");

//        System.out.println("num: " + adder.longValue());
        // 实际的逻辑是在这里
        PrintWriter out = response.getWriter();
        out.println(message);
    }

    public void destroy() {
        // 什么也不做
    }
}