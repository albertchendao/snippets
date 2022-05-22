package org.example.mybatis;

import java.util.Deque;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.Set;

public class Main {

// cbacdcbc    cbac  = cba bac   => bac
// bacdcbc     bacdc = bacd badc => bacd
// bacdbc      bacdb = bacd acdb => acdb
// acdbc       acdbc = acdb adbc => acdb

    public static String del(String str) {
        String[] arr = str.split("");
        int len = arr.length;
        Set<String> all = new HashSet<>();
        String dup = "";
        for (String s : arr) {
            if (all.contains(s)) {
                dup = s;
                break;
            }
            all.add(s);
        }
        String r1 = "";
        String r2 = "";
        boolean del = true;
        for (String s : arr) {
            if (s.equals(dup)) {
                if (del) {
                    r1 += s;
                    del = false;
                } else {
                    r2 += s;
                }
            } else {
                r1 += s;
                r2 += s;
            }
        }
        return r1.compareTo(r2) < 0 ? r1 : r2;
    }

    public static String solution(String s) {
        String result = "";
        int len = s.length();
        for (int end = 0; end < len; end++) {
            String sub = s.substring(end, end + 1);
            result += sub;
            result = del(result);
        }
        return result;
    }

    public static void main(String[] args) {
        System.out.println(removeDuplicateLetters("cbacdcbc"));
    }

    public static String removeDuplicateLetters(String s) {
        Deque<Character> stack = new LinkedList<>();

        for(int i = 0; i < s.length(); i++) {
            char c = s.charAt(i);
            // 栈中存在了当前字母，那么就不能再添加进去了
            if(stack.contains(c)) continue;
            // 如果栈顶元素比当前字母大，且后面还会出现该字母，那就弹出它
            while(!stack.isEmpty() && stack.peek() > c && s.indexOf(stack.peek(), i) > 0) {
                stack.pop();
            }
            // 当前字母入栈
            stack.push(c);
        }

        StringBuilder res = new StringBuilder();
        // 将栈中的元素从底部弹出，就是答案了
        while(!stack.isEmpty()) {
            res.append(stack.pollLast());
        }

        return res.toString();
    }
}
