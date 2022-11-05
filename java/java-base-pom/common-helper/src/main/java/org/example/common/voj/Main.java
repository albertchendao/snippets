package org.example.common.voj;

import java.util.Arrays;
import java.util.Comparator;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        int t = Integer.parseInt(in.nextLine());
        String[] count = in.nextLine().split(" ");
        int m = Integer.parseInt(count[0]);
        int n = Integer.parseInt(count[1]);
        int allCount = m + n;
        int mTag = 1;
        int nTag = 2;
        int[][] all = new int[allCount][3];
        for (int i = 1; i <= allCount; i++) {
            int tag = i <= m ? mTag : nTag;
            String[] arr = in.nextLine().split(" ");
            String[] startArr = arr[0].split(":");
            int start = Integer.parseInt(startArr[0]) * 60 + Integer.parseInt(startArr[1]);
            String[] endArr = arr[1].split(":");
            int end = (Integer.parseInt(endArr[0]) * 60 + Integer.parseInt(endArr[1]) + t) % 1440;
            int[] item = {start, end, tag};
            all[i - 1] = item;
        }
        int xMin = 0;
        int xCount = 0;
        int yMin = 0;
        int yCount = 0;
        Comparator<int[]> first = Comparator.comparing(o -> nTag == o[2] ? o[0] : o[1]);
        Comparator<int[]> comparator = first.thenComparingInt(o -> mTag == o[2] ? 0 : 1);
        Arrays.sort(all, comparator);
        for (int[] one : all) {
            yCount += one[2] == nTag ? -1 : +1;
            yMin = Math.min(yMin, yCount);
        }
        first = Comparator.comparing(o -> mTag == o[2] ? o[0] : o[1]);
        comparator = first.thenComparingInt(o -> nTag == o[2] ? 0 : 1);
        Arrays.sort(all, comparator);
        for (int[] one : all) {
            xCount += one[2] == mTag ? -1 : 1;
            xMin = Math.min(xMin, xCount);
        }
        int x = Math.abs(xMin);
        int y = Math.abs(yMin);
        System.out.println(x + " " + y);
    }
}
