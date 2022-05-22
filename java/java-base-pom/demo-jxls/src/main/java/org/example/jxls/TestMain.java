package org.example.jxls;

import java.io.FileOutputStream;
import java.io.OutputStream;
import java.util.HashMap;
import java.util.Map;

public class TestMain {

    public static void main(String[] args) throws Exception {


        // 模板路径和输出流
        String templatePath = "E:/template.xls";
        OutputStream os = new FileOutputStream("E:/out.xls");
        // 定义一个Map，往里面放入要在模板中显示数据
        Map<String, Object> model = new HashMap<String, Object>();
        model.put("id", "001");
        model.put("name", "张三");
        model.put("age", 18);
        //调用之前写的工具类，传入模板路径，输出流，和装有数据Map
        JxlsUtils.exportExcel(templatePath, os, model);
        os.close();
        System.out.println("完成");
    }
}
