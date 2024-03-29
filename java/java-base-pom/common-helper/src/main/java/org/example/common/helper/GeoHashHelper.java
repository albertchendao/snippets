package org.example.common.helper;

import java.util.BitSet;
import java.util.HashMap;

/**
 * GeoHash 精度
 * | geohash长度 | Lat位数 | Lng位数 | Lat误差     | Lng误差     | km误差     |
 * |-----------|-------|-------|-----------|-----------|----------|
 * | 1         | 2     | 3     | ±23       | ±23       | ±2500    |
 * | 2         | 5     | 5     | ± 2.8     | ±5.6      | ±630     |
 * | 3         | 7     | 8     | ± 0.70    | ± 0.7     | ±78      |
 * | 4         | 10    | 10    | ± 0.087   | ± 0.18    | ±20      |
 * | 5         | 12    | 13    | ± 0.022   | ± 0.022   | ±2.4     |
 * | 6         | 15    | 15    | ± 0.0027  | ± 0.0055  | ±0.61    |
 * | 7         | 17    | 18    | ±0.00068  | ±0.00068  | ±0.076   |
 * | 8         | 20    | 20    | ±0.000086 | ±0.000172 | ±0.01911 |
 * | 9         | 22    | 23    | ±0.000021 | ±0.000021 | ±0.00478 |
 */
public class GeoHashHelper {

    private static int numbits = 6 * 5;
    final static char[] digits = {'0', '1', '2', '3', '4', '5', '6', '7',
            '8', '9', 'b', 'c', 'd', 'e', 'f', 'g',
            'h', 'j', 'k', 'm', 'n', 'p', 'q', 'r',
            's', 't', 'u', 'v', 'w', 'x', 'y', 'z'};
    final static HashMap<Character, Integer> lookup = new HashMap<>();

    static {
        int i = 0;
        for (char c : digits)
            lookup.put(c, i++);
    }

    public double[] decode(String geoHash) {
        StringBuilder buffer = new StringBuilder();
        for (char c : geoHash.toCharArray()) {
            int i = lookup.get(c) + 32;
            buffer.append(Integer.toString(i, 2).substring(1));
        }

        BitSet lonset = new BitSet();
        BitSet latset = new BitSet();

        // even bits
        int j = 0;
        for (int i = 0; i < numbits * 2; i += 2) {
            boolean isSet = false;
            if (i < buffer.length())
                isSet = buffer.charAt(i) == '1';
            lonset.set(j++, isSet);
        }

        // odd bits
        j = 0;
        for (int i = 1; i < numbits * 2; i += 2) {
            boolean isSet = false;
            if (i < buffer.length())
                isSet = buffer.charAt(i) == '1';
            latset.set(j++, isSet);
        }

        double lon = decode(lonset, -180, 180);
        double lat = decode(latset, -90, 90);

        return new double[]{lat, lon};
    }

    private double decode(BitSet bs, double floor, double ceiling) {
        double mid = 0;
        for (int i = 0; i < bs.length(); i++) {
            mid = (floor + ceiling) / 2;
            if (bs.get(i))
                floor = mid;
            else
                ceiling = mid;
        }
        return mid;
    }

    public String encode(double lat, double lon) {
        BitSet latbits = getBits(lat, -90, 90);
        BitSet lonbits = getBits(lon, -180, 180);
        StringBuilder buffer = new StringBuilder();
        for (int i = 0; i < numbits; i++) {
            buffer.append(lonbits.get(i) ? '1' : '0');
            buffer.append(latbits.get(i) ? '1' : '0');
        }
        return base32(Long.parseLong(buffer.toString(), 2));
    }

    private BitSet getBits(double d, double floor, double ceiling) {
        BitSet buffer = new BitSet(numbits);
        for (int i = 0; i < numbits; i++) {
            double mid = (floor + ceiling) / 2;
            if (d >= mid) {
                buffer.set(i);
                floor = mid;
            } else {
                ceiling = mid;
            }
        }
        return buffer;
    }

    private static String base32(long i) {
        char[] buf = new char[65];
        int charPos = 64;
        boolean negative = (i < 0);
        if (!negative)
            i = -i;
        while (i <= -32) {
            buf[charPos--] = digits[(int) (-(i % 32))];
            i /= 32;
        }
        buf[charPos] = digits[(int) (-i)];

        if (negative)
            buf[--charPos] = '-';
        return new String(buf, charPos, (65 - charPos));
    }

    public static void main(String[] args) {
//        System.out.println(new GeoHashHelper().encode(经度, 纬度));
    }
}
