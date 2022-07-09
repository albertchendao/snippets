package org.example.common.helper;

/**
 * 版本比较
 */
public class VersionCompareHelper {

    private static String filterVersion(String v) {
        if(v == null) return v;
        return v.replaceAll("[^.0-9a-zA-Z]", "");
    }

    private int compareVersion(String v1, String v2) {
        if (v1 == null) {
            if (v2 == null) {
                return 0;
            } else {
                return -1;
            }
        } else {
            if (v2 == null) {
                return 1;
            } else {
                String[] v1Arr = v1.split("\\.");
                String[] v2Arr = v2.split("\\.");

                int length = Math.max(v1Arr.length, v2Arr.length);
                for (int i = 0; i < length; i++) {
                    try {
                        int thisPart = i < v1Arr.length ? Integer.parseInt(v1Arr[i]) : 0;
                        int thatPart = i < v2Arr.length ? Integer.parseInt(v2Arr[i]) : 0;
                        if (thisPart < thatPart) return -1;
                        if (thisPart > thatPart) return 1;
                    } catch (Exception e) {
                        return v1.compareTo(v2);
                    }
                }
                return 0;
            }
        }
    }

}
