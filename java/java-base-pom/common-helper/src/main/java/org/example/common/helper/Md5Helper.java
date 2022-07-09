package org.example.common.helper;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.apache.commons.codec.digest.DigestUtils;

import java.security.MessageDigest;

/**
 * MD5 工具
 */
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class Md5Helper {

    /**
     * 生成 MD5
     */
    public static String md5(String str) {
        return md5ByJdk(str);
    }

    /**
     * 通过原生 JDK 生成 MD5
     */
    private static String md5ByJdk(String str) {
        if (str == null || str.trim().equals("")) {
            str = "";
        }
        try {
            MessageDigest md5 = MessageDigest.getInstance("MD5");
            md5.update(str.getBytes());
            StringBuffer sb = new StringBuffer();
            String s = null;
            for (byte aByte : md5.digest()) {
                s = Integer.toHexString(0xff & aByte);
                if (s.length() == 1) {
                    sb.append("0" + s);
                } else {
                    sb.append(s);
                }
            }
            return sb.toString();
        } catch (Exception e) {
            return null;
        }
    }


    /**
     * 通过原生 JDK 生成 MD5
     */
    private static String md5ByCodec(String str) {
        return DigestUtils.md5Hex(str);
    }
}
