package org.example.proxy;

public class SayService implements ISay {
    public String say(String m) {
        System.out.println("Service:" + m);
        return "service:" + m;
    }
}
