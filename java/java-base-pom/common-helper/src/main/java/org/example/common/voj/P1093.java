package org.example.common.voj;

import java.util.Arrays;
import java.util.Scanner;

public class P1093 {

    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        int m = in.nextInt();
        int l = Math.max(m, 4);
        // 最后一次是 1,2,3 级台阶时的走法 总走法
        int[][] Box = new int[l + 1][4];
        Box[1] = new int[]{1, 0, 0, 1};
        Box[2] = new int[]{1, 1, 0, 2};
        Box[3] = new int[]{2, 1, 1, 4};
        for (int j = 4; j <= m; j++) {
            int[] tmp = new int[]{0, 0, 0, 0};
            tmp[0] = Box[j - 1][3];
            tmp[1] = Box[j - 2][3];
            // 最后是 3 需要排除前边的 3
            tmp[2] = Box[j - 3][3];
            for (int i = 1; i <= (j - 3); i++) {
                tmp[2] -= Box[i][2];
            }
            tmp[3] = tmp[0] + tmp[1] + tmp[2];
            Box[j] = tmp;
        }
        for (int i = 1; i < Box.length; i++) {
            System.out.println(i + " : " + Arrays.toString(Box[i]));
        }
        System.out.println(Box[m][3]);
    }
}
