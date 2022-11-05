package org.example.common.voj;

import java.util.Arrays;
import java.util.Comparator;
import java.util.HashSet;
import java.util.Scanner;
import java.util.Set;

public class P1085 {

    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        String[] firstLine = in.nextLine().split(" ");
        int m = Integer.parseInt(firstLine[0]);
        int n = Integer.parseInt(firstLine[1]);

        int[][] all = new int[n][3];
        for (int i = 1; i <= n; i++) {
            String[] arr = in.nextLine().split(" ");
            int day = Integer.parseInt(arr[0]);
            int n1 = Integer.parseInt(arr[1]);
            int n2 = Integer.parseInt(arr[2]);
            int[] item = {day, n1, n2};
            all[i-1] = item;
        }
        int fillDay = -1;
        Set<Integer> fill = new HashSet<>(m);
        Comparator<int[]> first = Comparator.comparing(o -> o[0]);
        Arrays.sort(all, first);
        for (int[] one : all) {
            if (fill.size() == m) {
                fillDay = one[0];
                break;
            }
            fill.add(one[1]);
            fill.add(one[2]);
        }
        if (fill.size() == m && fillDay < 0) {
            fillDay = all[n-1][0] + 1;
        }
        System.out.println(fillDay);
    }
}
