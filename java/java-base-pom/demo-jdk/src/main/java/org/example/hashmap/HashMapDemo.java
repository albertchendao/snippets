package org.example.hashmap;

public class HashMapDemo {
    private static int MAXIMUM_CAPACITY = 1 << 30;

    static final int tableSizeFor(int cap) {
        int n = cap - 1;
        System.out.println(toBinaryString(n, 32));
        n |= n >>> 1;
        System.out.println(toBinaryString(n, 32));
        n |= n >>> 2;
        System.out.println(toBinaryString(n, 32));
        n |= n >>> 4;
        System.out.println(toBinaryString(n, 32));
        n |= n >>> 8;
        System.out.println(toBinaryString(n, 32));
        n |= n >>> 16;
        System.out.println(toBinaryString(n, 32));
        return (n < 0) ? 1 : (n >= MAXIMUM_CAPACITY) ? MAXIMUM_CAPACITY : n + 1;
    }

    private static String toBinaryString(int n, int length) {
        String originBinStr = Integer.toBinaryString(n);
        StringBuilder resultBinBuilder = new StringBuilder(originBinStr);
        // 高位补 0
        if (originBinStr.length() < length) {
            int bit = length - originBinStr.length();
            for (int j = 0; j < bit; j++) {
                resultBinBuilder.insert(0, "0");
            }
        }
        return resultBinBuilder.toString();
    }

    public static void main(String[] args) throws Exception {
        System.out.println(tableSizeFor(11));
    }
}
