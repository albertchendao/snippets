package org.example.compiler;

import javax.lang.model.element.Modifier;
import javax.lang.model.element.NestingKind;
import javax.tools.JavaCompiler;
import javax.tools.JavaFileObject;
import javax.tools.StandardJavaFileManager;
import javax.tools.ToolProvider;
import java.io.*;
import java.lang.reflect.Method;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.Arrays;

public class CompilerTest {

    /**
     * 装载字符串成为java可执行文件
     * @param className className
     * @param javaCodes javaCodes
     * @return Class
     */
    private  Class<?> compile(String className, String javaCodes) {
        JavaCompiler compiler = ToolProvider.getSystemJavaCompiler();
        StandardJavaFileManager fileManager = compiler.getStandardFileManager(null,                 null, null);
        StrSrcJavaObject srcObject = new StrSrcJavaObject(className, javaCodes);
        Iterable<? extends JavaFileObject> fileObjects = Arrays.asList(srcObject);
        String flag = "-d";
        String outDir = "";
        try {
            File classPath = new File(Thread.currentThread().getContextClassLoader().getResource("").toURI());
            outDir = classPath.getAbsolutePath() + File.separator;
        } catch (URISyntaxException e1) {
            e1.printStackTrace();
        }
        Iterable<String> options = Arrays.asList(flag, outDir);
        JavaCompiler.CompilationTask task = compiler.getTask(null, fileManager, null, options, null, fileObjects);
        // 编译成功返回 true
        boolean result = task.call();
        if (result == true) {
            try {
                return Class.forName(className);
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
        }
        return null;
    }

private Object run(String method, String codes) {

        String className = "com.test.Eval";
        StringBuilder sb = new StringBuilder();
        sb.append("package com.test;");
        sb.append("\n public class Eval{\n ");
        sb.append(codes);
        sb.append("\n}");

        Class<?> clazz = compile(className, sb.toString());
        try {
            // 生成对象
            Object obj = clazz.newInstance();
            Class<? extends Object> cls = obj.getClass();
            // 调用main方法
            Method m = clazz.getMethod(method, String[].class);
            Object invoke = m.invoke(obj, new Object[]{new String[]{}});
            return invoke;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }

    }

    public static Object eval() {
//        Eval eval = mWeakReference.get();
//        String method = "main";
//        String codes = "public static void main(String[]args){" +
//                "System.out.print(\"hello world\"); }";
//        eval.run(method, codes);
        return null;
    }


    public static void main(String[] args) {
        eval();
    }

    private class StrSrcJavaObject implements JavaFileObject {
        public StrSrcJavaObject(String className, String javaCodes) {
        }

        @Override
        public Kind getKind() {
            return null;
        }

        @Override
        public boolean isNameCompatible(String simpleName, Kind kind) {
            return false;
        }

        @Override
        public NestingKind getNestingKind() {
            return null;
        }

        @Override
        public Modifier getAccessLevel() {
            return null;
        }

        @Override
        public URI toUri() {
            return null;
        }

        @Override
        public String getName() {
            return null;
        }

        @Override
        public InputStream openInputStream() throws IOException {
            return null;
        }

        @Override
        public OutputStream openOutputStream() throws IOException {
            return null;
        }

        @Override
        public Reader openReader(boolean ignoreEncodingErrors) throws IOException {
            return null;
        }

        @Override
        public CharSequence getCharContent(boolean ignoreEncodingErrors) throws IOException {
            return null;
        }

        @Override
        public Writer openWriter() throws IOException {
            return null;
        }

        @Override
        public long getLastModified() {
            return 0;
        }

        @Override
        public boolean delete() {
            return false;
        }
    }
}
