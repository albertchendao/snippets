package org.example.test.io;

/*
Class.getResourceAsStream 和 ClassLoader.getResourceAsStream

这两个方法还是略有区别的，基本上，两个都可以用于从 classpath 里面进行资源读取，  classpath包含classpath中的路径和classpath中的jar。

两个方法的区别是资源的定义不同， 一个主要用于相对与一个object取资源，而另一个用于取相对于classpath的资源，用的是绝对路径。

在使用Class.getResourceAsStream 时， 资源路径有两种方式， 一种以 / 开头，则这样的路径是指定绝对路径， 如果不以 / 开头， 则路径是相对与这个class所在的包的。

在使用ClassLoader.getResourceAsStream时， 路径直接使用相对于classpath的绝对路径。

举例，下面的三个语句，实际结果是一样的

com.explorers.Test.class.getResourceAsStream("abc.jpg")
com.explorers.Test.class.getResourceAsStream("/com/explorers/abc.jpg")
ClassLoader.getResourceAsStream("com/explorers/abc.jpg")
*/

import java.net.URISyntaxException;

/*
打包到jar包里的资源文件不能通过new File()的形式获取（因为jar包本身就是一个文件，不存在实际的以包名为路径的实际路径），可以使用getResourceAsStream方式获取resource的byte数组，拼成file
*/
public class ResourceStreamTests {

    public static void main(String[] args){
        try {
            System.out.println(ResourceStreamTests.class.getResource("").toURI().getPath());
            System.out.println(ResourceStreamTests.class.getClassLoader().getResource("").toURI().getPath());
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }
    }
}

