package org.example.common.voj;

import java.util.Scanner;

public class P1090 {

    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        String s = in.nextLine();
        int result = 0;
        int l = 1;
        int max = s.length() / 2;
        while (l <= max) {
            boolean isMath = false;
            for (int i = 0; i < l; i++) {
                char begin = s.charAt(i);
                char end = s.charAt(s.length() - l + i);
                if (begin != end) {
                    isMath = false;
                    break;
                } else if(i == l - 1) {
                    isMath = true;
                }
            }
            if (isMath) {
                result = Math.max(result, l);
            }
            l++;
        }
        System.out.println(s.substring(0, result));
    }
}
